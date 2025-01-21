package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable ,Dumpable{
    private String hash;
    private byte[] content;
    private String fileName;

    //blob的hash只跟他的content有关
    public Blob(File file) {
        this.fileName = file.getName();
        this.content = Utils.readContents(file);
        this.hash = Utils.sha1(content);
    }

    //同时保存blob的hash和name到staging文件夹中，保存blob对象到blobs文件夹中
    public void save() {
        //将blob的name和hash写入staging文件夹中，如果文件已经存在，就覆盖该文件
        File blobFile1 = Utils.join(Repository.STAGING_DIR, this.fileName);
        Utils.writeContents(blobFile1, this.hash);

        //序列化blob对象到blobs文件夹中，如果文件已经存在，就不写入
        File blobFile2 = Utils.join(Repository.BLOBS_DIR, this.hash);
        if (!blobFile2.exists()) {
            Utils.writeObject(blobFile2, this);
        }
    }

    public String getHash() {
        return hash;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public static Blob getBlob(String dir, String hash) {
        File blobFile = Utils.join(dir, hash);
        if (!blobFile.exists()) {
            return null;
        }
        Blob blob = Utils.readObject(blobFile, Blob.class);
        return blob;
    }


    @Override
    public void dump() {
        System.out.println("Blob{" +
                "hash='" + hash + '\'' +
                ", content=" + new String(content) +
                ", fileName='" + fileName + '\'' +
                '}');
    }
}
