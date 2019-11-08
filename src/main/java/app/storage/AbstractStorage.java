package main.java.app.storage;

import main.java.app.exception.ExistStorageException;
import main.java.app.exception.NotExistStorageException;
import main.java.app.model.Resume;
import main.java.app.model.TypeSection;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    //1 способ объявления лога
    //protected final Logger log = Logger.getLogger(getClass().getName());

    //2 способ объявления лога
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract void doSave(SK key, Resume newResume);

    protected abstract Resume getFromStorage(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume newResume);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getPosition(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(final Resume newResume) {
        LOG.info("Save: " + newResume);
        LOG.info("Save: " + newResume.getContacts());
        LOG.info("Save: " + newResume.getSections().get(TypeSection.valueOf("EDUCATION")));
        SK searchKey = getExistedPosition(newResume.getUuid());
        doSave(searchKey, newResume);
    }

    @Override
    public void update(final Resume newResume) {
        LOG.info("Update: " + newResume);
        SK searchKey = getNotExistedPosition(newResume.getUuid());
        doUpdate(searchKey, newResume);
    }

    @Override
    public void delete(final String uuid) {
        LOG.info("Delete: " + uuid);
        SK searchKey = getNotExistedPosition(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(final String uuid) {
        LOG.info("Get: " + uuid);
        SK searchKey = getNotExistedPosition(uuid);
        return getFromStorage(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> listResumes = doCopyAll();
        Collections.sort(listResumes);
        return listResumes;
    }

    private SK getExistedPosition(String uuid) {
        SK searchKey = getPosition(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Error. A resume with such " + uuid + " already exists. ");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedPosition(String uuid) {
        SK searchKey = getPosition(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not found in storage");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}