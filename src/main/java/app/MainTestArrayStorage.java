package main.java.app;

import main.java.app.model.Resume;
import main.java.app.storage.ArrayStorage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.setUuid("uuid1");
        final Resume r2 = new Resume();
        r2.setUuid("uuid2");
        final Resume r3 = new Resume();
        r3.setUuid("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.getSize());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll(ARRAY_STORAGE);
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll(ARRAY_STORAGE);
        ARRAY_STORAGE.clear();
        printAll(ARRAY_STORAGE);

        System.out.println("Size: " + ARRAY_STORAGE.getSize());
    }

    private static void printAll(ArrayStorage arrayStorage) {
        System.out.println("\nGet All");
        for (Resume r : arrayStorage.getAll()) {
            System.out.println(r);
        }
    }
}