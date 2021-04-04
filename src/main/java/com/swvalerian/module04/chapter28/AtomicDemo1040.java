package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo1040 {
    public static void main(String[] args) {
        new ThreadAtom("AAA");
        new ThreadAtom("BBB");
        new ThreadAtom("CCC");
    }
}

class SharedMe {
    static AtomicInteger ai = new AtomicInteger(1);
    static int inturist = 0;
}

class ThreadAtom implements Runnable{
    AtomicInteger atomicInteger;
    String name;
    ThreadAtom(String n) {
        name = n;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока - " + name);
        for (int i = 0; i < 5; i++) {

            System.out.println("Поток - " +name + " !получено: " + SharedMe.ai.getAndSet(i));

            SharedMe.inturist = i;
            System.out.println("Поток - " + name + " интурист = " + SharedMe.inturist);
        }
    }
}