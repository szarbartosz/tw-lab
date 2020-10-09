package lab1.zad1;

public class Main {
    public static void main(String[] args) {
        runThreads(10);
        runThreadsRunnable(10);
    }

    public static void runThreads(int numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            new MyThread().start();
        }
    }

    public static void runThreadsRunnable(int numberOfThreads) {
        Runnable[] runners = new Runnable[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            runners[i] = new MyRunnable();
            new Thread(runners[i]).start();
        }
    }
}

