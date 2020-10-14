package lab2.philosophers_semaphore;

public class Philosopher implements Runnable {
    private Semaphore leftFork;
    private Semaphore rightFork;

    public Philosopher(Semaphore leftFork, Semaphore rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep((int) (Math.random() * 1000));
    }

    @Override
    public void run() {
        try {
            while (true) {
                doAction("Thinking");
                leftFork.acquire();
                doAction("Picked up left fork");
                rightFork.acquire();
                doAction("Picked up right fork - eating");
                doAction("Put down right fork");
                rightFork.release();
                doAction("Put down the left fork - back to thinking");
                leftFork.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

