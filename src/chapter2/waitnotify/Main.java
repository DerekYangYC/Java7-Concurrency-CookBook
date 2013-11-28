package chapter2.waitnotify;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventStorage storage = new EventStorage();
		Producer producer = new Producer(storage);
		Consumer consumer = new Consumer(storage);
		
		Thread thread1 = new Thread(producer);
		Thread thread2 = new Thread(consumer);
		
		thread1.start();
		thread2.start();
	}

}
