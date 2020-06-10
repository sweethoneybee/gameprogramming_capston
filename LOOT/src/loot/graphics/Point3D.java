package loot.graphics;

/**
 * 3���� ���� ��(Viewport ��)�� Ư�� ��ġ�� ��Ÿ���� ���� Ŭ�����Դϴ�.<br>
 * ����: �� Ŭ������ Ư�� �޼����� ��ȯ �������� ����ϱ� ���� ���� �⺻���� ��ɸ� �����ϰ� ������<br>
 * ��κ��� ��� �������� ���� ������ �ϱ� ���� ������ �ڵ带 ����ؾ� �� ���Դϴ�.
 * 
 * @author Racin
 *
 */
public class Point3D
{
	/**
	 * ��ġ�� ��Ÿ���� x��ǥ�Դϴ�.<br>
	 * 3���� �������� x���� ���ʿ��� ���������� �����մϴ�.
	 */
	public double x;
	
	/**
	 * ��ġ�� ��Ÿ���� y��ǥ�Դϴ�.<br>
	 * 3���� �������� y���� <b>�Ʒ����� ����</b> �����մϴ�.
	 */
	public double y;
	
	/**
	 * ��ġ�� ��Ÿ���� z��ǥ�Դϴ�.<br>
	 * 3���� �������� z���� ȭ�� ���� ������ �� �� ���� ���ϴ� �������� �����մϴ�.
	 */
	public double z;
	
	public Point3D()
	{
	}
	
	public Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(Point3D other)
	{
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	/**
	 * �� ��ҿ� ��� ��ǥ ������ ��Į�� �Ÿ�( sqrt(x�Ÿ�^2 + y�Ÿ�^2 + z�Ÿ�^2) )�� ���մϴ�.
	 */
	public double GetDistance(Point3D target)
	{
		return GetDistance(this, target);
	}
	
	/**
	 * �� ��ҿ� ��� ��ǥ ������ ��Į�� �Ÿ�( sqrt(x�Ÿ�^2 + y�Ÿ�^2 + z�Ÿ�^2) )�� ���մϴ�.
	 */
	public double GetDistance(double target_x, double target_y, double target_z)
	{
		return GetDistance(this, target_x, target_y, target_z);
	}
	
	/**
	 * �� ��ǥ ������ ��Į�� �Ÿ�( sqrt(x�Ÿ�^2 + y�Ÿ�^2 + z�Ÿ�^2) )�� ���մϴ�.
	 */
	public static double GetDistance(Point3D origin, Point3D target)
	{
		return Math.sqrt(
				( target.x - origin.x ) * ( target.x - origin.x ) +
				( target.y - origin.y ) * ( target.y - origin.y ) +
				( target.z - origin.z ) * ( target.z - origin.z ) );
	}

	/**
	 * �� ��ǥ ������ ��Į�� �Ÿ�( sqrt(x�Ÿ�^2 + y�Ÿ�^2 + z�Ÿ�^2) )�� ���մϴ�.
	 */
	public static double GetDistance(Point3D origin, double target_x, double target_y, double target_z)
	{
		return Math.sqrt(
				( target_x - origin.x ) * ( target_x - origin.x ) +
				( target_y - origin.y ) * ( target_y - origin.y ) +
				( target_z - origin.z ) * ( target_z - origin.z ) );
	}

	/**
	 * �� ��ǥ ������ ��Į�� �Ÿ�( sqrt(x�Ÿ�^2 + y�Ÿ�^2 + z�Ÿ�^2) )�� ���մϴ�.
	 */
	public static double GetDistance(double origin_x, double origin_y, double origin_z, double target_x, double target_y, double target_z)
	{
		return Math.sqrt(
				( target_x - origin_x ) * ( target_x - origin_x ) +
				( target_y - origin_y ) * ( target_y - origin_y ) +
				( target_z - origin_z ) * ( target_z - origin_z ) );
	}
	
	@Override
	public String toString()
	{
		//�̰� ��ģ����
		
		return String.format("(%.2f, %.2f, %.2f)", x, y, z);
	}
}
