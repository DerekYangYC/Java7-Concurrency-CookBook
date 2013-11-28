package chapter2.readwritelock;

public class Writer implements Runnable {

	private PricesInfo pricesInfo;
	
	public Writer(PricesInfo pricesInfo){
		this.pricesInfo=pricesInfo;
	}
	
	@Override
	public void run() {
		for (int i=0; i<3; i++) {
			System.out.printf("Writer: Attempt to modify the prices.\n");
			pricesInfo.setPrices(i*i+3.0, i*2*i+4.0);
			System.out.printf("Writer: Prices have been modified.\n");
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		PricesInfo pricesInfo = new PricesInfo();
		
		Reader[] readers = new Reader[5];
		Thread[] threadReaders = new Thread[5];
		
		Writer writer = new Writer(pricesInfo);
		Thread threadWriter = new Thread(writer);
		
		for(int i=0;i<5;i++){
			readers[i]=new Reader(pricesInfo);
			threadReaders[i]=new Thread(readers[i]);
		}
		
		for(int i=0;i<5;i++){
			threadReaders[i].start();
		}
		threadWriter.start();
		
	}

}
