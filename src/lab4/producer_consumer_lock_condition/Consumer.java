package lab4.producer_consumer_lock_condition;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.buffer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
