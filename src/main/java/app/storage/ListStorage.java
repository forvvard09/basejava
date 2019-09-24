package main.java.app.storage;

import main.java.app.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class ListStorage based storage for Resume use List.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 28.03.2019
 */
public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> listStorage = new ArrayList<>();

    @Override
    protected void doSave(final Integer searchKey, final Resume newResume) {
        listStorage.add(newResume);
    }

    @Override
    protected Resume getFromStorage(final Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    protected void doUpdate(final Integer searchKey, final Resume newResume) {
        listStorage.set(searchKey, newResume);
    }

    @Override
    protected void doDelete(final Integer searchKey) {
        listStorage.remove(searchKey.intValue());
    }

    @Override
    protected Integer getPosition(final String uuid) {
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
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(listStorage);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }
}
