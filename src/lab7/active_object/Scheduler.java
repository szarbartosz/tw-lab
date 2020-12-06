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
                if (produceMethodRequest.guard()) {
                    lock.lock();
                    try {
                        if (produceMethodRequest.equals(queue.removeProductionRequest())) {
                            produceMethodRequest.call();
                        }
                    } finally {
                        lock.unlock();
                    }
                    requestTaken = true;
                }
            }

            if (consumeMethodRequest != null) {
                if (consumeMethodRequest.guard()) {
                    lock.lock();
                    try {
                        if (consumeMethodRequest.equals(queue.removeConsumptionRequest())) {
                            consumeMethodRequest.call();
                        }
                    } finally {
                        lock.unlock();
                    }
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