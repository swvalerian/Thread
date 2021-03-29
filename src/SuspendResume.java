// Приостановка и возобновление исполнения потока современным способом

class NewThread implements Runnable {
    String name;
    Thread t;
    boolean suspendFlag;

    NewThread(String threadName) {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток - " + t);
        suspendFlag = false;
        t.start(); // запуска нового потока на исполнение
    }

    @Override // вот тут и осуществляется исполнение потока
    public void run() {
        try {
            for (int i = 15; i > 0; i--) {
                System.out.println("Поток - " + name + ": " + i);
                Thread.sleep(500);
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Прерван - " + name);
        }
    }

    synchronized void mySuspend() {
        suspendFlag = true;
    }

    synchronized void myResume() {
        notify();
        suspendFlag = false;
    }
}

public class SuspendResume {
    public static void main(String[] args) {
        NewThread nt1 = new NewThread("Первый");
        NewThread nt2 = new NewThread("Второй");

        try {
            Thread.sleep(1000);
            nt1.mySuspend();
            System.out.println("Приостановка потока 1");
            Thread.sleep(1000);
            System.out.println("Возобновим поток 1");
            nt1.myResume();
            System.out.println("Приостановка потока 2");
            nt2.mySuspend();
            Thread.sleep(1000);
            System.out.println("Возобновим поток 2");
            nt2.myResume();
        } catch (InterruptedException e) {
            System.out.println("Прерван главный поток");
        }

        try { // ожидание завершения потоков
            nt1.t.join();
            System.out.println("первый поток завершен");
            nt2.t.join();
            System.out.println("Оба потока завершены");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Главный поток завершен и программа бай-бай");
    }
}
