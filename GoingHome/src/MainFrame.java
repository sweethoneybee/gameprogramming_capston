
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
		
		// 그림파일 읽을 때 쓰는 클래스랑 인스턴스 미리 만들어진 것.
		// 일단 파일을 읽어오는 것.
		images.LoadImage("Images/고등어.png", "img_fish");
		audios.LoadAudio("Audios/01_오프닝 겸 기본 플레이 브금.wav", "main_bgm", 1);
	}

	@Override
	public boolean Initialize() {		
		
		// 불러온 이미지륿 필드에 할당해주기
		player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		
		move_x = -5;
		move_y = -10;
		
		audios.Loop("main_bgm", -1);
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
		// 한계 x, y 값은 노가다로 알아냄.
		if(player_fish.x <= 0 || player_fish.x >= 550)
			move_x = -move_x;
		if(player_fish.y <= -100 || player_fish.y >= 400)
			move_y = -move_y;
		
		player_fish.x = player_fish.x + move_x;
		player_fish.y = player_fish.y + move_y;
		
		
		
		// 원래는 반환값을 false, true를 구분하는 것이 중요한데 
		// 우리는 간단한 게임이니 일단 true로 해둠
		return true;
	}

	@Override
	public void Draw(long timeStamp) {
		// 시작
		BeginDraw();
		ClearScreen();
		// 얘는 지금 이해 못해. 그냥 항상 이렇게 적는다고 생각해.
		player_fish.Draw(g);
		
		// 끝
		EndDraw();
	}

}
