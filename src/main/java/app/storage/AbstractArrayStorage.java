package main.java.app.storage;

import main.java.app.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int COUNT_ELEMENTS = 10_000;

    protected Resume[] storage;
    protected int size;

    public int getSize() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume not found in storage.");
        return null;
    }

    protected abstract int getIndex(String uuid);
}
