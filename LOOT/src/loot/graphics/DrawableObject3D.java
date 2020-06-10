package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Image�� ����Ͽ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��) �Ǵ� 3���� ���� ��(Viewport ��)�� �׸� �� �ִ� ��� �ϳ��� ��Ÿ���ϴ�.<br>
 * ����: ���� �� Ŭ������ �ν��Ͻ��� 2���� ��� ������ ����Ϸ��� ���<br>
 * �� Ŭ���� ��� DrawableObject class�� ����ϴ� ���� �� ���� ���Դϴ�.
 * 
 * @author Racin
 *
 */
public class DrawableObject3D extends VisualObject3D
{
	/**
	 * �� ��Ҹ� �׸� �� ����� Image�Դϴ�.<br>
	 * GameFrame�� ������ �ִ� images.GetImage()�� ȣ���ϸ� �� �ʵ带 �� �� �����ϰ� �ٷ� �� �ֽ��ϴ�.<br>
	 * ����: Image�� �� ����� ũ��(width�� height)�� ���� �ڵ����� Ȯ�� / ��ҵǾ� �׷����ϴ�. 
	 */
	public Image image;
	
	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D()
	{
		super(true);
	}
	
	/**
	 * �� �����ڴ� argument�� ���� �� ��Ҹ� 2���� ��鿡 �׸��� 3���� ������ �׸��� �����մϴ�.
	 */
	public DrawableObject3D(boolean trigger_coordination)
	{
		super(trigger_coordination);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(Image image)
	{
		super(true);
		this.image = image;
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 2���� ��鿡 �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(int width, int height)
	{
		super(width, height);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 2���� ��鿡 �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(int width, int height, Image image)
	{
		super(width, height);
		this.image = image;
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(double radius_x, double radius_y)
	{
		super(radius_x, radius_y);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(double radius_x, double radius_y, Image image)
	{
		super(radius_x, radius_y);
		this.image = image;
	}
	
	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
	}

	/**
	 * �� �����ڴ� �� ��Ҹ� 3���� ������ �׸� ��ҷ� �����մϴ�.
	 */
	public DrawableObject3D(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, Image image)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		this.image = image;
	}
	
	/**
	 * �� �����ڴ� ���� ��� ������ ���� �� ��Ҹ� 2���� ��鿡 �׸��� 3���� ������ �׸��� �����մϴ�.
	 */
	public DrawableObject3D(DrawableObject3D other)
	{
		super(other);
		image = other.image;
	}

	/**
	 * ���� ������ Image�� ����Ͽ� �� ��Ҹ� ȭ�鿡 �׸��ϴ�.
	 * 
	 * @param g
	 * 		GameFrame���� g ��� �ʵ尡 ��� �ֽ��ϴ�.<br>
	 * 		�������� Draw(g)�� ���� ȣ���� ��(���� ȭ�鿡 ���� �׸� ��)�� �� ������� ���� �� �ʵ带 �׳� ������ �˴ϴ�.
	 */
	@Override
	public void Draw(Graphics2D g)
	{
		g.drawImage(image, x, y, width, height, null);
	}
}
