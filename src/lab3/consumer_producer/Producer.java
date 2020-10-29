package lab3.consumer_producer;

import java.util.Random;

public class Producer implements Runnable {
    private Monitor monitor;

    public Producer(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while(true) {
            int data = rand.nextInt(10);
            this.monitor.produce(data);
            System.out.println("produced: " + data);
        }
    }

}
