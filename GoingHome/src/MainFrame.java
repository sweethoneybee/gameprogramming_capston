
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import loot.AudioManager;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject;

public class MainFrame extends GameFrame {
	static final double coef_friction_x = -0.001;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)
	static final double coef_friction_y = -0.005;			//�������� �����ϱ� ���� ���(�ӵ��� �� ���� ���� ��ŭ�� �������� ��. ���� �� ���� �������� ��. �������� ������ �ȼ�/ms^2)

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
	
	Player player_fish;										// �÷��̾� �����
	
	long timeStamp_lastFrame = 0;							//���� �������� timeStamp -> ������ ��꿡 ���
	
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
		audios.LoadAudio("Audios/01_������ �� �⺻ �÷��� ���.wav", "main_bgm", 1);
		
	}

	@Override
	public boolean Initialize() {		
		
		// �ҷ��� �̹����b �ʵ忡 �Ҵ����ֱ�
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
		// �Ѱ� x, y ���� �밡�ٷ� �˾Ƴ�.
		if(player_fish.x <= 0 || player_fish.x >= 550)
			move_x = -move_x;
		if(player_fish.y <= -100 || player_fish.y >= 400)
			move_y = -move_y;
		
		player_fish.x = player_fish.x + move_x;
		player_fish.y = player_fish.y + move_y;
		*/
		
		
		// �� ��ư�� ���¸� �˻��Ͽ� ����⿡ � �۾��� �����ؾ� �ϴ��� üũ
		boolean isLeftForceRequested;
		boolean isRightForceRequested;
		boolean isUpForceRequested;
		boolean isDownForceRequested;
		
		//���� ������ ���ķ� ����� �ð� ����
		double interval = timeStamp - timeStamp_lastFrame;
		
		// �̹��� ���ʹ�ư �������� �������� ���� �� ����.
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

		
		// ���� Ű�� �ȴ��� �ִٸ� �ӵ� / ���ӵ� �ݿ�
		if(isLeftForceRequested == false && isRightForceRequested == false 
				&& isDownForceRequested == false && isUpForceRequested == false) {
			// ������ ���
			player_fish.a_x = coef_friction_x * interval * player_fish.v_x;
			player_fish.a_y = coef_friction_y * interval * player_fish.v_y;
			
			// ���ӵ��� �ӵ��� ���� - ���ӵ��� ��� �̸� �ð��� �������Ƿ� ���⼭ �� �������� ����
			player_fish.v_x += player_fish.a_x;
			player_fish.v_y += player_fish.a_y;
			
			// �ӵ� ����
			player_fish.v_x %= 100;
			player_fish.v_y %= 100;
			
			// �ӵ��� ��ġ�� ���� - �� ���� �ð��� ���Ͽ� ����
			player_fish.p_x += player_fish.v_x * interval;
			player_fish.p_y += player_fish.v_y * interval;
			
			
			// ����������, ������� ��ġ�� ������� �ش� ����⸦ �׸� �ȼ��� ����
			player_fish.x = (int)player_fish.p_x;
			player_fish.y = (int)player_fish.p_y;
		}
		
		
		// ���� '���� ������'�� �� �̹� �������� ���� �ð� ���
		timeStamp_lastFrame = timeStamp;
		
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
