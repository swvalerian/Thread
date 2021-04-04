package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrier1018 {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
        new ThreadCycle(cb,"One");
        new ThreadCycle(cb,"Two");
        new ThreadCycle(cb,"Free");
    }
}
class ThreadCycle implements Runnable {
    CyclicBarrier cb;
    String name;
    ThreadCycle(CyclicBarrier cb, String name) {
        this.cb = cb;
        this.name = name;
        new Thread(this, name).start();
    }
    @Override
    public void run() {
        System.out.println("Стартанул поток - " + name);
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Поток - " + name + " закрылся");
    }
}

class BarAction implements Runnable {
    @Override
    public void run() {
        System.out.println("Запущен поток BarAction");
    }
}
