package lab8.active_object.main;

import lab8.active_object.*;
import lab8.monitor.Buffer;
import lab8.monitor.Consumer;
import lab8.monitor.Producer;
import lab8.utilities.StatisticsUtility;

import java.util.Arrays;

public class Main {
        public StatisticsUtility run(int bufferCapacity, int producersNo, int consumersNo, int prodDuration, int consDuration, int toProduce, int toConsume, int extraTaskDuration) {
        StatisticsUtility statisticsUtility = new StatisticsUtility();

        Buffer buffer = new Buffer(bufferCapacity, toProduce, toConsume, prodDuration, consDuration, statisticsUtility);

        Thread[] producers = new Thread[producersNo];
        Thread[] consumers = new Thread[consumersNo];

        for (int i = 0; i < consumersNo; i++) {
            consumers[i] = new Thread(new Consumer(buffer, extraTaskDuration), "C" + i);
        }

        for (int i = 0; i < producersNo; i++) {
            producers[i] = new Thread(new Producer(buffer, extraTaskDuration), "P" + i);
        }


        Servant servant = new Servant(bufferCapacity, prodDuration, consDuration, toProduce, toConsume);
        Scheduler scheduler = new Scheduler(servant, statisticsUtility);

        Thread[] aoProducers = new Thread[producersNo];
        Thread[] aoConsumers = new Thread[consumersNo];

        for (int i = 0; i < producersNo; i++) {
            aoProducers[i] = new AoProducer(new Proxy(scheduler, servant), servant, extraTaskDuration, statisticsUtility);
        }

        for (int i = 0; i < consumersNo; i++) {
            aoConsumers[i] = new AoConsumer(new Proxy(scheduler, servant), servant, extraTaskDuration, statisticsUtility);
        }

        scheduler.start();

        Arrays.stream(aoProducers).forEach(Thread::start);
        Arrays.stream(aoConsumers).forEach(Thread::start);


        Arrays.stream(producers).forEach(Thread::start);
        Arrays.stream(consumers).forEach(Thread::start);



        while (!statisticsUtility.isEverythingMeasured()) {
        }

        return statisticsUtility;
    }
//    public static void main(String[] args) {
//        StatisticsUtility statisticsUtility = run(40,10,10, 100, 100, 20, 20, 100);
//        System.out.println(statisticsUtility.toString());
//
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("1.txt"));
//            writer.write(statisticsUtility.toString());
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
