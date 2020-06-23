
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

	// 게임 중인지 확인
	static int gameScene = 0; // 0: 시작, 1: 게임 중, 2: 게임오버
	
	// 게임 시작, 엔딩 이미지용
	static final int title_width = 1600;
	static final int title_height = 900;
	static final int title_text_width = 1600;
	static final int title_text_height = 900;
	// 새우 먹는 소리 관련 상수
	static final int number_of_eat_sounds = 7;			// 최대 동시 재생 상수
	static final long interval_play_ms = 0;			// 재생 간 간격 상수
	// 체력 관련 상수
	static int hp_minus = 1;
	static int hp_plus = 15;
	
	// 장애물 관련 상수
	static int numberOfBlocks = 0;
	static int max_numberOfBlocks = 3;
	static final int block_width = 30;
	static final int block_height = 30;
	static final int block_upgrade_interval = 10000;
	static boolean nowCheck = false;
	
	// 새우 상수 - 개수, 크기
	static int numberOfEats = 0;
	static int max_numberOfEats = 5;
	static final int eats_width = 60;
	static final int eats_height = 60;
	static int eats_add_interval = 1000;
	
	// 플레이어 물고기 크기 상수
	static final int player_width = 120; // 가로 : 세로 = 2 : 1 유지
	static final int player_height = 60;

	// 체력바 상수
	static final int hp_width = 100;
	static final int hp_height = 10;
	
	// 물고리 꼬리치는 최저 속도용 상수
	static final double img_change_speed_min = 0.08;
	// 속도용 상수 - 일정 시간마다 한 번씩 입력 받는 용
	static final double speed_limit = 3;					// 최대 속도 // 0.4가 원본 
	static final double x_axis = 0.5;						// 플레이어 x축 이동 속도
	static final double y_axis = 0.5;						// 플레이어 y축 이동 속도
	static final double coef_friction_x = -0.0015;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
	static final double coef_friction_y = -0.0015;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)

	// 속도용 상수 - 꾹 누르는 입력 받는 용
//	static final double speed_limit = 0.9;					// 최대 속도
//	static final double x_axis = 0.01;						// 플레이어 x축 이동 속도
//	static final double y_axis = 0.01;						// 플레이어 y축 이동 속도
//	static final double coef_friction_x = -0.0001;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
//	static final double coef_friction_y = -0.0001;			//마찰력을 적용하기 위한 계수(속도에 이 값을 곱한 만큼이 마찰력이 됨. 따라서 이 값은 음수여야 함. 마찰력의 단위는 픽셀/ms^2)
	
	// 키입력 횟수 제한 용
	static boolean canInput = true;
	
	// 엔딩음악 제셍용 
	static boolean isPlayedGameover = false;
	
	// 새우 위치 랜덤 생성용
	Random rand;
	
	// 게임시작, 엔딩용 오브젝트
	public class TitleImage extends DrawableObject {
		public double p_x;
		public double p_y;
		
		public Image gamestart;
		public Image gameover;
		
		public TitleImage(String img_name) {
			super(settings.canvas_width / 999999999, 0, title_width, title_height, images.GetImage(img_name));
			p_x = x;
			p_y = y;
		}
	}
	public class TitleText extends DrawableObject {
		public double p_x;
		public double p_y;
		
		public TitleText() {
			super(settings.canvas_width / 999999999, 0, title_text_width, title_text_height, images.GetImage("img_gamestart2"));
			p_x = x;
			p_y = y;
		}
	}
	//DrawableObject player_fish;
	public class Hp extends DrawableObject{
		public double p_x;
		public double p_y;
		
		public Image hp_image;
		public double hp;
		
		public Hp(int x, int y) {
			super(x, y, hp_width, hp_height, images.GetImage("img_hp"));
			p_x = x;
			p_y = y;
			
			hp = 100;
		}
		
		public boolean getMinus(int minus) {
			if((this.hp -= minus) <= 0) {
				this.hp = 0;
				this.width = 0;
				return false;
			}
			this.width -= minus;
			return true;
		}
		
		public boolean getPlus(int plus) {
			if((this.hp += plus) >= 100) {
				this.hp = 100;
				this.width = 100;
			}
			this.width += plus;
			return true;
		}
	}
	public class Player extends DrawableObject
	{
		public double p_x;
		public double p_y;
		public double v_x;
		public double v_y;
		public double a_x;
		public double a_y;
		
		
		// 헤엄치는 이미지
		public Image[][] player_images;
		public int image_change_up_down;
		public int image_change_right_left;
		
		// 먹은 새우 마리수 카운팅
		public int eatCount;
		
		
		// 체력
		public double hp;
		// 게임종료
		public boolean isDead;
		
		public Player(int x, int y)
		{
			super(x, y,	player_width, player_height, images.GetImage("img_fish"));
			
			p_x = x;
			p_y = y;
			
			player_images = new Image[2][3];
			player_images[0] = new Image[3];
			player_images[1] = new Image[3];
			
			player_images[0][0] = images.GetImage("img_right_fish_idle");
			player_images[0][1] = images.GetImage("img_right_swimUp");
			player_images[0][2] = images.GetImage("img_right_swimDown");
			
			player_images[1][0] = images.GetImage("img_left_fish_idle");
			player_images[1][1] = images.GetImage("img_left_swimUp");
			player_images[1][2] = images.GetImage("img_left_swimDown");
			
			image_change_up_down = 1;
			image_change_right_left = 0;
			
			eatCount = 0;
			
			hp = 100;
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
	
	public class Block extends DrawableObject{
		public double p_x;
		public double p_y;
		public Image idle_img;
		public Image idle_blur_img;
		
		public Block(int x, int y) {
			super(x, y, eats_width, eats_height, images.GetImage("img_block_blur"));
			
			idle_img = images.GetImage("img_block");
			idle_blur_img = images.GetImage("img_block_blur");
			p_x = x;
			p_y = y;
		}
		
		public void changeImg() {
			this.image = this.idle_img;
		}
		
	}
	TitleImage title;										// 게임 타이틀
	TitleImage title_over;									// 게임 엔딩
	TitleText title_text;									// 게임 타이틀 텍스트
	Hp hp;													// 체력바
	Player player_fish;										// 플레이어 물고기
	ArrayList<Eats> eats = new ArrayList<Eats>();			// 먹이
	ArrayList<Block> blocks = new ArrayList<Block>();		// 장애물
	long timeStamp_eatAdd = 1;								// 먹이 생성 시간 간격
	long timeStamp_lastFrame = 0;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
	long timeStamp_collision = 0;							// collision을 위한 timeStamp
	long timeStamp_input = 0;								// 시간 당 input 횟수 제한을 위한 timeStamp
	long timeStamp_imgChange = 0;							// 물고기 꼬리 이미지 변경용
	long timeStamp_hp = 0;									// 물고기 hp 까는 용도
	long timeStamp_difficulty = 0;							// 난이도 조절용
	
	long timeStamp_lastPlayed = 0;							// 재생 호출 간격 용
	long timeStamp_block = 0;								// 장애물 생성 용
	
	long start_time = 0;									// 시작시간
	
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
		inputs.BindKey(KeyEvent.VK_SPACE, 4);
		

		images.LoadImage("Images/고등어_오른쪽.png", "img_right_fish_idle");
		images.LoadImage("Images/고등어_오른쪽_위로.png", "img_right_swimUp");
		images.LoadImage("Images/고등어_오른쪽_아래로.png", "img_right_swimDown");

		images.LoadImage("Images/고등어_왼쪽.png", "img_left_fish_idle");
		images.LoadImage("Images/고등어_왼쪽_위로.png", "img_left_swimUp");
		images.LoadImage("Images/고등어_왼쪽_아래로.png", "img_left_swimDown");
		
		images.LoadImage("images/체력바.png", "img_hp");
		
		images.LoadImage("Images/새우.png", "img_shrimp");
		images.LoadImage("Images/성게.png", "img_block");
		images.LoadImage("Images/성게_흐릿.png", "img_block_blur");
		
		images.LoadImage("Images/게임시작이미지_1.png", "img_gamestart1");
		images.LoadImage("Images/게임시작이미지_2.png", "img_gamestart2");
		images.LoadImage("Images/게임오버_이미지.png", "img_gameover");
		
		audios.LoadAudio("Audios/01_오프닝 겸 기본 플레이 브금.wav", "main_bgm", 1);
		audios.LoadAudio("Audios/02_스피드 업(새우).wav", "gameplay_bgm", 1);
		audios.LoadAudio("Audios/05_새우먹는소리2.wav", "eat_sound", number_of_eat_sounds);
		audios.LoadAudio("Audios/03_게임오버.wav", "gameover_bgm", 1);
	}

	@Override
	public boolean Initialize() {		
		rand = new Random();
		
		// 불러온 이미지륿 필드에 할당해주기
		//player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		player_fish = new Player(105, 100);
		move_x = -5;
		move_y = -10;
		
		// 체력바
		hp = new Hp(105, 90);
		
		// 먹을 것 랜덤 배치
//		for(int iEats = 0; iEats < numberOfEats; iEats++)
//			eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 10) + 100, rand.nextInt(settings.canvas_height - eats_height - 10) + 1));
		
		
		// 먹을 것 하나만 고정 추가
		eats.add(new Eats(800, 600));
		
		// 브금 추가
		audios.Loop("main_bgm", -1);
		
		// 게임 스타트 조건 추가
		player_fish.isDead = false;
		
		// 게임 시작, 엔딩용 이미지 초기화
		title = new TitleImage("img_gamestart1");
		title_over = new TitleImage("img_gameover");
		title_text = new TitleText();
		// 재시작용
//		numberOfEats = 0;
//		max_numberOfEats = 5;
//		eats_add_interval = 1000;
//		gameScene = 0; // 0: 시작, 1: 게임 중, 2: 게임오버
//		hp_minus = 1;
//		hp_plus = 15;
//		timeStamp_eatAdd = 1;								// 먹이 생성 시간 간격
//		timeStamp_lastFrame = 0;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
//		timeStamp_collision = 0;							// collision을 위한 timeStamp
//		timeStamp_input = 0;								// 시간 당 input 횟수 제한을 위한 timeStamp
//		timeStamp_imgChange = 0;							// 물고기 꼬리 이미지 변경용
//		timeStamp_hp = 0;									// 물고기 hp 까는 용도
//		timeStamp_difficulty = 0;							// 난이도 조절용
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
		boolean isSpacePressed = inputs.buttons[4].IsPressedNow();
		// 스페이스바 누르면 재시작
		if(gameScene == 2) {
			if(isSpacePressed == true) {
				gameScene = 0;			
				
				// 시작 텀용
				hp_minus = 1;
				hp_plus = 15;
				timeStamp_eatAdd = 0;								// 먹이 생성 시간 간격
				timeStamp_lastFrame = timeStamp;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
				timeStamp_collision = timeStamp;							// collision을 위한 timeStamp
				timeStamp_input = timeStamp;								// 시간 당 input 횟수 제한을 위한 timeStamp
				timeStamp_imgChange = timeStamp;							// 물고기 꼬리 이미지 변경용
				timeStamp_hp = timeStamp;									// 물고기 hp 까는 용도
				timeStamp_difficulty = timeStamp;							// 난이도 조절용
				timeStamp_block = timeStamp;								// 장애물 생성 용
				
				hp.hp = 100;
				hp.width = 100;
				eats_add_interval = 1000;
				numberOfEats = 0;
				max_numberOfEats = 5;
				numberOfBlocks = 0;
				start_time = timeStamp;
				player_fish.eatCount = 0;
				
				eats.clear();
				blocks.clear();
				
				player_fish.isDead = false;
				isPlayedGameover = false;
				player_fish.v_x = 0;
				player_fish.v_y = 0;
				audios.Loop("main_bgm", -1);
			}
		}
		if(gameScene == 0) {
			if(isSpacePressed == true) {
				gameScene = 1;			
				
				// 시작 텀용
				hp_minus = 1;
				hp_plus = 15;
				timeStamp_eatAdd = 0;								// 먹이 생성 시간 간격
				timeStamp_lastFrame = timeStamp;							//직전 프레임의 timeStamp -> 물리량 계산에 사용
				timeStamp_collision = timeStamp;							// collision을 위한 timeStamp
				timeStamp_input = timeStamp;								// 시간 당 input 횟수 제한을 위한 timeStamp
				timeStamp_imgChange = timeStamp;							// 물고기 꼬리 이미지 변경용
				timeStamp_hp = timeStamp;									// 물고기 hp 까는 용도
				timeStamp_difficulty = timeStamp;							// 난이도 조절용
				timeStamp_block = timeStamp;								// 장애물 생성 용
				
				hp.hp = 100;
				hp.width = 100;
				eats_add_interval = 1000;
				numberOfEats = 0;
				max_numberOfEats = 5;
				numberOfBlocks = 0;
				start_time = timeStamp;
				player_fish.eatCount = 0;
				
				player_fish.v_x = 0;
				player_fish.v_y = 0;
				eats.clear();
				blocks.clear();
				
			}
			
		}

		// 시간 지날수록 난이도 상승
		if(timeStamp - timeStamp_difficulty >= 7000) {
			if((hp_minus += 1) >= 5)
				hp_minus = 5;
//			if((hp_plus += 12) >= 20)
//				hp_plus = 70;
			if((eats_add_interval -= 400) <= 200)
				eats_add_interval = 200;
			if((max_numberOfEats += 10) >= 50)
				max_numberOfEats = 20;
			timeStamp_difficulty = timeStamp;
		}
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
		
		
		// 먹을 것 추가
		if(numberOfEats < max_numberOfEats) {
			if(timeStamp - timeStamp_eatAdd >= eats_add_interval) {
				numberOfEats++;
				eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 200) + 100, rand.nextInt(settings.canvas_height - eats_height - 200) + 100));
				timeStamp_eatAdd = timeStamp;
			}
		}
		
		// 장애물 추가
		if(timeStamp - timeStamp_block > block_upgrade_interval) {
			timeStamp_block = timeStamp;
			nowCheck = false;
			if(numberOfBlocks <= max_numberOfBlocks) {
				if(numberOfBlocks != max_numberOfBlocks)
					numberOfBlocks += 1;
				blocks.clear();
				for(int iBlocks = 0; iBlocks < numberOfBlocks; iBlocks++)
					blocks.add(new Block(rand.nextInt(settings.canvas_width - block_width - 200) + 100, rand.nextInt(settings.canvas_height - block_height - 200) + 100));
			}
		}
		// 장애물 진하고 이제 충돌 가능
		if(blocks.isEmpty() != true && nowCheck == false && (timeStamp - timeStamp_block > 1000)) {
			nowCheck = true;
			for(Block block : blocks) {
				block.changeImg();
				block.image = block.idle_img;
			}
		}
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
		if(canInput == true && player_fish.isDead != true) {
			// 버튼 받기
			isLeftForceRequested = inputs.buttons[0].IsPressedNow();
			isRightForceRequested = inputs.buttons[1].IsPressedNow();
			isUpForceRequested = inputs.buttons[2].IsPressedNow();
			isDownForceRequested = inputs.buttons[3].IsPressedNow();			
			
//			if(isLeftForceRequested == true || isRightForceRequested == true || isUpForceRequested == true || isDownForceRequested == true)
//				canInput = false;
		}
//		if(interval_input >= 200) {
//			canInput = true;
//			timeStamp_input = timeStamp;
//		}

		
		// 2. 꾹 누르는 입력 받는 용
//		if(inputs.buttons[0].IsPressedNow() == true || (inputs.buttons[0].isPressed == true && inputs.buttons[0].isChanged == false)) {
//			isLeftForceRequested = true;
//		}
//		if(inputs.buttons[1].IsPressedNow() == true || (inputs.buttons[1].isPressed == true && inputs.buttons[1].isChanged == false)) {
//			isRightForceRequested = true;
//		}
//		if(inputs.buttons[2].IsPressedNow() == true || (inputs.buttons[2].isPressed == true && inputs.buttons[2].isChanged == false)) {
//			isUpForceRequested = true;
//		}
//		if(inputs.buttons[3].IsPressedNow() == true || (inputs.buttons[3].isPressed == true && inputs.buttons[3].isChanged == false)) {
//			isDownForceRequested = true;
//		}
		
		
		
		// 충돌테스트
		if(timeStamp - timeStamp_collision >= 10) {
			for(Eats eat : eats) {
				if(player_fish.CollisionTest(eat) == true) {
					eats.remove(eat);
					timeStamp_collision = timeStamp;
					timeStamp_eatAdd = timeStamp;
					player_fish.eatCount++;
					numberOfEats--;
					hp.getPlus(hp_plus);
					
					// 소리 재생
					if(timeStamp - timeStamp_lastPlayed > interval_play_ms) {
						timeStamp_lastPlayed = timeStamp;
						audios.Play("eat_sound");						
					}
					break;
				}
			}
			if(nowCheck == true) {
				for(Block block : blocks) {
					if(player_fish.CollisionTest(block) == true) {
						player_fish.isDead = true;
						break;
					}
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
		

//		player_fish.v_x %= 1;
//		player_fish.v_y %= 1;
		
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
			player_fish.v_x = -(player_fish.v_x * 1);
		}
		if(player_fish.y <= 0 || player_fish.y >= settings.canvas_height - player_height) {
			player_fish.y = o_y;
			player_fish.p_y = o_p_y;
			player_fish.v_y = -(player_fish.v_y * 1);
		}
			
		// 체력바 위치 물고기에 맞추기
		hp.x = player_fish.x - 10;
		hp.y = player_fish.y - 15;
		
		// 이제 '직전 프레임'이 될 이번 프레임의 시작 시간 기록
		timeStamp_lastFrame = timeStamp;
		
		
		// 물고기 꼬리 움직이는 용
		if(player_fish.image_change_right_left == 0 && player_fish.v_x < 0)
			player_fish.image_change_right_left = 1;
		else if(player_fish.image_change_right_left == 1 && player_fish.v_x > 0)
			player_fish.image_change_right_left = 0;
		
		if(Math.abs(player_fish.v_x) >= img_change_speed_min || Math.abs(player_fish.v_y) >= img_change_speed_min) {
			if(interval_swim >= 500) {
				if(player_fish.image_change_up_down == 1) {
					player_fish.image = player_fish.player_images[player_fish.image_change_right_left][player_fish.image_change_up_down];
					player_fish.image_change_up_down = 2;
				}
				else {
					player_fish.image = player_fish.player_images[player_fish.image_change_right_left][player_fish.image_change_up_down];
					player_fish.image_change_up_down = 1;					
				}
				timeStamp_imgChange = timeStamp;
			}
		}
		else {
			player_fish.image = player_fish.player_images[player_fish.image_change_right_left][0];
		}
		
		
		// 물고기 체력 깎고 게임오버여부 확인
		if(timeStamp - timeStamp_hp >= 100) {
			//player_fish.hp -= hp_minus;
			if(!hp.getMinus(hp_minus)) {
				player_fish.isDead = true;
			}
			timeStamp_hp = timeStamp;
		}
		
		
		// 게임오버 여부 확인
		if(player_fish.isDead == true) {
			if(isPlayedGameover == false) {
				isPlayedGameover = true;
				gameScene = 2;
				audios.Stop("main_bgm");
//				audios.Stop("gameplay_bgm");
				audios.Play("gameover_bgm");
			}
			return true;
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
		
		// 게임시작화면
		if(gameScene == 0) {
			// testcode
			title.Draw(g);
			title_text.Draw(g);
		}
		// 게임 중 화면
		else if(gameScene == 1 || gameScene == 2) {
			// 얘는 지금 이해 못해. 그냥 항상 이렇게 적는다고 생각해.
			player_fish.Draw(g);
			hp.Draw(g);
			for(VisualObject eat : eats)
				eat.Draw(g);
			for(VisualObject block : blocks)
				block.Draw(g);
			
			int firstSeconds = (int)(timeStamp_lastFrame - start_time) / 1000;
			int secondSeconds = (int)(timeStamp_lastFrame - start_time) % 1000 / 100;
			DrawString(settings.canvas_width / 2 - 100, 30, "참치가 버틴 시간: %d.%d초", firstSeconds, secondSeconds);
			DrawString(settings.canvas_width / 2 - 100, 50, "참치가 먹은 새우:  %d 마리", player_fish.eatCount);
			DrawString(settings.canvas_width / 2 - 100, 70, "참치의 체력: %.0f", hp.hp);
			//DrawString(player_fish.x, player_fish.y + 45, "연어의 체력: %.0f", player_fish.hp);
		
			// 게임오버 화면 그리기
			if(gameScene == 2) {
				title_over.Draw(g);
			}
		}
		
		// 끝
		EndDraw();
	}

}
