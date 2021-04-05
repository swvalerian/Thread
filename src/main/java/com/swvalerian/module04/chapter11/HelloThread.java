package main.java.com.swvalerian.module04.chapter11;

public class HelloThread {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new HiFriend().start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Привет из основного потока!");

    }
}

class HiFriend extends Thread {

    @Override
    public synchronized void run() {

        System.out.println("Привет из потока - " + getName());
        try {
            Thread.sleep(1001);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}