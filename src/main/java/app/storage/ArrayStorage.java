package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.Arrays;

/**
 * Class ArrayStorage based storage for Resume.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        storage = new Resume[COUNT_ELEMENTS];
        size = 0;
    }


    public void save(Resume newResume) {
        if (size == COUNT_ELEMENTS) {
            System.out.println("Error is adding. Storage is full");
        } else if (getIndex(newResume.getUuid()) != -1) {
            System.out.println("Error. A resume with such uuid already exists.");
        } else {
            storage[size++] = newResume;
        }
    }


    public void delete(String uuid) {
        int indexDelElement = getIndex(uuid);
        if (indexDelElement != -1) {
            storage[indexDelElement] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume for del not found in storage.");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void update(Resume newResume) {
        int index = getIndex(newResume.getUuid());
        if (index != -1) {
            storage[index] = newResume;
        } else {
            System.out.println("Resume for update not found in storage.");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * Method return size resume by uuid, if it is in the store or -1 if it is not.
     *
     * @param uuid - uuid for resume
     */
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
