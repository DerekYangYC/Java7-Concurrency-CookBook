package chapter1.groupexception;

import java.util.Random;

public class Task implements Runnable {

	@Override
	public void run() {
		int result;
		Random random = new Random(Thread.currentThread().getId());
		while(true){
			result = 100/((int)random.nextDouble()*1000);
			System.out.printf("%s : %f\n", Thread.currentThread().getId(), result);
			if (Thread.currentThread().isInterrupted()) {
				System.out.printf("%d : Interrupted\n",Thread.currentThread().getId());
				return;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThreadGroup mtg = new MyThreadGroup("MyThreadGroup");
		Task task = new Task();
		for(int i=0;i<2;i++){
			Thread t =new Thread(mtg,task);
			t.start();
		}
	}

}
