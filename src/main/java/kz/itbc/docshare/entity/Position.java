package kz.itbc.docshare.entity;

public class Position implements Comparable {
    private int id;
    private Position parentPosition;
    private boolean isDeleted;
    private String LocaleRU;
    private String LocaleKZ;

    public String getLocaleRU() {
        return LocaleRU;
    }

    public String getLocaleKZ() {
        return LocaleKZ;
    }

    public void setLocaleRU(String localeRU) {
        LocaleRU = localeRU;
    }

    public void setLocaleKZ(String localeKZ) {
        LocaleKZ = localeKZ;
    }

    public int getId() {
        return id;
    }

    public Position getParentPosition() {
        return parentPosition;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentPosition(Position parentPosition) {
        this.parentPosition = parentPosition;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public int compareTo(Object o) {
        return this.getLocaleRU().compareTo(((Position) o).getLocaleRU());
    }
}
