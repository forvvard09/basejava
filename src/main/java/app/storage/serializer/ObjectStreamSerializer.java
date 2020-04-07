package main.java.app.storage.serializer;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializerStrategy {

    @Override
    public void doWrite(Resume newResume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(newResume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
