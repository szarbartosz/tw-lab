package lab5.producer_consumer.lock_conditoin_random_potrion_size;

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

    final Lock lock = new ReentrantLock();
    final Condition producers = lock.newCondition();
    final Condition consumers = lock.newCondition();

    public void produce(List<Integer> data) throws InterruptedException {
        lock.lock();
        try {
            while (!hasEnoughSpace(data.size())) {
                producers.await();
            }
            buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
            consumers.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> consume(int dataSize) throws InterruptedException {
        lock.lock();
        try {
            while (!hasEnoughData(dataSize)) {
                consumers.await();
            }
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < dataSize; i++) {
                data.add(buffer.remove());
            }
            producers.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }
}
