package lab8_9.active_object;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Servant {

    private final int capacity;
    private Queue<Integer> buffer;

    private final int prodDuration;
    private final int consDuration;
    private final int toProduce;
    private final int toConsume;
    private int alreadyProduced;
    private int alreadyConsumed;

    public Servant(int capacity, int prodDuration, int consDuration, int toProduce, int toConsume) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();

        this.prodDuration = prodDuration;
        this.consDuration = consDuration;
        this.toProduce = toProduce;
        this.toConsume = toConsume;
        this.alreadyProduced = 0;
        this.alreadyConsumed = 0;
    }

    public boolean hasEnoughSpace(int dataSize) {
        return capacity - buffer.size() >= dataSize;
    }

    public boolean hasEnoughData(int dataSize) {
        return buffer.size() >= dataSize;
    }

    public void produce(List<Integer> data) {
        buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
        alreadyProduced += data.size();
        try {
            Thread.sleep(this.getProdDuration() * data.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> consume(int dataSize) {
        List<Integer> data = new LinkedList<>();
        for (int i = 0; i < dataSize; i++) {
            data.add(buffer.remove());
        }
        alreadyConsumed += dataSize;
        try {
            Thread.sleep(this.getConsDuration());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int getBufferCapacity() {
        return this.capacity;
    }

    private int getProdDuration() {
        return this.prodDuration;
    }

    private int getConsDuration() {
        return this.consDuration;
    }

    public boolean hasEverythingProduced() {
        return alreadyProduced >= toProduce;
    }

    public boolean hasEverythingConsumed() {
        return alreadyConsumed >= toConsume;
    }
}