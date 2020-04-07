package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File file = new File(".\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error one", e);
        }
        System.out.println(file.isDirectory());


        File dir = new File("./src/main/java");
        try {
            System.out.println(dir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error second", e);
        }
        System.out.println(dir.isDirectory());

        String[] listFilesDir = dir.list();
        if (listFilesDir != null) {
            for (String name : listFilesDir) {
                System.out.println(name);
            }
        }

        /*FileInputStream fis = null;

        try {
             fis = new FileInputStream(file);
            System.out.println(fis.read());
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

        //начиная с java 7 можно длеать вот так:

        try (FileInputStream fis = new FileInputStream(file)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

