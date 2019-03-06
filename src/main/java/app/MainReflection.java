package main.java.app;

import main.java.app.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.get(r);
        System.out.println(r);
        field.set(r, "new_uuid");
        // TODO invoke r.toString via reflection
        System.out.println(r);

    }
}
