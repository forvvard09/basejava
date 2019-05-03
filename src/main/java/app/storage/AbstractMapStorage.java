package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected final Map<Object, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int getSize() {
        return mapStorage.size();
    }


    @Override
    protected List<Resume> doGetListResume() {
        return new ArrayList<>(mapStorage.values());
    }
}
