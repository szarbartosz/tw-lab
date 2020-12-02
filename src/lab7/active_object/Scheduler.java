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

//            boolean wasSomethingTaken = false;
//
//            if (produceMethodRequest != null && produceMethodRequest.guard()) {
//                wasSomethingTaken = true;
//                lock.lock();
//                try {
//                    if (queue.removeProductionRequest(produceMethodRequest)) {
//                        produceMethodRequest.call();
//                    }
//                } finally {
//                    lock.unlock();
//                }
//            }
//
//            if (consumeMethodRequest != null && consumeMethodRequest.guard()) {
//                wasSomethingTaken = true;
//                lock.lock();
//                try {
//                    if (queue.removeConsumptionRequest(consumeMethodRequest)) {
//                        consumeMethodRequest.call();
//                    }
//                } finally {
//                    lock.unlock();
//                }
//            }
//
//            if (!wasSomethingTaken) {
//                try {
//                    lock.lock();
//                    while (!queue.hasRequests()) {
//                        condition.await();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    lock.unlock();
//                }
//            }

            boolean requestTaken = false;

            if (queue.hasProductionRequests()) {
                ProduceMethodRequest request = queue.getProductionRequest();
                if (request.guard()) {
                    request.call();
                    queue.removeProductionRequest();
                    requestTaken = true;
                }
            }

            if (queue.hasConsumptionRequests()) {
                ConsumeMethodRequest request = queue.getConsumptionRequest();
                if (request.guard()) {
                    request.call();
                    queue.removeConsumptionRequest();
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