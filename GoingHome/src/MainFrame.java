
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
	static final int player_width = 80; // ���� : ���� = 2 : 1 ����
	static final int player_height = 40;

	// ���� ����ġ�� ���� �ӵ��� ���
	static final double img_change_speed_min = 0.08;
	// �ӵ��� ��� - ���� �ð����� �� ���� �Է� �޴� ��
//	static final double speed_limit = 0.4;					// �ִ� �ӵ�
//	static final double x_axis = 0.2;						// �÷��̾� x�� �̵� �ӵ�
//	static final double y_axis = 0.2;						// �÷��̾� y�� �̵� �ӵ�
//	static final double coef_friction_x = -0.0015;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
//	static final double coef_friction_y = -0.0015;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)

	// �ӵ��� ��� - �� ������ �Է� �޴� ��
	static final double speed_limit = 0.6;					// �ִ� �ӵ�
	static final double x_axis = 0.006;						// �÷��̾� x�� �̵� �ӵ�
	static final double y_axis = 0.006;						// �÷��̾� y�� �̵� �ӵ�
	static final double coef_friction_x = -0.001;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
	static final double coef_friction_y = -0.001;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
	
	// Ű�Է� Ƚ�� ���� ��
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
		
		
		// ���ġ�� �̹���
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
	Player player_fish;										// �÷��̾� �����
	ArrayList<Eats> eats = new ArrayList<Eats>();			// ����
	long timeStamp_lastFrame = 0;							//���� �������� timeStamp -> ������ ��꿡 ���
	long timeStamp_collision = 0;							// collision�� ���� timeStamp
	long timeStamp_input = 0;								// �ð� �� input Ƚ�� ������ ���� timeStamp
	long timeStamp_imgChange = 0;							// ����� ���� �̹��� �����
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
		
		
		images.LoadImage("Images/����.png", "img_fish");
		images.LoadImage("Images/����_����.png", "img_swimUp");
		images.LoadImage("Images/����_�Ʒ���.png", "img_swimDown");
		images.LoadImage("Images/����.png", "img_shrimp");
		
		audios.LoadAudio("Audios/01_������ �� �⺻ �÷��� ���.wav", "main_bgm", 1);
		
	}

	@Override
	public boolean Initialize() {		
		Random rand = new Random();
		
		// �ҷ��� �̹����b �ʵ忡 �Ҵ����ֱ�
		//player_fish = new DrawableObject((800-329) / 2, (600 - 329) / 2, 329, 329, images.GetImage("img_fish"));
		player_fish = new Player(105, 100);
		move_x = -5;
		move_y = -10;
		
		
		// ���� �� ���� ��ġ
		for(int iEats = 0; iEats < numberOfEats; iEats++)
			eats.add(new Eats(rand.nextInt(settings.canvas_width - eats_width - 10) + 100, rand.nextInt(settings.canvas_height - eats_height - 10) + 1));
			
		audios.Loop("main_bgm", -1);
		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		
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
//		if(canInput == true) {
//			// ��ư �ޱ�
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

		
		// 2. �� ������ �Է� �޴� ��
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
		
		
		
		// �浹�׽�Ʈ
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
		
		player_fish.v_x %= 1;
		player_fish.v_y %= 1;
		
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
		}
		if(player_fish.y <= 0 || player_fish.y >= settings.canvas_height - player_height) {
			player_fish.y = o_y;
			player_fish.p_y = o_p_y;
		}
			

		
		// ���� '���� ������'�� �� �̹� �������� ���� �ð� ���
		timeStamp_lastFrame = timeStamp;
		
		
		// ����� ���� �����̴� ��
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
		for(VisualObject eat : eats)
			eat.Draw(g);
		// ��
		EndDraw();
	}

}
