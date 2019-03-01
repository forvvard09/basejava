package main.java.app.storage;

import main.java.app.model.Resume;

/**
 * Class ArrayStorage based storage for Resume.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertToStorage(int index, Resume newResume) {
        storage[size] = newResume;
    }

    @Override
    protected void removeFromStorage(int index) {
        storage[index] = storage[size - 1];

    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}