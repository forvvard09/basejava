package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume newResume) {
        Integer location = (Integer) searchPositionInStorage(newResume.getUuid());
        if (isStorage(location)) {
            throw new ExistStorageException(newResume.getUuid());
        }
        saveToStorage(location, newResume);
    }

    @Override
    public void update(Resume newResume) {
        Integer location = (Integer) searchPositionInStorage(newResume.getUuid());
        if (!isStorage(location)) {
            throw new NotExistStorageException(newResume.getUuid());
        }
        updateInStorage(location, newResume);
    }

    @Override
    public void delete(String uuid) {
        Integer location = (Integer) searchPositionInStorage(uuid);
        if (!isStorage(location)) {
            throw new NotExistStorageException(uuid);
        }
        deleteFromStorage(location);
    }

    @Override
    public Resume get(String uuid) {
        Integer location = (Integer) searchPositionInStorage(uuid);
        if (!isStorage(location)) {
            throw new NotExistStorageException(uuid);
        }
        return getFromStorage(location);
    }

    private boolean isStorage(int index) {
        boolean result = false;
        if (index >= 0) {
            result = true;
        }
        return result;
    }

    protected abstract void saveToStorage(Object position, Resume newResume);

    protected abstract Resume getFromStorage(Object position);

    protected abstract void updateInStorage(Object position, Resume newResume);

    protected abstract void deleteFromStorage(Object position);

    protected abstract Object searchPositionInStorage(String uuid);
}