package lab2.wait_notify;

public class Main {
    public static void main(String[] args) {
        runThreads(1000);
    }

    public static void runThreads(int numberOfThreads) {
        Runnable[] runners = new Runnable[numberOfThreads];
        Thread[] threads = new Thread[numberOfThreads];
        IntOperation intOperation = new IntOperation();

        for (int i = 0; i < numberOfThreads; i++) {
            runners[i] = new MyRunnable(i, intOperation);
            threads[i] = new Thread(runners[i]);
            threads[i].start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        intOperation.print();
    }
}
