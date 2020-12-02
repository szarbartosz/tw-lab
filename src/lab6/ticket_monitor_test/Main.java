package lab6.ticket_monitor_test;

public class Main {
    public static void main(String[] args){
        int prodNo = 100;
        int consNo = 10;
        TicketMonitor monitor = new TicketMonitor(100);
        Buffer buffer = new Buffer(100);

        for (int i = 0; i < prodNo; i++){
            Thread p = new Thread(new Producer(monitor, buffer, i));
            p.start();
        }

        for (int i = 0; i < consNo; i ++){
            Thread c = new Thread(new Consumer(monitor, buffer, i));
            c.start();
        }
    }

}
