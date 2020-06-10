package loot.graphics;

import java.awt.Point;

/**
 * 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��) �Ǵ� 3���� ���� ��(Viewport ��)�� ��ġ�Ͽ� ������ ���� �� �� �ִ� ��� �ϳ��� �߻�ȭ�մϴ�.<br>
 * ����: �� Ŭ������ �ν��Ͻ��� 2������, �Ǵ� 3���������� Ȱ���� �� ������ �̴� trigger_coordination ���� ���� �޶����ϴ�.<br>
 * trigger_coordination ���� �����ڸ� ȣ���� �� ������ ������ �ڵ� �����ǹǷ�<br>
 * �������� ���ϴ� ������ �����ϵ��� ������ �����ڸ� ȣ���ϸ� �ǰڽ��ϴ�.
 * 
 * @author Racin
 *
 */
public abstract class VisualObject3D extends VisualObject
{
	/**
	 * �� ��Ұ� 3���� ���� ��(Viewport ��)�� ��ġ�� �߽����� x��ǥ�Դϴ�.<br>
	 * 3���� �������� x���� ���ʿ��� ���������� �����մϴ�.<br> 
	 * this.trigger_coordination ���� true�� ��� �� ���� ���� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.  
	 */
	public double pos_x;
	
	/**
	 * �� ��Ұ� 3���� ���� ��(Viewport ��)�� ��ġ�� �߽����� y��ǥ�Դϴ�.<br>
	 * 3���� �������� y���� <b>�Ʒ����� ����</b> �����մϴ�.<br> 
	 * this.trigger_coordination ���� true�� ��� �� ���� ���� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.  
	 */
	public double pos_y;
	
	/**
	 * �� ��Ұ� 3���� ���� ��(Viewport ��)�� ��ġ�� �߽����� z��ǥ�Դϴ�.<br>
	 * 3���� �������� z���� ȭ�� ���� ������ �� �� ���� ���ϴ� �������� �����մϴ�.<br> 
	 * this.trigger_coordination ���� true�� ��� �� ���� ���� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.  
	 */
	public double pos_z;
	
	/**
	 * �� ��Ұ� 3���� ���� ��(Viewport ��)���� �߽����� �������� ��/��� ������ x���� ������(<b>�ʺ��� ����</b>)�Դϴ�.<br>
	 * this.trigger_coordination ���� true�� ��� �� ���� ���� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.  
	 */
	public double radius_x;

	/**
	 * �� ��Ұ� 3���� ���� ��(Viewport ��)���� �߽����� �������� ��/�Ϸ� ������ y���� ������(<b>������ ����</b>)�Դϴ�.<br>
	 * this.trigger_coordination ���� true�� ��� �� ���� ���� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.  
	 */
	public double radius_y;
	
	/**
	 * �� �ʵ带 true�� ������ �θ� Viewport�� �� ��Ҹ� �׸� ��ġ�� ũ�⸦ �����մϴ�.<br>
	 * <br>
	 * �� �ʵ带 false�� ������ �θ� �� ��Ҵ� x, y, width, height���� �״�� ����Ͽ� �׸��� �۾��� �����մϴ�.<br>
	 * �� ��� �� ��Ҵ� VisualObject class�� �ν��Ͻ��� ������ ������� ó���˴ϴ�.<br>
	 * <br>
	 * ����:<br>
	 * ��Ҹ� ������ �� 2���� ��ǥ��(int ����)�� argument�� �Ͽ� �����Ͽ� ȣ���� ��� �� �ʵ�� �ڵ����� false�� �Ǹ�<br>
	 * 3���� ��ǥ��(double ����)�� argument�� �Ͽ� ȣ���� ��� �ڵ����� true�� �˴ϴ�.<br>
	 * ��¶��, ��κ��� ��� �� �ʵ��� ���� ���� ���� �̿ܿ��� ũ�� �Ű澲�� �ʾƵ� �� ���Դϴ�.
	 */
	public boolean trigger_coordination;

	/**
	 * �� �����ڴ� �� ��Ҹ� 2���� ��鿡 ���� ��ҷ� �����մϴ�.
	 */
	public VisualObject3D()
	{
	}

	/**
	 * �� �����ڴ� argument�� ���� �� ��Ҹ� 2���� ��鿡 ������ 3���� ������ ������ �����մϴ�.
	 */
	public VisualObject3D(boolean trigger_coordination)
	{
		this.trigger_coordination = trigger_coordination;
	}
	
	/**
	 * �� �����ڴ� �� ��Ҹ� 2���� ��鿡 ���� ��ҷ� �����մϴ�.
	 */
	public VisualObject3D(int width, int height)
	{
		super(width, height);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 2���� ��鿡 ���� ��ҷ� �����մϴ�.
	 */
	public VisualObject3D(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ ���� ��ҷ� �����մϴ�.
	 */
	public VisualObject3D(double radius_x, double radius_y)
	{
		this.radius_x = radius_x;
		this.radius_y = radius_y;
		this.trigger_coordination = true;
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ ���� ��ҷ� �����մϴ�.
	 */
	public VisualObject3D(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y)
	{
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.pos_z = pos_z;
		this.radius_x = radius_x;
		this.radius_y = radius_y;
		this.trigger_coordination = true;
	}

	/**
	 * �� �����ڴ� ���� ��� ������ ���� �� ��Ҹ� 2���� ��鿡 ������ 3���� ������ ������ �����մϴ�.
	 */
	public VisualObject3D(VisualObject3D other)
	{
		super(other);
		pos_x = other.pos_x;
		pos_y = other.pos_y;
		pos_z = other.pos_z;
		radius_x = other.radius_x;
		radius_y = other.radius_y;
		trigger_coordination = other.trigger_coordination;
	}
	
	
	/* ---------------------------------------------------
	 * 
	 * ���� �׽�Ʈ�� ���� �޼����
	 * 
	 */
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: double ������ Ư���� �� �޼���� radius_z�� ������ ������ ����ϴ� ���� �� �����մϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public boolean HitTest3D(double pos_x, double pos_y, double pos_z)
	{
		return	this.pos_x - radius_x <= pos_x && this.pos_x + radius_x >= pos_x &&
				this.pos_y - radius_y <= pos_y && this.pos_y + radius_y >= pos_y &&
				this.pos_z == pos_z;
	}
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: LOOT���� ��� ��Ҵ� ���������� 2���� ����̹Ƿ�<br>
	 * ���⼭�� ���� �׽�Ʈ�� �����ϰ� �ϱ� ���� z�� ���� �������� �����ϸ�<br>
	 * ����� ���� z��ǥ�� �Է¹��� z��ǥ ������ �Ÿ��� �ش� ���������� �۰ų� ���� ��� ������ ������ �����մϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	public boolean HitTest3D(double pos_x, double pos_y, double pos_z, double radius_z)
	{
		return	this.pos_x - radius_x <= pos_x && this.pos_x + radius_x >= pos_x &&
				this.pos_y - radius_y <= pos_y && this.pos_y + radius_y >= pos_y &&
				this.pos_z <= pos_z + radius_z && this.pos_z >= pos_z - radius_z;
	}
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: double ������ Ư���� �� �޼���� radius_z�� ������ ������ ����ϴ� ���� �� �����մϴ�.
	 * 
	 * @param pos ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� ��ǥ�Դϴ�.
	 */
	public final boolean HitTest3D(Point3D pos)
	{
		return HitTest3D(pos.x, pos.y, pos.z);
	}
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: LOOT���� ��� ��Ҵ� ���������� 2���� ����̹Ƿ�<br>
	 * ���⼭�� ���� �׽�Ʈ�� �����ϰ� �ϱ� ���� z�� ���� �������� �����ϸ�<br>
	 * ����� ���� z��ǥ�� �Է¹��� z��ǥ ������ �Ÿ��� �ش� ���������� �۰ų� ���� ��� ������ ������ �����մϴ�.
	 * 
	 * @param pos ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� ��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	public final boolean HitTest3D(Point3D pos, double radius_z)
	{
		return HitTest3D(pos.x, pos.y, pos.z, radius_z);
	}
	

	/**
	 * �� �޼���� VisualObject3D.HitTest3D()�� ��Ȯ�� ������ �۾��� �����մϴ�.<br>
	 * ������ Ŭ���� ������ ���� �ܰ迡 ���� �����Ǿ��� �� � ���� Ŭ������ ���� �ܼ��� ���� ��� ���� �׽�Ʈ(���⼭ �ϴ� �۾�)�� �� �� �ֵ���<br>
	 * ���������� �̷��� '�ٸ� �̸���' �޼��带 ����� ����մϴ�.<br>
	 * ��¶�� �������� �� �޼��带 ����� �� �����ϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	protected final boolean HitTest3D_Base(double pos_x, double pos_y, double pos_z)
	{
		return	this.pos_x - radius_x <= pos_x && this.pos_x + radius_x >= pos_x &&
				this.pos_y - radius_y <= pos_y && this.pos_y + radius_y >= pos_y &&
				this.pos_z == pos_z;
	}
	
	/**
	 * �� �޼���� VisualObject3D.HitTest3D()�� ��Ȯ�� ������ �۾��� �����մϴ�.<br>
	 * ������ Ŭ���� ������ ���� �ܰ迡 ���� �����Ǿ��� �� � ���� Ŭ������ ���� �ܼ��� ���� ��� ���� �׽�Ʈ(���⼭ �ϴ� �۾�)�� �� �� �ֵ���<br>
	 * ���������� �̷��� '�ٸ� �̸���' �޼��带 ����� ����մϴ�.<br>
	 * ��¶�� �������� �� �޼��带 ����� �� �����ϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	protected final boolean HitTest3D_Base(double pos_x, double pos_y, double pos_z, double radius_z)
	{
		return	this.pos_x - radius_x <= pos_x && this.pos_x + radius_x >= pos_x &&
				this.pos_y - radius_y <= pos_y && this.pos_y + radius_y >= pos_y &&
				this.pos_z <= pos_z + radius_z && this.pos_z >= pos_z - radius_z;
	}
	
	
	/* ---------------------------------------------------
	 * 
	 * �����ǥ ��� �� ��ǥ ��ȯ�� ���� �޼����
	 * 
	 */
	
	/**
	 * 3���� ���� �ȿ���, �ش� ��ǥ�� �� ��Ҹ� �������� �ϴ� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param pos_x �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public Point3D GetRelativePosition3D(double pos_x, double pos_y, double pos_z)
	{
		return new Point3D(pos_x - this.pos_x, pos_y - this.pos_y, pos_z - this.pos_z);
	}
	
	/**
	 * 3���� ���� �ȿ���, �ش� ��ǥ�� �� ��Ҹ� �������� �ϴ� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param pos �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� ��ǥ�Դϴ�.
	 */
	public Point3D GetRelativePosition3D(Point3D pos)
	{
		return GetRelativePosition3D(pos.x, pos.y, pos.z);
	}

	/**
	 * �� ��Ҹ� �������� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��� �� ��ǥ�� ����, �� ��Ұ� ���� �ִ� 3���� ���� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * ����: �� ��ҿ� ���� ��� ���� �ִ� ��ǥ�� ������� �����Ƿ� ������� �����ǥ�� pox_z ���� �׻� 0�� �˴ϴ�.
	 * 
	 * @param x 3���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y 3���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point3D GetRelativePosition3D(int x, int y)
	{
		return new Point3D(
				( ( x - this.x ) - width / 2.0 ) / width * radius_x * 2.0,
				( height / 2.0 - ( y - this.y ) ) / height * radius_y * 2.0,
				0);
	}
	
	/**
	 * �� ��Ҹ� �������� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��� �� ��ǥ�� ����, �� ��Ұ� ���� �ִ� 3���� ���� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * ����: �� ��ҿ� ���� ��� ���� �ִ� ��ǥ�� ������� �����Ƿ� ������� �����ǥ�� pox_z ���� �׻� 0�� �˴ϴ�.
	 * 
	 * @param pos 3���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� ��ǥ�Դϴ�.
	 */
	public final Point3D GetRelativePosition3D(Point pos)
	{
		return GetRelativePosition3D(pos.x, pos.y);
	}

	/**
	 * �� ��Ұ� ���� �ִ� 2���� ��� �� ��ǥ�� �����ϴ� 3���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * ����: �� ��ҿ� ���� ��� ���� �ִ� ��ǥ�� ������� �����Ƿ� ������� ��ǥ�� pox_z ���� �׻� �� ����� pos_z�� �����մϴ�.
	 * 
	 * @param x 3���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y 3���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point3D TransformTo3DPosition(int x, int y)
	{
		Point3D result = GetRelativePosition3D(x, y);
		result.x += this.pos_x;
		result.y += this.pos_y;
		result.z = this.pos_z;
		return result;
	}
	
	/**
	 * �� ��Ұ� ���� �ִ� 2���� ��� �� ��ǥ�� �����ϴ� 3���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * ����: �� ��ҿ� ���� ��� ���� �ִ� ��ǥ�� ������� �����Ƿ� ������� ��ǥ�� pox_z ���� �׻� �� ����� pos_z�� �����մϴ�.
	 * 
	 * @param pos 3���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 2���� ��ǥ�� �ȿ����� ��ǥ�Դϴ�.
	 */
	public final Point3D TransformTo3DPosition(Point pos)
	{
		return TransformTo3DPosition(pos.x, pos.y);
	}
	
	/**
	 * �� ��Ҹ� �������� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ���� �� ��ǥ�� ����, �� ��Ұ� ���� �ִ� 2���� ��� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * VisualObject3D.GetRelativePosition()�� argument�� z��ǥ�� ���� ������� ������<br>
	 * �׻� ( pos_x, pos_y, �ڽ��� z��ǥ )�� ���� 2���� �����ǥ�� ����� ��ȯ�մϴ�.<br>
	 * �̴� VisualObject3D ���忡�� z�� ������ '����'(Viewport.view_baseDistance ��)�� �� ���� ���� �����̸�<br>
	 * �������� ���ϴ�, �ش� 3���� ��ǥ�� ���� ��¥ 2���� �����ǥ�� ���Ϸ���<br>
	 * �� �޼��� ��� Viewport.GetRelativePositionFrom()�� ����ؾ� �մϴ�. 
	 * 
	 * @param pos_x 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z
	 * 			2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.<br>
	 * 			<br>
	 * 			<b>����:</b><br>
	 * 			VisualObject3D.GetRelativePosition()�� �� ���� ���� ������� �ʽ��ϴ�.<br>
	 * 			�ڼ��� ������ �޼��� ������ �����ϼ���.
	 */
	public Point GetRelativePosition(double pos_x, double pos_y, double pos_z)
	{
		int result_x = (int)( ( ( pos_x - this.pos_x ) + radius_x ) / radius_x / 2.0 * width );
		int result_y = (int)( ( radius_y - ( pos_y - this.pos_y ) ) / radius_y / 2.0 * height );
		
		return new Point(result_x, result_y);
	}
	
	/**
	 * �� ��Ҹ� �������� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ���� �� ��ǥ�� ����, �� ��Ұ� ���� �ִ� 2���� ��� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * VisualObject3D.GetRelativePosition()�� argument�� z��ǥ�� ���� ������� ������<br>
	 * �׻� ( pos_x, pos_y, �ڽ��� z��ǥ )�� ���� 2���� �����ǥ�� ����� ��ȯ�մϴ�.<br>
	 * �̴� VisualObject3D ���忡�� z�� ������ '����'(Viewport.view_baseDistance ��)�� �� ���� ���� �����̸�<br>
	 * �������� ���ϴ�, �ش� 3���� ��ǥ�� ���� ��¥ 2���� �����ǥ�� ���Ϸ���<br>
	 * Viewport.GetRelativePositionFrom()�� ����ؾ� �մϴ�.
	 * 
	 * @param pos 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� ��ǥ�Դϴ�.<br>
	 * 			<br>
	 * 			<b>����:</b><br>
	 * 			VisualObject3D.GetRelativePosition()�� �� ��ǥ�� z ���� ���� ������� �ʽ��ϴ�.<br>
	 * 			�ڼ��� ������ �޼��� ������ �����ϼ���.
	 */	
	public final Point GetRelativePosition(Point3D pos)
	{
		return GetRelativePosition(pos.x, pos.y, pos.z);
	}
	
	/**
	 * �� ��Ұ� ���� �ִ� 3���� ���� ������ �� ��Ұ� ���� �ִ� ��鿡 ���� �����ϴ� 2���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * VisualObject3D.TransformTo2DPosition()�� argument�� z��ǥ�� ���� ������� ������<br>
	 * �׻� ( pos_x, pos_y, �ڽ��� z��ǥ )�� ���� �����ϴ� 2���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * �̴� VisualObject3D ���忡�� z�� ������ '����'(Viewport.view_baseDistance ��)�� �� ���� ���� �����̸�<br>
	 * �������� ���ϴ�, �ش� 3���� ��ǥ�� ���� �����ϴ� ��¥ 2���� ��ǥ�� ���Ϸ���<br>
	 * Viewport.TransformToInternal2DPositionFromInternal3D()�� ����ؾ� �մϴ�. 
	 * 
	 * @param pos_x 2���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y 2���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z
	 * 			2���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.<br>
	 * 			<br>
	 * 			<b>����:</b><br>
	 * 			VisualObject3D.TransformTo2DPosition()�� �� ���� ���� ������� �ʽ��ϴ�.<br>
	 * 			�ڼ��� ������ �޼��� ������ �����ϼ���.
	 */
	public Point TransformTo2DPosition(double pos_x, double pos_y, double pos_z)
	{
		Point result = GetRelativePosition(pos_x, pos_y, pos_z);
		
		result.x += this.x;
		result.y += this.y;
		
		return result;
	}
	
	/**
	 * �� ��Ұ� ���� �ִ� 3���� ���� ������ �� ��Ұ� ���� �ִ� ��鿡 ���� �����ϴ� 2���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * VisualObject3D.TransformTo2DPosition()�� argument�� z��ǥ�� ���� ������� ������<br>
	 * �׻� ( pos_x, pos_y, �ڽ��� z��ǥ )�� ���� �����ϴ� 2���� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * �̴� VisualObject3D ���忡�� z�� ������ '����'(Viewport.view_baseDistance ��)�� �� ���� ���� �����̸�<br>
	 * �������� ���ϴ�, �ش� 3���� ��ǥ�� ���� �����ϴ� ��¥ 2���� ��ǥ�� ���Ϸ���<br>
	 * Viewport.TransformToInternal2DPositionFromInternal3D()�� ����ؾ� �մϴ�. 
	 * 
	 * @param pos 2���� ��ǥ�� ����� �ϴ�, �� ��Ұ� ���� �ִ� 3���� ��ǥ�� �ȿ����� ��ǥ�Դϴ�.<br>
	 * 			<br>
	 * 			<b>����:</b><br>
	 * 			VisualObject3D.TransformTo2DPosition()�� �� ��ǥ�� z ���� ���� ������� �ʽ��ϴ�.<br>
	 * 			�ڼ��� ������ �޼��� ������ �����ϼ���.
	 */
	public final Point TransformTo2DPosition(Point3D pos)
	{
		return TransformTo2DPosition(pos.x, pos.y, pos.z);
	}
}
