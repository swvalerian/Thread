package main.java.com.swvalerian.module04.chapter11;

class Q {
    int n;
    boolean valueSet = false;

    synchronized int get() {
        while (!valueSet) { // изначально условие = труе, поток в ожидание уходит
            try { // после того как значение отошлется придет уведомление о старте и выполнение этого потока продолжиться далее (т.о. это будет первый проход)
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Получено: " + n); // ВЫВЕДЕМ первое полученное значение

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        valueSet = false; // вновь подготовим переменную для ухода в сон этого потока
        notify(); // ну и кинем второму потоку команду продолжить выполнение.

        return n;
    }

    synchronized void put(int n) {
        while (valueSet) { // изначально условие ложь, поэтому не выполняется, второй заход сюда - покажет что у нас труе и второй поток встанет на паузу
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.n = n;
        System.out.println("Отправлено: " + n); // отправим значение, это будет сделано в первую очередь

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        valueSet = true; // изменим значение на труе, второй проход уже будет труе
        notify(); // кинем первому потоку команду на старт
    }
}

class Producer implements Runnable {
    Thread t;
    Q q;
    Producer(Q q) {
        this.q = q;
        t = new Thread(this, "Продюсер");
        t.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            q.put(i);
            i++;
        }
    }
}
class Consumer implements Runnable {
    Q q;
    public Consumer(Q q) {
        this.q = q;
        new Thread(this, "Потребитель!").start(); // такой вот еще способ создания потока
    }

    @Override
    public void run() {
        while (true) {
            q.get();
        }
    }
}
public class ProduceConsumer307 {
    public static void main(String[] args) {
        Q q = new Q();
        new Consumer(q);
        new Producer(q);
        System.out.println("Ддля остановки нажмите Ctrl+C");
    }
}
