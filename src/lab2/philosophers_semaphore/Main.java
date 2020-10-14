package lab2.philosophers_semaphore;

public class Main {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Semaphore[] forks = new Semaphore[philosophers.length];

        for (int i = 0; i < forks.length; i ++) {
            forks[i] = new Semaphore();
        }

        for (int i = 0; i < philosophers.length; i ++) {
            Semaphore leftFork = forks[i];
            Semaphore rightFork = forks[(i + 1) % forks.length];

//            philosophers[i] = new Philosopher(leftFork, rightFork);

            if (i == 0) {
                philosophers[i] = new Philosopher(rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1) + ":");
            t.start();
        }
    }
}
