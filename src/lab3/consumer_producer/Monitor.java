package lab3.consumer_producer;

public class Monitor {
    private int buffer = 0;

    private boolean isEmpty() {
        return buffer == 0;
    }

    public synchronized void produce(int data) {
        while(!isEmpty()) {
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
        int tmp = buffer;
        buffer = 0;
        notify();
        return tmp;
    }
}
