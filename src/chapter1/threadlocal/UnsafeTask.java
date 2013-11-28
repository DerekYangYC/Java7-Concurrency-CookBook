package chapter1.threadlocal;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnsafeTask implements Runnable {

	private Date startDate;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startDate = new Date();
		System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate);
		try {
			TimeUnit.SECONDS.sleep( (int)Math.rint(Math.random()*10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UnsafeTask task = new UnsafeTask();
		for(int i=0;i<10;i++){
//			UnsafeTask task = new UnsafeTask(); it's the solution too!
			Thread thread = new Thread(task);
			thread.start();
			try{
				TimeUnit.SECONDS.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
