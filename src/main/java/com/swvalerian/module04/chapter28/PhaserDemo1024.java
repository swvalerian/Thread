package main.java.com.swvalerian.module04.chapter28;


import java.util.concurrent.Phaser;

public class PhaserDemo1024 {
    public static void main(String[] args) {
        Phaser phsr = new Phaser(1);
        int curPhsr;
        System.out.println("Запуск потоков!");

        new MuayTai(phsr,"AAA");
        new MuayTai(phsr,"BBB");
        new MuayTai(phsr,"CCC");

        curPhsr = phsr.getPhase();
        phsr.arriveAndAwaitAdvance(); // ожидаем, когда все потоки завершат исполнение первой фазы
        System.out.println("Фаза " + curPhsr + " завершена!");

        curPhsr = phsr.getPhase();
        phsr.arriveAndAwaitAdvance(); // ожидаем, когда все потоки завершат исполнение первой фазы
        System.out.println("Фаза " + curPhsr + " завершена!");

        curPhsr = phsr.getPhase();
        phsr.arriveAndAwaitAdvance(); // ожидаем, когда все потоки завершат исполнение первой фазы
        System.out.println("Фаза " + curPhsr + " завершена!");

        // снимем с регистрации синхрофазатрон
        phsr.arriveAndDeregister();

        if (phsr.isTerminated()) {
            System.out.println("C синхрофазатроном покончено!");
        }
    }
}

class MuayTai implements Runnable {
    Phaser pshr;
    String name;
    MuayTai(Phaser ps, String n) {
        this.pshr = ps;
        this.name = n;

        pshr.register(); // регистрация СинхроФАзатрона, в обьекте, который будет исполнять поток
        new Thread(this).start();
    }
    @Override
    public void run() {
        System.out.println("Потока " + name + " начинает первую фазу");
        for (long i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        pshr.arriveAndAwaitAdvance(); // известить о достижении фазы
        System.out.println("Поток " + name + " уже в фазе, и сейчс будет пауза 1сек");
        // небольшая пауза для того, чтоб было более понятно принцип работы
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Потока " + name + " начинает вторую фазу");
        pshr.arriveAndAwaitAdvance(); // известить о достижении фазы
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Потока " + name + " начинает третью фазу");
        pshr.arriveAndDeregister(); // известить о достижении фазы
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Последняя фаза, последняя пауза и последний поток завершились уже ");
    }
}