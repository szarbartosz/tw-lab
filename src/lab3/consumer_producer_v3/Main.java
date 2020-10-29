package lab3.consumer_producer_v3;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Thread[] producers = new Thread[10];
        Thread[] consumers = new Thread[10];

        for (int i = 0; i < 10; i ++) {
            producers[i] = new Thread(new Producer(monitor));
            consumers[i] = new Thread(new Consumer(monitor));
        }

        for (int i = 0; i < 10; i++) {
            producers[i].start();
            consumers[i].start();
        }
    }
}
