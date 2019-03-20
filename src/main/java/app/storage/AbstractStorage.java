package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(final Resume newResume) {
        Integer position = (Integer) searchPositionInStorage(newResume.getUuid());
        if (checkPresence(position)) {
            throw new ExistStorageException(newResume.getUuid());
        }
        saveToStorage(position, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        Integer location = (Integer) searchPositionInStorage(newResume.getUuid());
        if (!checkPresence(location)) {
            throw new NotExistStorageException(newResume.getUuid());
        }
        updateInStorage(location, newResume);
    }

    @Override
    public void delete(final String uuid) {
        Integer position = (Integer) searchPositionInStorage(uuid);
        if (!checkPresence(position)) {
            throw new NotExistStorageException(uuid);
        }
        deleteFromStorage(position);
    }

    @Override
    public Resume get(final String uuid) {
        Integer position = (Integer) searchPositionInStorage(uuid);
        if (!checkPresence(position)) {
            throw new NotExistStorageException(uuid);
        }
        return getFromStorage(position);
    }

    private boolean checkPresence(final int index) {
        return index >= 0;
    }

    protected abstract void saveToStorage(final Object position, final Resume newResume);

    protected abstract Resume getFromStorage(final Object position);

    protected abstract void updateInStorage(final Object position, final Resume newResume);

    protected abstract void deleteFromStorage(final Object position);

    protected abstract Object searchPositionInStorage(final String uuid);
}