package loot;

import java.awt.Color;

/**
 * GameFrame class �Ǵ� �� ���� Ŭ�����鿡 ���� ���� ������ �����ϱ� ���� ����ϴ� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class GameFrameSettings
{
	/**
	 * �ʱ⿡ ������ â �����Դϴ�.<br>
	 * setTitle()�� ���� �������� �ʴ� ��� â ������ �� �ʵ��� ������ ��� �����˴ϴ�.<br>
	 * �⺻���� "������!" �Դϴ�.
	 */
	public String window_title = "������!";
	
	/**
	 * ���� ȭ���� ���� ����(�ȼ� ��)�Դϴ�.<br>
	 * �⺻���� 800�Դϴ�. 
	 */
	public int canvas_width = 800;
	
	/**
	 * ���� ȭ���� ���� ����(�ȼ� ��)�Դϴ�.<br>
	 * �⺻���� 600�Դϴ�.
	 */
	public int canvas_height = 600;

	/**
	 * ���� ȭ���� �����Դϴ�.<br>
	 * �⺻���� <code>Color.WHITE</code>�Դϴ�.
	 */
	public Color canvas_backgroundColor = Color.WHITE;

	/**
	 * ������ �����ϴ� �� ������ ������ ������ ������ ������ �����մϴ�.<br>
	 * �⺻���� 16666666ns�� ��� FPS(�ʴ� ������ ��)�� ȯ���ϸ� �� 60�� �˴ϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �� ���� ���������� ������ ������ ���� ȯ�濡 ���� �޶��� ���� �ֽ��ϴ�.<br>
	 * Ư�� ���� ������ ���� Ÿ�̹� ���� ���۽�ų ����<br>
	 * �������� ������ ����� ���� �������� �� ���� ���÷� �����Ͽ�<br>
	 * ���α׷��� ���ϴ� �ӵ��� ����ǵ��� ������ �ʿ䰡 �ֽ��ϴ�.
	 */
	public long gameLoop_interval_ns = 16666666;
	
	/**
	 * ���� ������ ���� Ÿ�̹� ���� ���۽�ų�� ���θ� �����մϴ�.<br>
	 * �� ���� true �� ��� �� �������� ���� �ð��� ���� ���� �׻� interval_ms��ŭ �����Ǹ� timeStamp ���� �׻� interval_ms��ŭ �����մϴ�.<br>
	 * �� ���� false �� ��� �� �������� �ּ� interval_ms��ŭ �������� �����ϸ� timeStamp�� ���� �ð��� ����Ͽ� �����մϴ�.<br>
	 * �⺻���� true�Դϴ�.
	 */
	public boolean gameLoop_use_virtualTimingMode = true;
	
	/**
	 * ���� ������ �� �� ���� ���� ��ư�� bind�Ͽ� ����� ������ �����մϴ�.<br>
	 * �⺻���� 8�Դϴ�.
	 */
	public int numberOfButtons = 8;
	
	public GameFrameSettings()
	{
	}
	
	public GameFrameSettings(GameFrameSettings other)
	{
		window_title = other.window_title;
		canvas_width = other.canvas_width;
		canvas_height = other.canvas_height;
		canvas_backgroundColor = other.canvas_backgroundColor;
		gameLoop_interval_ns = other.gameLoop_interval_ns;
		gameLoop_use_virtualTimingMode = other.gameLoop_use_virtualTimingMode;
		numberOfButtons = other.numberOfButtons;
	}
}
