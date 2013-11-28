package chapter4.multipleall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<Result> {

	private String name;
	
	public Task(String name) {
		super();
		this.name = name;
	}

	@Override
	public Result call() throws Exception {
		System.out.printf("%s: Staring\n",this.name);
		try {
			long duration=(long)(Math.random()*10);
			System.out.printf("%s: Waiting %d seconds for results.\n",this.name,duration);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int value=0;
		for (int i=0; i<5; i++){
			value+=(int)(Math.random()*100);
		}
		
		Result result=new Result();
		result.setName(this.name);
		result.setValue(value);
		
		System.out.println(this.name+": Ends");
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Task> taskList = new ArrayList<>();
		for(int i=0;i<3;i++){
			Task task = new Task(String.valueOf(i));
			taskList.add(task);
		}
		
		List<Future<Result>> resultList = null;
		
		try {
			resultList = executor.invokeAll(taskList); //key
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		
		System.out.println("Main: Printing the results");
		for (int i=0; i<resultList.size(); i++){
			Future<Result> future=resultList.get(i);
			try {
				Result result=future.get();
				System.out.println(result.getName()+": "+result.getValue());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
