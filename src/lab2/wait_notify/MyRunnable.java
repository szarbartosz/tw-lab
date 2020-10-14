package lab2.wait_notify;

public class MyRunnable implements Runnable {
    private int id;
    private IntOperation intOperation;
    private Semaphore sem = new Semaphore();

    MyRunnable(int id, IntOperation intOperation) {
        this.id = id;
        this.intOperation = intOperation;
    }

    @Override
    public void run() {
        if (id % 2 != 0) {
            try {
                sem.acquire();
                intOperation.decrement();
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sem.acquire();
                intOperation.increment();
                sem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
