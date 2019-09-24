package main.java.app.storage;

import main.java.app.model.Resume;

public class MapSearchKeyResumeStorage extends AbstractMapStorage {


    @Override
    protected Resume getFromStorage(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Object getPosition(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
