package lab1.zad1;

public class MyThread extends Thread {
    public void run(){
        System.out.println("(MyThread) current thread id: " + Thread.currentThread().getId());
    }
}
