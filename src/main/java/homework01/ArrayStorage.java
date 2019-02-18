package homework01;

import java.util.Arrays;

/**
 * Class ArrayStorage based storage for Resume.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 1.0
 * @since 18.02.2019
 */
public class ArrayStorage {

    /**
     * property array Resume based storage.
     */
    private Resume[] storage;
    /**
     * property index save count resume in storage.
     */
    private int index;

    /**
     * property default size SimpleArrayList.
     */
    private static final int COUNT_ELEMENTS = 10_000;

    /**
     * Constructor it creates container with the specified values.
     *
     */
    public ArrayStorage() {
        this.storage = new Resume[COUNT_ELEMENTS];
        this.index = 0;
    }

    /**
     * Method checks profile on tanking.
     *@param uuid - uuid for resume
     *
     */
    private boolean isDuplicates(String uuid) {
        for (int i = 0; i < index; i++) {
            if (this.storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method return index resume by uuid, if it is in the store or -1 if it is not.
     *@param uuid - uuid for resume
     *
     */
    private int getIndex(String uuid) {
        for (int i = 0; i < index; i++) {
            if (this.storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        int result = -1;
        return result;

    }

    /**
     * Method remove element from storage with preservation of structure.
     *@param indexRemove - index resume for remove
     *
     */
    private void removeElementsWithSaveStructure(int indexRemove) {
        Resume[] copiedSourceStorage = Arrays.copyOf(this.storage, this.storage.length);
        if (0 == indexRemove) {
            System.arraycopy(copiedSourceStorage, ++indexRemove, this.storage, 0, this.size() -1);
        } else if (indexRemove == size() - 1 || indexRemove == this.storage.length - 1) {
            this.storage[indexRemove] = null;
        } else {
            System.arraycopy(copiedSourceStorage, ++indexRemove, this.storage, --indexRemove, size() - ++indexRemove);
        }
        this.index--;
    }

    /**
     * Method is cleaning storage.
     *
     */
    public void clear() {
        for (int i = 0; i < index; i++) {
            this.storage[i] = null;
        }
        this.index = 0;
    }

    /**
     * Method is adding new resume to storage.
     *
     * @param newResume new resume
     *
     * @return result add
     */
    public boolean save(Resume newResume) {
        boolean result = false;
        if (this.index != 0) {
            if (!isDuplicates(newResume.uuid)) {
                this.storage[this.index++] = newResume;
                result = true;
            }

        } else {
            this.storage[this.index++] = newResume;
            result = true;
        }
        return result;
    }

    /**
     * Method return resume by uuid.
     *
     * @param uuid id resume
     *
     * @return resume or null, if resume dont search
     */
    public Resume get(String uuid) {
        int indexGet = getIndex(uuid);
        if (indexGet != -1) {
            return this.storage[indexGet];
        }
        return null;
    }

    /**
     * Method remove resume by uuid.
     *
     * @param uuid id resume
     *
     */
    public void delete(String uuid) {
        int getIndex = getIndex(uuid);
        if (getIndex != -1) {
            removeElementsWithSaveStructure(getIndex);
        }
    }

    /**
     * Method return resume all resumes.
     *
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resultAllResume = Arrays.stream(this.storage).limit(index).toArray(size -> new Resume[index]);
        return resultAllResume;
    }

    /**
     * Method return count of resume.
     *
     * @return count resume
     */
    public int size() {
        return this.index;
    }
}
