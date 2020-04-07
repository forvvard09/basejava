package main.java;

import main.java.app.util.LazySingleton;

import java.util.ArrayList;
import java.util.List;

public class MyConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ": " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        /*for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
                    inc();
                }

                private  void inc() {
                    synchronized (this) {
                        System.out.println(this);
                        counter++;
                    }
                }
            }).start();
        }*/

        System.out.println(thread0.getName() + ": " + thread0.getState());


        final MyConcurrency myConcurrency = new MyConcurrency();
        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);


        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myConcurrency.inc();
//                      counter++;
                }
            });
            thread.start();
            threadList.add(thread);
        }
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        Thread.sleep(999);
        LazySingleton.getInstance();
        System.out.println(counter);


        final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadLock(lock1, lock2);
        deadLock(lock2, lock1);
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private void inc() {
        synchronized (this) {
            counter++;
        }
    }
}
