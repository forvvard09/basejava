package main.java.app.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapKeyUuidStorageTest.class,
        MapSearchKeyResumeStorageTest.class
})
public class AllStorageTest {

}
