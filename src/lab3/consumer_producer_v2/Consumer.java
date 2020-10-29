package lab3.consumer_producer_v2;

public class Consumer implements Runnable {
    private Monitor monitor;

    public Consumer(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true) {
            int result = this.monitor.consume();
            System.out.println("consumed: " + result);
        }
    }

}