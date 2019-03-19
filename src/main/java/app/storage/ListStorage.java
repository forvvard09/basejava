package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> listStorage;

    public ListStorage() {
        this.listStorage = new ArrayList();
    }

    @Override
    protected void saveToStorage(Object position, Resume newResume) {
        listStorage.add(newResume);
    }

    @Override
    protected Resume getFromStorage(Object position) {
        return listStorage.get((int) position);
    }

    @Override
    protected void updateInStorage(Object position, Resume newResume) {
        listStorage.set((int) position, newResume);
    }

    @Override
    protected void deleteFromStorage(Object position) {
        listStorage.remove((int) position);
    }

    @Override
    protected Object searchPositionInStorage(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public int getSize() {
        return listStorage.size();
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }
}
