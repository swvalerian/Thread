package main.java.com.swvalerian.module04.chapter11;

import java.util.concurrent.Semaphore;

class KuKu {
    int n;
    static Semaphore semConsumer = new Semaphore(0); // этот симафор изначально недоступен никому!
    static Semaphore semProducer = new Semaphore(1); // у этого симафора есть 1 поток на разрешение

    void get() {
        try {
            semConsumer.acquire();
        } catch (InterruptedException e) {
            System.err.println("Потребитель послал исключение");
        }
        // ниже код выполнится только после того, как будет получен семафор semConsumer
        System.out.println("Получено - " + n);
//        semProducer.release(); // значит я все теперь понял, как работают семафоры! КРутая штука!
        // в книге тут нету комента, в книге семафор освобождается именно здесь, а я его освобождаю в методе put
    }

    void put(int n) {
        try {
            semProducer.acquire();
        } catch (InterruptedException e) {
            System.err.println("Исключение вызвал продюсер");
        }
        // ниже код выполнится только после того, как будет получен семафор semProducer
        this.n = n;
        System.out.println("Отправлено - " + n);
        semConsumer.release(); // и вот теперь - продолжит работут метод потребителя
        // ниже я заменил таким образом код из книги.
//        даю паузу в 1 секунду, чтоб поток ПОЛЬЗОВАТЕЛЯ успел прочитать то, что мы отослали, а после паузы - я
//        освобождаю семафор который ждет метод put
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // вот я его освобождаю после паузы!
        semProducer.release();  // Эврика, я понял как работают семофоры!
    }
}

class Producer2 implements Runnable {
    KuKu kuKu;

    Producer2(KuKu kuKu) {
        this.kuKu = kuKu;
        new Thread(this, "Producer").start();
    }

    public void run() {
        for (int i=0; i<20; i++) {
            this.kuKu.put(i);
        }
    }
}

class Consumer2 implements Runnable {
    KuKu kuKu;

    Consumer2(KuKu kuku) {
        this.kuKu = kuku;
        new Thread(this, "Consumer2").start();
    }

    @Override
    public void run() {
        for (int i = 20; i>0; i--) {
            // получаем
            kuKu.get();
        }
    }
}

public class ProdConsumDemo1013 {
    public static void main(String[] args) {
        KuKu k = new KuKu();
        Consumer2 con = new Consumer2(k);
        Producer2 pr = new Producer2(k);

    }
}
