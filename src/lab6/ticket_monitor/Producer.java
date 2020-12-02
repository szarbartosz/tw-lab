package lab6.ticket_monitor;

import java.util.Random;

public class Producer implements Runnable {
    TicketMonitor monitor;
    Buffer buffer;
    int idx;

    public Producer(TicketMonitor monitor, Buffer buffer, int idx){

        this.monitor = monitor;
        this.buffer = buffer;
        this.idx = idx;
    }

    @Override
    public void run() {
        while (true) {
            int ticket = this.monitor.ticket_producer();
//            System.out.println("Producer " + this.idx + " received ticket " + ticket);
            int value = new Random().nextInt(10);
            this.buffer.concurrentHashMap.put(ticket, value);
//            System.out.println("Producer " + this.idx + " produced: " + value);
//            this.buffer.printBuffer();
            try {
                Thread.sleep(new Random().nextInt(100) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("Producer " + this.idx + " returned ticket " + ticket);
            this.monitor.return_producer(ticket);
        }
    }

}
