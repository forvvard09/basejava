package main.java.app.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(final String uuid) {
        super("Resume " + uuid + " not found in storage", uuid);
    }
}