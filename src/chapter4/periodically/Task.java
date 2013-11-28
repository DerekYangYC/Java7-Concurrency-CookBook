package chapter4.periodically;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

	private String name;

	public Task(String name) {
		this.name = name;
	}

	@Override
	public void run(){
		System.out.printf("%s: Starting at : %s\n", name, new Date());
//		return "Hello, world";
	}

	public static void main(String[] args) {
		ScheduledExecutorService executor = (ScheduledExecutorService) Executors
				.newScheduledThreadPool(1);
		System.out.printf("Main: Starting at: %s\n", new Date());

		Task task = new Task("Task");
		ScheduledFuture<?> result = (ScheduledFuture<?>) executor
				.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
		
		for (int i=0; i<10; i++){
			System.out.printf("Main: Delay: %d\n",result.getDelay(TimeUnit.MILLISECONDS));
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Main: Finished at: %s\n",new Date());
	}


}
