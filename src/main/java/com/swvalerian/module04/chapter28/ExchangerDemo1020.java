package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.Exchanger;

public class ExchangerDemo1020 {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();
        new MakeString(exchanger);
        new UseString(exchanger);
    }
}

class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;
    MakeString(Exchanger<String> ex) {
        this.ex = ex;
        str = new String();
        new Thread(this).start();
    }
    @Override
    public void run() {
        char ch = 'A';
        for (int i = 0; i <5; i++) {
            for (int j = 0; j < 5; j++) {
                str += (char) ch++;
            }
            try {
                System.out.println("Отправил - " + str);
                str = ex.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class UseString implements Runnable {
    Exchanger<String> ex;
    String str;
    UseString(Exchanger<String> ex) {
        this.ex = ex;
        str = new String();
        new Thread(this).start();
    }
    @Override
    public void run() {
        for (int i = 0; i <5; i++) {
            try {
                str = ex.exchange(str); // попытка обменять пустой буфер на заполненный
                System.out.println("Получено: " + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
