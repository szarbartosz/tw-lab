package lab4.producer_consumer_multibuffer;

import java.util.Random;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            int chunkSize = rand.nextInt(10);
            try {
                this.buffer.consume(chunkSize);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
