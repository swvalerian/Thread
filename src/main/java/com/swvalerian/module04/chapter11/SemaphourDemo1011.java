package main.java.com.swvalerian.module04.chapter11;

import java.util.concurrent.Semaphore;

// Пример применения семафора
public class SemaphourDemo1011 {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);

        new IncThread(sem, "A");
        new DecThread(sem, "B");
    }
}

// общий ресурс, с которым и будут эксперименты
class Shared {
    static int count = 0;
}

// поток исполнения который будет увеличивать значение счетчика на единицу
class IncThread implements Runnable {
    String name;
    Semaphore sem;
    // конструктор создаем
    IncThread(Semaphore s, String str) {
        sem = s;
        name = str;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока - " + name);
        try {
            // для начала нужно попробовать получить разрешение
            System.out.println("Поток - " + name + " ожидает разрешения...");
            sem.acquire(); // поток исполнения делает запрос!
            System.out.println("Поток - " + name + " получил разрешение.");

            for (int i = 0; i < 6; i++) {
                System.out.println(" i = " + i);
                System.out.println(name + " : " + Shared.count++);

                // поставим паузу и если можно, то переключим контекст
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // освободим семафор
        System.out.println("Потока " + name + " освобождает разрешение...");
        sem.release();
    }
}

class DecThread implements Runnable {
    Semaphore sem;
    String name;
    // конструктор
    DecThread(Semaphore sem, String name) {
        this.sem = sem;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока - " + name);
        try {
            // для начала нужно попробовать получить разрешение
            System.out.println("Поток - " + name + " ожидает разрешения...");
            sem.acquire(); // поток исполнения делает запрос!
            System.out.println("Поток - " + name + " получил разрешение.");

            for (int i = 6; i > 0; i--) {
                System.out.println("i = " + i);
                System.out.println(name + " : " + Shared.count--);

                // поставим паузу и если можно, то переключим контекст
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // освободим семафор
        System.out.println("Поток " + name + " освобождает разрешение...");
        sem.release();
    }
}