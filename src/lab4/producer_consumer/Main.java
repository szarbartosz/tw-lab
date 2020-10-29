package lab4.producer_consumer;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(1);

        int producers_no = 2;
        int consumers_no = 1;

        Thread[] threads = new Thread[producers_no + consumers_no];

        for (int i = 0; i < producers_no; i++) {
            threads[i] = new Thread(new Producer(buffer));
        }

        for (int i = producers_no; i < producers_no + consumers_no; i++) {
            threads[i] = new Thread(new Consumer(buffer));
        }

        for (Thread t : threads) {
            t.start();
        }
    }
}
