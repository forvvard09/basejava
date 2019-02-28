package main.java.app;

import main.java.app.model.Resume;
import main.java.app.storage.SortedArrayStorage;
import main.java.app.storage.Storage;

/**
 * Test for ArrayStorage.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 21.02.2019
 */
public class ArrayStorageTest {
    public static void main(String[] args) {
        Storage storage = new SortedArrayStorage();
        for (int i = 0; i < 10000; i++) {
            Resume resume = new Resume();
            resume.setUuid(String.valueOf(i));
            storage.save(resume);
        }

        System.out.println(storage.getSize());
        //we receive a message that the storage is full
        storage.save(new Resume());
        storage.clear();
        System.out.println(storage.getSize());
    }
}
