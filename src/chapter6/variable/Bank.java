package chapter6.variable;

public class Bank implements Runnable {
	
	private Account account;
	
	public Bank(Account account){
		this.account = account;
	}
	
	@Override
	public void run() {
		for(int i=0;i<10;i++){
			account.subtractAmount(1000);
		}
	}
	
	public static void main(String[] args){
		Account account = new Account();
		account.setBalance(1000);
		
		Thread companyThread = new Thread(new Company(account));
		Thread bankThread = new Thread(new Bank(account));
		
		System.out.printf("Account : Initial Balance: %d\n",account.getBalance());
		
		companyThread.start();
		bankThread.start();
		
		try {
			companyThread.join();
			bankThread.join();
			System.out.printf("Account : Final Balance: %d\n",account.getBalance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
