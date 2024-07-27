package org.githubwuzupkev.exceptions;

public class DuplicatedUserFoundException extends Exception{
    public DuplicatedUserFoundException(String message) {
        super(message);
    }

    public DuplicatedUserFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedUserFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DuplicatedUserFoundException() {
    }

    public DuplicatedUserFoundException(Throwable cause) {
        super(cause);
    }
}
