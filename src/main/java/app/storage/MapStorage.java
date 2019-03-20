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
    private final static String NOT_FOUND = "not found";

    @Override
    protected void saveToStorage(final Object position, final Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return mapStorage.get(position.toString());
    }

    @Override
    protected void updateInStorage(final Object position, final Resume newResume) {
        mapStorage.put(newResume.getUuid(), newResume);
    }

    @Override
    protected void deleteFromStorage(final Object position) {
        mapStorage.remove(position.toString());
    }

    @Override
    protected final String getPosition(final String uuid) {
        if (mapStorage.get(uuid) != null) {
            return uuid;
        }
        return NOT_FOUND;
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
    protected boolean checkPresence(final Object position) {
        return !position.equals(NOT_FOUND);
    }
}
