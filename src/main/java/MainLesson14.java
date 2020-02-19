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

        //если ключ используется, то выполнится лямюда функция, и будет выполнено действие для сущестующего value
        // возвращет null и ничего не происходит если такой ключ не используется
        System.out.println(testMap.computeIfPresent("1", (key, value) -> "999" + "-> " + value));

        //запишет, если ключ 4 не используется и выведет результат выполнения lambda
        System.out.println(testMap.computeIfAbsent("3", value -> "Five"));

        System.out.println(testMap);

    }
}
