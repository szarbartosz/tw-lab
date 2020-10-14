package lab2.sender_receiver;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable{
    private Data load;

    public Receiver(Data load) {
        this.load = load;
    }

    @Override
    public void run() {
        for(String receivedMessage = load.receive();
            !"End".equals(receivedMessage);
            receivedMessage = load.receive()) {

            System.out.println(receivedMessage);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted: " + e);
            }
        }
    }
}
