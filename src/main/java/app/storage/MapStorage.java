package main.java.app.storage;

import main.java.app.model.Resume;

public class MapStorage extends AbstractMapStorage {

    @Override
    protected void doSaveMapStorage(Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected boolean doIsExistMap(Object key) {
        return mapStorage.containsKey(key.toString());
    }

    @Override
    protected Object doGetPosition(String key) {
        return key;
    }

    @Override
    protected void doDoUpdateMap(Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume doGetFromStorageMap(Object key) {
        return mapStorage.get(key.toString());
    }

    @Override
    protected void doDeleteMap(Object key) {
        mapStorage.remove(key.toString());
    }
}
