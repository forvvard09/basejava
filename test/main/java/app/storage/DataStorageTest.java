package main.java.app.storage;

import main.java.app.storage.serializer.DataStreamSerializer;

public class DataStorageTest extends AbstractStorageTest {
    public DataStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}
