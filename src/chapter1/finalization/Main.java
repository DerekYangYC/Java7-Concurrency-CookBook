package chapter1.finalization;

import java.util.Date;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread dataThread = new Thread(new DataSourcesLoader(),"DataSourceThread");
		Thread networkThread = new Thread(new NetworkConnectionsLoader(),"NetworkConnectionThread");
		
		dataThread.start();
		networkThread.start();
		
		try{
			dataThread.join();
			networkThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.printf("Config loaded: %s\n", new Date());
	}

}
