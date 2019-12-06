package main.java.app.exception;

public class ResumeException extends StorageException {
    public ResumeException(final String message, Exception e) {
        super(message, e);
    }
}
