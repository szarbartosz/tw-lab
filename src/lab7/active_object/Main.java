package lab7.active_object;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        run(10, 10, 50);
    }

    public static void run(int producersNo, int consumersNo, int bufferCapacity) {
        Servant servant = new Servant(bufferCapacity);
        Scheduler scheduler = new Scheduler();

        Thread[] producers = new Thread[producersNo];
        Thread[] consumers = new Thread[consumersNo];

        for (int i = 0; i < producersNo; i++) {
            producers[i] = new Producer(new Proxy(scheduler, servant), servant);
        }

        for (int i = 0; i < consumersNo; i++) {
            consumers[i] = new Consumer(new Proxy(scheduler, servant), servant);
        }

        scheduler.start();

        Arrays.stream(producers).forEach(Thread::start);
        Arrays.stream(consumers).forEach(Thread::start);
    }
}
