package gitlet;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 * 用来储存一些全局变量和方法
 * does at a high level.
 *
 * @author chegan
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * 文件夹的样式
     * --.gitlet
     * --commits
     * --staging
     * --blobs
     * --refs
     * --HEAD
     * --branches
     * --master
     */

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(GITLET_DIR, "commits");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File BLOBS_DIR = join(GITLET_DIR, "blobs");
    /**
     * The .gitlet/refs/HEAD directory.
     * 这里的HEAD指向当前的commit，当切换分支时，HEAD会指向不同分支的head，也就是其他分支的最新提交
     * HEAD中储存的是commit的序列化对象
     */
    public static final File HEAD = join(REFS_DIR, "HEAD");
    public static final File BRANCHES_DIR = join(REFS_DIR, "branches");
    public static final File MASTER_DIR = join(BRANCHES_DIR, "master");

    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        GITLET_DIR.mkdir();
        COMMITS_DIR.mkdir();
        STAGING_DIR.mkdir();
        REFS_DIR.mkdir();
        MASTER_DIR.mkdir();
        BRANCHES_DIR.mkdir();
        BLOBS_DIR.mkdir();

        //下面应该是创建一个新的commit，第一个commit
        Commit initialCommit = Commit.initCommit();
        //将这个commit保存到commits文件夹中
        initialCommit.save();
        //创建master指针指向第一个commit
        Branches master = new Branches(initialCommit.getHash(), "master");
        //保存branch指针
        master.save();
        //HEAD即为commit的序列化对象

    }


    public static void add(String fileName) {
//        1. 创建目录需要add的文件的副本
        File file = join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        Blob blob = new Blob(file);

        //同时保存blob的hash和name到staging文件夹中，保存blob对象到blobs文件夹中
        blob.save();

        //如果文件和当前commit中指向的blob一样，那么删除这个文件，即blob的hash值相同时
        Commit commit = Utils.readObject(Repository.HEAD, Commit.class);
        List<String[]> blobNameHashList = commit.getBlobNameHashList();
        File stagingBlobFile = Utils.join(Repository.STAGING_DIR, fileName);

        if (blobNameHashList.stream().anyMatch(vector -> vector[1].equals(blob.getHash()))) {
            stagingBlobFile.delete();
        }
//        4. 最坏时间复杂度为lgn，n为commit中的文件数量
//        5. 失败案例：如果文件不存在，
//        打印错误信息(File does not exist.) 然后不做任何改变就退出。
//        6. 大概20行
//        7. 一次只能add一个文件，而git可以一次性add多个文件
//        8. 执行rm命令不要分阶段进行，而是直接删除
    }

    @SuppressWarnings("DataFlowIssue")
    public static void commit(String message) {
        if(message == null){
            System.out.println("Please enter a commit message.");
            return;
        }
//        克隆前一个commit
        Commit commit = Utils.readObject(Repository.HEAD, Commit.class);
//        如果暂存区没有文件，则为默认情况，commit与前一个commit相同
        File[] stagingFileList = Repository.STAGING_DIR.listFiles();
        if (stagingFileList.length == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
//        修改commit 的parent commit 和message
        commit.setMessage(message);
        commit.setParentCommitId1(commit.getHash());

//        比较暂存区和前一个commit的文件的内容，如果文件不同，则更新commit 也就是更新commit中那个名字的文件所对应的哈希值，更新哈希值
//        如果暂存区中的文件commit中没有，则添加到commit中
//        如果commit中的文件暂存区没有，在commit中删除这个文件。
        List<String[]> blobNameHashList = commit.getBlobNameHashList();
        List<String[]> stagingBlobNameHashList = new ArrayList<>();
        //这里的fileName为blobname，内容为blobhash。
        for (File file : stagingFileList) {
            String nameInFile = file.getName();
            String hashInFile = Utils.readContentsAsString(file);
            stagingBlobNameHashList.add(new String[]{nameInFile, hashInFile});
        }
        List<String[]> mergedList = compareAndMergeLists(blobNameHashList, stagingBlobNameHashList);
        commit.setBlobNameHashList(mergedList);
//        将commit储存在commit树中，commit中指向他的父节点
//                将head指针指向当前commit文件
        commit.save();
//        提交后删除暂存区中的文件
        for (File file : stagingFileList) {
            if (!file.delete()) {
                System.out.println("Failed to delete file: " + file.getName());
            }
        }
//        运行时间不超过文件大小的线性，且与文件数量无关
//        错误： 如果没有文件被暂存，终止程序，并打印 （No changes added to the commit.) 如果没有message，终止程序，打印（Please enter a commit message.）
//        与git的区别：git中可能有多个父节点的文件
//                大概35行
    }

    public static List<String[]> compareAndMergeLists(List<String[]> list1, List<String[]> list2) {
        // 创建一个新的列表，避免直接修改 list1 导致副作用
        List<String[]> mergedList = new ArrayList<>(list1);

        // 使用迭代器遍历 list1，方便删除元素
        Iterator<String[]> iterator1 = mergedList.iterator();
        while (iterator1.hasNext()) {
            String[] item1 = iterator1.next();
            String name1 = item1[0];

            boolean foundInList2 = false;
            for (String[] item2 : list2) {
                String name2 = item2[0];
                if (name1.equals(name2)) {
                    foundInList2 = true;
                    if (!item1[1].equals(item2[1])) {
                        // name 相同，hash 不同，替换 list1 中的 hash
                        item1[1] = item2[1];
                    }
                    break; // 找到匹配的 name 后，跳出内层循环
                }
            }
            if (!foundInList2) {
                // list1 中有这个 name，但 list2 中没有，删除 list1 中的这对
                iterator1.remove();
            }
        }

        // 遍历 list2，添加 list1 中没有的 name-hash 对
        for (String[] item2 : list2) {
            String name2 = item2[0];
            boolean foundInMergedList = false;
            for (String[] item1 : mergedList) {
                String name1 = item1[0];
                if (name2.equals(name1)) {
                    foundInMergedList = true;
                    break;
                }
            }
            if (!foundInMergedList) {
                // list1 中没有这个 name，添加 list2 中的这对
                mergedList.add(item2.clone()); // 重要：使用 clone() 创建副本，避免修改原始 list2
            }
        }

        return mergedList;
    }


}
