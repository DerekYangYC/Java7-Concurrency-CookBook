package chapter6.blockdeque;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {

	private LinkedBlockingDeque<String> requestList;
	
	public Client(LinkedBlockingDeque<String> requiresList){
		this.requestList=requiresList;
	}
	
	@Override
	public void run() {
		for (int i=0; i<3; i++) {
			for (int j=0; j<5; j++) {
				StringBuilder request=new StringBuilder();
				request.append(i).append(":").append(j);
				try {
					requestList.put(request.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.printf("Client: %s at %s.\n",request,new Date());
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("Client: End.\n");
	}
	
	public static void main(String[] args) throws InterruptedException{
		LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>(3);
		Thread thread = new Thread(new Client(list));
		thread.start();
		
		for (int i=0; i<5 ; i++) {
			for (int j=0; j<3; j++) {
				String request=list.take();
				System.out.printf("Main: Request: %s at %s. Size: %d\n",request,new Date(),list.size());
			}
			TimeUnit.MILLISECONDS.sleep(300);
		}
		System.out.printf("Main: End of the program.\n");
	}

}
