package loot;

/**
 * Initialize(), Update(), Draw() 메서드를 일정 시간마다 반복적으로 호출하기 위해 사용하는 클래스입니다.<br>
 * 
 * @author Racin
 * 
 */
public class GameLoop
{
	/**
	 * 시간 간격을 측정하고 각 메서드를 호출하기 위한 내부 스레드입니다.
	 * 
	 * @author Racin
	 * 
	 */
	private class LoopThread extends Thread
	{
		//실제 모드에서는 게임이 시작된 실제 시각, 가상 모드에서는 0
		long startTime_ns;
		
		//게임이 시작된 이후 지난 시간
		long tick_ns;
		
		//각 프레임 사이의 최소 시간 간격
		long interval_ns;
		
		/**
		 * 새로운 LoopThread class의 인스턴스를 생성합니다.
		 * 
		 * @param isVirtualTimingMode
		 *            true 인 경우 매 프레임이 수행 시간과 관계 없이 항상 interval_ms만큼 지연되며 timeStamp 또한 항상 interval_ms만큼 증가합니다.<br>
		 *            false 인 경우 매 프레임이 최소 interval_ms만큼 지연됨을 보장하며 timeStamp는 실제 시간에 기반하여 증가합니다.
		 * @param interval_ns
		 *            프레임 사이의 간격을 설정하는 나노초 단위의 값입니다.
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
		 * 내부 스레드의 진입점 메서드입니다.<br>
		 * 이 메서드는 프로그램이 종료되거나 GameLoop.Abort()를 호출할 때까지 실행됩니다.
		 */
		@Override
		public void run()
		{
			//Initialize() 호출, 실패한 경우 실행 중단
			if ( methods.Initialize() == false )
				return;

			boolean isDrawRequired = false;

			//Virtual timing mode
			if ( startTime_ns == 0 )
			{
				//FPS는 항상 고정되어 있으므로 먼저 계산
				fps = 1000000000.0 / interval_ns;
				
				//GameLoop.Abort()를 호출할때까지 무한 반복
				while ( isInterrupted() == false )
				{
					//Update() 호출, return값을 통해 Draw() 수행 여부 결정
					isDrawRequired = methods.Update(tick_ns / 1000000);

					//선택적 Draw() 호출
					if ( isDrawRequired )
						methods.Draw(tick_ns / 1000000);
					
					try
					{
						//다음 프레임이 시작될 때까지 대기(무조건 interval만큼 대기)
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
								
				//FPS 계산 등을 위해 지난 60프레임의 시작 시각을 기록 -> 평균을 계산하여 FPS 산출
				long[] loop_startTimes = new long[60];
				int idx_loop_startTimes = 0;

				for ( int idx = 0; idx < 60; ++idx )
					loop_startTimes[idx] = startTime_ns - ( 60 - idx ) * interval_ns;
				
				//GameLoop.Abort()를 호출할때까지 무한 반복
				while ( isInterrupted() == false )
				{
					loop_startTime_ns = System.nanoTime();
					tick_ns = loop_startTime_ns - startTime_ns;

					//FPS 갱신
					fps = 1000000000.0 / ( loop_startTime_ns - loop_startTimes[idx_loop_startTimes] ) * 60;
					
					loop_startTimes[idx_loop_startTimes] = loop_startTime_ns;
					++idx_loop_startTimes;
					idx_loop_startTimes %= 60;

					//Update() 호출, return값을 통해 Draw() 수행 여부 결정
					isDrawRequired = methods.Update(tick_ns / 1000000);

					//선택적 Draw() 호출
					if ( isDrawRequired )
					{
						tick_ns = System.nanoTime() - startTime_ns;
						
						methods.Draw(tick_ns / 1000000);
					}
					
					// 10ms 이상 쉬어야 한다면 잠시 잠듦
					while ( loop_startTime_ns + interval_ns - System.nanoTime() > 10000000 )
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
						}
					
					// 남은 시간동안 대기
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
	 * 새로운 게임 루프 인스턴스를 생성합니다.<br>
	 * 
	 * @param isVirtualTimingMode
	 *            true 인 경우 매 프레임이 수행 시간과 관계 없이 항상 interval_ms만큼 지연되며 timeStamp 또한 항상 interval_ms만큼 증가합니다.<br>
	 *            false 인 경우 매 프레임이 최소 interval_ms만큼 지연됨을 보장하며 timeStamp는 실제 시간에 기반하여 증가합니다.
	 * @param interval_ns
	 *            프레임 사이의 간격을 설정하는 나노초 단위의 값입니다.
	 * @param methods
	 * 			  내부 스레드가 호출할 세 메서드를 구현한 인스턴스를 여기에 지정합니다.
	 */
	public GameLoop(boolean isVirtualTimingMode, long interval_ns, IGameLoopMethods methods)
	{
		thr = new LoopThread(isVirtualTimingMode, interval_ns);
		this.methods = methods;
	}

	/**
	 * 게임 루프를 시작합니다.
	 */
	public void Start()
	{
		thr.start();
	}

	/**
	 * 게임 루프의 실행을 중단합니다.<br>
	 * 대부분의 프로젝트 시나리오에서 이 메서드는 크게 신경쓰지 않아도 됩니다.
	 */
	public void Abort()
	{
		thr.interrupt();
	}

	/**
	 * 현재 게임 루프가 초당 몇 프레임을 소화하고 있는지 return합니다.
	 */
	public double GetFPS()
	{
		return fps;
	}
}
