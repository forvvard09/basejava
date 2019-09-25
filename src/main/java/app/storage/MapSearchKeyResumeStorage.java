package main.java.app.storage;

import main.java.app.model.Resume;

public class MapSearchKeyResumeStorage extends AbstractMapStorage<Resume> {


    @Override
    protected Resume getFromStorage(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        mapStorage.remove((searchKey).getUuid());
    }

    @Override
    protected Resume getPosition(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}
