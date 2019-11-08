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

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String TEST_UID = "testUuid";

    private static final Resume RESUME_ONE;
    private static final Resume RESUME_TWO;
    private static final Resume RESUME_THREE;
    protected static final Resume RESUME_TEST;

    private static final ResumeTestData testDataResume = new ResumeTestData();

    static  {
        //resume 1
        RESUME_ONE = new Resume(UUID_1, "Name1");
        testDataResume.setSectionContacts(RESUME_ONE);
        testDataResume.setSectionPersonal(RESUME_ONE);
        testDataResume.setSectionQualifications(RESUME_ONE);
        testDataResume.setSectionAchievement(RESUME_ONE);
        testDataResume.setSectionEducation(RESUME_ONE);
        testDataResume.setSectionExperience(RESUME_ONE);

        //resume 2
        RESUME_TWO = new Resume(UUID_2, "Name2");
        testDataResume.setContactEmeil(RESUME_TWO);
        testDataResume.setSectionPersonal(RESUME_TWO);
        testDataResume.setSectionQualifications(RESUME_TWO);
        testDataResume.setSectionExperience(RESUME_TWO);

        //resume 3
        RESUME_THREE = new Resume(UUID_3, "Name3");
        testDataResume.setContactSkype(RESUME_THREE);
        testDataResume.setContactPhone(RESUME_THREE);
        testDataResume.setSectionExperience(RESUME_THREE);

        //resume 4
        RESUME_TEST = new Resume(TEST_UID, "NameTest");
        testDataResume.setSectionPersonal(RESUME_TEST);
        testDataResume.setSectionExperience(RESUME_TEST);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_TWO);
        storage.save(RESUME_ONE);
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