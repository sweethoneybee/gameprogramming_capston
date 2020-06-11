
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import loot.AudioManager;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;

public class MainFrame extends GameFrame {
	static final double coef_friction_x = -0.001;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
	static final double coef_friction_y = -0.005;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)

	//DrawableObject player_fish;
	public class Player extends DrawableObject
	{
		public double p_x;
		public double p_y;
		public double v_x;
		public double v_y;
		public double a_x;
		public double a_y;
		
		public Player(int x, int y)
		{
			super(x, y,	-100, 100, images.GetImage("img_fish"));
			
			p_x = x;
			p_y = y;
		}
	}
	
	Player player_fish;										// 플레이어 물고기
	
	long timeStamp_lastFrame = 0;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
	
	int move_x;
	int move_y;
	
	
	
	
	public MainFrame(GameFrameSettings settings) {
		super(settings);
		
		// 그림파일 읽을 때 쓰는 클래스랑 인스턴스 미리 만들어진 것.
		// 일단 파일을 읽어오는 것.
		
		inputs.BindKey(KeyEvent.VK_LEFT, 0);
		inputs.BindKey(KeyEvent.VK_RIGHT, 1);
		inputs.BindKey(KeyEvent.VK_UP, 2);
		inputs.BindKey(KeyEvent.VK_DOWN, 3);
		
		
		images.LoadImage("Images/고등어.png", "img_fish");
		audios.LoadAudio("Audios/01_오프닝 겸 기본 플레이 브금.wav", "main_bgm", 1);
		
	}

	@Override
	public boolean Initialize() {		
		
		// 불러온 이미지륿 필드에 할당해주기
		//player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		player_fish = new Player(100, 300);
		move_x = -5;
		move_y = -10;
		
		audios.Loop("main_bgm", -1);
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		inputs.AcceptInputs();
		
		/*
		if (inputs.buttons[0].IsPressedNow()) {
			player_fish.width = 100;
			player_fish.height = 100;
		}
		if (inputs.buttons[1].IsPressedNow()) {
			player_fish.width = 0;
			player_fish.height = 0;
		}
		 */
		
		/*
		// 한계 x, y 값은 노가다로 알아냄.
		if(player_fish.x <= 0 || player_fish.x >= 550)
			move_x = -move_x;
		if(player_fish.y <= -100 || player_fish.y >= 400)
			move_y = -move_y;
		
		player_fish.x = player_fish.x + move_x;
		player_fish.y = player_fish.y + move_y;
		*/
		
		
		// 각 버튼의 상태를 검사하여 물고기에 어떤 작업을 수행해야 하는지 체크
		boolean isLeftForceRequested;
		boolean isRightForceRequested;
		boolean isUpForceRequested;
		boolean isDownForceRequested;
		
		//지난 프레임 이후로 경과된 시간 측정
		double interval = timeStamp - timeStamp_lastFrame;
		
		// 이번에 왼쪽버튼 눌렀으면 왼쪽으로 가게 할 거임.
		isLeftForceRequested = inputs.buttons[0].IsPressedNow();
		isRightForceRequested = inputs.buttons[1].IsPressedNow();
		isUpForceRequested = inputs.buttons[2].IsPressedNow();
		isDownForceRequested = inputs.buttons[3].IsPressedNow();
		
		//player_fish.a_x = 0;
		//player_fish.a_y = 0;
//		if(isLeftForceRequested == true) {
//			player_fish.v_x = -0.1;
//			player_fish.v_y = -0.3;
//		}
//		if(isRightForceRequested == true) {
//
//			player_fish.v_x = 0.1;
//			player_fish.v_y = -0.3;
//		}
		if(isDownForceRequested == true) {
			player_fish.v_x = 0.1;
			player_fish.v_y = 0.3;
		}
		if(isUpForceRequested == true) {
			player_fish.v_x = 0.1;
			player_fish.v_y = -0.3;
		}

		
		// 왼쪽 키가 안눌려 있다면 속도 / 가속도 반영
		if(isLeftForceRequested == false && isRightForceRequested == false 
				&& isDownForceRequested == false && isUpForceRequested == false) {
			// 마찰력 계산
			player_fish.a_x = coef_friction_x * interval * player_fish.v_x;
			player_fish.a_y = coef_friction_y * interval * player_fish.v_y;
			
			// 가속도를 속도에 적용 - 가속도의 경우 미리 시간을 곱했으므로 여기서 더 곱하지는 않음
			player_fish.v_x += player_fish.a_x;
			player_fish.v_y += player_fish.a_y;
			
			// 속도 제한
			player_fish.v_x %= 100;
			player_fish.v_y %= 100;
			
			// 속도를 위치에 적용 - 이 때는 시간을 곱하여 적용
			player_fish.p_x += player_fish.v_x * interval;
			player_fish.p_y += player_fish.v_y * interval;
			
			
			// 마지막으로, 물고기의 위치를 기반으로 해당 물고기를 그릴 픽셀값 설정
			player_fish.x = (int)player_fish.p_x;
			player_fish.y = (int)player_fish.p_y;
		}
		
		
		// 이제 '직전 프레임'이 될 이번 프레임의 시작 시간 기록
		timeStamp_lastFrame = timeStamp;
		
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
