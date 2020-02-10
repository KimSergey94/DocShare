package kz.itbc.docshare.entity;

public class Role {
    private int id;
    private boolean isDeleted;
    private String localeRU;
    private String localeKZ;

    public void setId(int id) {
        this.id = id;
    }

    public void setLocaleRU(String localeRU) {
        this.localeRU = localeRU;
    }

    public void setLocaleKZ(String localeKZ) {
        this.localeKZ = localeKZ;
    }

    public int getId() {
        return id;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getLocaleRU() {
        return localeRU;
    }

    public String getLocaleKZ() {
        return localeKZ;
    }
}
