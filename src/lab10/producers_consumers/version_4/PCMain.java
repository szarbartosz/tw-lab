package lab10.producers_consumers.version_4;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.Parallel;

public class PCMain {
    public static void main(String[] args) {
        int producersNo = 20;
        int consumersNo = 2;
        int capacity = 20;

        CSProcess[] procList = new CSProcess[producersNo + consumersNo + 1];

        for (int i = 0; i < producersNo; i++) {
            Producer producer = new Producer(Channel.one2oneInt(), i + 1);
            procList[i] = producer;
        }

        for (int i = 0; i < consumersNo; i++) {
            Consumer consumer = new Consumer(Channel.one2oneInt(), Channel.one2oneInt(), i + 1);
            procList[i + producersNo] = consumer;
        }

        Buffer buffer = new Buffer(procList, capacity);
        procList[producersNo + consumersNo] = buffer;

        Parallel par = new Parallel(procList);
        par.run();
    }
}
