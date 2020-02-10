package kz.itbc.docshare.entity;

public class Department {
    private int id_Department;
    private int id_ParentDepartment;
    private String phone;
    private boolean flagDeleted;
    private String localeRU;
    private String localeKZ;


    public int getId_Department() {
        return id_Department;
    }

    public void setId_Department(int id_Department) {
        this.id_Department = id_Department;
    }

    public int getId_ParentDepartment() {
        return id_ParentDepartment;
    }

    public void setId_ParentDepartment(int id_ParentDepartment) {
        this.id_ParentDepartment = id_ParentDepartment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFlagDeleted() {
        return flagDeleted;
    }

    public void setFlagDeleted(boolean flagDeleted) {
        this.flagDeleted = flagDeleted;
    }

    public String getLocaleRU() {
        return localeRU;
    }

    public void setLocaleRU(String localeRU) {
        this.localeRU = localeRU;
    }

    public String getLocaleKZ() {
        return localeKZ;
    }

    public void setLocaleKZ(String localeKZ) {
        this.localeKZ = localeKZ;
    }
}
