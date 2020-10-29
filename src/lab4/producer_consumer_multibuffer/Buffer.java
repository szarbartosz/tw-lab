package lab4.producer_consumer_multibuffer;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;

    final Lock lock = new ReentrantLock();
    final Condition producers = lock.newCondition();
    final Condition consumers = lock.newCondition();

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    private boolean hasEnoughData(int chunkSize) {
        return buffer.size() >= chunkSize;
    }

    private boolean hasEnoughSpace(List<Integer> chunk) {
        return buffer.size() + chunk.size() <= capacity;
    }

    public void produce(List<Integer> chunk) throws InterruptedException {
        lock.lock();
        try {
            while (!hasEnoughSpace(chunk)) {
                producers.await();
            }
            System.out.println("produced: " + Arrays.toString(chunk.toArray()));
            buffer.addAll(chunk);
            consumers.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int chunkSize) throws InterruptedException {
        lock.lock();
        try {
            while (!hasEnoughData(chunkSize)) {
                consumers.await();
            }
            List<Integer> chunk = new ArrayList<>();
            for (int i = 0; i < chunkSize; i ++) {
                chunk.add(buffer.remove());
            }
            System.out.println("consumed: " + chunk);
            producers.signal();
        } finally {
            lock.unlock();
        }
    }


}
