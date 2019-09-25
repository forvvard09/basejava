package main.java.app.storage;

import main.java.app.model.Resume;

public class MapKeyUuidStorage extends AbstractMapStorage<String> {


    @Override
    protected Resume getFromStorage(String searchKey) {
        return mapStorage.get(searchKey.toString());
    }

    @Override
    protected void doUpdate(String searchKey, Resume newResume) {
        mapStorage.put(searchKey, newResume);
    }

    @Override
    protected void doDelete(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected String getPosition(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return mapStorage.containsKey(searchKey);
    }
}
