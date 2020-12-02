package lab7.active_object;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler extends Thread {

    private final ActivationQueue queue = new ActivationQueue();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void addProductionRequest(ProduceMethodRequest request) {
        lock.lock();
        try {
            queue.addProductionRequest(request);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void addConsumptionRequest(ConsumeMethodRequest request) {
        lock.lock();
        try {
            queue.addConsumptionRequest(request);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        ProduceMethodRequest produceMethodRequest;
        ConsumeMethodRequest consumeMethodRequest;

        while (true) {
            lock.lock();
            try {
                produceMethodRequest = queue.getProductionRequest();
                consumeMethodRequest = queue.getConsumptionRequest();
            } finally {
                lock.unlock();
            }

            boolean requestTaken = false;

            if (produceMethodRequest != null) {
                ProduceMethodRequest request = queue.getProductionRequest();
                if (request.guard()) {
                    request = queue.removeProductionRequest();
                    request.call();
                    requestTaken = true;
                }
            }

            if (consumeMethodRequest != null) {
                ConsumeMethodRequest request = queue.getConsumptionRequest();
                if (request.guard()) {
                    request = queue.removeConsumptionRequest();
                    request.call();
                    requestTaken = true;
                }
            }

            if (!requestTaken) {
                try {
                    lock.lock();
                    while (!queue.hasRequests()) {
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}