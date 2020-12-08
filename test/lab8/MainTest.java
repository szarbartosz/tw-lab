package lab8;

import lab8.active_object.main.Main;
import lab8.utilities.StatisticsUtility;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainTest {
    @Test
    public void varyingCapacityTest() {
        int bufferCapacity = 20;
        int producersNo = 8;
        int consumersNo = 8;
        int prodDuration = 100;
        int consDuration = 100;
        int toProduce = 100;
        int toConsume = 100;
        int extraTaskDuration = 100;

        Main  main = new Main();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("capacity.txt"));
            for (int i = 0; i <= 80; i++) {
                StatisticsUtility statisticsUtility = main.run(bufferCapacity, producersNo, consumersNo, prodDuration, consDuration, toProduce, toConsume, extraTaskDuration);
                writer.write(bufferCapacity + " " + statisticsUtility.getMonitorTotalProductionTime() + " " + statisticsUtility.getAoTotalProductionTime() + " " +
                        statisticsUtility.getMonitorTotalConsumptionTime() + " " + statisticsUtility.getAoTotalConsumptionTime() +  " " +
                        statisticsUtility.getAoTotalExtraProductionTasksCompleted() + " " + statisticsUtility.getAoTotalExtraConsumptionTasksCompleted() + " " +
                        statisticsUtility.getAoTotalExtraProductionTasksDuration(extraTaskDuration) + " " + statisticsUtility.getAoTotalExtraConsumptionTasksDuration(extraTaskDuration) + "\n");
                System.out.println(statisticsUtility.toString());
                bufferCapacity++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void varyingExtraTaskDurationTest() {
        int bufferCapacity = 40;
        int producersNo = 8;
        int consumersNo = 8;
        int prodDuration = 100;
        int consDuration = 100;
        int toProduce = 100;
        int toConsume = 100;
        int extraTaskDuration = 60;

        Main  main = new Main();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("extraTaskDuration.txt"));
            for (int i = 0; i <= 80; i++) {
                StatisticsUtility statisticsUtility = main.run(bufferCapacity, producersNo, consumersNo, prodDuration, consDuration, toProduce, toConsume, extraTaskDuration);
                writer.write(extraTaskDuration + " " + statisticsUtility.getMonitorTotalProductionTime() + " " + statisticsUtility.getAoTotalProductionTime() + " " +
                        statisticsUtility.getMonitorTotalConsumptionTime() + " " + statisticsUtility.getAoTotalConsumptionTime() +  " " +
                        statisticsUtility.getAoTotalExtraProductionTasksCompleted() + " " + statisticsUtility.getAoTotalExtraConsumptionTasksCompleted() + " " +
                        statisticsUtility.getAoTotalExtraProductionTasksDuration(extraTaskDuration) + " " + statisticsUtility.getAoTotalExtraConsumptionTasksDuration(extraTaskDuration) + "\n");
                System.out.println(statisticsUtility.toString());
                extraTaskDuration++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void varyingProdConsNoTest() {
        int bufferCapacity = 40;
        int producersNo = 4;
        int consumersNo = 4;
        int prodDuration = 100;
        int consDuration = 100;
        int toProduce = 100;
        int toConsume = 100;
        int extraTaskDuration = 100;

        Main  main = new Main();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("prodCons.txt"));
            for (int i = 0; i <= 20; i++) {
                StatisticsUtility statisticsUtility = main.run(bufferCapacity, producersNo, consumersNo, prodDuration, consDuration, toProduce, toConsume, extraTaskDuration);
                writer.write(producersNo + " " + statisticsUtility.getMonitorTotalProductionTime() + " " + statisticsUtility.getAoTotalProductionTime() + " " +
                        statisticsUtility.getMonitorTotalConsumptionTime() + " " + statisticsUtility.getAoTotalConsumptionTime() +  " " +
                        statisticsUtility.getAoTotalExtraProductionTasksCompleted() + " " + statisticsUtility.getAoTotalExtraConsumptionTasksCompleted() + " " +
                        statisticsUtility.getAoTotalExtraProductionTasksDuration(extraTaskDuration) + " " + statisticsUtility.getAoTotalExtraConsumptionTasksDuration(extraTaskDuration) + "\n");
                System.out.println(statisticsUtility.toString());
                producersNo += 1;
                consumersNo += 1;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
