package lab3.consumer_producer;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Thread producer = new Thread(new Producer(monitor));
        Thread consumer = new Thread(new Consumer(monitor));

        producer.start();
        consumer.start();
    }
}
