package lab2.sender_receiver;

public class Data {
    private String packet;

//    true if receiver should wait
//    false if sender should wait
    private boolean transfer = true;

    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("thread interrupted" + e);
            }
        }
        transfer = false;

        this.packet = packet;
        notifyAll();
    }

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("thread interrupted" + e);
            }
        }
        transfer = true;

        notifyAll();
        return packet;
    }
}
