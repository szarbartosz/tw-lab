package lab4.producer_consumer_lock_condition;

import java.util.LinkedList;
import java.util.Queue;
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

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private boolean isFull() {
        return buffer.size() >= capacity;
    }

    public void produce(int chunk) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                producers.await();
            }
            this.buffer.add(chunk);
            System.out.println("produced: " + chunk);
            consumers.signal();
        } finally {
            lock.unlock();
        }
    }

    public int consume() throws InterruptedException {
        lock.lock();
        try {
            while (isEmpty()) {
                consumers.await();
            }
            int chunk = buffer.remove();
            System.out.println("consumed: " + chunk);
            producers.signal();
            return chunk;
        } finally {
            lock.unlock();
        }
    }
}
