package main.java.app.storage;

public class TestSingleton {
    private static TestSingleton ourInstance = new TestSingleton();

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) {
        String s = TestSingleton.getInstance().toString();
        System.out.println(s);

    }
}

    /*
    Ленивая инициализация

    public class TestSingleton {
       private static TestSingleton ourInstance;

         public static TestSingleton getInstance() {
           if (ourInstance == null) {
            ourInstance = new TestSingleton();
           }
           return ourInstance;
         }

         private TestSingleton() {
         }

         public static void main(String[] args) {
            TestSingleton.getInstance().toString();
         }
    }
     */

