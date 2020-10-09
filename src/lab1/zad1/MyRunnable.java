package lab1.zad1;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("(MyRunnable) current thread id: " + Thread.currentThread().getId());
    }
}
