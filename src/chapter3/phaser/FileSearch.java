package chapter3.phaser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable{

	private String initPath;
	private String end;
	
	private List<String> results;
	
	private Phaser phaser;
	
	public FileSearch(String initPath, String end, Phaser phaser){
		this.initPath=initPath;
		this.end=end;
		this.phaser=phaser;
		results = new ArrayList<String>();
	}
	
	@Override
	public void run() {
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s: Starting.\n",Thread.currentThread().getName());
		
		File file = new File(initPath);
		if (file.isDirectory()) {
			directoryProcess(file);
		}
		
		if(!checkResults()) return;
		
		filterResults();
		
		if(!checkResults()) return;
		
		showInfo();
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s: Work completed.\n",Thread.currentThread().getName());
		
	}
	
	private void directoryProcess(File file){
		
		File[] list = file.listFiles();
		if(list!=null){
			for(int i=0;i<list.length;i++){
				if(list[i].isDirectory()){
					directoryProcess(list[i]);
				}else{
					fileProcess(list[i]);
				}
			}
		}
	}
	private void fileProcess(File file) {
		if (file.getName().endsWith(end)) {
			results.add(file.getAbsolutePath());
		}
	}
	
	private void filterResults(){
		List<String> newResults = new ArrayList<>();
		long actualDate = new Date().getTime();
		
		for(int i=0;i<results.size();i++){
			File file=new File(results.get(i));
			long fileDate = file.lastModified();
			
			if(actualDate-fileDate<TimeUnit.MICROSECONDS.convert(1, TimeUnit.DAYS)){
				newResults.add(results.get(i));
			}
		}
		results = newResults;
	}
	
	private boolean checkResults(){
		if (results.isEmpty()) {
			System.out.printf("%s: Phase %d: 0 results.\n",Thread.currentThread().getName(),phaser.getPhase());
			System.out.printf("%s: Phase %d: End.\n",Thread.currentThread().getName(),phaser.getPhase());
			phaser.arriveAndDeregister();
			return false;
		} else {
			System.out.printf("%s: Phase %d: %d results.\n",Thread.currentThread().getName(),phaser.getPhase(),results.size());
			phaser.arriveAndAwaitAdvance();
			return true;
		} 
	}
	
	private void showInfo() {
		for (int i=0; i<results.size(); i++){
			File file=new File(results.get(i));
			System.out.printf("%s: %s\n",Thread.currentThread().getName(),file.getAbsolutePath());
		}
		phaser.arriveAndAwaitAdvance();
	}

	public static void main(String[] args){
		Phaser phaser = new Phaser(3);
		FileSearch system=new FileSearch("C:\\Windows", "log", phaser);
		FileSearch apps=new FileSearch("C:\\Program Files","log",phaser);
		FileSearch documents=new FileSearch("C:\\Documents And Settings","log",phaser);
		
		Thread systemThread=new Thread(system,"System");
		systemThread.start();
		Thread appsThread=new Thread(apps,"Apps");
		appsThread.start();
		Thread documentsThread=new Thread(documents, "Documents");
		documentsThread.start();
		
		//Wait for the finalization of the three threads. Otherwise, the print-out of main thread will be the first line of the output
		try {
			systemThread.join();
			appsThread.join();
			documentsThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Terminated: "+ phaser.isTerminated());
		
	}
	
}
