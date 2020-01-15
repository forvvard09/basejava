package main.java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainLesson12Streams {
    private volatile int sum;

    public static void main(String[] args) {

        MainLesson12Streams lesson = new MainLesson12Streams();
        System.out.println(lesson.minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(lesson.minValue(new int[]{9, 8}));
        System.out.println();
        System.out.println(lesson.addOrEven(Arrays.asList(1, 2, 3, 4, 5, 4)));
        System.out.println(lesson.addOrEven(Arrays.asList(1, 2, 3, 4, 5, 5)));
    }

    public int minValue(int[] values) {
        return (Arrays.stream(values)
                .distinct()
//                .sorted().reduce((x, y) -> x * 10 + y).getAsInt();
//                .sorted().reduce((x, y) -> x * 10 + y).ifPresentOrElse(System.out::println, () -> System.out.println("Error with data"));  java 9
//                .sorted().reduce((x, y) -> x * 10 + y).orElse(-1));
                .sorted()
                .reduce((x, y) -> x * 10 + y))
                .orElseThrow(RuntimeException::new);
    }

    public List<Integer> addOrEven(List<Integer> integers) {
        sum = 0;
        return integers.stream()
                .peek(x -> adding(x))
                .collect(Collectors.partitioningBy(x -> x % 2 == 0))
                .get(sum % 2 != 0);
    }

    private synchronized void adding(int x) {
        sum += x;
    }
}
