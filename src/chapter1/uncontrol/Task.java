package chapter1.uncontrol;

public class Task implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int numero = Integer.parseInt("TTT");
	}
	
	public static void main(String[] args){
		Thread thread = new Thread(new Task());
		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		thread.start();
	}

}
