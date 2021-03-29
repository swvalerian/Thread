package main.java.com.swvalerian.module04.chapter11;

class Incremenator extends Thread {
    private volatile boolean mIsInc = true;
    private volatile boolean mFinish = false;

    public void changeAction() { // метод меняет переменную на противоположную (меняем действие)
        mIsInc = !mIsInc;
    }

    public void finish() { // инициируем завершение потока исполнения
        mFinish = true;
    }

    @Override
    public void run() {
        do {
            if (!mFinish) { // пока не будет команды к завершению - делаем!
                if (mIsInc) {
                    IncremenatorHabr.mValue++;
                } else {
                    IncremenatorHabr.mValue--;
                }
                System.out.print(IncremenatorHabr.mValue + " ");
            } else {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true);
    }
}

public class IncremenatorHabr {
    static int mValue = 0;
    static Incremenator mInc; // второстепенный поток

    public static void main(String[] args) {
        mInc = new Incremenator();

        System.out.println("Значение = ");
        mInc.start(); // здесь точка входа во вторичный поток

        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(i * 2 * 1000);
                mInc.changeAction(); // меняем действие инкремента на противоположное
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mInc.finish(); // инициация завершения побочного потока
    }
}
