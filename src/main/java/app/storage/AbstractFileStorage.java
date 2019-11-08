package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doSave(File file, Resume newResume) {
        try {
            file.createNewFile();
            doWrite(newResume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume newResume, File file) throws IOException;

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Error read file", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void doUpdate(File file, Resume newResume) {
        try {
            doWrite(newResume, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Error remove file", file.getName());
        }
    }

    @Override
    protected File getPosition(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return null;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file: listFiles) {
                if (!file.isDirectory()) {
                    if(!file.delete()) {
                       throw new StorageException("Error remove file", file.getName());
                    }
                }
            }

        }

    }

    @Override
    public int getSize() {
        int countFiles = 0;
        File[] listFiles = directory.listFiles();
        for (File file: listFiles) {
            if(!file.isDirectory()) {
                countFiles++;
            }
        }
        return countFiles;
    }
}
