package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Image�� ����Ͽ� 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)�� �׸� �� �ִ� ��� �ϳ��� ��Ÿ���ϴ�.
 * 
 * @author Racin
 *
 */
public class DrawableObject extends VisualObject
{
	/**
	 * �� ��Ҹ� �׸� �� ����� Image�Դϴ�.<br>
	 * GameFrame�� ������ �ִ� images.GetImage()�� ȣ���ϸ� �� �ʵ带 �� �� �����ϰ� �ٷ� �� �ֽ��ϴ�.<br>
	 * ����: Image�� �� ����� ũ��(width�� height)�� ���� �ڵ����� Ȯ�� / ��ҵǾ� �׷����ϴ�. 
	 */
	public Image image;
	
	public DrawableObject()
	{
	}
	
	public DrawableObject(int width, int height)
	{
		super(width, height);
	}

	public DrawableObject(int width, int height, Image image)
	{
		super(width, height);
		this.image = image;
	}
	
	public DrawableObject(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public DrawableObject(int x, int y, int width, int height, Image image)
	{
		super(x, y, width, height);
		this.image = image;
	}
	
	public DrawableObject(DrawableObject other)
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
