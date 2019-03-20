package main.java.app.storage;

import main.java.app.model.Resume;

public interface Storage {

    void save(final Resume resume);

    Resume get(final String uuid);

    void delete(final String uuid);

    void update(final Resume newResume);

    void clear();

    int getSize();

    Resume[] getAll();
}
