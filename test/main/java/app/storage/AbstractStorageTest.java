package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static main.java.app.storage.ResumeTestData.*;
import static main.java.app.storage.ResumeTestData.TEST_UID;

public abstract class AbstractStorageTest {

    protected Storage storage;
    protected static final ResumeTestData testDataResume = new ResumeTestData();
    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(testDataResume.getResumeTwo());
        storage.save(testDataResume.getResumeOne());
        storage.save(testDataResume.getResumeThree());
    }

    @Test
    public void save() {
        storage.save(testDataResume.getResumeTest());
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(testDataResume.getResumeTest(), storage.get(testDataResume.getResumeTest().getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorage() {
        storage.save(testDataResume.getResumeOne());
    }

    @Test
    public void getAll() {
        List<Resume> expectedList = Arrays.asList(testDataResume.getResumeOne(), testDataResume.getResumeTwo(), testDataResume.getResumeThree());
        Collections.sort(expectedList);
        Assert.assertEquals(expectedList, storage.getAllSorted());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_1, "TEST_NAME");
        storage.update((r));
        Assert.assertSame(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(testDataResume.getResumeTest());
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
        Assert.assertSame(testDataResume.getResumeOne(), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(TEST_UID);
    }
}