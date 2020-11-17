package lab5.producer_consumer.two_locks_faulty;

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

    public int getCapacity() {
        return this.capacity;
    }

    public Buffer(int capacity) {
        this.capacity = capacity;
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
            while (lock.hasWaiters(firstProducer)) restProducers.await();
            while (!hasEnoughSpace(data.size())) {
                firstProducer.await();
            }
            System.out.println("firstProducer queue: " + lock.getWaitQueueLength(firstProducer));
            buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
            restProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> consume(int dataSize) throws InterruptedException {
        lock.lock();
        try {
            while (lock.hasWaiters(firstConsumer)) restConsumers.await();
            while (!hasEnoughData(dataSize)) {
                firstConsumer.await();
            }
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(buffer.remove());
            }
            System.out.println("firstConsumer queue: " + lock.getWaitQueueLength(firstConsumer));
            restConsumers.signal();
            firstProducer.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }
}
