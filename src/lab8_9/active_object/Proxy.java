package lab8_9.active_object;

import java.util.List;

public class Proxy {

    private final Scheduler scheduler;
    private final Servant servant;

    public Proxy(Scheduler scheduler, Servant servant) {
        this.scheduler = scheduler;
        this.servant = servant;
    }

    public Future produce(List<Integer> data) {
        Future future = new Future();
        ProduceMethodRequest request = new ProduceMethodRequest(servant, future, data);
        this.scheduler.addProductionRequest(request);
        return future;
    }

    public Future consume(int dataSize) {
        Future future = new Future();
        ConsumeMethodRequest request = new ConsumeMethodRequest(servant, future, dataSize);
        this.scheduler.addConsumptionRequest(request);
        return future;
    }
}
