package kz.itbc.docshare.exception;

public class DepartmentToFolderDAOException extends Exception{

    public DepartmentToFolderDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public DepartmentToFolderDAOException(String message){
        super(message);
    }
}
