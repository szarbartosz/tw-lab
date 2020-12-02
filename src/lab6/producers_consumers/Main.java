package lab6.producers_consumers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void run(int bufferCapacity, int producersNo, int consumersNo) {
        Buffer buffer = new Buffer(bufferCapacity, 1, 10, 491, 500);
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

        List<String> prodState = new LinkedList<>();
        List<String> consState = new LinkedList<>();

//        while(true) {
//            Arrays.stream(producers).forEach(p -> prodState.add(p.getName() + ": " + p.getState().toString()));
//            Arrays.stream(consumers).forEach(c -> consState.add(c.getName() + ": " + c.getState().toString()));
//            System.out.println("producers state: " + prodState);
//            System.out.println("consumers state: " + consState);
//            prodState.clear();
//            consState.clear();
//        }
    }
    public static void main(String[] args) {
        run(1000,100,100);
    }
}
