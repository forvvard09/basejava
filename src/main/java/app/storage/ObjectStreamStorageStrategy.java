package main.java.app.storage;

import main.java.app.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectStreamStorageStrategy {

    void doWrite(Resume newResume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
