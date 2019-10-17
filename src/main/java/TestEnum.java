package main.java;

import main.java.app.model.TypeSection;

public class TestEnum {
    public enum Singleton {
        INSTANCE
    }

    public static void main(String[] args) {

        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance);
        System.out.println(instance.ordinal()); //выводит порядковый номер
        System.out.println();
        for(TypeSection type : TypeSection.values()) {
            System.out.println(type.getTitle());
        }
    }
}
