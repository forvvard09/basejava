package main.java;

import java.io.File;
import java.io.IOException;

public class MainGetListFilesOrDir {
    public static void printListFiles(File dirPath, int level) {
        if (!dirPath.isDirectory()) {
            throw new IllegalArgumentException(dirPath.getAbsolutePath() + " is not directory");
        }
        if (!dirPath.canRead() || !dirPath.canWrite()) {
            throw new IllegalArgumentException(dirPath.getAbsolutePath() + " is not readable/writable");
        }
        try {
            dirPath = dirPath.getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] listFilesHomework = dirPath.listFiles();
            if (listFilesHomework != null) {
                for (File file : listFilesHomework) {
                    if (!file.isDirectory()) {
                        for (int i = 0; i < level; i++) {
                            System.out.print("   ");
                        }
                        System.out.println(file.getName());
                    } else {
                        level++;
                        for (int i = 0; i < level; i++) {
                            System.out.print("  ");
                        }

                        System.out.println(String.format("%s%s", file.getName(), "/"));
                        printListFiles(file, level);
                    }
                }
            }
    }
    public static void main(String[] args) throws IOException {
        //рекурсивный вывод имени файлов в каталогах и подкаталогах
        System.out.println("рекурсивный вывод имени файлов в каталогах и подкаталогах:");
        //get path project
        File startProjectPathDir = new File("./src/main/java");
        System.out.println(startProjectPathDir.getCanonicalPath());
        printListFiles(startProjectPathDir, 0);
    }
}
