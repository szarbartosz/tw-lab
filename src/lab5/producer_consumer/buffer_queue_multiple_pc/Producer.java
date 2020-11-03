package lab5.producer_consumer.buffer_queue_multiple_pc;

import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int data = random.nextInt(10);
            this.buffer.produce(data);
            System.out.println(Thread.currentThread().getName() + " produced: " + data);
        }
    }
}
