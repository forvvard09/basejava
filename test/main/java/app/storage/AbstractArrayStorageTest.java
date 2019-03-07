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
    private Resume r;


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        r = new Resume(TEST_UID);
    }

    @Test
    public void save() {
        Assert.assertEquals(3, storage.getSize());
        storage.save(r);
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorage() {
        Resume resume = new Resume(UUID_1);
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        int freeStorage = 10_000 - storage.getSize();
        for (int i = 0; i < freeStorage; i++) {
            try {
                storage.save(new Resume(String.valueOf(i)));
            } catch (StorageException ste) {
                Assert.fail("Unexpected overflow occurred storage.");
            }
        }
        storage.save(new Resume());
    }

    @Test
    public void getAll() {
        Resume[] expectedArray = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(expectedArray, storage.getAll());
    }

    @Test
    public void clear() {
        Assert.assertEquals(3, storage.getSize());
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1);
        storage.update((resume));
        Assert.assertEquals(3, storage.getSize());
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("testUuid"));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("testUuid");
    }

    @Test
    public void getSize() {
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void get() {
        Resume r = new Resume("test_uuid");
        storage.save(r);
        Assert.assertSame(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}