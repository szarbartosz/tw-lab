package lab10.producers_consumers.channel_version;

import org.jcsp.lang.*;

import java.util.Random;

public class Buffer implements CSProcess{
    private final Any2OneChannelInt channel;
    private final Any2OneChannelInt request;

    private final int id;
    private final Consumer[] consumers;
    private final Producer[] producers;
    private Guard[] guards;

    public Buffer(final Any2OneChannelInt channel, Any2OneChannelInt request, int id, Consumer[] consumers, Producer[] producers) {
        this.channel = channel;
        this.request = request;
        this.id = id;
        this.consumers = consumers;
        this.producers = producers;
        this.guards = new Guard[this.producers.length + this.consumers.length];
    }

    public void run() {
        for (int i = 0; i < consumers.length; i++) {
            guards[i] = consumers[i].getRequest().in();
        }

        Alternative alternative = new Alternative(guards);

        int item;
        while (true) {
//            int index = alternative.select();

            int index = new Random().nextInt(consumers.length);

            ChannelInputInt consumerRequest = consumers[index].getRequest().in();
            ChannelOutputInt consumerChannel = consumers[index].getChannel().out();

            request.out().write(0);
            item = channel.in().read();

            consumerRequest.read();
            consumerChannel.write(item);
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
