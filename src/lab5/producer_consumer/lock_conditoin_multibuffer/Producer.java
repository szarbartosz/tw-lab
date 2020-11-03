package lab5.producer_consumer.lock_conditoin_multibuffer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true) {
            int dataSize = random.nextInt(this.buffer.getCapacity());
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(random.nextInt(10));
            }
            try {
                this.buffer.produce(data);
                System.out.println(Thread.currentThread().getName() + " produced: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
