package lab7.active_object;

import java.util.List;
import java.util.Random;

public class Consumer extends Thread {

    private final Proxy proxy;
    private final int maxDataSize;

    public Consumer(Proxy proxy, Servant servant) {
        this.proxy = proxy;
        this.maxDataSize = servant.getBufferCapacity() / 2;
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            int dataSize = random.nextInt(maxDataSize) + 1;
            Future future = this.proxy.consume(dataSize);

            while (!future.isReady()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("liczę sobie cosinusa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<Integer> data = future.getData();
            System.out.println(Thread.currentThread().getName() + " consumed: " + data);
            System.out.println("consumed: " + dataSize + " elements");
        }
    }
}
