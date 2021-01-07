package lab10.producers_consumers.version_5;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt channel;
    private final One2OneChannelInt request;
    private final int id;

    public Consumer(final One2OneChannelInt request, final One2OneChannelInt channel, int id) {
        this.request = request;
        this.channel = channel;
        this.id = id;
    }

    public void run() {
        int item;
        while(true) {
            request.out().write(0);
            item = channel.in().read();
            System.out.println("Consumer " + id + " consumed: " + item);
        }
    }

    public One2OneChannelInt getRequest() {
        return request;
    }

    public One2OneChannelInt getChannel() {
        return channel;
    }
}
