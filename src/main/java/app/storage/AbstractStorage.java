package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract void doSave(SK key, Resume newResume);

    protected abstract Resume getFromStorage(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume newResume);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getPosition(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(final Resume newResume) {
        SK searchKey = getExistedPosition(newResume.getUuid());
        doSave(searchKey, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        SK searchKey = getNotExistedPosition(newResume.getUuid());
        doUpdate(searchKey, newResume);
    }

    @Override
    public void delete(final String uuid) {
        SK searchKey = getNotExistedPosition(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(final String uuid) {
        SK searchKey = getNotExistedPosition(uuid);
        return getFromStorage(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResumes = doCopyAll();
        Collections.sort(listResumes);
        return listResumes;
    }

    private SK getExistedPosition(String uuid) {
        SK searchKey = getPosition(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedPosition(String uuid) {
        SK searchKey = getPosition(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}