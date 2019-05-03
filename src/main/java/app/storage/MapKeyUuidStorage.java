package main.java.app.storage;

import main.java.app.model.Resume;

public class MapKeyUuidStorage extends AbstractMapStorage {

    @Override
    protected void doSave(Object searchKey, Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume getFromStorage(Object searchKey) {
        return mapStorage.get(searchKey.toString());
    }

    @Override
    protected void doUpdate(Object searchKey, Resume newResume) {
        mapStorage.put(searchKey, newResume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(searchKey.toString());
    }

    @Override
    protected Object getPosition(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey.toString());
    }
}
