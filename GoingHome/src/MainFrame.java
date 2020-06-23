
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

	// ���� ������ Ȯ��
	static int gameScene = 0; // 0: ����, 1: ���� ��, 2: ���ӿ���
	
	// ���� ����, ���� �̹�����
	static final int title_width = 1600;
	static final int title_height = 900;
	static final int title_text_width = 1600;
	static final int title_text_height = 900;
	// ���� �Դ� �Ҹ� ���� ���
	static final int number_of_eat_sounds = 7;			// �ִ� ���� ��� ���
	static final long interval_play_ms = 0;			// ��� �� ���� ���
	// ü�� ���� ���
	static int hp_minus = 1;
	static int hp_plus = 15;
	
	// ��ֹ� ���� ���
	static int numberOfBlocks = 0;
	static int max_numberOfBlocks = 3;
	static final int block_width = 30;
	static final int block_height = 30;
	static final int block_upgrade_interval = 10000;
	static boolean nowCheck = false;
	
	// ���� ��� - ����, ũ��
	static int numberOfEats = 0;
	static int max_numberOfEats = 5;
	static final int eats_width = 60;
	static final int eats_height = 60;
	static int eats_add_interval = 1000;
	
	// �÷��̾� ����� ũ�� ���
	static final int player_width = 120; // ���� : ���� = 2 : 1 ����
	static final int player_height = 60;

	// ü�¹� ���
	static final int hp_width = 100;
	static final int hp_height = 10;
	
	// ���� ����ġ�� ���� �ӵ��� ���
	static final double img_change_speed_min = 0.08;
	// �ӵ��� ��� - ���� �ð����� �� ���� �Է� �޴� ��
	static final double speed_limit = 3;					// �ִ� �ӵ� // 0.4�� ���� 
	static final double x_axis = 0.5;						// �÷��̾� x�� �̵� �ӵ�
	static final double y_axis = 0.5;						// �÷��̾� y�� �̵� �ӵ�
	static final double coef_friction_x = -0.0015;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
	static final double coef_friction_y = -0.0015;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)

	// �ӵ��� ��� - �� ������ �Է� �޴� ��
//	static final double speed_limit = 0.9;					// �ִ� �ӵ�
//	static final double x_axis = 0.01;						// �÷��̾� x�� �̵� �ӵ�
//	static final double y_axis = 0.01;						// �÷��̾� y�� �̵� �ӵ�
//	static final double coef_friction_x = -0.0001;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
//	static final double coef_friction_y = -0.0001;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
	
	// Ű�Է� Ƚ�� ���� ��
	static boolean canInput = true;
	
	// �������� ���Ŀ� 
	static boolean isPlayedGameover = false;
	
	// ���� ��ġ ���� ������
	Random rand;
	
	// ���ӽ���, ������ ������Ʈ
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
		
		
		// ���ġ�� �̹���
		public Image[][] player_images;
		public int image_change_up_down;
		public int image_change_right_left;
		
		// ���� ���� ������ ī����
		public int eatCount;
		
		
		// ü��
		public double hp;
		// ��������
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
	TitleImage title;										// ���� Ÿ��Ʋ
	TitleImage title_over;									// ���� ����
	TitleText title_text;									// ���� Ÿ��Ʋ �ؽ�Ʈ
	Hp hp;													// ü�¹�
	Player player_fish;										// �÷��̾� �����
	ArrayList<Eats> eats = new ArrayList<Eats>();			// ����
	ArrayList<Block> blocks = new ArrayList<Block>();		// ��ֹ�
	long timeStamp_eatAdd = 1;								// ���� ���� �ð� ����
	long timeStamp_lastFrame = 0;							//���� �������� timeStamp -> ������ ��꿡 ���
	long timeStamp_collision = 0;							// collision�� ���� timeStamp
	long timeStamp_input = 0;								// �ð� �� input Ƚ�� ������ ���� timeStamp
	long timeStamp_imgChange = 0;							// ����� ���� �̹��� �����
	long timeStamp_hp = 0;									// ����� hp ��� �뵵
	long timeStamp_difficulty = 0;							// ���̵� ������
	
	long timeStamp_lastPlayed = 0;							// ��� ȣ�� ���� ��
	long timeStamp_block = 0;								// ��ֹ� ���� ��
	
	long start_time = 0;									// ���۽ð�
	
	int move_x;
	int move_y;
	
	
	
	
	public MainFrame(GameFrameSettings settings) {
		super(settings);
		
		// �׸����� ���� �� ���� Ŭ������ �ν��Ͻ� �̸� ������� ��.
		// �ϴ� ������ �о���� ��.
		
		inputs.BindKey(KeyEvent.VK_LEFT, 0);
		inputs.BindKey(KeyEvent.VK_RIGHT, 1);
		inputs.BindKey(KeyEvent.VK_UP, 2);
		inputs.BindKey(KeyEvent.VK_DOWN, 3);
		inputs.BindKey(KeyEvent.VK_SPACE, 4);
		

		images.LoadImage("Images/����_������.png", "img_right_fish_idle");
		images.LoadImage("Images/����_������_����.png", "img_right_swimUp");
		images.LoadImage("Images/����_������_�Ʒ���.png", "img_right_swimDown");

		images.LoadImage("Images/����_����.png", "img_left_fish_idle");
		images.LoadImage("Images/����_����_����.png", "img_left_swimUp");
		images.LoadImage("Images/����_����_�Ʒ���.png", "img_left_swimDown");
		
		images.LoadImage("images/ü�¹�.png", "img_hp");
		
		images.LoadImage("Images/����.png", "img_shrimp");
		images.LoadImage("Images/����.png", "img_block");
		images.LoadImage("Images/����_�帴.png", "img_block_blur");
		
		images.LoadImage("Images/���ӽ����̹���_1.png", "img_gamestart1");
		images.LoadImage("Images/���ӽ����̹���_2.png", "img_gamestart2");
		images.LoadImage("Images/���ӿ���_�̹���.png", "img_gameover");
		
		audios.LoadAudio("Audios/01_������ �� �⺻ �÷��� ���.wav", "main_bgm", 1);
		audios.LoadAudio("Audios/02_���ǵ� ��(����).wav", "gameplay_bgm", 1);
		audios.LoadAudio("Audios/05_����Դ¼Ҹ�2.wav", "eat_sound", number_of_eat_sounds);
		audios.LoadAudio("Audios/03_���ӿ���.wav", "gameover_bgm", 1);
	}

	@Override
	public boolean Initialize() {		
		rand = new Random();
		
		// �ҷ��� �̹����b �ʵ忡 �Ҵ����ֱ�
		//player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		player_fish = new Player(105, 100);
		move_x = -5;
		move_y = -10;
		
		// ü�¹�
		hp = new Hp(105, 90);
		
		// ���� �� ���� ��ġ
//		for(int iEats = 0; iEats < numberOfEats; iEats++)
//			eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 10) + 100, rand.nextInt(settings.canvas_height - eats_height - 10) + 1));
		
		
		// ���� �� �ϳ��� ���� �߰�
		eats.add(new Eats(800, 600));
		
		// ��� �߰�
		audios.Loop("main_bgm", -1);
		
		// ���� ��ŸƮ ���� �߰�
		player_fish.isDead = false;
		
		// ���� ����, ������ �̹��� �ʱ�ȭ
		title = new TitleImage("img_gamestart1");
		title_over = new TitleImage("img_gameover");
		title_text = new TitleText();
		// ����ۿ�
//		numberOfEats = 0;
//		max_numberOfEats = 5;
//		eats_add_interval = 1000;
//		gameScene = 0; // 0: ����, 1: ���� ��, 2: ���ӿ���
//		hp_minus = 1;
//		hp_plus = 15;
//		timeStamp_eatAdd = 1;								// ���� ���� �ð� ����
//		timeStamp_lastFrame = 0;							//���� �������� timeStamp -> ������ ��꿡 ���
//		timeStamp_collision = 0;							// collision�� ���� timeStamp
//		timeStamp_input = 0;								// �ð� �� input Ƚ�� ������ ���� timeStamp
//		timeStamp_imgChange = 0;							// ����� ���� �̹��� �����
//		timeStamp_hp = 0;									// ����� hp ��� �뵵
//		timeStamp_difficulty = 0;							// ���̵� ������
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
		boolean isSpacePressed = inputs.buttons[4].IsPressedNow();
		// �����̽��� ������ �����
		if(gameScene == 2) {
			if(isSpacePressed == true) {
				gameScene = 0;			
				
				// ���� �ҿ�
				hp_minus = 1;
				hp_plus = 15;
				timeStamp_eatAdd = 0;								// ���� ���� �ð� ����
				timeStamp_lastFrame = timeStamp;							//���� �������� timeStamp -> ������ ��꿡 ���
				timeStamp_collision = timeStamp;							// collision�� ���� timeStamp
				timeStamp_input = timeStamp;								// �ð� �� input Ƚ�� ������ ���� timeStamp
				timeStamp_imgChange = timeStamp;							// ����� ���� �̹��� �����
				timeStamp_hp = timeStamp;									// ����� hp ��� �뵵
				timeStamp_difficulty = timeStamp;							// ���̵� ������
				timeStamp_block = timeStamp;								// ��ֹ� ���� ��
				
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
				
				// ���� �ҿ�
				hp_minus = 1;
				hp_plus = 15;
				timeStamp_eatAdd = 0;								// ���� ���� �ð� ����
				timeStamp_lastFrame = timeStamp;							//���� �������� timeStamp -> ������ ��꿡 ���
				timeStamp_collision = timeStamp;							// collision�� ���� timeStamp
				timeStamp_input = timeStamp;								// �ð� �� input Ƚ�� ������ ���� timeStamp
				timeStamp_imgChange = timeStamp;							// ����� ���� �̹��� �����
				timeStamp_hp = timeStamp;									// ����� hp ��� �뵵
				timeStamp_difficulty = timeStamp;							// ���̵� ������
				timeStamp_block = timeStamp;								// ��ֹ� ���� ��
				
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

		// �ð� �������� ���̵� ���
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
		
		// ���� ������ ���ķ� ����� �ð� ����
		double interval = timeStamp - timeStamp_lastFrame;
		double interval_input = timeStamp - timeStamp_input;
		double interval_swim = timeStamp - timeStamp_imgChange;
		
		double o_p_x, o_p_y;
		int o_x, o_y;
		o_p_x = player_fish.p_x;
		o_p_y = player_fish.p_y;
		o_x = player_fish.x;
		o_y = player_fish.y;
		
		
		// ���� �� �߰�
		if(numberOfEats < max_numberOfEats) {
			if(timeStamp - timeStamp_eatAdd >= eats_add_interval) {
				numberOfEats++;
				eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 200) + 100, rand.nextInt(settings.canvas_height - eats_height - 200) + 100));
				timeStamp_eatAdd = timeStamp;
			}
		}
		
		// ��ֹ� �߰�
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
		// ��ֹ� ���ϰ� ���� �浹 ����
		if(blocks.isEmpty() != true && nowCheck == false && (timeStamp - timeStamp_block > 1000)) {
			nowCheck = true;
			for(Block block : blocks) {
				block.changeImg();
				block.image = block.idle_img;
			}
		}
		// �� ��ư�� ���¸� �˻��Ͽ� ����⿡ � �۾��� �����ؾ� �ϴ��� üũ
		boolean isLeftForceRequested = false;
		boolean isRightForceRequested = false;
		boolean isUpForceRequested = false;
		boolean isDownForceRequested = false;
		
	
		
		
		/*
		 * 
		 *  TODO 
		 *  Ű �Է��� ��� �������� ���� �� ���� ������� ������.
		 *  UX�� �ٸ��ϱ� �ǰ� �޾ƺ� ��. 
		 *  1. ���� �ð����� �� ���� �Է� �޴� ��
		 *  2. �� ������ �Է� �޴� ��
		 *  
		 */
        // 1. ���� �ð����� �� ���� �Է� �޴� ��
		if(canInput == true && player_fish.isDead != true) {
			// ��ư �ޱ�
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

		
		// 2. �� ������ �Է� �޴� ��
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
		
		
		
		// �浹�׽�Ʈ
		if(timeStamp - timeStamp_collision >= 10) {
			for(Eats eat : eats) {
				if(player_fish.CollisionTest(eat) == true) {
					eats.remove(eat);
					timeStamp_collision = timeStamp;
					timeStamp_eatAdd = timeStamp;
					player_fish.eatCount++;
					numberOfEats--;
					hp.getPlus(hp_plus);
					
					// �Ҹ� ���
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
		 * �׽�Ʈ�� ���ƴٴϴ� ����
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
		 *  ���� ����
		 */
//		if(isDownForceRequested == true) {
//			player_fish.v_x = 0.1;
//			player_fish.v_y = 0.3;
//		}
//		if(isUpForceRequested == true) {
//			player_fish.v_x = 0.1;
//			player_fish.v_y = -0.3;
//		}


		// �ӵ� ����
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
		
		// ������ ���
		player_fish.a_x = coef_friction_x * interval * player_fish.v_x;
		player_fish.a_y = coef_friction_y * interval * player_fish.v_y;
		
		// ���ӵ��� �ӵ��� ���� - ���ӵ��� ��� �̸� �ð��� �������Ƿ� ���⼭ �� �������� ����
		player_fish.v_x += player_fish.a_x;
		player_fish.v_y += player_fish.a_y;

		
		
		// �ӵ��� ��ġ�� ���� - �� ���� �ð��� ���Ͽ� ����
		player_fish.p_x += player_fish.v_x * interval;
		player_fish.p_y += player_fish.v_y * interval;
		
		
		// ����������, ������� ��ġ�� ������� �ش� ����⸦ �׸� �ȼ��� ����
		player_fish.x = (int)player_fish.p_x;
		player_fish.y = (int)player_fish.p_y;
		
		// canvas ������ �÷��̾ �� ������ ����
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
			
		// ü�¹� ��ġ ����⿡ ���߱�
		hp.x = player_fish.x - 10;
		hp.y = player_fish.y - 15;
		
		// ���� '���� ������'�� �� �̹� �������� ���� �ð� ���
		timeStamp_lastFrame = timeStamp;
		
		
		// ����� ���� �����̴� ��
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
		
		
		// ����� ü�� ��� ���ӿ������� Ȯ��
		if(timeStamp - timeStamp_hp >= 100) {
			//player_fish.hp -= hp_minus;
			if(!hp.getMinus(hp_minus)) {
				player_fish.isDead = true;
			}
			timeStamp_hp = timeStamp;
		}
		
		
		// ���ӿ��� ���� Ȯ��
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
		// ������ ��ȯ���� false, true�� �����ϴ� ���� �߿��ѵ� 
		// �츮�� ������ �����̴� �ϴ� true�� �ص�
		return true;
	}

	@Override
	public void Draw(long timeStamp) {
		// ����
		BeginDraw();
		ClearScreen();
		
		// ���ӽ���ȭ��
		if(gameScene == 0) {
			// testcode
			title.Draw(g);
			title_text.Draw(g);
		}
		// ���� �� ȭ��
		else if(gameScene == 1 || gameScene == 2) {
			// ��� ���� ���� ����. �׳� �׻� �̷��� ���´ٰ� ������.
			player_fish.Draw(g);
			hp.Draw(g);
			for(VisualObject eat : eats)
				eat.Draw(g);
			for(VisualObject block : blocks)
				block.Draw(g);
			
			int firstSeconds = (int)(timeStamp_lastFrame - start_time) / 1000;
			int secondSeconds = (int)(timeStamp_lastFrame - start_time) % 1000 / 100;
			DrawString(settings.canvas_width / 2 - 100, 30, "��ġ�� ��ƾ �ð�: %d.%d��", firstSeconds, secondSeconds);
			DrawString(settings.canvas_width / 2 - 100, 50, "��ġ�� ���� ����:  %d ����", player_fish.eatCount);
			DrawString(settings.canvas_width / 2 - 100, 70, "��ġ�� ü��: %.0f", hp.hp);
			//DrawString(player_fish.x, player_fish.y + 45, "������ ü��: %.0f", player_fish.hp);
		
			// ���ӿ��� ȭ�� �׸���
			if(gameScene == 2) {
				title_over.Draw(g);
			}
		}
		
		// ��
		EndDraw();
	}

}
