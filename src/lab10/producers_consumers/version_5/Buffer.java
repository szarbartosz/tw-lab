package lab10.producers_consumers.version_5;

import org.jcsp.lang.*;

import java.util.LinkedList;

public class Buffer implements CSProcess {
    private final CSProcess[] procList;
    private final LinkedList<Integer> buffer;
    private final int capacity;
    private Guard[] guards;

    public Buffer(CSProcess[] procList, int capacity) {
        this.procList = procList;
        this.capacity = capacity;
        buffer = new LinkedList<>();
        fillGuards();
    }

    private void fillGuards(){
        guards = new Guard[procList.length - 1];
        int i;
        for (i = 0; i < guards.length; i++){
            if (procList[i] instanceof Producer) {
                guards[i] = ((Producer)procList[i]).getChannel().in();
            } else {
                guards[i] = ((Consumer)procList[i]).getRequest().in();
            }
        }
    }

    public void run() {
        Alternative alt = new Alternative(guards);
        while (true) {
            int index = alt.select();
            if (procList[index] instanceof Producer){
                if (hasEnoughSpace()) {
                    ChannelInputInt producersChannel = ((Producer)procList[index]).getChannel().in();
                    int item = producersChannel.read();
                    buffer.add(item);
                }
            } else {
                if (hasEnoughData()) {
                    ChannelInputInt consumerRequest = ((Consumer)procList[index]).getRequest().in();
                    ChannelOutputInt consumerChannel = ((Consumer)procList[index]).getChannel().out();
                    consumerRequest.read();
                    int item = buffer.poll();
                    consumerChannel.write(item);
                }
            }
        }
    }

    private boolean hasEnoughSpace() {
        return this.buffer.size() < this.capacity;
    }

    private boolean hasEnoughData() {
        return this.buffer.size() > 0;
    }
}
