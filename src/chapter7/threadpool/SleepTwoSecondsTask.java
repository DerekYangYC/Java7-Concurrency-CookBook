package chapter7.threadpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class SleepTwoSecondsTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(2);
		return new Date().toString();
	}
	
	public static void main(String[] args){
		MyExecutor myExecutor=new MyExecutor(2, 4, 1000, TimeUnit.
				MILLISECONDS, new LinkedBlockingDeque<Runnable>());
		List<Future<String>> results=new ArrayList<>();
		
		for (int i=0; i<10; i++) {
			SleepTwoSecondsTask task=new SleepTwoSecondsTask();
			Future<String> result=myExecutor.submit(task);
			results.add(result);
		}
		
		for (int i=0; i<5; i++){
			try {
				String result=results.get(i).get();
				System.out.printf("Main: Result for Task %d : %s\n",i,result);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		myExecutor.shutdown();
		
		for (int i=5; i<10; i++){
			try {
				String result=results.get(i).get();
				System.out.printf("Main: Result for Task %d : %s\n",i,result);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		//Wait for the completion of the executor using the awaitTermination()method
		try {
			myExecutor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Main: End of the program.\n");
	}
	
}
