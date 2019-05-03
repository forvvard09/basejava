package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class MapKeyUuidStorage based storage for Resume use List.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 28.03.2019
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(final Object searchKey, final Resume newResume) {
        listStorage.add(newResume);
    }

    @Override
    protected Resume getFromStorage(final Object searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    protected void doUpdate(final Object searchKey, final Resume newResume) {
        listStorage.set((int) searchKey, newResume);
    }

    @Override
    protected void doDelete(final Object searchKey) {
        listStorage.remove((int) searchKey);
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
    public List<Resume> getAllSorted() {
        Collections.sort(listStorage);
        return listStorage;
    }

    @Override
    protected List<Resume> doGetListResume() {
        return listStorage;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey != -1;
    }

}
