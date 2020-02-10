package kz.itbc.docshare.entity;

public class FileType {
    private int id_FileType;
    private boolean flagDeleted;
    private String name;


    public int getId_FileType() {
        return id_FileType;
    }

    public void setId_FileType(int id_FileType) {
        this.id_FileType = id_FileType;
    }

    public boolean isFlagDeleted() {
        return flagDeleted;
    }

    public void setFlagDeleted(boolean flagDeleted) {
        this.flagDeleted = flagDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
