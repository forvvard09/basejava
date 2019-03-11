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
    private Resume resumeOne = new Resume(UUID_1);
    private Resume resumeTwo = new Resume(UUID_2);
    private Resume resumeThree = new Resume(UUID_3);
    private Resume resumeTest = new Resume(TEST_UID);


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resumeOne);
        storage.save(resumeTwo);
        storage.save(resumeThree);
    }

    @Test
    public void save() {
        storage.save(resumeTest);
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(resumeTest, storage.get(resumeTest.getUuid()));
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
        storage.save(resumeTest);
    }

    @Test
    public void getAll() {
        Resume[] expectedArray = {resumeOne, resumeTwo, resumeThree};
        Assert.assertArrayEquals(expectedArray, storage.getAll());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() {
        storage.update((resumeOne));
        Assert.assertSame(resumeOne, storage.get(resumeOne.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resumeTest);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(resumeOne.getUuid());
        Assert.assertSame(resumeOne, storage.get(resumeOne.getUuid()));
    }

    @Test
    public void getSize() {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void get() {
        storage.save(resumeTest);
        Assert.assertSame(resumeTest, storage.get(resumeTest.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(TEST_UID);
    }
}