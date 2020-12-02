package lab6.ticket_monitor_test;

import java.util.LinkedList;

public class TicketQueue {
    LinkedList<Integer> free = new LinkedList<>();
    LinkedList<Integer> occupied = new LinkedList<>();

    public TicketQueue(int size){
        for (int i = 0; i < size; i ++)
            this.free.add(i);
    }

    public int prod(){
        if (this.free.isEmpty()){
            return -1;
        }
        else{
            return this.free.poll();
        }
    }

    public int cons(){
        if (this.occupied.isEmpty()){
            return -1;
        }
        else{
            return this.occupied.poll();
        }
    }

    public void removeFree(int ticket){
        this.free.remove(ticket);
    }

    public void removeOccupied(int ticket){
        this.occupied.remove(ticket);
    }

    public void addFree(int ticket){
        this.free.add(ticket);
    }

    public void addOccupied(int ticket){
        this.occupied.add(ticket);
    }

}
