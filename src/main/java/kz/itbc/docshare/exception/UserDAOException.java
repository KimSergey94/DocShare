package kz.itbc.docshare.exception;

public class UserDAOException extends Exception {
    public UserDAOException(String message) {
        super(message);
    }

    public UserDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
