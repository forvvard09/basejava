package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage<T> extends AbstractStorage<T> {

    protected final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(T key, Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int getSize() {
        return mapStorage.size();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }
}
