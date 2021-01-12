package lab10.producers_consumers.distributed_buffer_v2;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;

import java.util.Random;

import static lab10.producers_consumers.distributed_buffer.PCMain.consumersNo;

public class Buffer implements CSProcess {
    private final int id;
    private final Any2OneChannelInt channel;
    private final AltingChannelInputInt channelInput;
    private final SharedChannelOutputInt[] outputs;

    public Buffer(int id) {
        this.id = id;
        this.channel = Channel.any2oneInt(new BufferInt(1));
        this.channelInput = channel.in();
        this.outputs = new SharedChannelOutputInt[consumersNo];
    }

    @Override
    public void run() {
        while(true) {
            int data = channelInput.read();
            System.out.println("Buffer " + id + " is storing: " + data);
            this.outputs[new Random().nextInt(this.outputs.length)].write(data);
        }
    }

    public SharedChannelOutputInt getOutput(){
        return this.channel.out();
    }

    public void setOutputs(Consumer[] consumers) {
        for (int i = 0; i < consumersNo; i++) {
            this.outputs[i] = consumers[i].getOutput();
        }
    }
}
