package gitlet;

import java.io.File;
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
        writeObject(HEAD, initialCommit);
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
        List<String> blobList = commit.getBlobs();
        File stagingBlobFile = Utils.join(Repository.STAGING_DIR, fileName);

        if (blobList != null && blobList.contains(blob.getHash())) {
            stagingBlobFile.delete();
        }
//        4. 最坏时间复杂度为lgn，n为commit中的文件数量
//        5. 失败案例：如果文件不存在，
//        打印错误信息(File does not exist.) 然后不做任何改变就退出。
//        6. 大概20行
//        7. 一次只能add一个文件，而git可以一次性add多个文件
//        8. 执行rm命令不要分阶段进行，而是直接删除
    }
}
