package org.ivan_smirnov.orm.exception;

public class WrongAnnotationException extends RuntimeException {
    public WrongAnnotationException(String message) {
        super(message);
    }
}
