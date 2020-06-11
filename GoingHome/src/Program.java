import java.awt.Color;

import loot.GameFrameSettings;

public class Program {

	public static void main(String[] args) {
		
		// 기본으로 세팅된 값들이 들어감. 예 800 x 600
		GameFrameSettings settings = new GameFrameSettings();
		
		// 90fps
		settings.gameLoop_interval_ns = 11111111;
		
		// backgroundColor = 시안-파랑의 약간 밝은 색조
		settings.canvas_backgroundColor = new Color(0x40a0ff);
		
		settings.canvas_width = 1024;
		settings.canvas_height = 768;
		
		// mainFrame 초기환
		MainFrame mainFrame = new MainFrame(settings);
		
		// 얘는 자바에 기본적으로 제공되는 메소드들이 잔뜩 있음. 근데 일단 여기까지만 일단 됨.
		mainFrame.setVisible(true);
		
		
		
	}

}
