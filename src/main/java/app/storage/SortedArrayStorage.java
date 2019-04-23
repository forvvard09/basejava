package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {


    // вложеный  класс (статический)
    /*
    @Override
    protected Object getPosition(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, ResumeComparator);
    }

    private static final ResumeComparator RESUME_COMPARATOR = new ResumeComparator();

    private static final class ResumeComparator implements Comparator<Resume> {

        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    */


    // анонимный класс
    /*
    @Override
    protected Object getPosition(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };
    */

    // ----------------------------------------------------------------------

    // анонимный класс

    @Override
    protected Object getPosition(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
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