package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.COUNT_ELEMENTS; i++) {
                storage.save(new Resume(String.valueOf(i)));
            }
        } catch (StorageException ste) {
            Assert.fail("Unexpected overflow occurred storage.");
        }
        storage.save(RESUME_TEST);
    }
}