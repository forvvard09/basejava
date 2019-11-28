package main.java.app.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapKeyUuidStorageTest.class,
        MapSearchKeyResumeStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XmlStorageTest.class,
        JsonStorageTest.class
})
public class AllStorageTest {

}
