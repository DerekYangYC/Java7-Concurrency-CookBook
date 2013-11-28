package chapter4.cancel;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String> {

	@Override
	public String call() throws Exception {
		while(true){
			System.out.printf("Task: Test\n");
			Thread.sleep(100);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		
		Task task=new Task();
		System.out.printf("Main: Executing the Task\n");
		Future<String> result=executor.submit(task);
		
		//Put the main task to sleep for 2 seconds.
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Main: Canceling the Task\n");
		result.cancel(true);
		
		System.out.printf("Main: Canceled: %s\n",result.isCancelled());
		System.out.printf("Main: Done: %s\n",result.isDone());
		
		executor.shutdown();
		System.out.printf("Main: The executor has finished\n");
	}

}
