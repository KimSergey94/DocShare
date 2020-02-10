package kz.itbc.docshare.exception;

public class FolderDAOException extends Exception {
    public FolderDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public FolderDAOException(String message){
        super(message);
    }
}