package chapter4.rejected;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
	
	private String name;
	
	public Task(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public void run() {
		System.out.println("Task "+name+": Starting");
		try {
			long duration=(long)(Math.random()*10);
			System.out.printf("Task %s: ReportGenerator: Generating a report during %d seconds\n",name,duration);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Task %s: Ending\n",name);
	}
	
	public static void main(String[] args){
		RejectedTaskController controller = new RejectedTaskController();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		executor.setRejectedExecutionHandler(controller);
		
		System.out.printf("Main: Starting.\n");
		for (int i=0; i<3; i++) {
			Task task=new Task("Task"+i);
			executor.submit(task);
		}
		System.out.printf("Main: Shutting down the Executor.\n");
		executor.shutdown();
		
		System.out.printf("Main: Sending another Task.\n");
		Task task=new Task("RejectedTask");
		executor.submit(task);
		
		System.out.println("Main: End");
	}

}
