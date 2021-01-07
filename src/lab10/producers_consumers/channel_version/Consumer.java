package lab10.producers_consumers.channel_version;

import org.jcsp.lang.Any2OneChannelInt;
import org.jcsp.lang.CSProcess;

public class Consumer implements CSProcess {
    private final Any2OneChannelInt channel;
    private final Any2OneChannelInt request;
    private final int id;

    public Consumer(final Any2OneChannelInt request, final Any2OneChannelInt channel, int id) {
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

    public Any2OneChannelInt getRequest() {
        return request;
    }

    public Any2OneChannelInt getChannel() {
        return channel;
    }
}
