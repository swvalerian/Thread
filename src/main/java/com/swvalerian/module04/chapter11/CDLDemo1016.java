package main.java.com.swvalerian.module04.chapter11;

import java.util.concurrent.CountDownLatch;

public class CDLDemo1016 {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        System.out.println("ЗАпуск потока исполнения...");
        new MyThreadCDL(countDownLatch);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Завершение потока исполнения... основной поток закончился раньше");
    }
}

class MyThreadCDL implements Runnable {
    CountDownLatch latch;

    MyThreadCDL(CountDownLatch c) {
        latch = c;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i=5; i > 0; --i) {
            System.out.println(" i = " + i);
            latch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}