package lab7.active_object;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Servant {

    private final int capacity;
    private Queue<Integer> buffer;

    public Servant(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    public boolean hasEnoughSpace(int dataSize) {
        return capacity - buffer.size() >= dataSize;
    }

    public boolean hasEnoughData(int dataSize) {
        return buffer.size() >= dataSize;
    }

    public void produce(List<Integer> data) {
        buffer = Stream.concat(data.stream(), buffer.stream()).collect(Collectors.toCollection(LinkedList::new));
    }

    public List<Integer> consume(int dataSize) {
        List<Integer> data = new LinkedList<>();
        for (int i = 0; i < dataSize; i++) {
            data.add(buffer.remove());
        }
        return data;
    }

    public int getBufferCapacity() {
        return this.capacity;
    }
}