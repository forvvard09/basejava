package main.java.app.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(final String uuid) {
        super("Error. A resume with such " + uuid + " already exists.", uuid);
    }
}
