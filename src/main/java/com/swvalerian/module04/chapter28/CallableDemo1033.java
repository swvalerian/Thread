package main.java.com.swvalerian.module04.chapter28;

import java.util.concurrent.*;

public class CallableDemo1033 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService ex = Executors.newFixedThreadPool(3);// одновременно могут выполняться только три процесса, которые и вызовет экземпляр этого класса
        Future<Integer> f1;
        Future<Double> f2;
        Future<Integer> f3;

        System.out.println("Запуск");

        f1 = ex.submit(new Sum(9));
        f2 = ex.submit(new Hypot(4.0, 6.0));
        f3 = ex.submit(new Factorial(8));

        System.out.println(f1.get(1000, TimeUnit.SECONDS));
        System.out.println(f2.get(3000, TimeUnit.SECONDS));
        System.out.println(f3.get(2000, TimeUnit.SECONDS));

        ex.shutdown();

        System.out.println("Завершение");
    }
}

class Sum implements Callable<Integer> {
    int stop;
    Sum(int stop) {
        this.stop = stop;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= stop; i++) {
            sum += i;
        }
        return sum;
    }
}

class Hypot implements Callable<Double> {
    Double side1, side2;
    Hypot(Double s1, Double s2) {
        side1 = s1;
        side2 = s2;
    }

    @Override
    public Double call() throws Exception {
        return Math.sqrt((side1*side1) + (side2*side2));
    }
}

class Factorial implements Callable<Integer> {
    int stop;
    Factorial(int v) {
        stop = v;
    }
    @Override
    public Integer call() throws Exception {
        int fact = 1;
        for (int i = 2; i<= stop; i++ ) {
            fact *= i;
        }
        return fact;
    }
}