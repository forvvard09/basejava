package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int COUNT_ELEMENTS = 10_000;
    protected Resume[] storage = new Resume[COUNT_ELEMENTS];
    protected int size = 0;


    protected abstract void insertToStorage(int searchIndex, Resume newResume);

    protected abstract void removeFromStorage(int index);


    @Override
    protected void doSave(final Object index, final Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            throw new StorageException("Error is adding. Storage is full.", newResume.getUuid());
        } else {
            insertToStorage((int) index, newResume);
        }
        size++;
    }

    @Override
    protected Resume getFromStorage(final Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(final Object searchKey, final Resume newResume) {
        storage[(int) searchKey] = newResume;
    }

    @Override
    protected void doDelete(final Object searchKey) {
        removeFromStorage((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    public int getSize() {
        return size;
    }


}