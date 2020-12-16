package lab10.prod_cons_single_buffer;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    One2OneChannelInt fromProd;
    One2OneChannelInt toCons;

    public Buffer(One2OneChannelInt fromProd, One2OneChannelInt toCons) {
        this.fromProd = fromProd;
        this.toCons = toCons;
    }

    @Override
    public void run() {
        int value;
        while (true) {
            value = fromProd.in().read();
            toCons.out().write(value);
        }
    }
}
