package lab8_9.utilities;

public class StatisticsUtility {
    private volatile long MonitorTotalProductionTime;
    private volatile long MonitorTotalConsumptionTime;
//    private int MonitorTotalExtraProductionTasksCompleted = 0;
//    private int MonitorTotalExtraConsumptionTasksCompleted = 0;
    private volatile boolean isMonitorProductionTimeMeasured = false;
    private volatile boolean isMonitorConsumptionTimeMeasured = false;

    private volatile long AoTotalProductionTime;
    private volatile long AoTotalConsumptionTime;
    private int AoTotalExtraProductionTasksCompleted = 0;
    private int AoTotalExtraConsumptionTasksCompleted = 0;
    private volatile boolean isAoProductionTimeMeasured = false;
    private volatile boolean isAoConsumptionTimeMeasured = false;

    public boolean isEverythingMeasured() {
        return isMonitorProductionTimeMeasured && isMonitorConsumptionTimeMeasured && isAoProductionTimeMeasured && isAoConsumptionTimeMeasured;
    }

    public void setMonitorTotalProductionTime(long prodTime) {
        if (!isMonitorProductionTimeMeasured) {
            this.MonitorTotalProductionTime = prodTime;
            isMonitorProductionTimeMeasured = true;
        }
    }

    public void setMonitorTotalConsumptionTime(long consTime) {
        if (!isMonitorConsumptionTimeMeasured) {
            this.MonitorTotalConsumptionTime = consTime;
            isMonitorConsumptionTimeMeasured = true;
        }
    }

    public void setAoTotalProductionTime(long prodTime) {
        if (!isAoProductionTimeMeasured) {
            this.AoTotalProductionTime = prodTime;
            isAoProductionTimeMeasured = true;
        }
    }

    public void setAoTotalConsumptionTime(long consTime) {
        if (!isAoConsumptionTimeMeasured) {
            this.AoTotalConsumptionTime = consTime;
            isAoConsumptionTimeMeasured = true;
        }
    }

    public synchronized void incrementAoExtraProductionTasksCounter() {
        this.AoTotalExtraProductionTasksCompleted++;
    }

    public synchronized void incrementAoExtraConsumptionTasksCounter() {
        this.AoTotalExtraConsumptionTasksCompleted++;
    }

//    public synchronized void incrementMonitorExtraProductionTasksCounter() {
//        this.MonitorTotalExtraProductionTasksCompleted++;
//    }

//    public synchronized void incrementMonitorExtraConsumptionTasksCounter() {
//        this.MonitorTotalExtraConsumptionTasksCompleted++;
//    }

    @Override
    public String toString() {
        return  "\n total monitor production time: " + (double) MonitorTotalProductionTime / 1000.0 +
                "\n total monitor consumption time: " + (double) MonitorTotalConsumptionTime / 1000.0 +
//                ",\n total monitor production extra tasks completed: " + MonitorTotalExtraProductionTasksCompleted +
//                ",\n total monitor consumption extra tasks completed: " + MonitorTotalExtraConsumptionTasksCompleted +
                "\n total ActiveObject production time: " + (double) AoTotalProductionTime / 1000.0 +
                "\n total ActiveObject consumption time: " + (double) AoTotalConsumptionTime / 1000.0 +
                "\n total ActiveObject production extra tasks completed: " + AoTotalExtraProductionTasksCompleted +
                "\n total ActiveObject consumption extra tasks completed: " + AoTotalExtraConsumptionTasksCompleted + "\n";
    }

    public double getMonitorTotalProductionTime() {
        return MonitorTotalProductionTime / 1000.0;
    }

    public double getMonitorTotalConsumptionTime() {
        return MonitorTotalConsumptionTime / 1000.0;
    }

    public double getAoTotalProductionTime() {
        return AoTotalProductionTime / 1000.0;
    }

    public double getAoTotalConsumptionTime() {
        return AoTotalConsumptionTime / 1000.0;
    }

    public int getAoTotalExtraProductionTasksCompleted() {
        return AoTotalExtraProductionTasksCompleted;
    }

    public int getAoTotalExtraConsumptionTasksCompleted() {
        return AoTotalExtraConsumptionTasksCompleted;
    }

    public double getAoTotalExtraProductionTasksDuration(int extraTaskDuration) {
        return this.getAoTotalExtraProductionTasksCompleted() * extraTaskDuration / 1000.0;
    }

    public double getAoTotalExtraConsumptionTasksDuration(int extraTaskDuration) {
        return this.getAoTotalExtraConsumptionTasksCompleted() * extraTaskDuration / 1000.0;
    }
}
