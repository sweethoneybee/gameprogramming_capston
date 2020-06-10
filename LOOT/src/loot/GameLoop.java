package loot;

/**
 * Initialize(), Update(), Draw() �޼��带 ���� �ð����� �ݺ������� ȣ���ϱ� ���� ����ϴ� Ŭ�����Դϴ�.<br>
 * 
 * @author Racin
 * 
 */
public class GameLoop
{
	/**
	 * �ð� ������ �����ϰ� �� �޼��带 ȣ���ϱ� ���� ���� �������Դϴ�.
	 * 
	 * @author Racin
	 * 
	 */
	private class LoopThread extends Thread
	{
		//���� ��忡���� ������ ���۵� ���� �ð�, ���� ��忡���� 0
		long startTime_ns;
		
		//������ ���۵� ���� ���� �ð�
		long tick_ns;
		
		//�� ������ ������ �ּ� �ð� ����
		long interval_ns;
		
		/**
		 * ���ο� LoopThread class�� �ν��Ͻ��� �����մϴ�.
		 * 
		 * @param isVirtualTimingMode
		 *            true �� ��� �� �������� ���� �ð��� ���� ���� �׻� interval_ms��ŭ �����Ǹ� timeStamp ���� �׻� interval_ms��ŭ �����մϴ�.<br>
		 *            false �� ��� �� �������� �ּ� interval_ms��ŭ �������� �����ϸ� timeStamp�� ���� �ð��� ����Ͽ� �����մϴ�.
		 * @param interval_ns
		 *            ������ ������ ������ �����ϴ� ������ ������ ���Դϴ�.
		 */
		public LoopThread(boolean isVirtualTimingMode, long interval_ns)
		{
			if ( isVirtualTimingMode )
				startTime_ns = 0;
			else
				startTime_ns = System.nanoTime();

			this.interval_ns = interval_ns;
			tick_ns = 0;
		}

		/**
		 * ���� �������� ������ �޼����Դϴ�.<br>
		 * �� �޼���� ���α׷��� ����ǰų� GameLoop.Abort()�� ȣ���� ������ ����˴ϴ�.
		 */
		@Override
		public void run()
		{
			//Initialize() ȣ��, ������ ��� ���� �ߴ�
			if ( methods.Initialize() == false )
				return;

			boolean isDrawRequired = false;

			//Virtual timing mode
			if ( startTime_ns == 0 )
			{
				//FPS�� �׻� �����Ǿ� �����Ƿ� ���� ���
				fps = 1000000000.0 / interval_ns;
				
				//GameLoop.Abort()�� ȣ���Ҷ����� ���� �ݺ�
				while ( isInterrupted() == false )
				{
					//Update() ȣ��, return���� ���� Draw() ���� ���� ����
					isDrawRequired = methods.Update(tick_ns / 1000000);

					//������ Draw() ȣ��
					if ( isDrawRequired )
						methods.Draw(tick_ns / 1000000);
					
					try
					{
						//���� �������� ���۵� ������ ���(������ interval��ŭ ���)
						Thread.sleep(interval_ns / 1000000);
					}
					catch (InterruptedException e)
					{
						break;
					}

					tick_ns += interval_ns;
				}
			}

			//Real timing mode
			else
			{
				long loop_startTime_ns;
								
				//FPS ��� ���� ���� ���� 60�������� ���� �ð��� ��� -> ����� ����Ͽ� FPS ����
				long[] loop_startTimes = new long[60];
				int idx_loop_startTimes = 0;

				for ( int idx = 0; idx < 60; ++idx )
					loop_startTimes[idx] = startTime_ns - ( 60 - idx ) * interval_ns;
				
				//GameLoop.Abort()�� ȣ���Ҷ����� ���� �ݺ�
				while ( isInterrupted() == false )
				{
					loop_startTime_ns = System.nanoTime();
					tick_ns = loop_startTime_ns - startTime_ns;

					//FPS ����
					fps = 1000000000.0 / ( loop_startTime_ns - loop_startTimes[idx_loop_startTimes] ) * 60;
					
					loop_startTimes[idx_loop_startTimes] = loop_startTime_ns;
					++idx_loop_startTimes;
					idx_loop_startTimes %= 60;

					//Update() ȣ��, return���� ���� Draw() ���� ���� ����
					isDrawRequired = methods.Update(tick_ns / 1000000);

					//������ Draw() ȣ��
					if ( isDrawRequired )
					{
						tick_ns = System.nanoTime() - startTime_ns;
						
						methods.Draw(tick_ns / 1000000);
					}
					
					// 10ms �̻� ����� �Ѵٸ� ��� ���
					while ( loop_startTime_ns + interval_ns - System.nanoTime() > 10000000 )
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
						}
					
					// ���� �ð����� ���
					while ( loop_startTime_ns + interval_ns - System.nanoTime() > 0 )
						;
				}
			}
		}
	}

	private LoopThread thr;
	private IGameLoopMethods methods;
	private double fps;

	/**
	 * ���ο� ���� ���� �ν��Ͻ��� �����մϴ�.<br>
	 * 
	 * @param isVirtualTimingMode
	 *            true �� ��� �� �������� ���� �ð��� ���� ���� �׻� interval_ms��ŭ �����Ǹ� timeStamp ���� �׻� interval_ms��ŭ �����մϴ�.<br>
	 *            false �� ��� �� �������� �ּ� interval_ms��ŭ �������� �����ϸ� timeStamp�� ���� �ð��� ����Ͽ� �����մϴ�.
	 * @param interval_ns
	 *            ������ ������ ������ �����ϴ� ������ ������ ���Դϴ�.
	 * @param methods
	 * 			  ���� �����尡 ȣ���� �� �޼��带 ������ �ν��Ͻ��� ���⿡ �����մϴ�.
	 */
	public GameLoop(boolean isVirtualTimingMode, long interval_ns, IGameLoopMethods methods)
	{
		thr = new LoopThread(isVirtualTimingMode, interval_ns);
		this.methods = methods;
	}

	/**
	 * ���� ������ �����մϴ�.
	 */
	public void Start()
	{
		thr.start();
	}

	/**
	 * ���� ������ ������ �ߴ��մϴ�.<br>
	 * ��κ��� ������Ʈ �ó��������� �� �޼���� ũ�� �Ű澲�� �ʾƵ� �˴ϴ�.
	 */
	public void Abort()
	{
		thr.interrupt();
	}

	/**
	 * ���� ���� ������ �ʴ� �� �������� ��ȭ�ϰ� �ִ��� return�մϴ�.
	 */
	public double GetFPS()
	{
		return fps;
	}
}
