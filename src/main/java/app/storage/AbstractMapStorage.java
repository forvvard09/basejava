package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected final Map<Object, Resume> mapStorage = new HashMap<>();

    protected abstract void doSaveMapStorage(Resume newResume);
    protected abstract boolean doIsExistMap(Object key);
    protected abstract Object doGetPosition(String key);
    protected abstract void doDoUpdateMap(Resume newResume);
    protected abstract Resume doGetFromStorageMap(Object key);
    protected abstract void doDeleteMap(Object key);


    @Override
    protected void doSave(final Object position, final Resume newResume) {
        doSaveMapStorage(newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return doGetFromStorageMap(position);
    }

    @Override
    protected void doUpdate(final Object position, final Resume newResume) {
        doDoUpdateMap(newResume);
    }

    @Override
    protected void doDelete(final Object position) {
        doDeleteMap(position);
    }

    @Override
    protected final Object getPosition(final String position) {
        return doGetPosition(position);
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
    public List<Resume> getAllSorted() {
        List<Resume> listResumes = new ArrayList<Resume>(mapStorage.values());
        Collections.sort(listResumes);
        return listResumes;
    }

    @Override
    protected boolean isExist(final Object key) {
        return doIsExistMap(key);
    }
}
