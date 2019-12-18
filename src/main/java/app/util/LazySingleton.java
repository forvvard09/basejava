package main.java.app.util;

public class LazySingleton {

    private static volatile LazySingleton INSTANCE;

    private LazySingleton() {
    }

    //Первый способ ленивой инициалихации
    /* public static  LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }*/

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    //второй сопсоб ленивой инициализации
    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
}


