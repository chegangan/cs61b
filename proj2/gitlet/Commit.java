package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static gitlet.Utils.writeObject;

/**
 * Represents a gitlet commit object.
 * 这个类表示一个gitlet提交对象。
 * does at a high level.
 *
 * @author chegan
 */
public class Commit implements Serializable, Dumpable {

    /**
     * The message of this Commit.
     */
    private String message;
    /**
     * The timestamp of this Commit.
     * 这个在save的时候自动赋值
     */
    private long timestamp;
    /**
     * The parent of this Commit.
     */
    private String parentCommitId1;
    private String parentCommitId2;
    /**
     * The file of this Commit.
     */
    private List<String[]> blobNameHashList = new ArrayList<>();
    /**
     * The hash of this Commit.
     * 这个在save的时候自动赋值为blobNameHashList.toString的hash值
     */
    private String hash;

    //所有commit的构造方法都会自动生成时间戳
    //当save commit时，会自动生成hash值和时间戳
    public Commit() {
        this.message = null;
        this.parentCommitId1 = null;
        this.parentCommitId2 = null;
        this.hash = null;
    }

    public Commit(String message) {
        this.message = message;
        this.parentCommitId1 = null;
        this.parentCommitId2 = null;
        this.hash = null;
    }

    //默认情况下的commit中的blobs为空字符串“”
    public static Commit initCommit() {
        Commit commit = new Commit("initial commit");
        commit.parentCommitId1 = null;
        commit.parentCommitId2 = null;
        return commit;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getParentCommitId1() {
        return parentCommitId1;
    }

    public void setParentCommitId1(String parentCommitId1) {
        this.parentCommitId1 = parentCommitId1;
    }

    public String getParentCommitId2() {
        return parentCommitId2;
    }

    public void setParentCommitId2(String parentCommitId2) {
        this.parentCommitId2 = parentCommitId2;
    }

    public List<String[]> getBlobNameHashList() {
        return blobNameHashList;
    }

    public void setBlobNameHashList(List<String[]> blobNameHashList) {
        this.blobNameHashList = blobNameHashList;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFormattedTimestamp() {
        Instant instant = Instant.ofEpochSecond(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy XXX", Locale.US)
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    @Override
    public void dump() {
        System.out.println(this.toString());
    }


    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", parentCommitId1='" + parentCommitId1 + '\'' +
                ", parentCommitId2='" + parentCommitId2 + '\'' +
                ", file=" + blobNameHashList + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

        //自动生成当前的hash值和时间戳，保存到commits文件夹中，名字为hash值，并将HEAD指向当前commit,branch的head指向当前commit
    public void save() {
        this.hash = this.Hash();
        this.timestamp = Instant.now().getEpochSecond();
        Utils.writeObject(Utils.join(Repository.COMMITS_DIR, this.hash), this);
        writeObject(Repository.HEAD, this);
        //将当前分支的head指向当前commit
        if(Repository.CURRENT_BRANCH.exists()) {
            Branches branch = Utils.readObject(Repository.CURRENT_BRANCH, Branches.class);
            branch.setHead(this.hash);
            branch.save();
        }else{
            Branches branch = new Branches(this.hash, "master");
            branch.save();
        }
    }

    // 这里的hash值是commit中的blobs的hash值
    public String Hash() {
        String data = this.blobNameHashList.toString();
        return Utils.sha1(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commit commit = (Commit) o;
        return timestamp == commit.timestamp && Objects.equals(message, commit.message) && Objects.equals(parentCommitId1, commit.parentCommitId1) && Objects.equals(parentCommitId2, commit.parentCommitId2) && Objects.equals(blobNameHashList, commit.blobNameHashList) && Objects.equals(hash, commit.hash);
    }

}
