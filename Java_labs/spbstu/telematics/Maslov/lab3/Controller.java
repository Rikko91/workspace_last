import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Controller implements Runnable {
	public boolean flag_of_entrance; 
	public boolean event = false;
	public Director director;
	public PassEast museum;
	public PassWest museum2;
	public MuseumState st;
	
	
	public Controller(Director director_, PassEast museum_, PassWest museum2_) {
		director=director_;
		museum = museum_;
		museum2 = museum2_;
	}
	
	
	public boolean isFlag_of_entrance() {
		return flag_of_entrance;
	}


	public void test () {
		director.getDirectorLock().lock();
		try {
			
			if (director.getState() == MuseumState.Open) {//���� ����� ������
				flag_of_entrance=true; //���������� ��������� ������� ����� � ��������� ����� 
				museum.setOpengate(true);
				
				if ( st != director.getState() ) 
				System.out.println("���������� ������ ��������");
				st = director.getState();
			}
			else
				{// � ������ ������ ��������� ������ ������� �����
				flag_of_entrance=false;
				if ( st != director.getState() ) 
				System.out.println("���������� ������ ��������");
				st = director.getState();
				}
			director.getDirectorFunds().signalAll();
		}
		finally 
		{
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
				if (event == true)
				test();
			}
			finally {
				director.getDirectorLock().unlock();
			}
			
		event = false;
		}
	}
	
	
	
}
