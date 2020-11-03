package lab5.producer_consumer.buffer_int;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer monitor) {
        this.buffer = monitor;
    }

    @Override
    public void run() {
        while (true) {
            int data = this.buffer.consume();
            System.out.println(Thread.currentThread().getName() + " consumed: " + data);
        }
    }
}
