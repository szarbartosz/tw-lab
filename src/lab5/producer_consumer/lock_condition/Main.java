package lab5.producer_consumer.lock_condition;

import java.util.Arrays;

public class Main {
    public static void run(int bufferCapacity, int producersNo, int consumersNo) {
        Buffer buffer = new Buffer(bufferCapacity);
        Thread[] producers = new Thread[producersNo];
        Thread[] consumers = new Thread[consumersNo];

        for (int i = 0; i < producersNo; i++) {
            producers[i] = new Thread(new Producer(buffer), "P" + i);
        }

        for (int i = 0; i < consumersNo; i++) {
            consumers[i] = new Thread(new Consumer(buffer), "C" + i);
        }

        Arrays.stream(producers).forEach(Thread::start);
        Arrays.stream(consumers).forEach(Thread::start);
    }
    public static void main(String[] args) {
        run(1,2,1);
    }
}
