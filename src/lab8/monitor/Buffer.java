package lab8.monitor;

import lab8.utilities.StatisticsUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Buffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;

    private final int prodDuration;
    private final int consDuration;
    private final int toProduce;
    private final int toConsume;
    private int alreadyProduced;
    private int alreadyConsumed;

    private final long prodTimer;
    private final long consTimer;

    private final StatisticsUtility statisticsUtility;

    private boolean firstProducerOccupied = false;
    private boolean firstConsumerOccupied = false;

    public int getCapacity() {
        return this.capacity;
    }

    public Buffer(int capacity, int toProduce, int toConsume, int prodDuration, int consDuration, StatisticsUtility statisticsUtility) {
        this.capacity = capacity;

        this.prodDuration = prodDuration;
        this.consDuration = consDuration;
        this.toProduce = toProduce;
        this.toConsume = toConsume;

        this.prodTimer = System.currentTimeMillis();
        this.consTimer = System.currentTimeMillis();
        this.alreadyProduced = 0;
        this.alreadyConsumed = 0;

        this.statisticsUtility = statisticsUtility;
    }

    private boolean hasEnoughSpace(int dataSize) {
        return capacity - buffer.size() >= dataSize;
    }

    private boolean hasEnoughData(int dataSize) {
        return buffer.size() >= dataSize;
    }


    final ReentrantLock lock = new ReentrantLock();

    final Condition firstProducer = lock.newCondition();
    final Condition firstConsumer = lock.newCondition();

    final Condition  restProducers = lock.newCondition();
    final Condition  restConsumers = lock.newCondition();


    public void produce(List<Integer> data) throws InterruptedException {
        lock.lock();
        try {
            while (firstProducerOccupied) restProducers.await();
            while (!hasEnoughSpace(data.size())) {
                firstProducerOccupied = true;
                firstProducer.await();
            }
            firstProducerOccupied = false;

            buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
            alreadyProduced += data.size();
            Thread.sleep(this.getProdDuration() * data.size());

            if (alreadyProduced >= toProduce) {
                this.statisticsUtility.setMonitorTotalProductionTime(System.currentTimeMillis() - prodTimer);
            }

            restProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> consume(int dataSize) throws InterruptedException {
        lock.lock();
        try {
            while (firstConsumerOccupied) restConsumers.await();
            while (!hasEnoughData(dataSize)) {
                firstConsumerOccupied = true;
                firstConsumer.await();
            }
            firstConsumerOccupied = false;

            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(buffer.remove());
            }
            alreadyConsumed += dataSize;
            Thread.sleep(this.getConsDuration() * dataSize);

            if(alreadyConsumed >= toConsume) {
                this.statisticsUtility.setMonitorTotalConsumptionTime(System.currentTimeMillis() - consTimer);
            }

            restConsumers.signal();
            firstProducer.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }

    private int getProdDuration() {
        return this.prodDuration;
    }

    private int getConsDuration() {
        return this.consDuration;
    }
}
