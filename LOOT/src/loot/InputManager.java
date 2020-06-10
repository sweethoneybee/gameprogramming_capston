package loot;

import java.awt.Canvas;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * ���� ȭ�鿡 ���� Ű���� / ���콺 �Է��� ó���ϴ� Ŭ�����Դϴ�. 
 * 
 * @author Racin
 *
 */
public class InputManager
{
	/* -----------------------------------------------
	 * 
	 * �������� ����ϰ� �� public ��ҵ��� �ִ� �κ�
	 * 
	 */
	
	/**
	 * ���� ������ ���Ǵ� ��ư �ϳ��� ���¸� ��Ÿ���� Ŭ�����Դϴ�.<br>
	 * �� Ŭ������ �������� ���� �ν��Ͻ�ȭ�� �� ������<br>
	 * InputManager.buttons �迭�� ���� ID�� �� ��ư�� ���� ���¸� Ȯ���ϰų�<br>
	 * InputManager.buttons_changed ����� ���� �̹� �����ӿ� ���°� �ٲ� ��ư�鸸 ���� Ȯ���� �� �ֽ��ϴ�.
	 * 
	 * @author Racin
	 *
	 */
	public class ButtonState
	{
		/**
		 * �� ��ư�� �Ϸ� ��ȣ�Դϴ�.<br>
		 * ���� �� ��ư�� ���� bind���� �ʾҴٸ� �� ���� -1�Դϴ�.
		 */
		public int ID;
		
		/**
		 * �� ��ư�� ���� ���� �ִ��� ���θ� ��Ÿ���ϴ�.<br>
		 * <br>
		 * isPressed�� isChanged�� ���� ���� ������ ���� ����� �� �� �ֽ��ϴ�:<br>
		 * <br>
		 * isPressed�� | isChanged��  | �ǹ�<br>
		 * --------------------------------------------------<br>
		 * true        | true         | �� ���¿��µ� �̹��� ����<br>
		 * true        | false        | ��� ������ ����<br>
		 * false       | true         | ������ ���¿��µ� �̹��� ��<br>
		 * false       | false        | ��� ������ ����<br>
		 */
		public boolean isPressed;
		
		/**
		 * �� ��ư�� ���� AcceptInputs() ȣ�� ���ķ� ���°� �ٲ������ ���θ� ��Ÿ���ϴ�.<br>
		 * <br>
		 * isPressed�� isChanged�� ���� ���� ������ ���� ����� �� �� �ֽ��ϴ�:<br>
		 * <br>
		 * isPressed�� | isChanged��  | �ǹ�<br>
		 * --------------------------------------------------<br>
		 * true        | true         | �� ���¿��µ� �̹��� ����<br>
		 * true        | false        | ��� ������ ����<br>
		 * false       | true         | ������ ���¿��µ� �̹��� ��<br>
		 * false       | false        | ��� ������ ����<br>
		 */
		public boolean isChanged;
		
		/**
		 * �� ��ư�� ���������� �� ���¿��ٰ� �̹� �����ӿ� �������� ���θ� return�մϴ�.<br>
		 * return���� isPressed && isChanged�� �����մϴ�.
		 */
		public boolean IsPressedNow()
		{
			return isPressed && isChanged;
		}

		/**
		 * �� ��ư�� ���������� ���� ���¿��ٰ� �̹� �����ӿ� �ô��� ���θ� return�մϴ�.<br>
		 * return���� isPressed == false && isChanged�� �����մϴ�.
		 */
		public boolean IsReleasedNow()
		{
			return isPressed == false && isChanged;
		}
		
		/**
		 * �� �ʵ�� InputManager ���ο��� �Է� ó�� ���� ����� ���� ���Ǹ�<br>
		 * �������� �� �ʵ带 �� �� �����ϴ�.
		 */
		private boolean isAcceptedInThisFrame;
		
		/**
		 * ButtonState class�� �������� ���� �ν��Ͻ�ȭ�� �� �����ϴ�.
		 */
		private ButtonState()
		{
			ID = -1;
		}
	}
	
	/**
	 * Ű���� �Ǵ� ���콺�� bind�� ��ư���� ��� �ִ� �迭�Դϴ�.<br>
	 * ID�� 0�� ��ư�� �� �迭�� 0��° ĭ�� ��� �ֽ��ϴ�.<br>
	 * ���� bind���� ���� ��ư�� ��� ID�� -1�� �����Ǿ� �ֽ��ϴ�.<br>
	 * <br>
	 * ����:<br>
	 * GameLoop.Update()�� ���� �κп���<br>
	 * ���� AcceptInputs()�� �� �� ȣ���Ͽ�<br>
	 * ���� frame ���� ����� ���� Ű���� / ���콺 �Է��� �� ��ư�� �ݿ��ǵ��� �ؾ� �մϴ�. 
	 */
	public ButtonState[] buttons;
	
	/**
	 * ���� AcceptInputs() ȣ�� ���ķ� ���°� �ٲ� ��ư���� ����Դϴ�.<br>
	 * <br>
	 * ����:<br>
	 * GameLoop.Update()�� ���� �κп���<br>
	 * ���� AcceptInputs()�� �� �� ȣ���Ͽ�<br>
	 * ���� frame ���� ����� ���� Ű���� / ���콺 �Է��� �� ��ư�� �ݿ��ǵ��� �ؾ� �մϴ�. 
	 */
	public ArrayList<ButtonState> buttons_changed;
	
	/**
	 * ���� ���� ȭ�� �� �ִ� ���콺 Ŀ���� ��ġ�� ��Ÿ���ϴ�.<br>
	 * <br>
	 * ����:<br>
	 * GameLoop.Update()�� ���� �κп���<br>
	 * ���� AcceptInputs()�� �� �� ȣ���Ͽ�<br>
	 * ���� frame ���� ����� ���� ���콺 �̵��� �� �ʵ忡 �ݿ��ǵ��� �ؾ� �մϴ�. 
	 */
	public Point pos_mouseCursor;

	/**
	 * ���콺 Ŀ���� ��ġ�� ���� AcceptInputs() ȣ�� ���ķ� �ٲ������ ���θ� ��Ÿ���ϴ�.<br>
	 * <br>
	 * ����:<br>
	 * GameLoop.Update()�� ���� �κп���<br>
	 * ���� AcceptInputs()�� �� �� ȣ���Ͽ�<br>
	 * ���� frame ���� ����� ���� ���콺 �̵��� �� �ʵ忡 �ݿ��ǵ��� �ؾ� �մϴ�. 
	 */
	public boolean isMouseCursorMoved;
	
	
	/**
	 * ���ο� InputManager class�� �ν��Ͻ��� �����մϴ�.
	 * 
	 * @param canvas �������� ����� �ִ� ���ӿ��� ȭ���� �׸��� ���� ����ϴ� Canvas class�� �ν��Ͻ��� ���⿡ ��������.
	 * @param numberOfButtons �������� ����� �ִ� ���ӿ��� Ű���� / ���콺�� ��� �����Ͽ� �� �� ���� ��ư�� ����� �������� ���⿡ �Է��ϼ���.
	 */
	public InputManager(Canvas canvas, int numberOfButtons)
	{
		//public �ʵ� �ʱ�ȭ �κ�
		keyBindings_IDtoKeyCode = new int[numberOfButtons];
		for ( int iBinding = 0; iBinding < numberOfButtons; ++iBinding )
			keyBindings_IDtoKeyCode[iBinding] = -1;
		
		mouseBindings_buttonIdxToID = new int[MouseInfo.getNumberOfButtons()];
		for ( int iBinding = 0; iBinding < mouseBindings_buttonIdxToID.length; ++iBinding )
			mouseBindings_buttonIdxToID[iBinding] = -1;

		buttons = new ButtonState[numberOfButtons];
		for ( int iButton = 0; iButton < numberOfButtons; ++iButton )
			buttons[iButton] = new ButtonState();
		
		buttons_changed = new ArrayList<>();
		
		pos_mouseCursor = new Point();
		
		isMouseCursorMoved = false;
		
		//private �ʵ� �ʱ�ȭ �κ�
		buttonInputQueue = new ButtonState[length_buttonInputQueue];
		for ( int iQueue = 0; iQueue < length_buttonInputQueue; ++iQueue )
			buttonInputQueue[iQueue] = new ButtonState();
		
		lock_buttonInputQueue = new Object();
		
		pos_lastMouseCursor = new Point();
		
		canvas.addKeyListener(listener_key);
		canvas.addMouseListener(listener_mouse_click);
		canvas.addMouseMotionListener(listener_mouse_move);
	}

	/**
	 * GameLoop.Update()�� ���� �κп��� �� �� �� ȣ������ν�<br>
	 * ���� frame ���� ���� Ű���� / ���콺 �Է��� �� ��ư�� �ݿ��մϴ�.<br>
	 * <br>
	 * ����:<br>
	 * GameLoop.Update()�� �ƴ� �ٸ� ������ �� �޼��带 ȣ���ϰų�<br>
	 * GameLoop.Update() �ȿ��� �� �޼��带 �� �� �̻� ȣ������ �ʵ��� �����ؾ� �մϴ�.
	 */
	public void AcceptInputs()
	{
		//���� �� ���� ���� ���� �ʵ�� �ʱ�ȭ
		for ( ButtonState state : buttons )
		{
			state.isChanged = false;
			state.isAcceptedInThisFrame = false;
		}
		
		buttons_changed.clear();
		
		isMouseCursorMoved = false;

		
		//��ư �Է� �ݿ� ���� - ���� �ð� �������� ������ �Է��� ������� üũ�� ��, �� �� ���� �ð� ���� ���콺 Ŀ�� ��ġ�� ����� �� 
		int idx_buttonInputQueue_last;
		Point pos_lastMouseCursor_fixed = new Point();
		
		synchronized ( lock_buttonInputQueue )
		{
			idx_buttonInputQueue_last = idx_buttonInputQueue_end;
			pos_lastMouseCursor_fixed.x = pos_lastMouseCursor.x;
			pos_lastMouseCursor_fixed.y = pos_lastMouseCursor.y;
		}
		
		/*
		 * ��ư �Է� �ݿ�:
		 * ť�� ���� ��ġ�� �� ��ġ�� ���� ��(ť�� ���̰� 0�� �� ��)���� �ϳ� �ϳ� �̾Ƽ� ����.
		 * ��, ���� ��ư�� ���� ���� �Է��� ���� ��� ���� �������� ���� �͸� ����.
		 * (���� ����� ���� ��ǻ� queue�� stackó�� �ٷ�� ������ ������� ���鿡���� queue �����̹Ƿ� �׳� ť ��� �θ�)
		 */
		while ( idx_buttonInputQueue_start != idx_buttonInputQueue_last )
		{
			if ( idx_buttonInputQueue_last == 0 )
				idx_buttonInputQueue_last = length_buttonInputQueue - 1;
			else
				--idx_buttonInputQueue_last;

			//��ȭ�� �ݿ��� ��ư ã�� - �׻� ����
			ButtonState changes = buttonInputQueue[idx_buttonInputQueue_last];
			ButtonState relatedButton = buttons[changes.ID];
			
			//�ش� ��ư�� �̹� ��ȭ�� �ݿ��� ��쿡�� ��ŵ(������ �������� ����), �׷��� ���� ��� ��ȭ ����
			if ( relatedButton.isAcceptedInThisFrame == false )
			{
				//���� ��ư�� ���� ���°� �ٲ� ��� �̸� ���� �� ǥ��, '���°� �ٲ� ��ư ���'�� �߰�
				if ( relatedButton.isPressed != changes.isPressed )
				{
					relatedButton.isPressed = changes.isPressed;
					relatedButton.isChanged = true;
					
					buttons_changed.add(relatedButton);
				}
				
				//�׸��� �� ��ư�� '��ȭ�� �ݿ��Ǿ���'���� ǥ���Ͽ� �ٸ�(�� ������) ������ ������ ����
				relatedButton.isAcceptedInThisFrame = true;
			}
		}
		
		//���콺 Ŀ�� �̵� �ݿ�
		if ( pos_mouseCursor.equals(pos_lastMouseCursor_fixed) == false )
		{
			pos_mouseCursor = pos_lastMouseCursor_fixed;
			isMouseCursorMoved = true;
		}
	}

	/**
	 * �ش� Ű�� �־��� ��ư�� bind�մϴ�.<br>
	 * �̹� �ش� Ű�� bind�Ǿ� �ְų� �̹� �־��� ��ư�� bind�Ǿ� �ִ� ��� �����մϴ�.<br>
	 * ���� ���θ� return�մϴ�.
	 * 
	 * @param keyCode
	 * 			bind�� Ű�� ���� �ڵ� ���Դϴ�.<br>
	 * 			<code>KeyEvent.</code>�� �Է��ϸ� ��Ͽ� �ߴ� <code>VK_LEFT</code>�� ���� ������ ����ϸ� �˴ϴ�. 
	 * @param buttonID
	 * 			bind�� ��ư�� �Ϸ� ��ȣ�Դϴ�.
	 */
	public boolean BindKey(int keyCode, int buttonID)
	{
		//�̹� �ش� Ű�� bind�Ǿ� �ִٸ� ����
		for ( int code : keyBindings_IDtoKeyCode )
			if ( code == keyCode )
				return false;
		
		//�̹� �ش� ��ư�� bind�Ǿ� �ִٸ� ����
		if ( buttons[buttonID].ID != -1 )
			return false;
		
		//bind ����
		keyBindings_IDtoKeyCode[buttonID] = keyCode;
		buttons[buttonID].ID = buttonID;
		
		return true;
	}
	
	/**
	 * �ش� ���콺_��ư�� �־��� ��ư�� bind�մϴ�.<br>
	 * �̹� �ش� ���콺_��ư�� bind�Ǿ� �ְų� �̹� �־��� ��ư�� bind�Ǿ� �ִ� ��� �����մϴ�.<br>
	 * ���� ���θ� return�մϴ�.
	 * 
	 * @param mouseButtonNumber
	 * 			bind�� ���콺_��ư�� ���� ��ȣ�Դϴ�.<br>
	 * 			<code>MouseEvent.</code>�� �Է��ϸ� ��Ͽ� �ߴ� <code>BUTTON1</code>�� ���� ������ ����ϸ� �˴ϴ�. 
	 * @param buttonID
	 * 			bind�� ��ư�� �Ϸ� ��ȣ�Դϴ�.
	 */
	public boolean BindMouseButton(int mouseButtonNumber, int buttonID)
	{
		//�̹� �ش� Ű�� bind�Ǿ� �ִٸ� ����
		if ( mouseBindings_buttonIdxToID[mouseButtonNumber] != -1 )
			return false;
		
		//�̹� �ش� ��ư�� bind�Ǿ� �ִٸ� ����
		if ( buttons[buttonID].ID != -1 )
			return false;
		
		//bind ����
		mouseBindings_buttonIdxToID[mouseButtonNumber] = buttonID;
		buttons[buttonID].ID = buttonID;
		
		return true;
	}
	
	/**
	 * �ش� ��ư�� ���� bind�� �����մϴ�.
	 * 
	 * @param buttonID
	 */
	public void Unbind(int buttonID)
	{
		//�ش� ��ư�� ���콺_��ư�� bind�Ǿ� �ִٸ� ����
		for ( int iMouseButton = 0; iMouseButton < mouseBindings_buttonIdxToID.length; ++iMouseButton )
			if ( mouseBindings_buttonIdxToID[iMouseButton] == buttonID )
			{
				mouseBindings_buttonIdxToID[iMouseButton] = -1;
				buttons[buttonID].ID = -1;
				return;
			}
		
		//�ش� ��ư�� Ư�� Ű�� bind�Ǿ� �ִٸ� ����
		if ( keyBindings_IDtoKeyCode[buttonID] != -1 )
		{
			keyBindings_IDtoKeyCode[buttonID] = -1;
			buttons[buttonID].ID = -1;
		}
	}
		
	
	/* -----------------------------------------------
	 * 
	 * �������� ���� �� private ��ҵ��� �ִ� �κ�
	 * 
	 */
	
	/**
	 * Ű���� �Է��� ������ �����Ƿ�<br>
	 * ������ �� ��ư ����ŭ �迭�� ��� �� ����<br>
	 * �� ��ư�� ID�� index�� ��� bind�� keyCode�� �迭 �ȿ� ���<br>
	 * --> ��ϵ� ���� -1�̸� �ش� ��ư�� Ű���忡 ���ε��� �ʾ����� �ǹ� 
	 */
	private int[] keyBindings_IDtoKeyCode;
	
	/**
	 * ���콺 �Է��� ������ �����Ƿ�<br>
	 * ���콺�� �޸� �� ��ư(���� ������ ���� ��ư�� �ƴ� ���� ���콺_��ư) ����ŭ �迭�� ��� �� ����<br>
	 * �� ���콺_��ư�� bind�� ��ư�� ID�� �迭 �ȿ� ���<br>
	 * --> ��ϵ� ���� -1�̸� �ش� ���콺_��ư�� � ��ư���� ���ε��� �ʾ����� �ǹ�
	 */
	private int[] mouseBindings_buttonIdxToID;

	/**
	 * �Ʒ��� �ִ� ť�� ����<br>
	 * --> ����� ��� ���� ������ ��ĥ �� ����<br>
	 * --> 10FPS ȯ���̶� �ϴ��� 1/10�� �ȿ� 256�� Ű �Է��� �ϴ� ���� ���������� ������״� ������ ��
	 */
	private static final int length_buttonInputQueue = 256;
	
	/**
	 * ���� AcceptInputs()�� ȣ���ϱ� ������ ������ ��ư �Էµ��� ��� �α� ���� ť
	 */
	private ButtonState[] buttonInputQueue;

	/**
	 * ť�� ���� ��ġ - ���� AcceptInputs()�� ȣ��� ���� ���� ���� ù ��ư �Է��� ��ġ
	 */
	private int idx_buttonInputQueue_start = 0;
	
	/**
	 * ť�� �� ��ġ - ���� AcceptInputs()�� ȣ��� ���� ���� ���� ������ ��ư �Է��� �ٷ� ���� ĭ ��ġ<br>
	 * <br>
	 * --> ���� ��ġ�� �� ��ġ�� ���ٸ� ť�� ��� ������ �ǹ�<br>
	 * --> ��� ť�� �ܼ� �迭�� �����߱� ������ ť�� ���� �� ������ ���� ��ġ�� �� ��ġ�� ����������<br>
	 * --> �׷��Ƿ� ���� �� ���� ���� ������ �迭�� �� ��� ��� �� �ʿ䰡 ����
	 */
	private int idx_buttonInputQueue_end;
	
	/**
	 * ��Ƽ������ ȯ�濡�� enqueue �۾��� ���� �ʵ��� ���� ���� ������ ��ݿ� object
	 */
	private Object lock_buttonInputQueue;

	/**
	 * ���� AcceptInputs()�� ȣ��� ���� ���� ���������� Ȯ�ε� ���콺 Ŀ�� ��ġ
	 */
	private Point pos_lastMouseCursor;
	
	/**
	 * Ű���� �Է��� �޾� ó���ϱ� ���� �̺�Ʈ ������
	 */
	private KeyListener listener_key = new KeyListener()
	{
		@Override
		public void keyTyped(KeyEvent e) { }
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			ButtonState changes;
			int ID = -1;
			int keyCode = e.getKeyCode();
			
			//�ش� Ű�� ���ε� ��ư Ž��
			for ( int iKeyBinding = 0; iKeyBinding < keyBindings_IDtoKeyCode.length; ++iKeyBinding )
				if ( keyBindings_IDtoKeyCode[iKeyBinding] == keyCode )
				{
					ID = iKeyBinding;
					break;
				}
			
			//���ε� ��ư�� ���ٸ� �ش� Ű �Է��� ����
			if ( ID == -1 )
				return;
			
			//�Է� ť�� �������� ��ư �� ���� �߰�
			synchronized ( lock_buttonInputQueue )
			{
				changes = buttonInputQueue[idx_buttonInputQueue_end];
				changes.ID = ID;
				changes.isPressed = false;
				++idx_buttonInputQueue_end;
				idx_buttonInputQueue_end %= length_buttonInputQueue;
			}
			
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			ButtonState changes;
			int ID = -1;
			int keyCode = e.getKeyCode();
			
			//�ش� Ű�� ���ε� ��ư Ž��
			for ( int iKeyBinding = 0; iKeyBinding < keyBindings_IDtoKeyCode.length; ++iKeyBinding )
				if ( keyBindings_IDtoKeyCode[iKeyBinding] == keyCode )
				{
					ID = iKeyBinding;
					break;
				}
			
			//���ε� ��ư�� ���ٸ� �ش� Ű �Է��� ����
			if ( ID == -1 )
				return;
			
			//�Է� ť�� �������� ��ư �� ���� �߰�
			synchronized ( lock_buttonInputQueue )
			{
				changes = buttonInputQueue[idx_buttonInputQueue_end];
				changes.ID = ID;
				changes.isPressed = true;
				++idx_buttonInputQueue_end;
				idx_buttonInputQueue_end %= length_buttonInputQueue;
			}
		}
	};

	/**
	 * ���콺 Ŭ�� �Է��� �޾� ó���ϱ� ���� �̺�Ʈ ������
	 */
	private MouseListener listener_mouse_click = new MouseListener()
	{
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			ButtonState changes;
			int ID = -1;
			int buttonNumber = e.getButton();
			
			//�ش� ���콺_��ư�� ���ε� ��ư Ȯ��
			ID = mouseBindings_buttonIdxToID[buttonNumber];

			//���ε� ��ư�� ���ٸ� �ش� Ű �Է��� ����
			if ( ID == -1 )
				return;
			
			//�Է� ť�� �������� ��ư �� ���� �߰�
			synchronized ( lock_buttonInputQueue )
			{
				changes = buttonInputQueue[idx_buttonInputQueue_end];
				changes.ID = ID;
				changes.isPressed = false;
				++idx_buttonInputQueue_end;
				idx_buttonInputQueue_end %= length_buttonInputQueue;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			ButtonState changes;
			int ID = -1;
			int buttonNumber = e.getButton();
			
			//�ش� ���콺_��ư�� ���ε� ��ư Ȯ��
			ID = mouseBindings_buttonIdxToID[buttonNumber];

			//���ε� ��ư�� ���ٸ� �ش� Ű �Է��� ����
			if ( ID == -1 )
				return;
			
			//�Է� ť�� �������� ��ư ���� ���� �߰�
			synchronized ( lock_buttonInputQueue )
			{
				changes = buttonInputQueue[idx_buttonInputQueue_end];
				changes.ID = ID;
				changes.isPressed = true;
				++idx_buttonInputQueue_end;
				idx_buttonInputQueue_end %= length_buttonInputQueue;
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) { }
		
		@Override
		public void mouseEntered(MouseEvent e) { }
		
		@Override
		public void mouseClicked(MouseEvent e) { }
	};
	
	/**
	 * ���콺 �̵��� �޾� ó���ϱ� ���� �̺�Ʈ ������
	 */
	private MouseMotionListener listener_mouse_move = new MouseMotionListener()
	{
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			//���ο� ��ǥ�� ���
			pos_lastMouseCursor = e.getPoint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e)
		{
			//���ο� ��ǥ�� ���
			pos_lastMouseCursor = e.getPoint();			
		}
	};
}
