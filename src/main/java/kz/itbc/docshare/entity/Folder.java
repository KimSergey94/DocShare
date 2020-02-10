package kz.itbc.docshare.entity;

import java.sql.Timestamp;

public class Folder {
    private int id_Folder;
    private String name;
    private boolean flagHidden;
    private boolean flagDeleted;
    private User userCreated;
    private User userDeleted;
    private Timestamp creationDate;
    private boolean flagErased;
    private User userErased;



    public int getId_Folder() {
        return id_Folder;
    }

    public void setId_Folder(int id_Folder) {
        this.id_Folder = id_Folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
