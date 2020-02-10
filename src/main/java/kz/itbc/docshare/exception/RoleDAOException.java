package kz.itbc.docshare.exception;

public class RoleDAOException extends Exception {
    public RoleDAOException(String message) {
        super(message);
    }

    public RoleDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
