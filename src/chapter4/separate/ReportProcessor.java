package chapter4.separate;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable {

	private CompletionService<String> service;
	private boolean end;
	
	public ReportProcessor(CompletionService<String> service, boolean end) {
		super();
		this.service = service;
		this.end = end;
	}

	public void setEnd(boolean end){
		this.end = end;
	}
	
	@Override
	public void run() {
		while(!end){
			try {
				Future<String> result = service.poll(20,  TimeUnit.SECONDS);
				if(result!=null){
					String report = result.get();
					System.out.printf("ReportReceiver: Report Received: %s\n",report);
				}
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("ReportSender: End\n");
		}
	}

	public static void main(String[] args){
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<String> service = new ExecutorCompletionService<>(executor);
		
		ReportRequest faceRequest=new ReportRequest("Face", service);
		ReportRequest onlineRequest=new ReportRequest("Online", service); 
		Thread faceThread=new Thread(faceRequest);
		Thread onlineThread=new Thread(onlineRequest);
		
		ReportProcessor processor=new ReportProcessor(service,false);
		Thread senderThread=new Thread(processor);
		
		System.out.printf("Main: Starting the Threads\n");
		faceThread.start();
		onlineThread.start();
		senderThread.start();
		
		//Wait for the finalization of the ReportRequestthreads
		try {
			System.out.printf("Main: Waiting for the report generators.\n");
			faceThread.join();
			onlineThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Main: Shutting down the executor.\n");
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		processor.setEnd(true);
		System.out.println("Main: Ends");
	}
	
}
