package chapter2.independent;

/*
 * Actually it's not a good example because just two thread to access tow different variables in the same object.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Cinema cinema = new Cinema();
		
		Thread thread1=new Thread(new TicketOffice1(cinema),"office1");
		Thread thread2=new Thread(new TicketOffice2(cinema),"offcie2");
		
		thread1.start();
		thread2.start();
		
		try{
			thread1.join();
			thread2.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.printf("Room 1 Vacancies: %d\n",cinema.getVacanciesCinema1());
		System.out.printf("Room 2 Vacancies: %d\n",cinema.getVacanciesCinema2());
	}

}
