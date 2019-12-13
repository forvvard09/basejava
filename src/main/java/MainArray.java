package main.java;

import main.java.app.model.Resume;
import main.java.app.storage.ListStorage;
import main.java.app.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName | delete uuid | read uuid | update uuid fullName | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param = null;
            if (params.length > 1) {
                param = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "getSize":
                    System.out.println(ARRAY_STORAGE.getSize());
                    break;
                case "save":
                    r = new Resume(param);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(param);
                    printAll();
                    break;
                case "read":
                    System.out.println(ARRAY_STORAGE.get(param));
                    break;
                case "update":
                    String secondParam = params[2].intern();
                    r = new Resume(param, secondParam);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        List<Resume> listResumes = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (listResumes.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : listResumes) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}