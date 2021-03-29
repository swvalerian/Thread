package main.java.com.swvalerian.module04.chapter11;

class NewThreadM implements Runnable {
    String name;
    Thread t;

    NewThreadM(String name) {
        this.name = name;
        t = new Thread(this, name);
        System.out.println("Поток " + name + " запущен " + t);
        t.start();
    }

    public void run() {
        try {
            for (int i = 5; i >0; i--) {
                System.out.println(t.getName() + ":" + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class MultiThread297 {
    public static void main(String[] args) {
        NewThreadM nt1 = new NewThreadM("Первый пошел.!.");
        NewThreadM nt2 = new NewThreadM("Второй пошел.!.");
        NewThreadM nt3 = new NewThreadM("Третий пошел.!.");

        System.out.println("Поток 1 запущен = " + nt1.t.isAlive());
        System.out.println("Поток 2 запущен = " + nt2.t.isAlive());
        System.out.println("Поток 3 запущен = " + nt3.t.isAlive());

        try {
            System.out.println("Ожидание завершения потоков");
            nt1.t.join();
            nt2.t.join();
            nt3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван! увы");
        }
        System.out.println("Программа завершена успешно");
    }
}
