package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract void doSave(Object key, Resume newResume);

    protected abstract Resume getFromStorage(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume newResume);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getPosition(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(final Resume newResume) {
        Object searchKey = getExistedPosition(newResume.getUuid());
        doSave(searchKey, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        Object searchKey = getNotExistedPosition(newResume.getUuid());
        doUpdate(searchKey, newResume);
    }

    @Override
    public void delete(final String uuid) {
        Object searchKey = getNotExistedPosition(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(final String uuid) {
        Object searchKey = getNotExistedPosition(uuid);
        return getFromStorage(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResumes = doCopyAll();
        Collections.sort(listResumes);
        return listResumes;
    }

    private Object getExistedPosition(String uuid) {
        Object searchKey = getPosition(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedPosition(String uuid) {
        Object searchKey = getPosition(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}