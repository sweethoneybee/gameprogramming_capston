package loot.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.StringTokenizer;

/**
 * 2���� ��� ��(���� ȭ�� ��ü, �Ǵ� Layer ��)�� ���ڿ��� ���� ���� ����ϴ� Ŭ�����Դϴ�.<br>
 * <br>
 * <b>����:</b><br>
 * �� Ŭ������ ����� �� �׽�Ʈ �뵵�� ����ϱ� ���� �����������<br>
 * ���� ������ ���� �� Ŭ������ ���� ���� ���α׷����� ����ϴ� ���� �ٶ������� �ʽ��ϴ�.<br>
 * ���α׷� ������ ���ڿ��� ���� �� �ʿ䰡 �ִ� ��쿡��<br>
 * �ش� ���ڿ��� �̸� �׸� ���� �׸� ���Ϸ� ����� ����ϴ� ���� ���ڽ��ϴ�.
 *  
 * @author Racin
 *
 */
public class TextBox extends VisualObject
{
	/**
	 * ���� ���� ������ �ϴ� ���ڿ��Դϴ�.
	 */
	public String text;
	
	/**
	 * ���ڿ��� ���� �� ����� Font�Դϴ�.<br>
	 * �� �ʵ带 null�� �δ� ��� GameFrame���� ������ �� �⺻ ����ü�� ����մϴ�.<br>
	 * �⺻���� null�Դϴ�.
	 */
	public Font font = null;
	
	/**
	 * �� �� ������ ����(�ȼ� ��)�Դϴ�.<br>
	 * �⺻���� 2�Դϴ�.
	 */
	public int margin_between_lines = 2;
	
	/**
	 * ���ڿ��� ���� �� ����� ���� ����(�ȼ� ��)�Դϴ�.<br>
	 * �⺻���� 8�Դϴ�.
	 */
	public int margin_left = 8;
	
	/**
	 * ���ڿ��� ���� �� ����� �� ����(�ȼ� ��)�Դϴ�.<br>
	 * �⺻���� 8�Դϴ�.
	 */
	public int margin_top = 8;
	
	/**
	 * ���ڿ��� ���� �� ����� �����(���� ��ü�� ��)�Դϴ�.<br>
	 * �⺻���� Color.BLACK(���� ��)�Դϴ�.
	 */
	public Color foreground_color = Color.BLACK;

	/**
	 * ���ڿ��� ���� �� ����� �����Դϴ�.<br>
	 * �⺻���� Color.WHITE(�� ��)�Դϴ�.
	 */
	public Color background_color = Color.WHITE;

	public TextBox()
	{
		text = "";
	}
	
	public TextBox(String text)
	{
		this.text = text;
	}

	public TextBox(int width, int height)
	{
		super(width, height);
		text = "";
	}

	public TextBox(int width, int height, String text)
	{
		super(width, height);
		this.text = text;
	}

	public TextBox(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		text = "";
	}

	public TextBox(int x, int y, int width, int height, String text)
	{
		super(x, y, width, height);
		this.text = text;
	}

	/**
	 * text �ʵ忡 ������ ���ڿ��� �����ϴ�.
	 * 
	 * @param g
	 * 		GameFrame���� g ��� �ʵ尡 ��� �ֽ��ϴ�.<br>
	 * 		�������� Draw(g)�� ���� ȣ���� ��(���� ȭ�鿡 ���� �׸� ��)�� �� ������� ���� �� �ʵ带 �׳� ������ �˴ϴ�.
	 */
	@Override
	public void Draw(Graphics2D g)
	{
		//g�� �����Ǿ� �ִ� ���� �� Font ���� ���
		Color original_color = g.getColor();
		Font original_font = g.getFont();
		
		//���� ũ�� ���� - font �ʵ忡 ������ ��, �Ǵ� g�� ���� �⺻��
		int font_size;
		
		if ( font == null )
			font_size = original_font.getSize();
		else
			font_size = font.getSize();
		
		//��� ĥ�ϱ�
		g.setColor(background_color);
		g.fillRect(x, y, width, height);
		
		//���ڿ� ����
		g.setColor(foreground_color);
		g.setFont(font);
		StringTokenizer lines = new StringTokenizer(text, "\n");
		
		int x_line = x + margin_left;
		int y_line = y + margin_top + font_size;
		
		while ( lines.hasMoreTokens() == true )
		{
			g.drawString(lines.nextToken(), x_line, y_line);
			
			y_line += font_size;
			y_line += margin_between_lines;
		}

		//����� �� ���� �� Font ���� ����
		g.setFont(original_font);
		g.setColor(original_color);
	}
}
