package main.java.app.storage;

import main.java.app.model.Resume;

public class MyMapStorage extends AbstractMapStorage {

    @Override
    protected void doSaveMapStorage(Resume newResume) {
        mapStorage.put(newResume, newResume);
    }

    @Override
    protected boolean doIsExistMap(Object key) {
        return mapStorage.containsKey(key);
    }

    @Override
    protected Object doGetPosition(String uuid) {
        return mapStorage.get(new Resume(uuid));
    }

    @Override
    protected void doDoUpdateMap(Resume newResume) {
        mapStorage.put(newResume, newResume);
    }

    @Override
    protected Resume doGetFromStorageMap(Object key) {
        return mapStorage.get(key);
    }

    @Override
    protected void doDeleteMap(Object key) {
        mapStorage.remove(key);
    }
}
