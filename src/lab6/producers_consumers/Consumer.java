package lab6.producers_consumers;

import java.util.List;
import java.util.Random;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int dataSize = random.nextInt(this.buffer.getConsMax() - this.buffer.getConsMin() + 1) + this.buffer.getConsMin();
            try {
                List<Integer> data = this.buffer.consume(dataSize);
//                System.out.println(Thread.currentThread().getName() + " consumed: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
