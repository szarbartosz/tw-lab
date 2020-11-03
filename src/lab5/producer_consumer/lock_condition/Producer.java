package lab5.producer_consumer.lock_condition;

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
            try {
                this.buffer.produce(data);
                System.out.println(Thread.currentThread().getName() + " produced: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
