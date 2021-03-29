package main.java.com.swvalerian.module04.chapter11;
class CallMe {
    /*synchronized*/ public void Call(String msg) {
        System.out.print("[" + msg);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("]");
    }
}
class Caller implements Runnable {
    String msg;
    CallMe callMe;
    Thread t;

    public Caller(CallMe ob, String msg) {
        this.msg = msg;
        callMe = ob;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        synchronized (callMe) {
            callMe.Call(msg);
        }
    }
}

public class Synch302 {
    public static void main(String[] args) {
        CallMe c = new CallMe();
        Caller caller1 = new Caller(c, "Добро пожаловать");
        Caller caller2 = new Caller(c, "В многопоточный");
        Caller caller3 = new Caller(c, "мир JAVA!");

        try {
            caller1.t.join();
            caller2.t.join();
            caller3.t.join();
            System.out.println("Все параллельный исполнения закончились удачно");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершена успешно!");
    }
}
