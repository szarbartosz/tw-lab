package lab10.producers_consumers.distributed_buffer;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.Parallel;
import org.jcsp.util.ints.InfiniteBufferInt;

import java.util.Arrays;

public class PCMain {
    public static int producersNo = 20;
    public static int consumersNo = 20;
    public static int buffersNo = 40;

    public static void main(String[] args) {
        Producer[] producers = new Producer[producersNo];
        Consumer[] consumers = new Consumer[consumersNo];
        Buffer[] buffers = new Buffer[buffersNo];

        for (int i = 0; i < producersNo; i++) {
            producers[i] = new Producer(i + 1);
        }

        for (int i = 0; i < consumersNo; i++) {
            consumers[i] = new Consumer(i + 1);
        }

        for (int i = 0; i < buffersNo; i++) {
            buffers[i] = new Buffer(Channel.any2oneInt(new InfiniteBufferInt()), consumersNo, buffersNo);
        }

        Parallel parallel = new Parallel(prepareProcesses(producers, consumers, buffers));
        parallel.run();
    }

    public static CSProcess[] prepareProcesses(Producer[] producers, Consumer[] consumers, Buffer[] buffers){
        CSProcess[] processes = new CSProcess[producersNo + consumersNo + buffersNo];

        Arrays.stream(producers).forEach(producer -> producer.setOutputs(buffers));
        Arrays.stream(buffers).forEach(buffer -> buffer.setOutputs(buffers, consumers));

        for (int i = 0; i < producersNo; i++) {
            processes[i] = producers[i];
        }
        for (int i = 0; i < consumersNo; i++) {
            processes[i + producersNo] = consumers[i];
        }
        for (int i = 0; i < buffersNo; i++) {
            processes[i + producersNo + consumersNo] = buffers[i];
        }

        return processes;
    }
}
