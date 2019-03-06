package main.java.app.storage;

import main.java.app.model.Resume;

public interface Storage {

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    void update(Resume newResume);

    void clear();

    int getSize();
}
