import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PassEast implements Runnable {
	
	public int people_in_museum;
	public boolean flagOfTakeVisitors = false;
	public boolean opengate = false;
	public Director director; 
	public Controller controller; 
	public Visitors visitors;
	public boolean entrance; 
	
	
	
	public PassEast ( Visitors visitors_, Director director_,int input) {
	director = director_;
	visitors = visitors_;
	people_in_museum=input;
	}
		
	public int getInputPeople() {
		return people_in_museum;
	}
	

	public void setFlagOfTakeVisitors(boolean flagOfTakeVisitors) {
		this.flagOfTakeVisitors = flagOfTakeVisitors;
	}

	public boolean isFlagOfTakeVisitors() {
		return flagOfTakeVisitors;
	}

	public void setOpengate(boolean opengate) {
		this.opengate = opengate;
	}

	public boolean permitPeople() throws InterruptedException { // ������� ����������� ���� ������ ����
		director.getDirectorLock().lock();
		try 
		{
			if (controller.isFlag_of_entrance() == true ) //���� ���������� ��������� ��� ����� 
			{
				entrance = true;
				return true;
			}
			
		}
		finally 
		{
			director.getDirectorLock().unlock();
		}
		entrance = false;
		return false;
	}
	
	public void takeVisitors () throws InterruptedException {	
			if (permitPeople() == true  ) // ���� ���� ��������
			{
				int count = visitors.countVisitorsToMuseum;
				while (visitors.getCountVisitorsToMuseum() != 0 ) {
					people_in_museum++;
					visitors.countVisitorsToMuseum--;
				}
				System.out.println("����� EAST ����� - " + count + " �����");
				System.out.println("����� � ����� ����� - " + people_in_museum );
			}
		
	}
	
	public void listenController () throws InterruptedException {
		director.getDirectorLock().lock();
		try {
			
		if (controller.isFlag_of_entrance() == true )
			System.out.println("�������� EAST ������(����������)");
		else 
			System.out.println("�������� EAST ������(����������)");
		}
		finally {
			director.getDirectorLock().unlock();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
			director.getDirectorLock().lock();
			try {
				try {
					director.getDirectorFunds().await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				if (flagOfTakeVisitors == true  ) 
					try {
						takeVisitors();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(opengate == true ) {
					try {
						listenController();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				flagOfTakeVisitors = false;
				opengate = false;
			}
			finally {
				director.getDirectorLock().unlock();
			}
			
		}//endwhile
	}
		
	
	
}
