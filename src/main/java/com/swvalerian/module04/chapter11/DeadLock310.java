package main.java.com.swvalerian.module04.chapter11;
class A {
    synchronized void foo(B b) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод A.foo() ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "Пытается вызвать метод B.last() ");

        b.last();
    }
    synchronized void last() {
        System.out.println("В методе A.last() ");
    }
}
class B {
    synchronized void bar(A a) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод B.bar() ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "Пытается вызвать метод A.last() ");

        a.last();
    }
    synchronized void last() {
        System.out.println("В методе B.last() ");
    }
}
class DeadLock implements Runnable {
    A a = new A();
    B b = new B();

    DeadLock() {
        Thread.currentThread().setName("Главный поток");
        Thread t = new Thread(this, "Соперничащий поток");
        t.start();

        a.foo(b); // получим блокировку для обьекта А в этом потоке исполнения
        System.out.println("Назад в главный поток!");
    }
    @Override
    public void run() {
        b.bar(a); // а здесь получим блокировку для обьекта B в другом потоке исполнения
    }
}
public class DeadLock310 {
    public static void main(String[] args) {
        new DeadLock();
    }
}
