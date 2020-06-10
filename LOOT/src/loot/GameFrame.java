package loot;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

import javax.swing.JFrame;

/**
 * LOOT ���̺귯���� �� ��Ҹ� ����ϸ� â ��ü�� ���� ȭ������ ��� �����ϴ�<br>
 * ���� â �ϳ��� ��Ÿ���� Ŭ�����Դϴ�.<br>
 * <br>
 * �� Ŭ������ ����ϱ� ���� GameFrame.java ������ ���� �� Ŭ������ ��� �ִ� �� �ʵ� / �޼��带 �� �� ���캸����.
 * 
 * @author Racin
 *
 */
@SuppressWarnings("serial")
public abstract class GameFrame extends JFrame implements IGameLoopMethods
{
	
	/* ----------------------------------------------
	 * 
	 * ���� â�� �����ϴ� �ʵ� ���� �κ�
	 * 
	 */
	
	/**
	 * ���� â�� �����ϱ� ���� ���� ������ ��� �ֽ��ϴ�.<br>
	 * �ڼ��� ������ GameFrameSettings.java ������ �����ϼ���.
	 */
	protected GameFrameSettings settings;
	
	/**
	 * �������� ���� ȭ���� �׸��� ���� ���˴ϴ�.<br>
	 * �Ƹ��� �������� �� �ʵ带 ���� ����� ���� ���� ���Դϴ�.
	 */
	protected Canvas canvas;
	
	/**
	 * �ٷ�� ���� Ű���� / ���콺 �Է��� ���� ������ ����ϴ� ���� ��ư���� �����ϰ�<br>
	 * �� ��ư�� ���� ���¸� ���� �� ������ �ݴϴ�.<br>
	 * �ڼ��� ������ <code>input.</code>�� �Է��ϸ� ������ �� �ʵ� / �޼��� ������ �����ϼ���.
	 */
	protected InputManager inputs;

	/**
	 * ���� ȭ���� �׸��� ���� �ʿ��� Image���� �׸� ���Ͽ��� �̸� �о�� �ս��� ����� �� �ְ� �� �ݴϴ�.<br>
	 * <br>
	 * �ڼ��� ������ <code>images.</code>�� �Է��ϸ� ������ �� �ʵ� / �޼��� ������ �����ϼ���.
	 */
	protected ImageResourceManager images;
	
	/**
	 * ���� ������ ����� ���� ȿ������ �Ҹ� ���Ͽ��� �̸� �о� ���� ����� �ݴϴ�.<br>
	 * <br>
	 * �ڼ��� ������ <code>audios.</code>�� �Է��ϸ� ������ �� �ʵ� / �޼��� ������ �����ϼ���.
	 */
	protected AudioManager audios;
	
	/**
	 * ������ ���� �ð� �������� �����Ű�� ���� ���˴ϴ�.<br>
	 * �Ƹ��� �������� �� �ʵ带 ���� ����� ���� ���� ���Դϴ�.
	 */
	protected GameLoop loop;
	
	
	/* ----------------------------------------------
	 * 
	 * �������� ���� ȭ���� �׸� �� ����� �ʵ� / �޼����
	 * 
	 */
	
	/**
	 * <b>����:</b><br>
	 * �� �ʵ�� �������� Draw() �������� ���Ǿ�� �ϸ�<br>
	 * �� �� �ݵ�� Draw() ���� �κп��� BeginDraw()��,<br>
	 * �� �κٿ��� EndDraw()�� ȣ���� �־�� �մϴ�.<br>
	 * <br>
	 * ���� ȭ���� �׸� �� ����� '��'�� �Ǵ� ����Դϴ�.<br>
	 * BeginDraw()�� ȣ���ϸ� �� �ʵ忡 ������ �ν��Ͻ��� �Ҵ�Ǹ�<br>
	 * ���� <code>g.</code>�� �Է��Ͽ� �������� �׸��� �۾��� ������ �� �ֽ��ϴ�.<br>
	 * �Ǵ�, DrawObject() ���� ȣ���Ͽ� Ư�� ��Ҹ� �����ϰ� �׸� ���� �� �ʵ尡 ���˴ϴ�.
	 */
	protected Graphics2D g;
	
	/**
	 * ����: �� �޼���� �������� �ۼ��ϴ� Draw()�� ���� �κп��� �ݵ�� ȣ��Ǿ�� �մϴ�.<br>
	 * <br>
	 * ���� ȭ�� �׸��� �۾��� �����մϴ�.
	 */
	public void BeginDraw()
	{
		if ( g == null )
		{
			buf = canvas.getBufferStrategy();
			g = (Graphics2D)buf.getDrawGraphics();
			g.setFont(currentFont);
			g.setColor(currentColor);
		}
	}
	
	/**
	 * <b>����:</b><br>
	 * �� �޼���� �������� Draw() �������� ���Ǿ�� �ϸ�<br>
	 * �� �� �ݵ�� Draw() ���� �κп��� BeginDraw()��,<br>
	 * �� �κٿ��� EndDraw()�� ȣ���� �־�� �մϴ�.<br>
	 * <br>
	 * ��ü ȭ���� ����� �������� �ٽ� ĥ�մϴ�.<br>
	 * �������� �ۼ��ϴ� Draw()���� BeginDraw()�� ȣ���� ������ �ٷ� �� �޼��带 �����ϸ�<br>
	 * ������ �׷� �� ������ ��� ����� �� �׸��� �׸� �� �ְ� �˴ϴ�.<br>
	 * ���� ������ GameFrameSettings class �ȿ� �ִ� canvas_backgroundColor �ʵ带 ���� �� �� �ֽ��ϴ�.
	 */
	public void ClearScreen()
	{
		g.clearRect(0, 0, settings.canvas_width, settings.canvas_height);
	}

	/**
	 * <b>����:</b><br>
	 * �� �޼���� ������ ���۵Ǳ� ���� Initialize()���� �̸� ȣ��� �� �ֵ��� ����Ǿ�����<br>
	 * Draw()���� '���� ����� �� ����'�� �Ϸ��� ��� SetColor()�� ����ؾ� �մϴ�.<br>
	 * <br>
	 * ���� �����Ӻ��� �׸� �׸��⿡ ����� ���� �����մϴ�.<br>
	 * �� ���� <code>g.</code>�� �Է��ϸ� ������ draw...()�� fill...()���� ���˴ϴ�.<br>
	 * ��: drawRect()�� ȣ���ϸ� ���� ������ ������ �簢���� �׸� �� �ֽ��ϴ�.
	 * 
	 * @param newColor ������ ���Դϴ�. ������ <code>Color.</code>�� �Է��ϸ� ������ ���� �� �ϳ��� ����մϴ�.
	 */
	public void LoadColor(Color newColor)
	{
		currentColor = newColor;
	}
	
	/**
	 * <b>����:</b><br>
	 * �� �޼���� �������� Draw() �������� ���Ǿ�� �ϸ�<br>
	 * �� �� �ݵ�� Draw() ���� �κп��� BeginDraw()��,<br>
	 * �� �κٿ��� EndDraw()�� ȣ���� �־�� �մϴ�.<br>
	 * <br>
	 * �׸� �׸��⿡ ����� ���� �����մϴ�.<br>
	 * �� ���� <code>g.</code>�� �Է��ϸ� ������ draw...()�� fill...()���� ���˴ϴ�.<br>
	 * ��: drawRect()�� ȣ���ϸ� ���� ������ ������ �簢���� �׸� �� �ֽ��ϴ�.
	 * 
	 * @param newColor ������ ���Դϴ�. ������ <code>Color.</code>�� �Է��ϸ� ������ ���� �� �ϳ��� ����մϴ�.
	 */
	public void SetColor(Color newColor)
	{
		g.setColor(newColor);
		currentColor = newColor;
	}
	
	/**
	 * <b>����:</b><br>
	 * �� �޼���� ������ ���۵Ǳ� ���� Initialize()���� �̸� ȣ��� �� �ֵ��� ����Ǿ�����<br>
	 * Draw()���� '���� ����� ����ü ����'�� �Ϸ��� ��� SetFont()�� ����ؾ� �մϴ�.<br>
	 * <br>
	 * �־��� �̸��� ����ü�� ���� ������ ����� �� �ֵ��� �����ɴϴ�.<br>
	 * ������ ����ü�� '���� ����ü'�� �����Ǹ�<br>
	 * <code>g.drawString()</code> �Ǵ� this.DrawString()���� ���ڿ��� ����� �� ���˴ϴ�.<br>
	 * <br>
	 * �� �� ����ü�� ũ��� ��Ÿ���� ������ �� �ֽ��ϴ�.<br>
	 * ũ��� ��Ÿ���� �����Ϸ��� fontName�� "[�̸�] [��Ÿ��] [ũ��]"�� �����ϸ� �˴ϴ�.<br>
	 * ��: 14pt ���� ����ü�� ����Ϸ��� ��� fontName�� "����ü BOLD 14"�� ����<br>
	 * <br>
	 * ��Ÿ���� PLAIN, BOLD, ITALIC, BOLDITALIC �� �ϳ��� ������ �� ������ �����ϸ� PLAIN���� ���ֵ˴ϴ�.<br>
	 * ũ��� ��� ���� ������ �����Ӱ� ������ �� ������ �����ϸ� 12�� ���ֵ˴ϴ�.
	 * 
	 * @param fontName
	 * 			������ ����ü�� �̸��Դϴ�. �ʿ��� ��� ����ü�� ũ��� ��Ÿ���� ������ ���� �ֽ��ϴ�.
	 * @return ����ü�� ���������� �����Դ��� ���θ� return�մϴ�.
	 */
	public boolean LoadFont(String fontName)
	{
		//���� �ش� �̸��� ����ü�� ������ ���� ���ٸ� ���� �õ�
		if ( fonts.containsKey(fontName) == false )
		{
			Font newFont = Font.decode(fontName);
			
			//�ش� �̸��� ����ü�� ���ٸ� ����
			if ( newFont == null )
			{
				System.err.println("Error. �̸��� " + fontName + "�� ����ü�� �������� �ʽ��ϴ�.");
				return false;
			}
			
			//�ε��� ����ü�� ��Ͽ� �߰�
			fonts.put(fontName, newFont);
		}
		
		//������ ����ü�� ���� ����ü�� ����
		currentFont = fonts.get(fontName);
		return true;
	}
	
	/**
	 * <b>����:</b><br>
	 * �� �޼���� �������� Draw() �������� ���Ǿ�� �ϸ�<br>
	 * �� �� �ݵ�� Draw() ���� �κп��� BeginDraw()��,<br>
	 * �� �κٿ��� EndDraw()�� ȣ���� �־�� �մϴ�.<br>
	 * <br>
	 * ���� ����� ����ü�� �־��� �̸��� ����ü�� �����մϴ�.<br>
	 * ���� �ش� �̸��� ����ü�� ���� �غ���� �ʾҴٸ� �����ϱ� ���� ���� �������� �۾��� �����մϴ�.<br>
	 * �� �۾��� ���� �ɸ� �� �����Ƿ�(���� ������ '��'�� ������ �� �����Ƿ�<br>
	 * �ʿ��� ����ü�� �̸� LoadFont()�� ����Ͽ� ������ �δ� ���� ���ڽ��ϴ�.<br>
	 * �� �� fontName�� LoadFont()���� �����ߴ� �̸��� �����ؾ� �մϴ�.<br>
	 * �򰥸��� �ʵ��� <code>static final String font_dotUmChe = "����ü"</code>�� ����<br>
	 * ������ �ʵ带 ����� ����ϸ� ���ڽ��ϴ�.<br>  
	 * <br>
	 * fontName�� ���� ����ü�� ũ��� ��Ÿ���� ������ �� �ֽ��ϴ�.<br>
	 * ũ��� ��Ÿ���� �����Ϸ��� fontName�� "[�̸�] [��Ÿ��] [ũ��]"�� �����ϸ� �˴ϴ�.<br>
	 * ��: 14pt ���� ����ü�� ����Ϸ��� ��� fontName�� "����ü BOLD 14"�� ����<br>
	 * <br>
	 * ��Ÿ���� PLAIN, BOLD, ITALIC, BOLDITALIC �� �ϳ��� ������ �� ������ �����ϸ� PLAIN���� ���ֵ˴ϴ�.<br>
	 * ũ��� ��� ���� ������ �����Ӱ� ������ �� ������ �����ϸ� 12�� ���ֵ˴ϴ�.
	 * 
	 * @param fontName
	 * 		������ ����ü�� �̸��Դϴ�. �� ���� LoadFont()�� ���� ������ ���� �����ؾ� �մϴ�.
	 */
	public boolean SetFont(String fontName)
	{
		//�ش� �̸��� ����ü�� �̹� �����԰ų� ���� ������ �� �ִٸ� ���� ����ü�� ����
		if ( LoadFont(fontName) == true )
		{
			g.setFont(currentFont);
			return true;
		}
		
		//�ش� �̸��� ����ü�� ���ٸ� ����
		return false;
	}
	
	/**
	 * <b>����:</b><br>
	 * �� �޼���� �������� Draw() �������� ���Ǿ�� �ϸ�<br>
	 * �� �� �ݵ�� Draw() ���� �κп��� BeginDraw()��,<br>
	 * �� �κٿ��� EndDraw()�� ȣ���� �־�� �մϴ�.<br>
	 * <br>
	 * �־��� ��Ŀ� ���� ������ ���ڿ��� ���� ȭ���� �ش� ��ġ�� �׸��ϴ�.<br>
	 * ������ ���ڿ���<br>
	 * LoadColor() �Ǵ� SetColor()�� ���� ������ ����<br>
	 * LoadFont() �Ǵ� SetFont()�� ���� ������ ����ü�� ����Ͽ� �׷����ϴ�.<br>
	 * 
	 * @param left ���ڿ��� �׸��� ������ ��ġ(ù ������ ���� �𼭸�)�� ��Ÿ���� x��ǥ�Դϴ�.
	 * @param bottom ���ڿ��� �׸��� ������ ��ġ(ù ������ �Ʒ� �𼭸�)�� ��Ÿ���� y��ǥ�Դϴ�.
	 * @param format ���ڿ��� ������ ����Դϴ�. ������ <code>printf()</code>�� �����ϴ�. ��, \n�� ����� �� �����ϴ�.
	 * @param args ��Ŀ� ���� ���ڿ��� ���Ե� ��ҵ��� ����Դϴ�. ������ <code>printf()</code>�� �����ϴ�.
	 */
	public void DrawString(int left, int bottom, String format, Object... args)
	{
		g.drawString(String.format(format, args), left, bottom);
	}

	/**
	 * ����: �� �޼���� �������� �ۼ��ϴ� Draw()�� �� �κп��� �ݵ�� ȣ��Ǿ�� �մϴ�.<br>
	 * <br>
	 * ���� ȭ�� �׸��� �۾��� �����ϰ� ȭ���� �����մϴ�.
	 */
	public void EndDraw()
	{
		if ( g != null )
		{
			g.clipRect(0, 0, settings.canvas_width, settings.canvas_height);
			buf.show();
			g.dispose();
			g = null;
		}
	}
	
	/* ----------------------------------------------
	 * 
	 * �������� ���� �� ��ҵ��� �ִ� �κ�
	 * 
	 */
	
	/**
	 * ���ο� GameFrame class�� �ν��Ͻ��� �����մϴ�.
	 * 
	 * @param settings
	 * 			  ���� â�� �����ϱ� ���� ���� ������ ��� �ֽ��ϴ�.<br>
	 * 			  �ڼ��� ������ GameFrameSettings.java ������ �����ϼ���.
	 */
	public GameFrame(GameFrameSettings settings)
	{
		this.settings = new GameFrameSettings(settings);
		
		//Frame �⺻ ����
		setTitle(this.settings.window_title);		//â ������ �־��� ���ڿ��� ���� 
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//â�� ������ ���α׷� ��ü�� ����ǵ��� ����
		setLocationByPlatform(true);				//â�� �ʱ� ��ġ�� OS�� �˾Ƽ� ���� �ֵ��� ����
		setResizable(false);						//â�� ũ�⸦ ����ڰ� ���� �ٲ� �� ������ ����
		setIgnoreRepaint(true);						//â�� '�ڵ� �ٽ� �׸���' �ɼ��� ���� -> �׻� Draw()�� ���� �������� �ٽ� �׸�
		
		//Canvas(���� ȭ��) ����
		canvas = new Canvas();
		canvas.setSize(settings.canvas_width, settings.canvas_height);	//���� ȭ���� ũ�⸦ �־��� ������ ����
		canvas.setBackground(settings.canvas_backgroundColor);			//���� ȭ���� ������ �־��� ������ ����
		add(canvas);													//���� ȭ���� â�� �߰�
		pack();															//â�� ũ�⸦ ���� ȭ�� ũ�⿡ �°� ����
		canvas.createBufferStrategy(2);									//���� ȭ���� ���� ���۸��� �����ϵ��� ����

		//��Ÿ ��� ����
		
		inputs = new InputManager(canvas, settings.numberOfButtons);
		setFocusable(false);			//â�� ���� �Է��� ���� ���� ������ ����
		canvas.setFocusable(true);		//(â ���) ���� ȭ���� ���� �Է��� �޵��� ����

		images = new ImageResourceManager();
		audios = new AudioManager();
		
		loop = new GameLoop(settings.gameLoop_use_virtualTimingMode, settings.gameLoop_interval_ns, this);
		fonts = new HashMap<>();

		//Frame �̺�Ʈ ���ű� ����
		addWindowListener(listener_window_activated);	//â�� ó�� ������ �� �ٷ� ������ ����ǵ��� ������ ���ű⸦ â�� ����(listener_window_activated�� ���� �ϴܿ� ����)
	}
	
	/**
	 * ���� ȭ���� ���� ���۸� ����� ����ϱ� ���� �ʿ��� �ʵ��Դϴ�.
	 */
	private BufferStrategy buf;

	/**
	 * ���� ������ ���Դϴ�.<br>
	 * BeginDraw()�� ȣ���� �� ���� ������ ���� �ڵ����� '���� �׸� ��'���� �����˴ϴ�.<br>
	 * �� �ʵ��� ���� �ٲٷ��� LoadColor() �Ǵ� SetColor()�� ����ϼ���.
	 */
	private Color currentColor;
	
	/**
	 * ���� ������ ����ü�Դϴ�.<br>
	 * BeginDraw()�� ȣ���� �� ���� ������ ����ü�� �ڵ����� '���� ����� ����ü'�� �����˴ϴ�.<br>
	 * �� �ʵ��� ���� �ٲٷ��� LoadFont() �Ǵ� SetFont()�� ����ϼ���.
	 */
	private Font currentFont;
	
	/**
	 * ������� ������ ����ü ����Դϴ�.<br>
	 * ����ü �������� �۾��� �ð��� �� ���� �ɸ��Ƿ�<br>
	 * �� �� ������ ����ü�� ���⿡ ��� �� ���� ��� �����ϰ� �˴ϴ�.
	 */
	private HashMap<String, Font> fonts;
	
	/**
	 * â�� ó�� ������ �� �ٷ� ������ ����ǵ��� �����ϱ� ���� �̺�Ʈ ���ű��Դϴ�.<br>
	 * �ʵ� ������ ���ڱ� �߰�ȣ�� ���� �� �ȿ� �޼��带 �����ϰ� ������(�͸� Ŭ���� ����)<br>
	 * ũ�� �Ű澲���� �ʾƵ� �����ϴ�.
	 */
	private WindowListener listener_window_activated = new WindowListener()
	{
		boolean isFirstActivation = false;
		
		public void windowOpened(WindowEvent e) { }
		public void windowIconified(WindowEvent e) { }
		public void windowDeiconified(WindowEvent e) { }
		public void windowDeactivated(WindowEvent e) { }
		public void windowClosing(WindowEvent e) { }
		public void windowClosed(WindowEvent e) { }
		
		/**
		 * â�� Ȱ��ȭ�Ǿ��� �� ����Ǵ� �޼����Դϴ�.<br>
		 * 'Ȱ��ȭ'�� ó�� ���Ȱų� Alt+Tab ���� ���� �ٽ� ���� ȭ�� ���� �ö���� �� �߻��մϴ�.<br>
		 * ������ ���� ũ�� �Ű澲�� �ʾƵ� �����ϴ�.<br>
		 * <br>
		 * ���� ������ �� õõ�� �ϰ� ���� ��쿡��<br>
		 * �� �޼��带 �ǵ���� ���� ���� ������ �̷�⺸�ٴ� '���� ��ư'�� ����� ��ġ�ϴ� ���� ����� ������.
		 */
		@Override
		public void windowActivated(WindowEvent e)
		{
			//���� �̹��� ù Ȱ��ȭ���
			if ( isFirstActivation == false )
			{
				//�̹� ù Ȱ��ȭ�� �Ǿ��ٰ� ǥ���ϰ� ���� ���� ����
				isFirstActivation = true;
				loop.Start();
			}
		}
	};
}
