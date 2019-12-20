package main.java;

import static java.lang.Thread.State.BLOCKED;

public class Deadlock {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();


    public void doLock(Object lock1, Object lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
            }
        }
    }

    public static void main(String[] args) {

        Deadlock deadlock = new Deadlock();

        Thread th1 = new Thread(() -> {
            deadlock.doLock(deadlock.lock1, deadlock.lock2);
        });

        Thread th2 = new Thread(() -> {
            deadlock.doLock(deadlock.lock2, deadlock.lock1);
        });

        th1.start();
        th2.start();
    }
}
