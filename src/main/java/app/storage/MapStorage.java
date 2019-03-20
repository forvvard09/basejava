package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MapStorage extends AbstractStorage {

    private int keyId;

    public MapStorage() {
        this.keyId = 0;
    }

    private final Map<Object, Resume> mapStorage = new HashMap<>();

    @Override
    protected void saveToStorage(final Object position, final Resume newResume) {
        mapStorage.put(keyId++, newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return mapStorage.get(position);
    }

    @Override
    protected void updateInStorage(final Object position, final Resume newResume) {
        mapStorage.put(position, newResume);
    }

    @Override
    protected void deleteFromStorage(final Object position) {
        mapStorage.remove(position);
    }

    @Override
    protected final Object searchPositionInStorage(final String uuid) {
        for (Map.Entry<Object, Resume> entry : mapStorage.entrySet()) {
            if (Objects.equals(uuid, entry.getValue().getUuid())) {
                return entry.getKey();
            }
        }
        return -1;
    }


    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int getSize() {
        return mapStorage.size();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }
}
