package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;
import main.java.app.storage.serializer.StreamSerializerStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private StreamSerializerStrategy streamSerializerStrategy;

    protected FileStorage(File directory, StreamSerializerStrategy streamSerializerStrategy) {
        Objects.requireNonNull(streamSerializerStrategy, "streamSerializerStrategy must not be null");
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializerStrategy = streamSerializerStrategy;
    }

    @Override
    protected void doSave(File file, Resume newResume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(file, newResume);
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return streamSerializerStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Error read file", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(File file, Resume newResume) {
        try {
            streamSerializerStrategy.doWrite(newResume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", newResume.getUuid(), e);
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
        List<Resume> listResume = new ArrayList<>();
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    try {
                        listResume.add(streamSerializerStrategy.doRead(new BufferedInputStream(new FileInputStream(file) {
                        })));
                    } catch (IOException e) {
                        throw new StorageException("Error read file", file.getName(), e);
                    }
                }
            }
        } else {
            throw new StorageException("folder does not contain resume files. Folder: ", directory.getAbsolutePath());
        }
        return listResume;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    if (!file.delete()) {
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
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    countFiles++;
                }
            }
        } else {
            throw new StorageException("Folder haven't of resume.", directory.getName());
        }
        return countFiles;
    }
}
