package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void doSave(Object position, Resume newResume);

    protected abstract Resume getFromStorage(Object position);

    protected abstract void doUpdate(Object position, Resume newResume);

    protected abstract void doDelete(Object position);

    protected abstract Object getPosition(String uuid);

    protected abstract boolean isExist(Object key);

    @Override
    public void save(final Resume newResume) {
        Object position = getExistedPosition(newResume.getUuid());
        doSave(position, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        Object position = getNotExistedPosition(newResume.getUuid());
        doUpdate(position, newResume);
    }

    @Override
    public void delete(final String uuid) {
        Object position = getNotExistedPosition(uuid);
        doDelete(position);
    }

    @Override
    public Resume get(final String uuid) {
        Object position = getNotExistedPosition(uuid);
        return getFromStorage(position);
    }

    private Object getExistedPosition(String uuid) {
        Object position = getPosition(uuid);
        if (isExist(position)) {
            throw new ExistStorageException(uuid);
        }
        return position;
    }

    private Object getNotExistedPosition(String uuid) {
        Object position = getPosition(uuid);
        if (!isExist(position)) {
            throw new NotExistStorageException(uuid);
        }
        return position;
    }
}