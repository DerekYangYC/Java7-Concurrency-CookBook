package chapter2.independent;


public class Cinema {
	
	private long vacanciesCinema1;
	private long vacanciesCinema2;
	
	private final Object c1Lock,c2Lock;
	
	public Cinema(){
		c1Lock = new Object();
		c2Lock = new Object();
		vacanciesCinema1 = 20;
		vacanciesCinema2 = 20;
	}
	
	public boolean sellTickets1 (int number) {
//		synchronized (c1Lock) {
			if (number<vacanciesCinema1) {
				vacanciesCinema1-=number;
				return true;
			} else {
				return false;
			}
//		}
	}
	
	public boolean sellTickets2 (int number) {
//		synchronized (c2Lock) {
			if (number<vacanciesCinema2) {
				vacanciesCinema2-=number;
				return true;
			} else {
				return false;
//			}
		}
	}
	
	public boolean returnTickets1(int number){
//		synchronized (c1Lock) {
			vacanciesCinema1+=number;
			return true;
//		}
	}
	
	public boolean returnTickets2(int number){
//		synchronized (c2Lock) {
			vacanciesCinema2+=number;
			return true;
//		}
	}
	
	public long getVacanciesCinema1() {
		return vacanciesCinema1;
	}
	
	public long getVacanciesCinema2() {
		return vacanciesCinema2;
	}
}
