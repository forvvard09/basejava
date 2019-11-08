package main.java.app.exception;

import java.io.IOException;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(final String message, final String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }
}
