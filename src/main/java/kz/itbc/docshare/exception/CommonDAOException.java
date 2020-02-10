package kz.itbc.docshare.exception;

public class CommonDAOException extends Exception{

    public CommonDAOException(String message, Throwable cause){
        super(message, cause);
    }

    public CommonDAOException(String message){
        super(message);
    }
}