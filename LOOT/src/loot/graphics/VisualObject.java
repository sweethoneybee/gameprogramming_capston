package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)�� ��ġ�Ͽ� ������ ���� �� �� �ִ� ��� �ϳ��� �߻�ȭ�մϴ�.
 * 
 * @author Racin
 *
 */
public abstract class VisualObject
{
	/**
	 * �� ��Ұ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)�� ��ġ�� x��ǥ(width�� ����� ��� ���� ���� �ȼ��� ��ǥ)�Դϴ�.<br>
	 * 2���� ��鿡�� x���� ���ʿ��� ���������� �����մϴ�.<br>
	 * �� ��Ұ� Layer �Ǵ� Viewport�� ���ԵǾ� �ִ� ��� �� ���� �׸��� �������� ����� �� �ֽ��ϴ�.
	 */
	public int x;
	
	/**
	 * �� ��Ұ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)�� ��ġ�� y��ǥ(height�� ����� ��� ���� �� �ȼ��� ��ǥ)�Դϴ�.<br>
	 * 2���� ��鿡�� y���� <b>������ �Ʒ���</b> �����մϴ�.<br>
	 * �� ��Ұ� Layer �Ǵ� Viewport�� ���ԵǾ� �ִ� ��� �� ���� �׸��� �������� ����� �� �ֽ��ϴ�.
	 */
	public int y;
	
	/**
	 * �� ��Ұ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)���� ������ ���� ����(�ȼ� ��)�Դϴ�.<br>
	 * �� ���� ������ ��Ҵ� ��/�� �����Ǿ� ���̰� �˴ϴ�.<br>
	 * �� ��Ұ� Layer �Ǵ� Viewport�� ���ԵǾ� �ִ� ��� �� ���� �׸��� �������� ����� �� �ֽ��ϴ�.
	 */
	public int width;
	
	/**
	 * �� ��Ұ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)���� ������ ���� ����(�ȼ� ��)�Դϴ�.<br>
	 * �� ���� ������ ��Ҵ� ��/�� �����Ǿ� ���̰� �˴ϴ�.<br>
	 * �� ��Ұ� Layer �Ǵ� Viewport�� ���ԵǾ� �ִ� ��� �� ���� �׸��� �������� ����� �� �ֽ��ϴ�.
	 */
	public int height;
	
	/**
	 * �� �ʵ带 true�� ������ �θ� Layer�� �� ��Ҹ� ���� ȭ�鿡 �׸��� �ʽ��ϴ�.<br>
	 * ���� �� ��Ұ� � Layer���� ���ԵǾ� ���� �ʴ� ��� �� �ʵ�� �ƹ� ���⵵ ���� �ʽ��ϴ�.
	 */
	public boolean trigger_hide;
	
	/**
	 * �� �ʵ带 true�� ������ �θ� Layer�� �� ��Ҹ� ���� �׽�Ʈ �������� �����մϴ�.<br>
	 * ���� �� ��Ұ� � Layer���� ���ԵǾ� ���� �ʴ� ��� �� �ʵ�� �ƹ� ���⵵ ���� �ʽ��ϴ�.
	 */
	public boolean trigger_ignoreDuringHitTest;
	
	/**
	 * �� �ʵ带 true�� ������ �θ� Layer�� �� ��Ҹ� ���� ��Ͽ��� �����մϴ�.<br>
	 * ���� �� ��Ұ� � Layer���� ���ԵǾ� ���� �ʴ� ��� �� �ʵ�� �ƹ� ���⵵ ���� �ʽ��ϴ�.
	 */
	public boolean trigger_remove;
	
	public VisualObject()
	{
	}

	public VisualObject(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public VisualObject(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public VisualObject(VisualObject other)
	{
		x = other.x;
		y = other.y;
		width = other.width;
		height = other.height;
		trigger_hide = other.trigger_hide;
	}
	
	/**
	 * ��Ҹ� ȭ�鿡 �׸��ϴ�.<br>
	 * �� �޼���� �� Ŭ�����鸶�� �ٸ��� �����Ǿ� ������<br>
	 * ��¶�� ��Ҹ� ȭ�鿡 �׸��ϴ�.
	 * 
	 * @param g
	 * 		GameFrame���� g ��� �ʵ尡 ��� �ֽ��ϴ�.<br>
	 * 		�������� Draw(g)�� ���� ȣ���� ��(���� ȭ�鿡 ���� �׸� ��)�� �� ������� ���� �� �ʵ带 �׳� ������ �˴ϴ�.
	 */
	public abstract void Draw(Graphics2D g);

	
	/* ---------------------------------------------------
	 * 
	 * ���� �׽�Ʈ�� ���� �޼����
	 * 
	 */
	
	/**
	 * �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.
	 * 
	 * @param x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public boolean HitTest(int x, int y)
	{
		return	this.x <= x && this.x + width >= x &&
				this.y <= y && this.y + height >= y;
	}
	
	/**
	 * �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.
	 * 
	 * @param pos ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final boolean HitTest(Point pos)
	{
		return HitTest(pos.x, pos.y);
	}

	/**
	 * �� �޼���� VisualObject.HitTest()�� ��Ȯ�� ������ �۾��� �����մϴ�.<br>
	 * ������ Ŭ���� ������ ���� �ܰ迡 ���� �����Ǿ��� �� � ���� Ŭ������ ���� �ܼ��� ���� ��� ���� �׽�Ʈ(���⼭ �ϴ� �۾�)�� �� �� �ֵ���<br>
	 * ���������� �̷��� '�ٸ� �̸���' �޼��带 ����� ����մϴ�.<br>
	 * ��¶�� �������� �� �޼��带 ����� �� �����ϴ�.
	 * 
	 * @param x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	protected final boolean HitTest_Base(int x, int y)
	{
		return	this.x <= x && this.x + width >= x &&
				this.y <= y && this.y + height >= y;
	}

	
	/* ---------------------------------------------------
	 * 
	 * �����ǥ ��� �� ��ǥ ��ȯ�� ���� �޼����
	 * 
	 */
	
	/**
	 * �ش� ��ǥ�� �� ��Ҹ� �������� �ϴ� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param x �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point GetRelativePosition(int x, int y)
	{
		return new Point(x - this.x, y - this.y);
	}
	
	/**
	 * �ش� ��ǥ�� �� ��Ҹ� �������� �ϴ� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param pos �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final Point GetRelativePosition(Point pos)
	{
		return GetRelativePosition(pos.x, pos.y);
	}
	
	/**
	 * �� ����� �߽���(�ʺ��� ����, ������ ���� ��ġ�� �ִ� ��)�� �������� �ϴ� �ش� ��ǥ�� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param x �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public final Point GetRelativePositionFromCenter(int x, int y)
	{
		return GetRelativePosition(x - width / 2, y - height / 2);
	}
	
	/**
	 * �� ����� �߽���(�ʺ��� ����, ������ ���� ��ġ�� �ִ� ��)�� �������� �ϴ� �ش� ��ǥ�� �����ǥ�� ��ȯ�մϴ�.
	 * 
	 * @param pos �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final Point GetRelativePositionFromCenter(Point pos)
	{
		return GetRelativePosition(pos.x - width / 2, pos.y - height / 2);
	}
}