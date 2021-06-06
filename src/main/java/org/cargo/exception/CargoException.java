package org.cargo.exception;

public class CargoException extends Exception {

    public CargoException(String message) {
        super(message);
    }

    public CargoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CargoException(Throwable cause) {
        super(cause);
    }
}
