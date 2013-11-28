package chapter5.cancel;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ArrayGenerator {

    public int[] generateArray(int size) {
	int array[] = new int[size];
	Random random = new Random();
	for (int i = 0; i < size; i++) {
	    array[i] = random.nextInt(10);
	}
	return array;
    }

    public static void main(String[] args) {
	int array[] = (new ArrayGenerator()).generateArray(1000);

	TaskManager manager = new TaskManager();

	ForkJoinPool pool = new ForkJoinPool();

	SearchNumberTask task = new SearchNumberTask(array, 0, 1000, 5, manager);

	pool.execute(task);
	pool.shutdown();

	try {
	    pool.awaitTermination(1, TimeUnit.DAYS);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.printf("Main: The program has finished\n");
    }

}
