package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.exception.StorageException;
import main.java.app.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String TEST_UID = "testUuid";
    private static final Resume RESUME_ONE = new Resume(UUID_1);
    private static final Resume RESUME_TWO = new Resume(UUID_2);
    private static final Resume RESUME_THREE = new Resume(UUID_3);
    private static final Resume RESUME_TEST = new Resume(TEST_UID);


    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_ONE);
        storage.save(RESUME_TWO);
        storage.save(RESUME_THREE);
    }

    @Test
    public void save() {
        storage.save(RESUME_TEST);
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(RESUME_TEST, storage.get(RESUME_TEST.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorage() {
        Resume resume = new Resume(UUID_1);
        storage.save(resume);
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

    @Test
    public void getAll() {
        Resume[] expectedArray = {RESUME_ONE, RESUME_TWO, RESUME_THREE};
        Assert.assertArrayEquals(expectedArray, storage.getAll());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1);
        storage.update((r));
        Assert.assertSame(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_TEST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
        storage.get(UUID_1);

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(TEST_UID);
    }

    @Test
    public void getSize() {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void get() {
        Assert.assertSame(RESUME_ONE, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(TEST_UID);
    }
}