package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(final Resume newResume) {
        Object position = getPosition(newResume.getUuid());
        if (checkPresence(position)) {
            throw new ExistStorageException(newResume.getUuid());
        }
        saveToStorage(position, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        Object position = getPosition(newResume.getUuid());
        if (!checkPresence(position)) {
            throw new NotExistStorageException(newResume.getUuid());
        }
        updateInStorage(position, newResume);
    }

    @Override
    public void delete(final String uuid) {
        Object position = getPosition(uuid);
        if (!checkPresence(position)) {
            throw new NotExistStorageException(uuid);
        }
        deleteFromStorage(position);
    }

    @Override
    public Resume get(final String uuid) {
        Object position = getPosition(uuid);
        if (!checkPresence(position)) {
            throw new NotExistStorageException(uuid);
        }
        return getFromStorage(position);
    }


    protected abstract void saveToStorage(Object position, Resume newResume);

    protected abstract Resume getFromStorage(Object position);

    protected abstract void updateInStorage(Object position, Resume newResume);

    protected abstract void deleteFromStorage(Object position);

    protected abstract Object getPosition(String uuid);

    protected abstract boolean checkPresence(Object position);



}