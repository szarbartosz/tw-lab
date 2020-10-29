package lab2.inc_dec_semaphore;

public class BinarySemaphore {
    private boolean released = true;

    public synchronized void acquire() {
        while(!released) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        released = false;
    }

    public synchronized void release() {
        released = true;
        notify();
    }
}
