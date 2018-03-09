package stuffs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadShutdownAndAwaitTermination {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		WorkerThread tasks[] = new WorkerThread[10];
		for (int i = 0; i < 10; i++) {
			tasks[i] = new WorkerThread("task" + i);
			exec.execute(tasks[i]);
		}
		exec.shutdown();
		System.out.println(exec.isTerminated());
		exec.awaitTermination(1, TimeUnit.SECONDS);
		// coloca a thread em espera até todos os processos terminarem de acordo com o tempo colocado para esperar. 
		// i.e O tempo tem que ser maior do que o tempo que os processos vão levar, se não ele vai deixar essa thread passar.
		System.out.println("Execution Completed");
		System.out.println(exec.isTerminated());
	}

}

class WorkerThread implements Runnable {
	String taskName;

	WorkerThread(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public String toString() {
		return taskName;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			Thread t1 = Thread.currentThread();
			System.out.println(t1.getName() + " executing the task " + taskName);
			t1.sleep(1000);
			System.out.println(t1.getName() + " COMPLETED the task " + taskName);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}