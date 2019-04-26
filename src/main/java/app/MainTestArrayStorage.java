package main.java.app;

import main.java.app.model.Resume;
import main.java.app.storage.ArrayStorage;
import main.java.app.storage.Storage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");

        STORAGE.save(r1);
        STORAGE.save(r2);
        STORAGE.save(r3);


        System.out.println("Get r1: " + STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + STORAGE.getSize());

        System.out.println("Get dummy: " + STORAGE.get("dummy"));

        printAll(STORAGE);
        STORAGE.delete(r1.getUuid());
        printAll(STORAGE);
        STORAGE.clear();
        printAll(STORAGE);

        System.out.println("Size: " + STORAGE.getSize());
    }

    private static void printAll(Storage storage) {
        System.out.println("\nGet All");
        for (Resume r : storage.getAllSorted()) {
            System.out.println(r);
        }
    }
}