
import loot.AudioManager;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;

public class MainFrame extends GameFrame {

	DrawableObject player_fish;
	int move_x;
	int move_y;
	
	public MainFrame(GameFrameSettings settings) {
		super(settings);
		
		// �׸����� ���� �� ���� Ŭ������ �ν��Ͻ� �̸� ������� ��.
		// �ϴ� ������ �о���� ��.
		images.LoadImage("Images/����.png", "img_fish");
		audios.LoadAudio("Audios/01_������ �� �⺻ �÷��� ���.wav", "main_bgm", 1);
	}

	@Override
	public boolean Initialize() {		
		
		// �ҷ��� �̹����b �ʵ忡 �Ҵ����ֱ�
		player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		
		move_x = -5;
		move_y = -10;
		
		audios.Loop("main_bgm", -1);
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
		// �Ѱ� x, y ���� �밡�ٷ� �˾Ƴ�.
		if(player_fish.x <= 0 || player_fish.x >= 550)
			move_x = -move_x;
		if(player_fish.y <= -100 || player_fish.y >= 400)
			move_y = -move_y;
		
		player_fish.x = player_fish.x + move_x;
		player_fish.y = player_fish.y + move_y;
		
		
		
		// ������ ��ȯ���� false, true�� �����ϴ� ���� �߿��ѵ� 
		// �츮�� ������ �����̴� �ϴ� true�� �ص�
		return true;
	}

	@Override
	public void Draw(long timeStamp) {
		// ����
		BeginDraw();
		ClearScreen();
		// ��� ���� ���� ����. �׳� �׻� �̷��� ���´ٰ� ������.
		player_fish.Draw(g);
		
		// ��
		EndDraw();
	}

}
