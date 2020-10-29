package lab4.producer_consumer;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            int chunk = this.buffer.consume();

        }
    }
}
