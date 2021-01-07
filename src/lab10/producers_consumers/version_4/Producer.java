package lab10.producers_consumers.version_4;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt channel;
    private final int id;

    public Producer(final One2OneChannelInt channel, int id) {
        this.channel = channel;
        this.id = id;
    }

    public void run() {
        while(true) {
            int item = (int)(Math.random() * 100) + 1;
            channel.out().write(item);
            System.out.println("Producer " + id + " produced: " + item);
        }
    }

    public One2OneChannelInt getChannel() {
        return channel;
    }
}
