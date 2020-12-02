package lab6.ticket_monitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer {
    int size;
    Map<Integer, Integer> concurrentHashMap;


    public Buffer(int size){
        this.size = size;
//        this.concurrentHashMap = new ConcurrentHashMap<>(size);
        this.concurrentHashMap = new HashMap<>(size);
    }

    public void printBuffer(){
        System.out.println(Arrays.asList(concurrentHashMap));
    }

}
