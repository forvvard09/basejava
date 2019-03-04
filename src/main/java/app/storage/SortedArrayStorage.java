package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertToStorage(int index, Resume newResume) {
        index = -index - 1;
        if (index == size) {
            storage[size] = newResume;
        } else {
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = newResume;
        }
    }

    @Override
    protected void removeFromStorage(int index) {
        if (index == size - 1) {
            storage[index] = null;
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}