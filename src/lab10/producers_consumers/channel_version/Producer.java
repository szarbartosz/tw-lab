package lab10.producers_consumers.channel_version;

import org.jcsp.lang.*;

public class Producer implements CSProcess {
    private final int id;
    private final Buffer[] buffers;
    private Guard[] guards;

    public Producer(int id, Buffer[] buffers) {
        this.id = id;
        this.buffers = buffers;
        this.guards = new Guard[buffers.length];
    }

    public void run() {
        for (int i = 0; i < buffers.length; i++) {
            guards[i] = buffers[i].getRequest().in();
        }

        Alternative alternative = new Alternative(guards);

        while(true) {
            int index = alternative.select();

            ChannelInputInt bufferRequest = buffers[index].getRequest().in();
            ChannelOutputInt bufferChannel = buffers[index].getChannel().out();

            bufferRequest.read();
            int item = (int)(Math.random() * 100) + 1;
            bufferChannel.write(item);

            System.out.println("Producer " + id + " produced: " + item);
        }
    }
}
