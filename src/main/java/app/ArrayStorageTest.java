package main.java.app;

import main.java.app.model.Resume;
import main.java.app.storage.ArrayStorage;

/**
 * Test for ArrayStorage.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 21.02.2019
 */
public class ArrayStorageTest {


    public static void main(String[] args) {

        ArrayStorage arrayStorage = new ArrayStorage();
        for (int i = 0; i < 10000; i++) {
            Resume resume = new Resume();
            arrayStorage.save(resume);
        }

        System.out.println(arrayStorage.getSize());
        //we receive a message that the storage is full
        arrayStorage.save(new Resume());
        arrayStorage.clear();
        System.out.println(arrayStorage.getSize());
    }
}