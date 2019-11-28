package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;
import main.java.app.storage.serializer.StreamSerializerStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private StreamSerializerStrategy streamSerializerStrategy;

    protected PathStorage(String dir, StreamSerializerStrategy streamSerializerStrategy) {
        Objects.requireNonNull(streamSerializerStrategy, "streamSerializerStrategy must not be null");
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writeble");
        }
        this.streamSerializerStrategy = streamSerializerStrategy;
    }

    @Override
    protected void doSave(Path path, Resume newResume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path: " + path, getFileName(path), e);
        }
        doUpdate(path, newResume);
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return streamSerializerStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Error read file", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume newResume) {
        try {
            streamSerializerStrategy.doWrite(newResume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error remove file", getFileName(path), e);
        }
    }

    @Override
    protected Path getPosition(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        //return Files.exists(file);
        return Files.isRegularFile(path);

    }

    @Override
    protected List<Resume> doCopyAll() {
        if ((int) getFilesList().count() > 0) {
            return getFilesList().map(this::getFromStorage).collect(Collectors.toList());
        } else {
            throw new StorageException("Folder has not file", getFileName(directory));
        }
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
            /*
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            list.forEach(this::doDelete);
             */
    }

    @Override
    public int getSize() {
        return (int) getFilesList().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory).filter(p -> !Files.isDirectory(p));
        } catch (IOException e) {
            throw new StorageException("Directory read error", getFileName(directory), e);
        }
    }
}