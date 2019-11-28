package main.java.app.storage;

import main.java.app.serializer.XmlStreamSerializer;

public class XmlStorageTest extends AbstractStorageTest {
    public XmlStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}
