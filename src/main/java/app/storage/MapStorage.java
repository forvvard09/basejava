package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MapStorage based storage for Resume use Map.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 28.03.2019
 */
public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(final Object position, final Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return mapStorage.get(position.toString());
    }

    @Override
    protected void doUpdate(final Object position, final Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void doDelete(final Object position) {
        mapStorage.remove(position.toString());
    }

    @Override
    protected final Object getPosition(final String uuid) {
            return uuid;
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
        Resume[] allResume = mapStorage.values().toArray(new Resume[0]);
        Arrays.sort(allResume);
        return allResume;
    }

    @Override
    protected boolean isExist(final Object position) {
        return mapStorage.containsKey(position.toString());
    }
}
