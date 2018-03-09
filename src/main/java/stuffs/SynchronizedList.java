package stuffs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedList {
	
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Integer> list = new ArrayList<>();
		// List<Integer> list = Collections.synchronizedList(list2);
		Collections.addAll(list, new Integer[100000]);
		System.out.println("initial size: " + list.size());

		final ExecutorService executorService = Executors.newFixedThreadPool(10);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			executorService.execute(() -> {
				list.remove(0);
			});
		}
		executorService.shutdownNow();
		executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
		System.out.println("Final Size " + list.size());// should be zero
	}

}
