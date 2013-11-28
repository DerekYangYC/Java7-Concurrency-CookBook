package chapter6.variable;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
	
//	private AtomicLong balance;
//	
//	public Account(){
//		balance = new AtomicLong();
//	}
//
//	public long getBalance() {
//		return balance.get();
//	}
//
//	public void setBalance(long balance) {
//		this.balance.set(balance);
//	}
//	
//	public void addAmount(long amount){
//		this.balance.getAndAdd(amount);
//	}
//	
//	public void subtractAmount(long amount){
//		this.balance.getAndAdd(-amount);
//	}
	
	private long balance;
	
	public Account(){
		balance = 0;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public void addAmount(long amount){
		this.balance += amount;
	}
	
	public void subtractAmount(long amount){
		this.balance -= amount;
	}
}
