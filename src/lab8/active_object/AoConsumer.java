package lab8.active_object;

import lab8.utilities.StatisticsUtility;

import java.util.List;
import java.util.Random;

import static lab8.utilities.ExtraTaskUtility.performExtraTask;

public class AoConsumer extends Thread {

    private final Proxy proxy;
    private final int maxDataSize;
    private final int extraWorkDuration;

    private final StatisticsUtility statisticsUtility;

    public AoConsumer(Proxy proxy, Servant servant, int extraWorkDuration, StatisticsUtility statisticsUtility) {
        this.proxy = proxy;
        this.maxDataSize = servant.getBufferCapacity() / 2;
        this.extraWorkDuration = extraWorkDuration;
        this.statisticsUtility = statisticsUtility;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int dataSize = random.nextInt(maxDataSize) + 1;
            Future future = this.proxy.consume(dataSize);

            while (!future.isReady()) {
                performExtraTask(extraWorkDuration);
                this.statisticsUtility.incrementAoExtraConsumptionTasksCounter();
            }

            List<Integer> data = future.getData();
        }
    }
}
