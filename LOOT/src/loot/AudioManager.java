package loot;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * ���α׷����� ����� �������� �о���� ���� / ������ �� �ִ� Ŭ�����Դϴ�.<br>
 * <br>
 * <b>����:</b><br>
 * �� Ŭ������ ��� �Ҹ� ���� ������ �������� ������<br>
 * ������ ���� �����̶� ���� ������ ���� ���� ���ΰ� �޶����⵵ �մϴ�.<br>
 * �������� ���� ������ ����� ��ϵ��� �ʴ� ���<br>
 * �ݵ�� �������� ������ ��û�ϼ���.
 * 
 * @author Racin
 *
 */
public class AudioManager
{
	HashMap<String, Clip[]> clips;
	
	public AudioManager()
	{
		clips = new HashMap<>();
	}
	
	/**
	 * �ش� ���Ͽ��� ������ �о�� �־��� �̸����� ����մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * AudioManager class�� ��� �Ҹ� ���� ������ �������� ������<br>
	 * ������ ���� �����̶� ���� ������ ���� ���� ���ΰ� �޶����⵵ �մϴ�.<br>
	 * �������� ���� ������ ����� ��ϵ��� �ʴ� ���<br>
	 * �ݵ�� �������� ������ ��û�ϼ���.
	 * 
	 * @param fileName ������ �о�� ���� �̸��Դϴ�.
	 * @param clipName ���α׷� ������ ����ϱ� ���� �ο��ϴ� �� ������ �̸��Դϴ�.
	 * @param numberOfChannels �ش� ������ ä�� ��('���ÿ�' �󸶳� ���� ����� �� �ִ���)�� �����մϴ�. 
	 * @return ���������� ����� ��� true�� return�մϴ�.
	 */
	public boolean LoadAudio(String fileName, String clipName, int numberOfChannels)
	{
		try
		{
			//�̹� �ش� �̸��� ������ ��ϵǾ� �ִ� ��� ����
			if ( clips.containsKey(clipName) == true )
			{
				return false;
			}
			
			File f = new File(fileName);
			
			//�ش� �̸��� ������ ���� ��� ����
			if ( f.exists() == false )
			{
				return false;
			}
			
			Clip[] channels = new Clip[numberOfChannels];
			
			for ( int iChannel = 0; iChannel < numberOfChannels; ++iChannel )
			{
				Clip clip = AudioSystem.getClip();
				AudioInputStream as = AudioSystem.getAudioInputStream(f);
				clip.open(as);
				channels[iChannel] = clip;
			}
			
			clips.put(clipName, channels);
		}
		catch ( Exception e )
		{
			//������ �о���� �� �� ��� ����
			e.printStackTrace();
			return false;			
		}
		
		return true;
	}
	
	
	/**
	 * �ش� �̸����� ��ϵ� ������ ����մϴ�.<br>
	 * �ش� ������ ������ �ִ� ä���� ��� ������� ��� ���� ������ ä���� ���߰� �ٽ� ����� �����մϴ�.
	 * 
	 * @param clipName ����� �� �ο��� ������ �̸��Դϴ�.
	 */
	public void Play(String clipName)
	{
		//�ش� �̸����� ��ϵ� ������ ���� ��� ����
		if ( clips.containsKey(clipName) == false )
			return;
		
		Clip[] channels = clips.get(clipName);
		
		//����ϰ� ���� ���� ä���� �ִ� ��� ���
		for ( Clip clip : channels )
		{
			if ( clip.isRunning() == false )
			{
				clip.setFramePosition(0);
				clip.start();
				return;
			}
		}
		
		//��� ä���� ������� ��� ���� ������ ä���� ���߰� �ٽ� ��� ����
		int max_framePosition = -1;
		Clip clip_max_framePosition = null;
		
		for ( Clip clip : channels )
		{
			if ( clip.getFramePosition() > max_framePosition )
			{
				clip_max_framePosition = clip;
				max_framePosition = clip.getFramePosition();
			}
		}
		
		clip_max_framePosition.stop();
		clip_max_framePosition.flush();
		clip_max_framePosition.setFramePosition(0);
		clip_max_framePosition.start();
	}
	
	/**
	 * �ش� �̸����� ��ϵ� ������ �ݺ� ����մϴ�.<br>
	 * �ش� ������ ������ �ִ� ä���� ��� ������� ��� ���� ������ ä���� ���߰� �ٽ� ����� �����մϴ�.
	 * 
	 * @param clipName ����� �� �ο��� ������ �̸��Դϴ�.
	 * @param count
	 * 			�ݺ��� Ƚ���� �����մϴ�.<br>
	 * 			�� ���� 1�̸� '�� �� �ݺ�'�� �����Ͽ� '�� �� ���'�ϰ� �˴ϴ�.<br>
	 * 			�� ���� ������ '���� �ݺ�'�� �����մϴ�.
	 */
	public void Loop(String clipName, int count)
	{
		//�ش� �̸����� ��ϵ� ������ ���� ��� ����
		if ( clips.containsKey(clipName) == false )
			return;
		
		//�ݺ� Ƚ���� ������ ��� '���� �ݺ�'���� ����
		if ( count < 0 )
			count = Clip.LOOP_CONTINUOUSLY;
		
		Clip[] channels = clips.get(clipName);
		
		//����ϰ� ���� ���� ä���� �ִ� ��� ���
		for ( Clip clip : channels )
		{
			if ( clip.isRunning() == false )
			{
				clip.setFramePosition(0);
				clip.loop(count);
				return;
			}
			
			clip.loop(0);
			
		}
		
		//��� ä���� ������� ��� ���� ������ ä���� ���߰� �ٽ� ��� ����
		int max_framePosition = -1;
		Clip clip_max_framePosition = null;
		
		for ( Clip clip : channels )
		{
			if ( clip.getFramePosition() > max_framePosition )
			{
				clip_max_framePosition = clip;
				max_framePosition = clip.getFramePosition();
			}
		}
		
		clip_max_framePosition.stop();
		clip_max_framePosition.flush();
		clip_max_framePosition.setFramePosition(0);
		clip_max_framePosition.loop(count);
	}
	
	/**
	 * �ش� �̸����� ��ϵ� ������ ����� ��� �ߴ��մϴ�.<br>
	 * �ش� ������ ���� ä���� ������ �ִ� ��� ��� ä���� ����� �ߴܵ˴ϴ�.
	 * 
	 * @param clipName ����� �� �ο��� ������ �̸��Դϴ�.
	 */
	public void Stop(String clipName)
	{
		//�ش� �̸����� ��ϵ� ������ ���� ��� ����
		if ( clips.containsKey(clipName) == false )
			return;
		
		Clip[] channels = clips.get(clipName);
		
		//��� ä�� ����
		for ( Clip clip : channels )
		{
			clip.stop();
			clip.flush();
		}
	}
	
	/**
	 * �ش� �̸����� ��ϵ� ������ ���̸� �����ɴϴ�. ������ ms�Դϴ�.
	 * 
	 * @param clipName ����� �� �ο��� ������ �̸��Դϴ�.
	 */
	public long GetLength(String clipName)
	{
		//�ش� �̸����� ��ϵ� ������ ���� ��� ����
		if ( clips.containsKey(clipName) == false )
			return -1;
		
		Clip[] channels = clips.get(clipName);
		
		return channels[0].getMicrosecondLength() / 1000L;
	}
}
