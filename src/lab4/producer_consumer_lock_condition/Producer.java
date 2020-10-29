package lab4.producer_consumer_lock_condition;

import java.util.Random;

public class Producer implements Runnable{
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            int chunk = rand.nextInt(10);
            try {
                this.buffer.produce(chunk);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
