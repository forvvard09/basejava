package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int COUNT_ELEMENTS = 10_000;

    protected Resume[] storage = new Resume[COUNT_ELEMENTS];
    protected int size = 0;


    @Override
    protected void saveToStorage(final Object position, final Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            throw new StorageException("Error is adding. Storage is full.", newResume.getUuid());
        } else {
            insertToStorage((int) position, newResume);
        }

        size++;
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return storage[(int) position];
    }

    @Override
    protected void updateInStorage(final Object position, final Resume newResume) {
        storage[(int) position] = newResume;
    }

    @Override
    protected void deleteFromStorage(final Object position) {
        removeFromStorage((int) position);
        storage[size - 1] = null;
        size--;
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


    protected abstract void insertToStorage(final int searchIndex, final Resume newResume);

    protected abstract void removeFromStorage(final int index);
}