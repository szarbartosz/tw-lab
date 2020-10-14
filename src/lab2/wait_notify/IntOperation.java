package lab2.wait_notify;

public class IntOperation {
    private int number = 0;

    public void increment() {
        this.number++;
    }

    public void decrement() {
        this.number--;
    }

    public void print() {
        System.out.println("result: " + this.number);
    }
}
