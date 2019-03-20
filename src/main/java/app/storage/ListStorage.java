package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void saveToStorage(final Object position, final Resume newResume) {
        listStorage.add(newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return listStorage.get((int) position);
    }

    @Override
    protected void updateInStorage(final Object position, final Resume newResume) {
        listStorage.set((int) position, newResume);
    }

    @Override
    protected void deleteFromStorage(final Object position) {
        listStorage.remove((int) position);
    }

    @Override
    protected Object searchPositionInStorage(final String uuid) {
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
