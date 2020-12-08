package lab8.active_object;

import java.util.Deque;
import java.util.LinkedList;

public class ActivationQueue {

    private final Deque<ProduceMethodRequest> producersQueue;
    private final Deque<ConsumeMethodRequest> consumersQueue;

    public ActivationQueue() {
        this.producersQueue = new LinkedList<>();
        this.consumersQueue = new LinkedList<>();
    }

    public void addProductionRequest(ProduceMethodRequest request) {
        producersQueue.addLast(request);
    }

    public void addConsumptionRequest(ConsumeMethodRequest request) {
        consumersQueue.addLast(request);
    }

    public ProduceMethodRequest getProductionRequest() {
        if (producersQueue.isEmpty()) return null;
        return producersQueue.getFirst();
    }

    public ConsumeMethodRequest getConsumptionRequest() {
        if (consumersQueue.isEmpty()) return null;
        return consumersQueue.getFirst();
    }

    public ProduceMethodRequest removeProductionRequest() {
        if (producersQueue.isEmpty()) return null;
        return producersQueue.removeFirst();
    }

    public ConsumeMethodRequest removeConsumptionRequest() {
        if (consumersQueue.isEmpty()) return null;
        return consumersQueue.removeFirst();
    }

    public boolean hasRequests() {
        return hasProductionRequests() || hasConsumptionRequests();
    }

    public boolean hasProductionRequests() {
        return !this.producersQueue.isEmpty();
    }

    public boolean hasConsumptionRequests() {
        return !this.consumersQueue.isEmpty();
    }
}
