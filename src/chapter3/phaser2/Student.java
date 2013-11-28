package chapter3.phaser2;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable {
	
	private Phaser phaser;
	
	public Student(MyPhaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.printf("%s: Has arrived to do the exam. %s\n",Thread.currentThread().getName(),new Date());
		
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the first exercise. %s\n",Thread.currentThread().getName(),new Date());
		
		doExercise1();
		
		System.out.printf("%s: Has done the first exercise. %s\n",Thread.currentThread().getName(),new Date());
		
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the second exercise.%s\n",Thread.currentThread().getName(),new Date());
		
		doExercise2();
		
		System.out.printf("%s: Has done the second exercise. %s\n",Thread.currentThread().getName(),new Date());
		
		phaser.arriveAndAwaitAdvance();
		
		System.out.printf("%s: Is going to do the third exercise. %s\n",Thread.currentThread().getName(),new Date());
		
		doExercise3();
		
		System.out.printf("%s: Has finished the exam. %s\n",Thread.currentThread().getName(),new Date());
		
		phaser.arriveAndAwaitAdvance();
	}
	
	private void doExercise1() {
		try {
			long duration=(long)(Math.random()*10);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void doExercise2() {
		try {
			long duration=(long)(Math.random()*10);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void doExercise3() {
		try {
			long duration=(long)(Math.random()*10);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		MyPhaser phaser = new MyPhaser();
		Student[] students = new Student[5];
		for(int i=0;i<students.length;i++){
			students[i] = new Student(phaser);
			phaser.register();
		}
		
		Thread[] threads = new Thread[students.length];
		for(int i=0;i<students.length;i++){
			threads[i] = new Thread(students[i],"Student "+i);
			threads[i].start();
		}
		
		//wait for the completion of the five threads
		for (int i=0; i<threads.length; i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("Main: The phaser has finished: %s.\n",phaser.isTerminated());
	}
	
}
