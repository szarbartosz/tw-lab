package lab2.inc_dec_semaphore;

class Counter {
    private int counter = 0;
    public void increment() {
        counter++;
    }
    public void decrement() {
        counter--;
    }
    public int getCounter() {
        return counter;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BinarySemaphore semaphore = new BinarySemaphore();

        Counter counter = new Counter();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< 1000000; i++) {
                    semaphore.acquire();
                    counter.increment();
                    semaphore.release();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000000; i++) {
                    semaphore.acquire();
                    counter.decrement();
                    semaphore.release();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter.getCounter());
    }
}
