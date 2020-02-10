package kz.itbc.docshare.entity;

public class CurrentPage {
    private int id;
    private boolean isDepartment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDepartment() {
        return isDepartment;
    }

    public void setDepartment(boolean department) {
        isDepartment = department;
    }
}
