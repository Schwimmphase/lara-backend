package edu.kit.iti.scale.lara.backend.exceptions;

public class NotInDataBaseException extends Exception {

    public NotInDataBaseException(String message) {
        super(message);
    }

    public NotInDataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInDataBaseException(Throwable cause) {
        super(cause);
    }

    public NotInDataBaseException() {
        super();
    }

}
