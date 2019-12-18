package main.java;

import java.util.ArrayList;
import java.util.List;

public class MyConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private Object LOCK = new Object();
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
        System.out.println(counter);
    }

    private  void inc() {
            synchronized (this) {
                counter++;
            }
    }
}
