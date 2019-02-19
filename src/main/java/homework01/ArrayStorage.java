package homework01;

import java.util.Arrays;

/**
 * Class ArrayStorage based storage for Resume.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class ArrayStorage {

    private Resume[] storage;
    private int index;
    private static final int COUNT_ELEMENTS = 10_000;

    public ArrayStorage() {
        storage = new Resume[COUNT_ELEMENTS];
        index = 0;
    }

    /**
     * Method is cleaning storage.
     *
     */
    public void clear() {
        for (int i = 0; i < index; i++) {
            storage[i] = null;
        }
        index = 0;
    }

    public void save(Resume newResume) {
        if (index != 0) {
            if (-1 == getIndex(newResume.getUuid())) {
                storage[index++] = newResume;
            }
        } else {
            storage[index++] = newResume;
        }
    }

    public Resume get(String uuid) {
        int indexGet = getIndex(uuid);
        if (indexGet != -1) {
            return storage[indexGet];
        }
        return null;
    }

    /**
     * Method return index resume by uuid, if it is in the store or -1 if it is not.
     *@param uuid - uuid for resume
     *
     */
    private int getIndex(String uuid) {
        for (int i = 0; i < index; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        int indexDelElement = getIndex(uuid);
        if (indexDelElement != -1) {
            removeElementsWithSaveStructure(indexDelElement);
        }
    }

    /**
     * Method remove element from storage with preservation of structure.
     *@param indexRemove - index resume for remove
     *
     */
    private void removeElementsWithSaveStructure(int indexRemove) {
        //Resume[] copiedSourceStorage = Arrays.copyOf(storage, storage.length);
        if (0 == indexRemove) {
            System.arraycopy(storage, ++indexRemove, storage, 0, sizeStorage() -1);
        } else if (indexRemove == sizeStorage() - 1 || indexRemove == storage.length - 1) {
            storage[indexRemove] = null;
        } else {
            System.arraycopy(storage, ++indexRemove, storage, --indexRemove, sizeStorage() - ++indexRemove);
        }
        index--;
    }

    public Resume[] getAll() {
        return Arrays.stream(storage).limit(index).toArray(size -> new Resume[index]);
    }

    public int sizeStorage() {
        return index;
    }
}
