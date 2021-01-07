package lab10.producers_consumers.distributed_buffer;

import org.jcsp.lang.*;

import java.util.Random;

import static lab10.producers_consumers.distributed_buffer.PCMain.buffersNo;

public class Producer implements CSProcess {
    private final int id;
    private final SharedChannelOutputInt[] outputs;

    public Producer(int id) {
        this.id = id;
        this.outputs = new SharedChannelOutputInt[buffersNo];
    }

    public void run() {
        while(true) {
            int data = (int)(Math.random() * 100) + 1;
            this.outputs[new Random().nextInt(this.outputs.length)].write(data);
            System.out.println("Producer " + id + " produced: " + data);
        }
    }

    public void setOutputs(Buffer[] buffers) {
        for (int i = 0; i < buffersNo; i++) {
            this.outputs[i] = buffers[i].getOutput();
        }
    }
}