package chapter1.sleep;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileClock implements Runnable {

	@Override
	public void run() {
		for (int i=0; i<10; i++){
			System.out.printf("%s\n", new Date());
			try{
				TimeUnit.SECONDS.sleep(1);
			}catch(InterruptedException e){
				System.out.println("FileClock interrupted!");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new FileClock());
		thread.start();
		
		try{
			TimeUnit.SECONDS.sleep(5);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		
		thread.interrupt();
	}

}
