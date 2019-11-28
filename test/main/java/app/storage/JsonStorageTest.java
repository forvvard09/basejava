package main.java.app.storage;

import main.java.app.storage.serializer.JsonStreamSerializer;

public class JsonStorageTest extends AbstractStorageTest {
    public JsonStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}
