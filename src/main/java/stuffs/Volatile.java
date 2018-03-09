package stuffs;

public class Volatile {
    private int counter;

    public void inc() {
	counter++;
    }

    public void dec() {
	counter--;
    }

    public int get() {
	return counter;
    }

    public static void main(String[] args) throws InterruptedException {
	int value = 0;
	Volatile unsafeCounter = new Volatile();
	while (value == 0) {
	    Thread first = new Thread(() -> {
		for (int i = 0; i < 5; i++) {
		    unsafeCounter.inc();
		}
	    });
	    Thread second = new Thread(() -> {
		for (int i = 0; i < 5; i++) {
		    unsafeCounter.dec();
		}
	    });
	    first.start();
	    second.start();
	    first.join();
	    second.join();
	    int value2 = unsafeCounter.get();
	    System.out.println("Current counter value: " + value2);
	    value = value2;
	}
    }
}
