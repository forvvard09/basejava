package main.java;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrencyNext {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private static final Lock lock = new ReentrantLock();
//    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    //you can lambda
    /*private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>(){

    @Override
    protected SimpleDateFormat initialValue() {
        return new SimpleDateFormat("HH:mm:ss");
    }
    };*/

    //private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm:ss"));
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm:ss"));

    public static void main(String[] args) throws InterruptedException {

        final MyConcurrencyNext myConcurrency = new MyConcurrencyNext();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        CompletionService completionService = new ExecutorCompletionService(executorService);


        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(()->

//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    myConcurrency.inc();
                    System.out.println(THREAD_LOCAL.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
            completionService.poll();
//            thread.start();
        }
        executorService.shutdown();
        latch.await(10, TimeUnit.SECONDS);
        System.out.println(myConcurrency.atomicCounter.get());
        System.out.println("Count processors: " + Runtime.getRuntime().availableProcessors());
    }

    //use atomic, we don't use lock and synchronized
    /*private void inc() {
            lock.lock();
            try {
                counter++;
            } finally {
                lock.unlock();
            }
    }*/

    //use atomic
    private void inc() {
        atomicCounter.incrementAndGet();
    }

}
