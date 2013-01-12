/**
 * 
 */

/**
 * @author 1
 *
 */
public interface IVector<T> {
/**
 * ��������� ������ � ���������
 * @param o
 */
	void add (T o);
	
	/**
	 * ��������� ������ � ��������� �� ��������� �������
	 *@param o
	 *@param pos
	 */
	void add(T o, int pos);
	
	/**
	 * ������� ������ �� ���������, ����������� �� ������������ �������
	 * @param index
	 * @return
	 */
	T get(int index);
	
	/**
	 * ���������� ������ �������, ���� ����� ���� � ������. ���� ������ ���, �� -1.
	 * @param o
	 * @return
	 */
	int IndexOf(T o);
	
	 /**
     * ������� ������ �� ���������, ����������� �� ��������� �������
     * @param index
     */
    void remove(int index);

}
