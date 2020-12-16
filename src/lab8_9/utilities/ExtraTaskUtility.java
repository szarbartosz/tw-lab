package lab8_9.utilities;

public class ExtraTaskUtility {
    public static void performExtraTask(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
