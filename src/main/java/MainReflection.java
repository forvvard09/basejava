package main.java;

import main.java.app.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("testFullName");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.get(r);
        System.out.println(r);
        field.set(r, "new_uuid");
        // TODO invoke r.toString via reflection
        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println("method toString via reflection: " + method.invoke(r));
        System.out.println("method toString without reflection: " + r.toString());
    }
}