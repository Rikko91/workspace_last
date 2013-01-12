

public class PassWest implements Runnable {
	public int people_in_museum;
	public boolean takeOutvisitors = false;
	public Director director; // �����njh
	public Controller controller; //����������
	public Visitors visitors;
	public boolean entrance; // ���� �� �����
	public PassEast museum;
	
	

	public void setTakeOutvisitors(boolean takeOutvisitors) {
		this.takeOutvisitors = takeOutvisitors;
	}


	public PassWest ( Visitors visitors_, Director director_,int input, PassEast museum) {
	director = director_;
	visitors = visitors_;
	people_in_museum=input;
	this.museum = museum;
	}
	
	
	
	public void takeOutVisitors () throws InterruptedException {	// ������� ����������� �� �����
		director.getDirectorLock().lock();
		try {
			//if (permitPeople() == false || permitPeople() == true  )
			//{
				//System.out.println("����� ������ - �������� West ��������");
				people_in_museum = visitors.getCountOutVisitorsToMuseum();
				
				while (people_in_museum != 0 ) {
					people_in_museum--;
					
				}
				System.out.println("Через WEST вышло - " + visitors.countOutVisitorsToMuseum + " людей, осталось  " + museum.people_in_museum - visitors.countOutVisitorsToMuseum) ) ;
				museum.people_in_museum = museum.people_in_museum - visitors.countOutVisitorsToMuseum;
			//}
		}
		finally {
			director.getDirectorLock().unlock();
		}
	}
	

	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			director.getDirectorLock().lock();
			try {
				try {
					director.getDirectorFunds().await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (takeOutvisitors == true) {
					try {
						takeOutVisitors();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
			}
			finally {
				director.getDirectorLock().unlock();
			}
			takeOutvisitors = false;
			
		}
	}
	
}
