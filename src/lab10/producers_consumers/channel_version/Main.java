package lab10.producers_consumers.channel_version;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.Parallel;

public class Main {
    public static void main(String[] args) {
        int producersNo = 10;
        int consumersNo = 10;
        int buffersNo = 2;

        Producer[] producers = new Producer[producersNo];
        Consumer[] consumers = new Consumer[consumersNo];
        Buffer[] buffers = new Buffer[buffersNo];

        CSProcess[] processes = new CSProcess[producersNo + consumersNo + buffersNo];

        for (int i = 0; i < producersNo; i++){
            Producer producer = new Producer(i + 1, buffers);
            producers[i] = producer;
            processes[i] = producer;
        }

        for (int i = 0; i < consumersNo; i++){
            Consumer consumer = new Consumer(Channel.any2oneInt(), Channel.any2oneInt(), i + 1);
            consumers[i] = consumer;
            processes[i + producersNo] = consumer;
        }

        for (int i = 0; i < buffersNo; i++) {
            Buffer buffer = new Buffer(Channel.any2oneInt(), Channel.any2oneInt(),i + 1, consumers, producers);
            buffers[i] = buffer;
            processes[i + producersNo + consumersNo] = buffer;
        }

        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}
