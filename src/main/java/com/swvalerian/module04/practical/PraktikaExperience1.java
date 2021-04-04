package main.java.com.swvalerian.module04.practical;

import java.util.concurrent.Semaphore;

public class PraktikaExperience1 {
    public static void main(String[] args) {
        Foo foo = new Foo();

        new Bbb(foo, "BBB");
        new Aaa(foo, "AAA");
        new Ccc(foo, "CCC");

    }
}

class Foo {
    static Semaphore sem1 = new Semaphore(1);
    static Semaphore sem2 = new Semaphore(0);
    static Semaphore sem3 = new Semaphore(0);

    public void first() {
        try {
            sem1.acquire(); // ждем семафора
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("First");
        sem2.release(); // когда поток завершил свои дела, лишь после этого, он выдаст разрешение на семафор
    }
    public void second() {
        try {
            sem2.acquire(); // и лишь когда нужный поток выполнит свои действия, и отдаст разрешение на семофор2 - тогда и продолжит работу второй поток
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Second");
        sem3.release(); // а закончив свои делишки, второй поток, даст добро - семофор три откроется.
    }
    public void third() {
        try {
            sem3.acquire(); // и тут же будет запущен поток номер три!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Third");
    }
}

class Aaa implements Runnable{
    String name;
    Foo foo;

    Aaa(Foo f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.first();
    }
}

class Bbb implements Runnable {
    String name;
    Foo foo;

    Bbb(Foo f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.second();
    }
}

class  Ccc implements Runnable {
    String name;
    Foo foo;

    Ccc(Foo f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.third();
    }
}