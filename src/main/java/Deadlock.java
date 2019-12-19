package main.java;

import static java.lang.Thread.State.BLOCKED;

public class Deadlock {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void showOne() {
        synchronized (lock1) {
            System.out.println("Show one. Lock1 hold.");
            synchronized (lock2) {
                System.out.println("Show one. Lock2 hold.");
            }
        }
    }

    public void showTwo() {
        synchronized (lock2) {
            System.out.println("Show Two. Lock2 hold.");
            synchronized (lock1) {
                System.out.println("Show Two. Lock1 hold.");
            }
        }
    }

    public static void main(String[] args) {

        Deadlock deadlock = new Deadlock();

        Thread th1 = new Thread(() -> {
            deadlock.showOne();
        });

        Thread th2 = new Thread(() -> {
            deadlock.showTwo();
        });

        th1.setDaemon(true);
        th2.setDaemon(true);

        th1.start();
        th2.start();

        try {
            th1.join(5000);
            th2.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (th1.getState().equals(BLOCKED) && th2.getState().equals(BLOCKED)) {
            System.out.println("Show deadlock");
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish demo daeadlock.");
    }
}
