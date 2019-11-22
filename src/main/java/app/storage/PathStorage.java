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
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private ObjectStreamStorageStrategy strategyStream;

    protected PathStorage(String dir, ObjectStreamStorageStrategy strategyStream) {
        Objects.requireNonNull(strategyStream, "strategyStream must not be null");
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writeble");
        }
        this.strategyStream = strategyStream;
    }

    @Override
    protected void doSave(Path file, Resume newResume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.toString(), e);
        }
        doUpdate(file, newResume);
    }

    @Override
    protected Resume getFromStorage(Path file) {
        try {
            return strategyStream.doRead(new BufferedInputStream(new FileInputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("Error read file", file.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path file, Resume newResume) {
        try {
            strategyStream.doWrite(newResume, new BufferedOutputStream(new FileOutputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("File write error", newResume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Error remove file", file.getFileName().toString());
        }
    }

    @Override
    protected Path getPosition(String uuid) {
        Path pathResume = directory.resolve(uuid);
        return pathResume;
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = new ArrayList<>();
        List<Path> listPathFilesResume = null;
        try {
            listPathFilesResume = Files.list(directory).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error by get list files from folder.", directory.toString());
        }
        if (listPathFilesResume.size() > 0) {
            listPathFilesResume.forEach(pathFileResume -> {
                try {
                    listResume.add(strategyStream.doRead(new BufferedInputStream(new FileInputStream(pathFileResume.toFile()))));
                } catch (IOException e) {
                    throw new StorageException("Error read file", pathFileResume.getFileName().toString(), e);
                }

            });
        } else {
            throw new StorageException("Folder has not file", directory.toString());
        }
        return listResume;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
            /*
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            list.forEach(this::doDelete);
             */
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int getSize() {
        int countResumes = 0;
        try {
            countResumes = (int) Files.list(directory).filter(p -> !Files.isDirectory(p)).count();
        } catch (IOException e) {
            throw new StorageException("Folder hasn't of resume.", directory.toString());
        }
        return countResumes;
    }
}