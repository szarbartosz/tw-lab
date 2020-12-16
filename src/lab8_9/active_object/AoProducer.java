package lab8_9.active_object;

import lab8_9.utilities.StatisticsUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static lab8_9.utilities.ExtraTaskUtility.performExtraTask;

public class AoProducer extends Thread {

    private final Proxy proxy;
    private final int maxDataSize;
    private final int extraTaskDuration;

    private final StatisticsUtility statisticsUtility;

    public AoProducer(Proxy proxy, Servant servant, int extraTaskDuration, StatisticsUtility statisticsUtility) {
        this.proxy = proxy;
        this.maxDataSize = servant.getBufferCapacity() / 2;
        this.extraTaskDuration = extraTaskDuration;
        this.statisticsUtility = statisticsUtility;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            List<Integer> data = new LinkedList<>();
            int dataSize = random.nextInt(maxDataSize) + 1;
            for (int i = 0; i < dataSize; i++) {
                data.add(random.nextInt(10));
            }

            Future future = this.proxy.produce(data);

            while (!future.isReady()) {
                performExtraTask(extraTaskDuration);
                this.statisticsUtility.incrementAoExtraProductionTasksCounter();
            }
        }
    }
}