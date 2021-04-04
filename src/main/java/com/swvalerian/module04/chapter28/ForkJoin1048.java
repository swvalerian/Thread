package main.java.com.swvalerian.module04.chapter28;
// стратегия "разделяй и властвуй", класс recursiveAction
// правда я нихрена пока не понимаю (((

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class SqrtTransform extends RecursiveAction {
    final int seqThreshold = 100_000; // в примере из книги было значение = 1000, а я опытным путем получил, что оптимально 100_000
    //А ИДЕАЛЬНО = 1_000_000 - т.е. сделать всё в одном потоке!
    // обрабатываемый массив
    double[] data;

    int start, end; // определяем часть обрабатываемых данных

    SqrtTransform(double[] vals, int s, int e) {
        data = vals;
        start = s;
        end = e;
    }

    // этот самый метод, выполняет параллельное вычисление
    @Override
    protected void compute() {
        // если количество элементов меньше порогового значения, выполнить дальнейшуюю обработку последовательно
        if ((end - start) < seqThreshold) {
            // преобразуем значение каждого элемента массива в его квадратный корень
            for (int i= start; i < end; i++) {
                data[i] = Math.sqrt(data[i]);
            }
        }
        else // в противном случае, продолжим разделение данных на меньшие части
        {
            // найдем середину
            int middle = (start+end) / 2;

            System.out.println(middle);

            // запустим новые задачи на выполнение , используя разделенные на части данные
            // один поток будет обрабатывать данные с начало и до середины, а второй поток - с середины и до конца, что и указали в параметрах аргументами
            invokeAll(new SqrtTransform(data, start, middle), new SqrtTransform(data, middle, end));
        }
    }
}

public class ForkJoin1048 {
    public static void main(String[] args) {
        // демонстрация параллельного выполнения
        // создаем ПУЛЛ задач!
        ForkJoinPool fjp = new ForkJoinPool();
        double[] nums = new double[1_000_000];

        // инициализируем наши данные
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (double) i;
        }

        System.out.println("Часть исходной последовательности:");
        for (int i = 0; i < 100; i++) {
            System.out.print(nums[i] + " : ");
        }
        System.out.println("\n");

        SqrtTransform task = new SqrtTransform(nums, 0 , nums.length);

        long beginT, endT;

        beginT = System.currentTimeMillis();
        // запустим главную задачу типа ForkJoinTask на выполнение
        fjp.invoke(task);
        endT = System.currentTimeMillis();

        System.out.println("Часть преобразованной последовательности: ");
        for (int i = 0; i < 100; i++) {
            System.out.format("%.4f : ", nums[i]);
            System.out.println();
        }

        System.out.println("\n Время потрачено - " + (endT-beginT) + "м.с.");
        System.out.println("Доступно процессоров в системе = " + fjp.getParallelism());
    }
}
