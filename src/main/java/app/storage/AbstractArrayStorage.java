package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int COUNT_ELEMENTS = 10_000;

    protected Resume[] storage = new Resume[COUNT_ELEMENTS];
    protected int size = 0;


    @Override
    protected void saveToStorage(Object position, Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            throw new StorageException("Error is adding. Storage is full.", newResume.getUuid());
        } else {
            insertToStorage((int) position, newResume);
        }

        size++;
    }

    @Override
    protected Resume getFromStorage(Object position) {
        return storage[(int) position];
    }

    @Override
    protected void updateInStorage(Object position, Resume newResume) {
        storage[(int) position] = newResume;
    }

    @Override
    protected void deleteFromStorage(Object position) {
        removeFromStorage((int) position);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Object searchPositionInStorage(String uuid) {
        return getIndex(uuid);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertToStorage(int searchIndex, Resume newResume);

    protected abstract void removeFromStorage(int index);
}