package lab5.producer_consumer.lock_condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public Queue<Integer> getBuffer() {
        return this.buffer;
    }

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private boolean isFull() {
        return buffer.size() >= capacity;
    }

    final Lock lock = new ReentrantLock();
    final Condition producers = lock.newCondition();
    final Condition consumers = lock.newCondition();

    public void produce(int data) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                producers.await();
            }
            buffer.add(data);
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
            int data = buffer.remove();
            producers.signal();
            return data;
        } finally {
            lock.unlock();
        }
    }
}
