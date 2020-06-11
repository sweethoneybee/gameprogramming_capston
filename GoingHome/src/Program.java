import java.awt.Color;

import loot.GameFrameSettings;

public class Program {

	public static void main(String[] args) {
		
		// �⺻���� ���õ� ������ ��. �� 800 x 600
		GameFrameSettings settings = new GameFrameSettings();
		
		// 90fps
		settings.gameLoop_interval_ns = 11111111;
		
		// backgroundColor = �þ�-�Ķ��� �ణ ���� ����
		settings.canvas_backgroundColor = new Color(0x40a0ff);
		
		settings.canvas_width = 1024;
		settings.canvas_height = 768;
		
		// mainFrame �ʱ�ȯ
		MainFrame mainFrame = new MainFrame(settings);
		
		// ��� �ڹٿ� �⺻������ �����Ǵ� �޼ҵ���� �ܶ� ����. �ٵ� �ϴ� ��������� �ϴ� ��.
		mainFrame.setVisible(true);
		
		
		
	}

}
