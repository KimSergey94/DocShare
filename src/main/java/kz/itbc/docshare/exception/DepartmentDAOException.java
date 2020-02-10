package kz.itbc.docshare.exception;

public class DepartmentDAOException extends Exception{

    public DepartmentDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public DepartmentDAOException(String message){
        super(message);
    }
}