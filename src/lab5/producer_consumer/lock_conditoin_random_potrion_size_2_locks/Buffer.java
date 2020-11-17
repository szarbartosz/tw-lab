package lab5.producer_consumer.lock_conditoin_random_potrion_size_2_locks;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Buffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;
    private boolean firstProducerPresent = false;
    private boolean firstConsumerPresent = false;


    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    private boolean hasEnoughSpace(int dataSize) {
        return capacity - buffer.size() >= dataSize;
    }

    private boolean hasEnoughData(int dataSize) {
        return buffer.size() >= dataSize;
    }

    public void setProducerPresent(boolean isAbsent) {
        this.firstProducerPresent = isAbsent;
    }

    public void setConsumerPresent(boolean isAbsent) {
        this.firstConsumerPresent = isAbsent;
    }

    final Lock firstLock = new ReentrantLock();
    final Condition firstProducer = firstLock.newCondition();
    final Condition firstConsumer = firstLock.newCondition();

    final Lock restLock = new ReentrantLock();
    final Condition restProducers = restLock.newCondition();
    final Condition restConsumers = restLock.newCondition();

    public void produce(List<Integer> data) throws InterruptedException {
        firstLock.lock();
        restLock.lock();
        try {
            if (firstProducerPresent) restProducers.await();
            while (!hasEnoughSpace(data.size())) firstProducer.await();
            buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
            restProducers.signal();
            setProducerPresent(true);
            firstConsumer.signal();
            setConsumerPresent(false);
        } finally {
            firstLock.unlock();
            restLock.unlock();
        }
    }

    public List<Integer> consume(int dataSize) throws InterruptedException {
        firstLock.lock();
        restLock.lock();
        try {
            if (firstConsumerPresent) restConsumers.await();
            while (!hasEnoughData(dataSize)) firstConsumer.await();
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(buffer.remove());
            }
            restConsumers.signal();
            setConsumerPresent(true);
            firstProducer.signal();
            setProducerPresent(false);
            return data;
        } finally {
            firstLock.unlock();
            restLock.unlock();
        }
    }
}
