package lab8_9.monitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final int extraWorkDuration;

    public Producer(Buffer buffer, int extraTaskDuration) {
        this.buffer = buffer;
        this.extraWorkDuration = extraTaskDuration;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int dataSize = random.nextInt(this.buffer.getCapacity() / 2) + 1;
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(random.nextInt(10));
            }
            try {
                this.buffer.produce(data);
//                performExtraTask(extraWorkDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
