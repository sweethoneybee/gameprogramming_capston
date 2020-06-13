
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import loot.AudioManager;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;
import loot.graphics.VisualObject;

public class MainFrame extends GameFrame {

	static final int numberOfEats = 5;
	static final int eats_width = 100;
	static final int eats_height = 100;
	static final int player_width = 80; // 가로 : 세로 = 2 : 1 유지
	static final int player_height = 40;

	// 물고리 꼬리치는 최저 속도용 상수
	static final double img_change_speed_min = 0.08;
	// 속도용 상수 - 일정 시간마다 한 번씩 입력 받는 용
//	static final double speed_limit = 0.4;					// 최대 속도
//	static final double x_axis = 0.2;						// 플레이어 x축 이동 속도
//	static final double y_axis = 0.2;						// 플레이어 y축 이동 속도
//	static final double coef_friction_x = -0.0015;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
//	static final double coef_friction_y = -0.0015;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)

	// 속도용 상수 - 꾹 누르는 입력 받는 용
	static final double speed_limit = 0.6;					// 최대 속도
	static final double x_axis = 0.006;						// 플레이어 x축 이동 속도
	static final double y_axis = 0.006;						// 플레이어 y축 이동 속도
	static final double coef_friction_x = -0.001;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
	static final double coef_friction_y = -0.001;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
	
	// 키입력 횟수 제한 용
	static boolean canInput = true;
	
	//DrawableObject player_fish;
	public class Player extends DrawableObject
	{
		public double p_x;
		public double p_y;
		public double v_x;
		public double v_y;
		public double a_x;
		public double a_y;
		
		
		// 헤엄치는 이미지
		public Image image_idle;
		public Image image_swimUp;
		public Image image_swimDown;
		public int image_change;
		
		public Player(int x, int y)
		{
			super(x, y,	player_width, player_height, images.GetImage("img_fish"));
			
			p_x = x;
			p_y = y;
			
			image_idle = images.GetImage("img_fish");
			image_swimUp = images.GetImage("img_swimUp");
			image_swimDown = images.GetImage("img_swimDown");
			image_change = 1;
		}
	}
	
	public class Eats extends DrawableObject{
		public double p_x;
		public double p_y;
		
		public Eats(int x, int y) {
			super(x, y, eats_width, eats_height, images.GetImage("img_shrimp"));
			
			p_x = x;
			p_y = y;
		}
	}
	Player player_fish;										// 플레이어 물고기
	ArrayList<Eats> eats = new ArrayList<Eats>();			// 먹이
	long timeStamp_lastFrame = 0;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
	long timeStamp_collision = 0;							// collision을 위한 timeStamp
	long timeStamp_input = 0;								// 시간 당 input 횟수 제한을 위한 timeStamp
	long timeStamp_imgChange = 0;							// 물고기 꼬리 이미지 변경용
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
		images.LoadImage("Images/고등어_위로.png", "img_swimUp");
		images.LoadImage("Images/고등어_아래로.png", "img_swimDown");
		images.LoadImage("Images/새우.png", "img_shrimp");
		
		audios.LoadAudio("Audios/01_오프닝 겸 기본 플레이 브금.wav", "main_bgm", 1);
		
	}

	@Override
	public boolean Initialize() {		
		Random rand = new Random();
		
		// 불러온 이미지륿 필드에 할당해주기
		//player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		player_fish = new Player(105, 100);
		move_x = -5;
		move_y = -10;
		
		
		// 먹을 것 랜덤 배치
		for(int iEats = 0; iEats < numberOfEats; iEats++)
			eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 10) + 100, rand.nextInt(settings.canvas_height - eats_height - 10) + 1));
			
		audios.Loop("main_bgm", -1);
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
		inputs.AcceptInputs();
		
		// 지난 프레임 이후로 경과된 시간 측정
		double interval = timeStamp - timeStamp_lastFrame;
		double interval_input = timeStamp - timeStamp_input;
		double interval_swim = timeStamp - timeStamp_imgChange;
		
		double o_p_x, o_p_y;
		int o_x, o_y;
		o_p_x = player_fish.p_x;
		o_p_y = player_fish.p_y;
		o_x = player_fish.x;
		o_y = player_fish.y;
		
		// 각 버튼의 상태를 검사하여 물고기에 어떤 작업을 수행해야 하는지 체크
		boolean isLeftForceRequested = false;
		boolean isRightForceRequested = false;
		boolean isUpForceRequested = false;
		boolean isDownForceRequested = false;
		
	
		
		
		/*
		 * 
		 *  TODO 
		 *  키 입력을 어떻게 받을지에 따라 두 가지 방식으로 나뉜다.
		 *  UX가 다르니깐 의견 받아볼 것. 
		 *  1. 일정 시간마다 한 번씩 입력 받는 용
		 *  2. 꾹 누르는 입력 받는 용
		 *  
		 */
        // 1. 일정 시간마다 한 번씩 입력 받는 용
//		if(canInput == true) {
//			// 버튼 받기
//			isLeftForceRequested = inputs.buttons[0].IsPressedNow();
//			isRightForceRequested = inputs.buttons[1].IsPressedNow();
//			isUpForceRequested = inputs.buttons[2].IsPressedNow();
//			isDownForceRequested = inputs.buttons[3].IsPressedNow();			
//			
//			if(isLeftForceRequested == true || isRightForceRequested == true || isUpForceRequested == true || isDownForceRequested == true)
//				canInput = false;
//		}
//		if(interval_input >= 200) {
//			canInput = true;
//			timeStamp_input = timeStamp;
//		}

		
		// 2. 꾹 누르는 입력 받는 용
		if(inputs.buttons[0].IsPressedNow() == true || (inputs.buttons[0].isPressed == true && inputs.buttons[0].isChanged == false)) {
			isLeftForceRequested = true;
		}
		if(inputs.buttons[1].IsPressedNow() == true || (inputs.buttons[1].isPressed == true && inputs.buttons[1].isChanged == false)) {
			isRightForceRequested = true;
		}
		if(inputs.buttons[2].IsPressedNow() == true || (inputs.buttons[2].isPressed == true && inputs.buttons[2].isChanged == false)) {
			isUpForceRequested = true;
		}
		if(inputs.buttons[3].IsPressedNow() == true || (inputs.buttons[3].isPressed == true && inputs.buttons[3].isChanged == false)) {
			isDownForceRequested = true;
		}
		
		
		
		// 충돌테스트
		if(timeStamp - timeStamp_collision >= 10) {
			for(Eats eat : eats) {
				if(player_fish.CollisionTest(eat) == true) {
					eats.remove(eat);
					timeStamp_collision = timeStamp;
					break;
				}
			}
			timeStamp_collision = timeStamp;
		}
		
		
		/*
		 * 테스트용 돌아다니는 버전
		 */
		if(isLeftForceRequested == true) {
			player_fish.v_x += -x_axis;
		}
		if(isRightForceRequested == true) {
			player_fish.v_x += x_axis;
		}
		if(isDownForceRequested == true) {
			player_fish.v_y += y_axis;
		}
		if(isUpForceRequested == true) {
			player_fish.v_y += -y_axis;
		}	
		
		/*
		 *  기존 구현
		 */
//		if(isDownForceRequested == true) {
//			player_fish.v_x = 0.1;
//			player_fish.v_y = 0.3;
//		}
//		if(isUpForceRequested == true) {
//			player_fish.v_x = 0.1;
//			player_fish.v_y = -0.3;
//		}


		// 속도 제한
		if(Math.abs(player_fish.v_x) >= speed_limit) {
			double over = Math.abs(player_fish.v_x) - speed_limit;
			if(player_fish.v_x >= 0)
				player_fish.v_x -= over;
			else
				player_fish.v_x += over;
		}
		if(Math.abs(player_fish.v_y) >= speed_limit) {
			double over = Math.abs(player_fish.v_y) - speed_limit;
			if(player_fish.v_y >= 0)
				player_fish.v_y -= over;
			else
				player_fish.v_y += over;
		}
		
		player_fish.v_x %= 1;
		player_fish.v_y %= 1;
		
		// 마찰력 계산
		player_fish.a_x = coef_friction_x * interval * player_fish.v_x;
		player_fish.a_y = coef_friction_y * interval * player_fish.v_y;
		
		// 가속도를 속도에 적용 - 가속도의 경우 미리 시간을 곱했으므로 여기서 더 곱하지는 않음
		player_fish.v_x += player_fish.a_x;
		player_fish.v_y += player_fish.a_y;

		
		
		// 속도를 위치에 적용 - 이 때는 시간을 곱하여 적용
		player_fish.p_x += player_fish.v_x * interval;
		player_fish.p_y += player_fish.v_y * interval;
		
		
		// 마지막으로, 물고기의 위치를 기반으로 해당 물고기를 그릴 픽셀값 설정
		player_fish.x = (int)player_fish.p_x;
		player_fish.y = (int)player_fish.p_y;

		// canvas 밖으로 플레이어가 못 나가게 막음
		if(player_fish.x <= 0 || player_fish.x >= settings.canvas_width - player_width) {
			player_fish.x = o_x;
			player_fish.p_x = o_p_x;
		}
		if(player_fish.y <= 0 || player_fish.y >= settings.canvas_height - player_height) {
			player_fish.y = o_y;
			player_fish.p_y = o_p_y;
		}
			

		
		// 이제 '직전 프레임'이 될 이번 프레임의 시작 시간 기록
		timeStamp_lastFrame = timeStamp;
		
		
		// 물고기 꼬리 움직이는 용
		if(Math.abs(player_fish.v_x) >= img_change_speed_min || Math.abs(player_fish.v_y) >= img_change_speed_min) {
			if(interval_swim >= 500) {
				if(player_fish.image_change == 1)
					player_fish.image = player_fish.image_swimUp;
				else
					player_fish.image = player_fish.image_swimDown;
				player_fish.image_change = -(player_fish.image_change);
				timeStamp_imgChange = timeStamp;
			}
		}
		else {
			player_fish.image = player_fish.image_idle;			
		}
		
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
		for(VisualObject eat : eats)
			eat.Draw(g);
		// 끝
		EndDraw();
	}

}
