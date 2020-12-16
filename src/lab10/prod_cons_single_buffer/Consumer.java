package lab10.prod_cons_single_buffer;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private One2OneChannelInt in;

    public Consumer(final One2OneChannelInt in) {
        this.in = in;
    }

    public void run() {
        int item;
        while(true) {
            item = in.in().read();
            System.out.println(item);
        }
    }
}
