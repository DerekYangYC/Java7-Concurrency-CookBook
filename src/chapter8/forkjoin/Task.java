package chapter8.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class Task extends RecursiveAction {

    private int array[];
    private int start;
    private int end;

    public Task(int[] array, int start, int end) {
	super();
	this.array = array;
	this.start = start;
	this.end = end;
    }

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
	// TODO Auto-generated method stub
	ForkJoinPool pool=new ForkJoinPool();
	int array[] = new int[1000];
	Task task1 = new Task(array,0,array.length);
	
	pool.execute(task1);
	
	while (!task1.isDone()) {
	    showLog(pool);
	    TimeUnit.SECONDS.sleep(1);
	    }
	
	pool.shutdown();
	pool.awaitTermination(1, TimeUnit.DAYS);
	
	showLog(pool);
	System.out.printf("Main: End of the program.\n");
    }

    @Override
    protected void compute() {
	if (end - start > 100) {
	    int mid = (start + end) / 2;
	    Task task1 = new Task(array, start, mid);
	    Task task2 = new Task(array, mid, end);

	    task1.fork();
	    task2.fork();

	    task1.join();
	    task1.join();
	} else {
	    for (int i = start; i < end; i++) {
		array[i]++;
		try {
		    Thread.sleep(5);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }

	}

    }
    private static void showLog(ForkJoinPool pool) {
	System.out.printf("**********************\n");
	System.out.printf("Main: Fork/Join Pool log\n");
	System.out.printf("Main: Fork/Join Pool: Parallelism: %d\n",pool.getParallelism());
	System.out.printf("Main: Fork/Join Pool: Pool Size: %d\n",pool.getPoolSize());
	System.out.printf("Main: Fork/Join Pool: Active Thread Count: %d\n",pool.getActiveThreadCount());
	System.out.printf("Main: Fork/Join Pool: Running Thread Count: %d\n",pool.getRunningThreadCount());
	System.out.printf("Main: Fork/Join Pool: Queued Submission: %d\n",pool.getQueuedSubmissionCount());
	System.out.printf("Main: Fork/Join Pool: Queued Tasks: %d\n",pool.getQueuedTaskCount());
	System.out.printf("Main: Fork/Join Pool: Queued Submissions: %s\n",pool.hasQueuedSubmissions());
	System.out.printf("Main: Fork/Join Pool: Steal Count: %d\n",pool.getStealCount());
	System.out.printf("Main: Fork/Join Pool: Terminated : %s\n",pool.isTerminated());
	System.out.printf("**********************\n");
	}
}
