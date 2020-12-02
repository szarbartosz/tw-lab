package lab7.active_object;

import java.util.List;

public class ConsumeMethodRequest implements MethodRequest {

    private final Servant servant;
    private final Future future;
    private final int dataSize;

    public ConsumeMethodRequest(Servant servant, Future future, int dataSize) {
        this.servant = servant;
        this.future = future;
        this.dataSize = dataSize;
    }

    @Override
    public void call() {
        List<Integer> result = servant.consume(dataSize);
        future.setReady();
        future.setData(result);
    }

    @Override
    public boolean guard() {
        return servant.hasEnoughData(dataSize);
    }
}
