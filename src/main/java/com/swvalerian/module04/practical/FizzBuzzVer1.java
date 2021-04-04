package main.java.com.swvalerian.module04.practical;

public class FizzBuzzVer1 {
    public static void main(String[] args) {
        FizzBuzz fb = new FizzBuzz(15);

        // Потоки висят постоянно в памяти и делают свое дело. Работают паралельно
        A a = new A(fb);
        B b = new B(fb);
        C c = new C(fb);
        D d = new D(fb);

        for (int i = 0; i < fb.n; i++) {
            FizzBuzz.item = i + 1; // заморочка со счетчиком, чтоб наверняка успевали обработать последний элемент.
            fb.nextItem = true; // вот после этого, сработает случайный метод одного из четырех потоков,
            while (fb.nextItem != false) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // с устаревшим методом stop работает четко! другие способы порою выводят в конец списка следующее число
        a.t.stop();
        b.t.stop();
        c.t.stop();
        d.t.stop();
    }
}

class A implements Runnable {
    FizzBuzz fizzBuzz;
    Thread t;

    A (FizzBuzz fb) {
        fizzBuzz = fb;
        t = new Thread(this, "AAA");
        t.start();
    }

    @Override
    public void run() {
        while (FizzBuzz.item <= fizzBuzz.n) {
            if ( fizzBuzz.nextItem == true) {
                fizzBuzz.fizzBuzz();
            }
        }
    }
}

// этот поток видоизменяет числовое представление цифры 3
class B implements Runnable {
    FizzBuzz fizzBuzz;
    Thread t;
    B (FizzBuzz fb) {
        fizzBuzz = fb;
        t = new Thread(this, "BBB");
        t.start();
    }

    @Override
    public void run() {
        // поток будет выполняться до тех пор, пока итератор меньше либо равен статической переменной, которая создается при создании обьекта.
        // как только будет больше итератор - поток закончит выполнение...
        while (FizzBuzz.item <= fizzBuzz.n) {
            if ( fizzBuzz.nextItem == true) {
                fizzBuzz.fizz();
            }
        }
    }
}

class C implements Runnable {
    FizzBuzz fizzBuzz;
    Thread t;
    C (FizzBuzz fb) {
        fizzBuzz = fb;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (FizzBuzz.item <= fizzBuzz.n) {
            if ( fizzBuzz.nextItem == true) {
                fizzBuzz.buzz();
            }
        }
    }
}

class D implements Runnable {
    FizzBuzz fizzBuzz;
    Thread t;
    D (FizzBuzz fb) {
        fizzBuzz = fb;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (FizzBuzz.item <= fizzBuzz.n) {
            if ( fizzBuzz.nextItem == true) {
                fizzBuzz.number();
            }
        }
    }
}

// основной, общий класс, методы которого вызывают различные потоки
class FizzBuzz {
    int n;
    static volatile int item; // если эту переменную не сделать volatile - программа зависнит намертво. А так - идет работа с монитором
    boolean nextItem;

    public FizzBuzz(int n) {
        this.n = n;
        nextItem = false;
    }

    public void fizzBuzz() {
        if ((item % 3)==0 && (item % 5)==0) {
            System.out.print("fizzbuz,");
            nextItem = false;
        }
    }

    public void fizz() {
        if ((item % 3) == 0 && (item % 5) != 0 ) {
            System.out.print("fizz,");
            nextItem = false;
        }
    }

    public void buzz() {
        if ((item % 5) == 0 && (item % 3) != 0 ) {
            System.out.print("buzz,");
            nextItem = false;
        }
    }

    public void number() {
        if (((item % 5) != 0) && ((item % 3) != 0 )) {
            System.out.print(item + ",");
            nextItem = false;
        }
    }
}



/*

// версия первая! работает. но неверно, потому как - потоки не постоянны, а каждый раз создаются новые, выполняются один раз и завершаются, а не висят в памяти постоянно

package main.java.com.swvalerian.module04.practical;

public class FizzBuzzVer1 {
    public static void main(String[] args) {
        FizzBuzz fb = new FizzBuzz(15);

        for (int i = 1; i <= fb.n; i++) {
            FizzBuzz.item = i;
            new A(fb);
            new B(fb);
            new C(fb);
            new D(fb);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class A implements Runnable {
    FizzBuzz fizzBuzz;
    A (FizzBuzz fb) {
        fizzBuzz = fb;
        new Thread(this).start();
    }

    @Override
    public void run() {
        fizzBuzz.fizzBuzz();
    }
}
class B implements Runnable {
    FizzBuzz fizzBuzz;
    B (FizzBuzz fb) {
        fizzBuzz = fb;
        new Thread(this).start();
    }

    @Override
    public void run() {
        fizzBuzz.fizz();
    }
}
class C implements Runnable {
    FizzBuzz fizzBuzz;
    C (FizzBuzz fb) {
        fizzBuzz = fb;
        new Thread(this).start();
    }

    @Override
    public void run() {
        fizzBuzz.buzz();
    }
}
class D implements Runnable {
    FizzBuzz fizzBuzz;

    D (FizzBuzz fb) {
        fizzBuzz = fb;
        new Thread(this).start();
    }

    @Override
    public void run() {
        fizzBuzz.number();
    }
}

class FizzBuzz {
    int n;
    static volatile int item;

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizzBuzz() {
        if ((item % 3)==0 && (item % 5)==0) {
            System.out.print("fizzbuz" + item + ",");
        }
    }
    public void fizz() {
        if ((item % 3) == 0 && (item % 5) != 0 ) {
            System.out.print("fizz" + item + ",");
        }
    }
    public void buzz() {
        if ((item % 5) == 0 && (item % 3) != 0 ) {
            System.out.print("buzz" + item + ",");
        }
    }
    public void number() {
        if (((item % 5) != 0) && ((item % 3) != 0 )) {
            System.out.print(item + ",");
        }
    }
}*/
