package gitlet;

import java.io.Serializable;

public class Branches implements Serializable {
    //head为commitId
    private String head;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Branches(String head, String name) {
        this.head = head;
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    //文件名保存为branch分支的名字。
    public void save() {
        Utils.writeObject(Utils.join(Repository.BRANCHES_DIR, name), this);
    }

}
