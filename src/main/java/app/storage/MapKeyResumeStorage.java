package main.java.app.storage;

import main.java.app.model.Resume;

public class MapKeyResumeStorage extends AbstractMapStorage {

    @Override
    protected void doSave(Object searchKey, Resume newResume) {
        mapStorage.put(newResume, newResume);
    }

    @Override
    protected Resume getFromStorage(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume newResume) {
        mapStorage.put(newResume, newResume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(searchKey);
    }


    @Override
    protected Object getPosition(String uuid) {
        Resume resume = new Resume(uuid, "template");
        return mapStorage.get(resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }
}
