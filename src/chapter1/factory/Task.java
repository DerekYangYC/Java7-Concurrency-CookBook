package chapter1.factory;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
		Task task = new Task();
		Thread thread;
		System.out.printf("Starting the Threads\n");
		
		for (int i=0; i<10; i++){
			thread=factory.newThread(task);
			thread.start();
		}
		System.out.printf("Factory stats:\n");
		System.out.printf("%s\n",factory.getStats());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}

}
