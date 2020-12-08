package lab8.monitor;

import java.util.List;
import java.util.Random;

import static lab8.utilities.ExtraTaskUtility.performExtraTask;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int extraWorkDuration;

    public Consumer(Buffer buffer, int extraTaskDuration) {
        this.buffer = buffer;
        this.extraWorkDuration = extraTaskDuration;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int dataSize = random.nextInt(this.buffer.getCapacity() / 2) + 1;
            try {
                List<Integer> data = this.buffer.consume(dataSize);
//                performExtraTask(extraWorkDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
