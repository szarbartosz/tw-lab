package lab5.producer_consumer.buffer_queue;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);
        Thread producer = new Thread(new Producer(buffer), "P1");
        Thread consumer = new Thread(new Consumer(buffer), "C1");

        producer.start();
        consumer.start();
    }
}
