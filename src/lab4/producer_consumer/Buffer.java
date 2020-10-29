package lab4.producer_consumer;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Buffer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int capacity;
    private static final Random generator = new Random();

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private boolean isFull() {
        return buffer.size() >= capacity;
    }

    public synchronized void produce(int chunk) {
        while (isFull()) {
            try {
                wait();
                System.out.println("tried to produce, but buffer is full");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            Thread.sleep(Duration.ofSeconds(generator.nextInt(2)).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.buffer.add(chunk);
        System.out.println("produced: " + chunk);
        notify();
    }

    public synchronized int consume() {
        while (isEmpty()) {
            try {
                wait();
                System.out.println("tried to consume, but buffer is empty");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        }
        try {
            Thread.sleep(Duration.ofSeconds(generator.nextInt(2)).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int chunk = buffer.remove();
        System.out.println("consumed: " + chunk);
        notify();
        return chunk;
    }
}
