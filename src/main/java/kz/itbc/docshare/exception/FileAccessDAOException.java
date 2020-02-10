package kz.itbc.docshare.exception;

public class FileAccessDAOException extends Exception {
    public FileAccessDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public FileAccessDAOException(String message){
        super(message);
    }
}