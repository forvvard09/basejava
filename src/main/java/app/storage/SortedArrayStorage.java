package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Object getPosition(String uuid) {
        Resume searchIndex = new Resume(uuid, "template");
        return Arrays.binarySearch(storage, 0, size, searchIndex, RESUME_COMPARATOR);
    }

    @Override
    protected void insertToStorage(int index, final Resume newResume) {
        index = -index - 1;
        if (index == size) {
            storage[size] = newResume;
        } else {
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = newResume;
        }
    }


    @Override
    protected void removeFromStorage(final int index) {
        if (index == size - 1) {
            storage[index] = null;
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        }
    }
}