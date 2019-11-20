package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if(!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writeble");
        }
        this.directory = directory;
    }

    @Override
    protected void doSave(Path file, Resume newResume) {
        //todo
        doUpdate(file, newResume);
    }

    protected abstract void doWrite(Resume newResume, OutputStream os) throws IOException;

    @Override
    protected Resume getFromStorage(Path file) {
        //todo
            //return doRead(new BufferedInputStream(new FileInputStream(file)));
        return null;

    }

    protected abstract Resume doRead(InputStream file) throws IOException;

    @Override
    protected void doUpdate(Path file, Resume newResume) {
        //todo
    }

    @Override
    protected void doDelete(Path file) {
        //todo
    }

    @Override
    protected Path getPosition(String uuid) {
        //todo
        return null;
    }

    @Override
    protected boolean isExist(Path file) {
        //todo
        return false;
    }


    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = new ArrayList<>();
       //todo
        return listResume;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int getSize() {
        //todo
        return 0;
    }
}
