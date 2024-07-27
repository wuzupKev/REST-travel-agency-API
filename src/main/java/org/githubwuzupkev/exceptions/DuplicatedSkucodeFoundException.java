package org.githubwuzupkev.exceptions;

public class DuplicatedSkucodeFoundException extends Exception{
    public DuplicatedSkucodeFoundException() {
    }

    public DuplicatedSkucodeFoundException(String message) {
        super(message);
    }

    public DuplicatedSkucodeFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedSkucodeFoundException(Throwable cause) {
        super(cause);
    }

    public DuplicatedSkucodeFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
