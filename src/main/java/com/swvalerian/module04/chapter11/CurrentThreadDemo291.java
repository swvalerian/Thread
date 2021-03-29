package main.java.com.swvalerian.module04.chapter11;

public class CurrentThreadDemo291 {
    public static void main(String[] args) {
        Thread t = Thread.currentThread(); // получили текущий поток
        System.out.println(t.getName());
        System.out.println("Вывод данных о потоке" + t);

        t.setName("Valeriy");
        System.out.println("Change complit: " + t);

        try {
            for (int n = 5; n!= 0 ; n--) {
                System.out.println(n);
                Thread.sleep(1000); // организовали паузу потока в 1 секунду. Если во время паузы поступит запрос от
            }
//        от другого потока - тогда произойдет выброс исключения
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
