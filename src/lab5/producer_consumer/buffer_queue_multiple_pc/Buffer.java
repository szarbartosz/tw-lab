package lab5.producer_consumer.buffer_queue_multiple_pc;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private boolean isFull() {
        return buffer.size() >= capacity;
    }

    public synchronized void produce(int data) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.add(data);
        notify();
    }

    public synchronized int consume() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int data = buffer.remove();
        notify();
        return data;
    }
}
