package lab3.consumer_producer_v2;

import java.util.LinkedList;
import java.util.Queue;

public class Monitor {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int capacity = 10;

    private boolean isFull() {
        return buffer.size() >= capacity;
    }

    public synchronized void produce(int data) {
        while(isFull()) {
            try {
                wait();
                System.out.println("the buffer is full");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.add(data);
        notify();
    }

    public synchronized int consume() {
        while(!isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int tmp = buffer.remove();
        notify();
        return tmp;
    }
}
