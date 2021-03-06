package main.java.app.exception;

public class StorageException extends RuntimeException {
    private String uuid;

    public StorageException(final String message, final String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(final String message, Exception e) {
        super(message, e);
    }

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }
}
