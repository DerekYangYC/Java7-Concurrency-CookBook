package chapter7.factory;

import java.util.concurrent.TimeUnit;

public class MyTask implements Runnable {

    @Override
    public void run() {
	try {
	    TimeUnit.SECONDS.sleep(2);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }
    
    public static void main(String[] args) throws InterruptedException{
	MyThreadFactory myFactory = new MyThreadFactory("MyThreadFactory");
	MyTask task = new MyTask();
	
	Thread thread = myFactory.newThread(task);
	
	thread.start();
	thread.join();
	
	System.out.printf("Main: Thread information.\n");
	System.out.printf("%s\n",thread);
	System.out.printf("Main: End of the example.\n");
    }
    
}
