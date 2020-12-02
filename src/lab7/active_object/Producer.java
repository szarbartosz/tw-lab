package lab7.active_object;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Producer extends Thread {

    private final Proxy proxy;
    private final int maxDataSize;

    public Producer(Proxy proxy, Servant servant) {
        this.proxy = proxy;
        this.maxDataSize = servant.getBufferCapacity() / 2;
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            List<Integer> data = new LinkedList<>();
            int dataSize = random.nextInt(maxDataSize) + 1;
            for (int i = 0; i < dataSize; i++) {
                data.add(random.nextInt(10));
            }

            Future future = this.proxy.produce(data);

            while (!future.isReady()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("liczę sobie sinusa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " produced: " + data);
            System.out.println("produced: " + dataSize + " elements");
        }
    }
}