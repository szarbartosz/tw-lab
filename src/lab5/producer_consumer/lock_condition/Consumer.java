package lab5.producer_consumer.lock_condition;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        while (true) {
            try {
                int data = this.buffer.consume();
                System.out.println(Thread.currentThread().getName() + " consumed: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
