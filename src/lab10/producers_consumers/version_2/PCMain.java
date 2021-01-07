package lab10.producers_consumers.version_2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public class PCMain {
    public static void main(String[] args) {
        new PCMain();
    }

    public PCMain() {
        final One2OneChannelInt prod = Channel.one2oneInt();
        final One2OneChannelInt cons = Channel.one2oneInt();

        CSProcess[] procList = {
                new Producer(prod), new Consumer(cons), new Buffer(prod, cons)
        };
        Parallel par = new Parallel(procList);
        par.run();
    }
}
