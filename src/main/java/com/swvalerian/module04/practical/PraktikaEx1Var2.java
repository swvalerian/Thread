package main.java.com.swvalerian.module04.practical;

import java.util.concurrent.Semaphore;

public class PraktikaEx1Var2 {
    public static void main(String[] args) {
        Foo foo = new Foo();

        new Aaa(foo, "AAA");
        new Bbb(foo, "BBB");
        new Ccc(foo, "CCC");
    }
}

class Foo1 {
    static Semaphore sem1 = new Semaphore(3, true);

    public void first() {
        try {
            sem1.acquire(); // ждем семафора
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("First");
        sem1.release(2); // когда поток завершил свои дела, лишь после этого, он выдаст разрешение на семафор
    }
    public void second() {
        try {
            sem1.acquire(2); // и лишь когда нужный поток выполнит свои действия, и отдаст разрешение на семофор2 - тогда и продолжит работу второй поток
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Second");
        sem1.release(3); // а закончив свои делишки, второй поток, даст добро - семофор три откроется.
    }
    public void third() {
        try {
            sem1.acquire(3); // и тут же будет запущен поток номер три!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Third");
        sem1.release(3);
    }
}

class Aaa1 implements Runnable{
    String name;
    Foo1 foo;

    Aaa1(Foo1 f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.first();
    }
}

class Bbb1 implements Runnable {
    String name;
    Foo1 foo;

    Bbb1(Foo1 f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.second();
    }
}

class  Ccc1 implements Runnable {
    String name;
    Foo1 foo;

    Ccc1(Foo1 f, String name) {
        foo = f;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        foo.third();
    }
}