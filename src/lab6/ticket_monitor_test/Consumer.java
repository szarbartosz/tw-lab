package lab6.ticket_monitor_test;

import java.util.Random;

public class Consumer implements Runnable{
    TicketMonitor monitor;
    Buffer buffer;
    int idx;

    public Consumer(TicketMonitor monitor, Buffer buffer, int idx) {

        this.monitor = monitor;
        this.buffer = buffer;
        this.idx = idx;
    }

    @Override
    public void run() {
        while (true) {
            int ticket = this.monitor.ticket_consumer();
            System.out.println("Consumer " + this.idx + " received ticket " + ticket);
            int value = this.buffer.concurrentHashMap.remove(ticket);
            this.buffer.printBuffer();
            System.out.println("Consumer " + this.idx + " consumed: " + value);
            try {
                Thread.sleep(new Random().nextInt(100) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consumer " + this.idx + " returned ticket " + ticket);
            this.monitor.return_consumer(ticket);
        }
    }

}
