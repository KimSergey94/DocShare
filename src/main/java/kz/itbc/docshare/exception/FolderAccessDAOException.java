package kz.itbc.docshare.exception;

public class FolderAccessDAOException extends Exception {
    public FolderAccessDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public FolderAccessDAOException(String message){
        super(message);
    }
}