package main.java.com.swvalerian.module04.chapter11;

// создам второй поток, который реализуется класс ранэйбл
class NewThread implements Runnable {
    Thread t;

    NewThread() {
        t = new Thread(this, "Valerik");
        System.out.println("Дочерний поток создан: " + t + " prioritet = " + t.getPriority() );
        t.start();// стартанем поток! передадим управление в метод Run
    }

    public void run() {
        try {
            for (int i = 5; i >0; i--) {
                System.out.println("дочка -> " + i);
                Thread.sleep(2300);
            }
        } catch (InterruptedException ex) {
            System.err.println("поймали из дочернего метода");
        }
        System.out.println("Дочерний поток заверешен успешно!");
    }
}

public class ThreadDemo293 {
    public static void main(String[] args) {
        NewThread nt = new NewThread(); // вот тут происходит запуск конструктора второго - дочернего потока

        try {
            for (int i = 5; i >0; i--) {
                System.out.println("ОСНОВНОЙ -> " + i);
                Thread.sleep(900);
            }
        } catch (InterruptedException ex) {
            System.err.println("Главный поток прерван, ух");
        }

        try {
            System.out.println("Ждем завершения дочки");
            nt.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Программа завершилась успешно. Основной поток закрыт.");
    }
}
