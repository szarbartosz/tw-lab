package lab5.producer_consumer.buffer_int;

public class Buffer {
    private int buffer;

    private boolean isEmpty() {
        return buffer == 0;
    }

    private boolean isFull() {
        return buffer > 0;
    }

    public synchronized void produce(int data) {
        while(isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer = data;
        notify();
    }

    public synchronized int consume() {
        while(isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int data = buffer;
        buffer = 0;
        notify();
        return data;
    }
}
