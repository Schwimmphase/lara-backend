package edu.kit.iti.scale.lara.backend.exceptions;

/**
 * This exception is thrown when an element is accessed that is not in the database.
 *
 * @author ukgcc
 */
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
