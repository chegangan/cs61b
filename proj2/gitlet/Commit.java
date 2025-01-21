package gitlet;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private List<String> blobs;
    /**
     * The hash of this Commit.
     */
    private String hash;

    //所有commit的构造方法都会自动生成时间戳
    public Commit() {
        this.timestamp = Instant.now().getEpochSecond();
        this.message = null;
        this.parentCommitId1 = null;
        this.parentCommitId2 = null;
        this.blobs = null;
        this.hash = null;
    }

    public Commit(String message) {
        this.message = message;
        this.timestamp = Instant.now().getEpochSecond();
        this.parentCommitId1 = null;
        this.parentCommitId2 = null;
        this.blobs = null;
        this.hash = null;
    }

    //默认情况下的commit中的blobs为空字符串“”
    public static Commit initCommit() {
        Commit commit = new Commit("initial commit");
        commit.blobs = null;
        commit.parentCommitId1 = null;
        commit.parentCommitId2 = null;
        String data = "";
        commit.hash = Utils.sha1(data);
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

    public List<String> getBlobs() {
        return blobs;
    }

    public void setBlobs(List<String> blobs) {
        this.blobs = blobs;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFormattedTimestamp() {
        Instant instant = Instant.ofEpochSecond(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d yyyy HH:mm:ss Z").
                withZone(ZoneId.systemDefault());
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
                ", file=" + blobs + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    //文件名保存为commit没有赋值hash之前的的hash值。
    public void save() {
        Utils.writeObject(Utils.join(Repository.COMMITS_DIR, this.hash), this);
    }

    // 这里的hash值是commit中的blobs的hash值
    public String Hash() {
        String data = this.blobs.toString();
        return Utils.sha1(data);
    }
}
