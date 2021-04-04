package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpExecPool1031 {
    public static void main(String[] args) {
        CountDownLatch cdl1 = new CountDownLatch(5);
        CountDownLatch cdl2 = new CountDownLatch(5);
        CountDownLatch cdl3 = new CountDownLatch(5);
        CountDownLatch cdl4 = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        System.out.println("Запуск потоков:");
        executorService.execute( new ThreadPool(cdl1,"AAA"));
        executorService.execute( new ThreadPool(cdl2,"BBB"));
        executorService.execute( new ThreadPool(cdl3,"CCC"));
        executorService.execute( new ThreadPool(cdl4,"DDD"));

        try {
            cdl1.await();
            cdl2.await();
            cdl3.await();
            cdl4.await();
            System.out.println("всех дождались");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Но программа не завершилась? вернее основной потока завершился, а остальные четыре потока - висят");
        System.out.println(executorService.isShutdown());
        System.out.println("теперь завершим");

        executorService.shutdown();
        System.out.println(executorService.isShutdown());
    }
}

class ThreadPool implements Runnable {
    String name;
    CountDownLatch latch;
    ThreadPool(CountDownLatch latch, String name) {
        this.latch = latch;
        this.name = name;
        new Thread(this);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.println(name + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }
}