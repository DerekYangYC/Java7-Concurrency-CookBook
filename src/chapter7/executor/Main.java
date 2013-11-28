package chapter7.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import chapter7.factory.MyTask;
import chapter7.factory.MyThreadFactory;

public class Main {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
	MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
	ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
	
	MyTask task=new MyTask();
	executor.submit(task);
	executor.shutdown();
	
	executor.awaitTermination(1, TimeUnit.DAYS);
	System.out.printf("Main: End of the program.\n");
    }

}
