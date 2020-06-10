package loot;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * ���α׷����� ����� Image���� �о���� �����ϴ� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class ImageResourceManager
{
	HashMap<String, Image> images;

	public ImageResourceManager()
	{
		images = new HashMap<String, Image>();
	}
	
	/**
	 * �־��� ���� ����Ͽ� �ӽ÷� ����� �ܻ� Image�� ����� ����մϴ�.
	 * 
	 * @param color
	 * 			  �ӽ� Image�� ���� �����Դϴ�. 
	 * @param imageName
	 * 			  ���α׷� ������ ����ϱ� ���� �ο��ϴ� �� Image�� �̸��Դϴ�.
	 * @return ���������� ����� ��� true�� return�մϴ�.
	 */
	public boolean CreateTempImage(Color color, String imageName)
	{
		if ( images.containsKey(imageName) == true )
			return false;
		
		BufferedImage newImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		newImage.setRGB(0, 0, color.getRGB());
		
		images.put(imageName, newImage);
		
		return true;
	}

	/**
	 * �ش� ���Ͽ��� Image�� �о�� �־��� �̸����� ����մϴ�.
	 * 
	 * @param fileName
	 *            Image�� �о�� ���� �̸��Դϴ�.
	 * @param imageName
	 *            ���α׷� ������ ����ϱ� ���� �ο��ϴ� �� Image�� �̸��Դϴ�.
	 * @return ���������� ����� ��� true�� return�մϴ�.
	 */
	public boolean LoadImage(String fileName, String imageName)
	{
		try
		{
			//�̹� �ش� �̸��� Image�� ��ϵǾ� �ִ� ��� ����
			if ( images.containsKey(imageName) == true )
				return false;

			File f = new File(fileName);

			//�ش� �̸��� ������ ���� ��� ����
			if ( f.exists() == false )
				return false;

			Image newImage = ImageIO.read(f);

			images.put(imageName, newImage);
		}
		catch (Exception e)
		{
			//Image�� �о���� �� �� ��� ����
			return false;
		}

		return true;
	}

	/**
	 * �ش� �̸����� ��ϵ� Image�� �����ɴϴ�.
	 * 
	 * @param imageName
	 *            ����� �� �ο��� Image �̸��Դϴ�.
	 * @return �ش� �̸��� Image�� �ִ� ��� �� Image�� return�մϴ�. �׷��� ���� ��� null�� return�մϴ�.
	 */
	public Image GetImage(String imageName)
	{
		return images.get(imageName);
	}

	/**
	 * �ش� �̸����� ��ϵ� Image�� �����մϴ�.
	 * 
	 * @param imageName
	 *            ����� �� �ο��� Image �̸��Դϴ�.
	 */
	public void DisposeImage(String imageName)
	{
		images.remove(imageName);
	}
}
