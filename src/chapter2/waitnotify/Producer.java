package chapter2.waitnotify;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

	private EventStorage storage;
	
	public Producer(EventStorage storage){
		this.storage=storage;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<100;i++){
			storage.set();
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
