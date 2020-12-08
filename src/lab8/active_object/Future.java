package lab8.active_object;

import java.util.List;

public class Future {
    private volatile boolean isReady;
    private volatile List<Integer> data;

    public boolean isReady() {
        return isReady;
    }

    public void setReady() {
        this.isReady = true;
    }

    public List<Integer> getData() {
        return this.data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
