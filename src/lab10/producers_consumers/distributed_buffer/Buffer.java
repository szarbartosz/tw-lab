package lab10.producers_consumers.distributed_buffer;

import org.jcsp.lang.*;

import java.util.Random;

import static lab10.producers_consumers.distributed_buffer.PCMain.buffersNo;
import static lab10.producers_consumers.distributed_buffer.PCMain.consumersNo;

public class Buffer implements CSProcess {
    private final Any2OneChannelInt channel;
    private final AltingChannelInputInt channelInput;
    private final SharedChannelOutputInt[] outputs;

    public Buffer(Any2OneChannelInt channel, int consumersNo, int BuffersNo) {
        this.channel = channel;
        this.channelInput = channel.in();
        this.outputs = new SharedChannelOutputInt[consumersNo + BuffersNo];
    }

    @Override
    public void run() {
        while(true) {
            int data = channelInput.read();
            this.outputs[new Random().nextInt(this.outputs.length)].write(data);
        }
    }

    public SharedChannelOutputInt getOutput(){
        return this.channel.out();
    }

    public void setOutputs(Buffer[] buffers, Consumer[] consumers) {
        for (int i = 0; i < buffersNo; i++) {
            this.outputs[i] = buffers[i].getOutput();
        }
        for (int i = 0; i < consumersNo; i++) {
            this.outputs[i + buffersNo] = consumers[i].getOutput();
        }
    }
}
