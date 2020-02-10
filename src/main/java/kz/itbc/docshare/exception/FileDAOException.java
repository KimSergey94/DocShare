package kz.itbc.docshare.exception;

public class FileDAOException extends Exception {
    public FileDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public FileDAOException(String message){
        super(message);
    }
}
