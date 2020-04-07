package main.java.app.storage;

import main.java.app.exception.StorageException;
import main.java.app.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int COUNT_ELEMENTS = 10_000;
    protected Resume[] storage = new Resume[COUNT_ELEMENTS];
    protected int size = 0;


    protected abstract void insertToStorage(int searchIndex, Resume newResume);

    protected abstract void removeFromStorage(int index);


    @Override
    protected void doSave(final Integer index, final Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            throw new StorageException("Error is adding. Storage is full.", newResume.getUuid());
        } else {
            insertToStorage(index, newResume);
        }
        size++;
    }

    @Override
    protected Resume getFromStorage(final Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(final Integer searchKey, final Resume newResume) {
        storage[searchKey] = newResume;
    }

    @Override
    protected void doDelete(final Integer searchKey) {
        removeFromStorage(searchKey);
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
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public int getSize() {
        return size;
    }
}