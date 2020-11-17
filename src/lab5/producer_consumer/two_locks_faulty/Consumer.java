package lab5.producer_consumer.two_locks_faulty;

import javax.swing.plaf.TableHeaderUI;
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
            int dataSize = random.nextInt(this.buffer.getCapacity()/2 - this.buffer.getCapacity()/3 + 1) + this.buffer.getCapacity()/3;
            try {
                List<Integer> data = this.buffer.consume(dataSize);
                System.out.println(Thread.currentThread().getName() + " consumed: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
