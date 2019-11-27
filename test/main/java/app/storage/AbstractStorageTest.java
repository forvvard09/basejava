package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = new File("./src/main/java/app/resumes");
    protected Storage storage;
    protected  ObjectStreamStorageStrategy strategyStream;

    protected static final ResumeTestData testDataResume = new ResumeTestData();
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String TEST_UID = "testUuid";
    private static final Resume RESUME_ONE;
    private static final Resume RESUME_TWO;
    private static final Resume RESUME_THREE;
    protected static final Resume RESUME_TEST;

    static {
        //resume 1
        RESUME_ONE = testDataResume.fillResume(UUID_1, "Name1");
        //resume 2
        RESUME_TWO = testDataResume.fillResume(UUID_2, "Name2");
        //resume 3
        RESUME_THREE = testDataResume.fillResume(UUID_3, "Name3");
        //resume 4
        RESUME_TEST = testDataResume.fillResume(TEST_UID, "NameTest");
    }

    protected AbstractStorageTest(Storage storage) {
        Objects.requireNonNull(storage, "storage must not be null");
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.save(RESUME_ONE);
        storage.save(RESUME_TWO);
        storage.save(RESUME_THREE);
    }

    @After
    public void doClean() {
        storage.clear();
    }

    @Test
    public void save() {
        storage.save(RESUME_TEST);
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(RESUME_TEST, storage.get(RESUME_TEST.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorage() {
        storage.save(RESUME_ONE);
    }

    @Test
    public void getAll() {
        List<Resume> expectedList = Arrays.asList(RESUME_ONE, RESUME_TWO, RESUME_THREE);
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
        //Assert.assertSame(r, storage.get(r.getUuid()));
        assertEquals(r, storage.get(UUID_1));
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
        Assert.assertEquals(RESUME_ONE, storage.get(UUID_1));

    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(TEST_UID);
    }
}