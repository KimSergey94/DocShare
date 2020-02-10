package kz.itbc.docshare.exception;

public class UserToFolderDAOException extends Exception{

    public UserToFolderDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public UserToFolderDAOException(String message){
        super(message);
    }
}
