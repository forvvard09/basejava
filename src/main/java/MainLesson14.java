package main.java;

import java.util.HashMap;
import java.util.Map;

public class MainLesson14 {
    public static void main(String[] args) {
        Map<String, String> testMap = new HashMap<>();

        testMap.put("1", "One");
        testMap.put("2", "Two");
        testMap.put("3", "Three");

        //запишет только если нет записи с ключом "1", в нашем случае не запишет,
        // возвращет null уесли успешно записывает, и value, если запись уже есть
        System.out.println(testMap.putIfAbsent("1", "test"));

        testMap.computeIfPresent("1", String::concat);


        System.out.println(testMap);

    }
}
