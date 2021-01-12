package lab10.producers_consumers.distributed_buffer_v2;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;

public class Consumer implements CSProcess {
    private final int id;
    private final Any2OneChannelInt channel;
    private final AltingChannelInputInt channelInput;

    public Consumer(int id) {
        this.id = id;
        this.channel = Channel.any2oneInt(new BufferInt(1));
        this.channelInput = channel.in();
    }

    @Override
    public void run() {
        while(true) {
            int data = channelInput.read();
            System.out.println("Consumer " + id + " consumed: " + data);
        }
    }

    public SharedChannelOutputInt getOutput() {
        return this.channel.out();
    }
}
