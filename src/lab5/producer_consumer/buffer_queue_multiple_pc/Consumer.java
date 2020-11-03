package lab5.producer_consumer.buffer_queue_multiple_pc;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            int data = this.buffer.consume();
            System.out.println(Thread.currentThread().getName() + " consumed: " + data);
        }
    }
}
