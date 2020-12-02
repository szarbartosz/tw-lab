package lab7.active_object;

import java.util.List;

public class Future {
    private volatile boolean isReady;
    private volatile List<Integer> data;

    public void setReady() {
        this.isReady = true;
    }

    public boolean isReady() {
        return isReady;
    }

    public List<Integer> getData() {
        return this.data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
