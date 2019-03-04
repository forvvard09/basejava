package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int COUNT_ELEMENTS = 10_000;

    protected Resume[] storage = new Resume[COUNT_ELEMENTS];
    protected int size = 0;

    @Override
    public void save(Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            throw new StorageException("Error is adding. Storage is full.", newResume.getUuid());
        } else {
            int searchIndex = getIndex(newResume.getUuid());
            if (searchIndex >= 0) {
                throw new ExistStorageException(newResume.getUuid());
            } else {
                insertToStorage(searchIndex, newResume);
                size++;
            }
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume newResume) {
        int index = getIndex(newResume.getUuid());
        if (index >= 0) {
            storage[index] = newResume;
        } else {
            throw new NotExistStorageException(newResume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int indexDelElement = getIndex(uuid);
        if (indexDelElement >= 0) {
            removeFromStorage(indexDelElement);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract void removeFromStorage(int index);

    /**
     * Method return size resume by uuid, if it is in the store or -1 if it is not.
     *
     * @param uuid - uuid for resume
     */
    protected abstract int getIndex(String uuid);

    protected abstract void insertToStorage(int searchIndex, Resume newResume);
}