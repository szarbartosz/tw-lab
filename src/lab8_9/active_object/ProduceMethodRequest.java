package lab8_9.active_object;

import java.util.List;

public class ProduceMethodRequest implements MethodRequest {

    private final Servant servant;
    private final Future future;
    private final List<Integer> data;

    public ProduceMethodRequest(Servant servant, Future future, List<Integer> data) {
        this.servant = servant;
        this.future = future;
        this.data = data;
    }

    @Override
    public void call() {
        this.servant.produce(data);
        future.setReady();
    }

    @Override
    public boolean guard() {
        return servant.hasEnoughSpace(data.size());
    }
}