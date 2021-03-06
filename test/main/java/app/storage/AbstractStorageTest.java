package main.java.app.storage;

import main.java.Config;
import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.ContactType;
import main.java.app.model.Resume;
import main.java.app.storage.serializer.StreamSerializerStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static main.java.app.storage.ResumeTestData.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    /*protected static final String DB_URL = Config.get().getDbUrl();
    protected static final String DB_USER = Config.get().getDbUser();
    protected static final String DB_PASSWORD = Config.get().getDbPassword();*/

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final ResumeTestData testDataResume = new ResumeTestData();


    protected Storage storage;
    protected StreamSerializerStrategy strategyStream;

    protected AbstractStorageTest(Storage storage) {
        Objects.requireNonNull(storage, "storage must not be null");
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_TWO);
        storage.save(RESUME_ONE);
        storage.save(RESUME_THREE);
    }

    @After
    public void doClean() {
//        storage.clear();
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
        r.setContact(ContactType.SKYPE, "USERS");
        r.setContact(ContactType.PHONE, "+78921022020");
        storage.update((r));
        //Assert.assertSame(r, storage.read(r.getUuid()));
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