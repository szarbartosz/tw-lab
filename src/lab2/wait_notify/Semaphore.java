package lab2.wait_notify;

public class Semaphore {
    private boolean s = false;

    public synchronized void acquire() throws InterruptedException {
        while (s) wait();
        s = true;
    }

    public synchronized void release() {
        s = false;
        notify();
    }
}
