package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class ArrayStorage based storage for Resume.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertToStorage(final int index, final Resume newResume) {
        storage[size] = newResume;
    }

    @Override
    protected void removeFromStorage(final int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected List<Resume> getSortedList() {
        List<Resume> listResumes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            listResumes.add(storage[i]);
        }
        Collections.sort(listResumes);
        return listResumes;
    }
}