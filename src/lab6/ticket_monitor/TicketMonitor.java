package lab6.ticket_monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketMonitor {
    private TicketQueue tickets;
    private Lock lock = new ReentrantLock();
    private Condition prodQueue = lock.newCondition();
    private Condition consQueue = lock.newCondition();

    int ticketsInUse = 0;

    public TicketMonitor(int size){
        this.tickets = new TicketQueue(size);
    }


    public int ticket_producer() {
        int ticket = -1 ;
        this.lock.lock();
        try{
            while(true){
                ticket = this.tickets.prod();
                if (ticket == -1)
                    this.prodQueue.await();
                else {
                    this.tickets.removeFree(ticket);
                    break;
                }
            }
        }

        finally{
            this.ticketsInUse++;
            System.out.println("Tickets in use: " + Integer.toString(ticketsInUse));
            this.lock.unlock();
            return ticket;
        }
    }


    public void return_producer(int ticket){
        this.lock.lock();
        try {
            this.tickets.addOccupied(ticket);
            this.consQueue.signal();
        }
        finally {
            ticketsInUse--;
            this.lock.unlock();
        }

    }

    public int ticket_consumer(){
        int ticket = -1;
        this.lock.lock();
        try{
            while(true){
                ticket = this.tickets.cons();
                if(ticket == -1) {
                    try {
                        this.consQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    this.tickets.removeOccupied(ticket);
                    break;
                }
            }
        }
        finally {
            this.ticketsInUse++;
            System.out.println("Tickets in use: " + Integer.toString(ticketsInUse));
            this.lock.unlock();
            return ticket;
        }
    }

    public void return_consumer(int ticket){
        this.lock.lock();
        try {
            this.tickets.addFree(ticket);
            this.prodQueue.signal();
        }
        finally {
            ticketsInUse--;
            this.lock.unlock();
        }
    }

}
