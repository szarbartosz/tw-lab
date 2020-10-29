package lab4.producer_consumer_multibuffer;

import java.util.ArrayList;
import java.util.List;
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
            int chunkSize = rand.nextInt(10);
            List<Integer> chunk = new ArrayList<>();
            for (int i = 0; i < chunkSize; i ++) {
                chunk.add(rand.nextInt(10));
            }
            try {
                this.buffer.produce(chunk);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
