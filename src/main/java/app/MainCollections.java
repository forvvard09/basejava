package main.java.app;

import main.java.app.model.Resume;

import java.util.*;

public class MainCollections {

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String TEST_UID = "testUuid";
    private static final Resume RESUME_ONE = new Resume(UUID_1);
    private static final Resume RESUME_TWO = new Resume(UUID_2);
    private static final Resume RESUME_THREE = new Resume(UUID_3);
    private static final Resume RESUME_TEST = new Resume(TEST_UID);


    public static void main(String[] args) {

        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_ONE);
        collection.add(RESUME_TWO);
        collection.add(RESUME_THREE);

        for (Resume r : collection) {
            System.out.println(r);

            if (r.getUuid().equals(UUID_1)) {
//              collection.remove(r);
            }
        }

        Iterator<Resume> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (r.getUuid().equals(UUID_1)) {
                iterator.remove();
            }

        }
        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_ONE);
        map.put(UUID_2, RESUME_TWO);
        map.put(UUID_3, RESUME_THREE);

        // не правильно
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        // правильно
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}