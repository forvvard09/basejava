package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * Class MapStorage based storage for Resume use List.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 28.03.2019
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(final Object position, final Resume newResume) {
        listStorage.add(newResume);
    }

    @Override
    protected Resume getFromStorage(final Object position) {
        return listStorage.get((int) position);
    }

    @Override
    protected void doUpdate(final Object position, final Resume newResume) {
        listStorage.set((int) position, newResume);
    }

    @Override
    protected void doDelete(final Object position) {
        listStorage.remove((int) position);
    }

    @Override
    protected Object getPosition(final String uuid) {
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

    @Override
    protected boolean isExist(Object position) {
        return (Integer) position != -1;
    }

}
