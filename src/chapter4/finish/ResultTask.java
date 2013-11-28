package chapter4.finish;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ResultTask extends FutureTask<String> {
	
	private String name;
	public ResultTask(Callable<String> callable) {
		super(callable);
		this.name = ((ExecutableTask)callable).getName();
	}
	
	@Override
	protected void done() {
		if (isCancelled()) {
			System.out.printf("%s: Has been canceled\n",name);
		} else {
			System.out.printf("%s: Has finished\n",name);
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		ResultTask resultTasks[]=new ResultTask[5];
		
		for (int i=0; i<5; i++) {
			ExecutableTask executableTask=new ExecutableTask("Task "+i);
			resultTasks[i]=new ResultTask(executableTask);
			executor.submit(resultTasks[i]);
		}
		
		//Put the main thread to sleep for 4 seconds.
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//Cancel all the tasks you have sent to the executor
		for (int i=0; i<resultTasks.length; i++) {
			resultTasks[i].cancel(true);
		}
		
		//Why throw InterruptedException!!!!????
		//Write to the console the result of those tasks that haven't been canceled
		for(int i=0;i<resultTasks.length;i++){
			if(!resultTasks[i].isCancelled()){
				try {
					System.out.printf("%s\n",resultTasks[i].get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			TimeUnit.SECONDS.sleep(14);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		executor.shutdown();
	}

}
