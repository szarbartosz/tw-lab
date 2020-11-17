package lab5.extra_task.two_locks_faulty;

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
    private final int prodMin;
    private final int prodMax;
    private final int consMin;
    private final int consMax;

    public int getCapacity() {
        return this.capacity;
    }

    public Buffer(int capacity, int prodMin, int prodMax, int consMin, int consMax) {
        this.capacity = capacity;
        this.prodMin = prodMin;
        this.prodMax = prodMax;
        this.consMin = consMin;
        this.consMax = consMax;
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
                System.out.println("producers in firstProducer queue: " + lock.getWaitQueueLength(firstProducer));
            }
            buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
//            System.out.println(Thread.currentThread().getName() + " produced: " + data);
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
                System.out.println("consumers in firstConsumer queue: " + lock.getWaitQueueLength(firstConsumer));
            }
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(buffer.remove());
            }
//            System.out.println(Thread.currentThread().getName() + " consumed: " + data);
            restConsumers.signal();
            firstProducer.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }

    public int getProdMin() {
        return prodMin;
    }

    public int getProdMax() {
        return prodMax;
    }

    public int getConsMin() {
        return consMin;
    }

    public int getConsMax() {
        return consMax;
    }
}
