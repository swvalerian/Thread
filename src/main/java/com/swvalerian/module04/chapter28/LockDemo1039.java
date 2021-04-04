package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo1039 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new LockThread(lock, "AAA");
        new LockThread(lock, "BBB");
    }
}

// общий ресурс
class Sharedd {
    static int count = 0;
}

// поток инкремент счетчика - обещго ресурса
class LockThread implements Runnable {
    String name;
    ReentrantLock reentrantLock;
    LockThread(ReentrantLock rl, String n) {
        reentrantLock = rl;
        name = n;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока - " + name); // все потоки выведут это сообщение и перейдут в пазу на 3 сек
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // потока который первый получит блокировку - тот и будет первым работать, остальные будут тупо ожидать когда освободится блокировка
        System.out.println("Поток - " + name + " ожидает блокировки счетчика");
        reentrantLock.lock(); // блокируем наш ресурс
        System.out.println("Поток - " + name + " блокирует счетчик и три секунды выжидает, наслаждаясь обладанием блокировки");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Shared.count++;
        System.out.println("Поток " + name + " :: " + Shared.count );
        System.out.println("\n попытка переключить контекст, если это возможно \n");
        System.out.println("Поток " + name + " ожидает 3 сек");
        System.out.println("а ничего не произойдет... блокировка рулит!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток " + name +"разблокирует счетчик через 3 сек");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reentrantLock.unlock();
        System.out.println("Поток " + name +"разблокировал счетчик!"); // это сообщение обычно выводится уже после того, как
        // другой поток - захвативший блокировку, отпишется о том, что он обладает блокировкой
        // для красоты - его лучше написать перед методом разблокировки счетчика
    }
}