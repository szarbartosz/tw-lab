package lab6.producers_consumers;

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
        while (true) {
            int dataSize = random.nextInt(this.buffer.getProdMax() - this.buffer.getProdMin() + 1) + this.buffer.getProdMin();
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(random.nextInt(10));
            }
            try {
                this.buffer.produce(data);
//                System.out.println(Thread.currentThread().getName() + " produced: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
