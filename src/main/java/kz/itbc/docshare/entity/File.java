package kz.itbc.docshare.entity;

import java.io.InputStream;
import java.sql.Timestamp;

public class File {
    private long id_File;
    private String name;
    private InputStream data;
    private boolean flagHidden;
    private boolean flagDeleted;
    private boolean flagErased;
    private int id_FileType;
    private int id_Folder;
    private User userCreated;
    private User userDeleted;
    private User userErased;
    private Timestamp creationDate;
    private byte[] fileData;



    public long getId_File() {
        return id_File;
    }

    public void setId_File(long id_File) {
        this.id_File = id_File;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream data) {
        this.data = data;
    }

    public boolean isFlagHidden() {
        return flagHidden;
    }

    public void setFlagHidden(boolean flagHidden) {
        this.flagHidden = flagHidden;
    }

    public boolean isFlagDeleted() {
        return flagDeleted;
    }

    public void setFlagDeleted(boolean flagDeleted) {
        this.flagDeleted = flagDeleted;
    }

    public int getId_FileType() {
        return id_FileType;
    }

    public void setId_FileType(int id_FileType) {
        this.id_FileType = id_FileType;
    }

    public int getId_Folder() {
        return id_Folder;
    }

    public void setId_Folder(int id_Folder) {
        this.id_Folder = id_Folder;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(User userDeleted) {
        this.userDeleted = userDeleted;
    }

    @Override
    public String toString() {
        return this.getName()+"getName, "+this.getId_Folder()+"getId_Folder, "+this.getId_FileType()+"getId_FileType, "+
                this.isFlagHidden()+"getId_Folder, isFlagHidden"+this.isFlagHidden();
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public User getUserErased() {
        return userErased;
    }

    public void setUserErased(User userErased) {
        this.userErased = userErased;
    }

    public boolean isFlagErased() {
        return flagErased;
    }

    public void setFlagErased(boolean flagErased) {
        this.flagErased = flagErased;
    }
}
