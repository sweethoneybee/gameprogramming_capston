package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;

/**
 * 2���� ���� ��ǥ�踦 ����� �̸� ������ ������ ���� ȸ����Ų ���� ���� ��ҵ��� �׸��� Ŭ�����Դϴ�.<br>
 * <br>
 * <b>����:</b><br>
 * Layer �� 2���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ�������� ���� ����� ���� ��ġ�� �ڽ��� ���� ���� �ִ��� �˻����� �ʽ��ϴ�.<br>
 * ��κ��� ��Ȳ���� �� ������ ū ������ ���� �ʰ�����<br>
 * ���� ���ɻ��� ������ '������ �ʴ� ���'�� �׸��� �ʵ��� �����ؾ� �Ѵٸ�<br>
 * children ��Ͽ� �ִ� �� ��Ҹ� �������� üũ�Ͽ�<br>
 * ������ ��� (�׸��� �� �̻� ������ ������ ����) ����� trigger_remove �ʵ带 true�� ���������ν�<br>
 * Layer�� �̹� �����Ӻ��� �ش� ��Ҹ� �׸��� �ʵ��� ���� �� �ֽ��ϴ�.
 * 
 * @author Racin
 *
 */
public class RotatableLayer extends Layer
{	
	/**
	 * ȸ���� ����(������ radian)�Դϴ�.<br>
	 * �� ���� ����� ȸ���� �ð� �������� �Ͼ�ϴ�.<br>
	 * ����: ���� ���� degree <-> radian�� ��ȯ�� ����<br>
	 * RotatableLayer.GetDegreeFromRadian() �Ǵ� RotatableLayer.GetRadianFromDegree()�� ����ϼ���.
	 */
	public double angle;
	
	/**
	 * ȸ�� �߽��� x��ġ�� �����մϴ�.<br>
	 * ȸ�� �߽��� Layer ���ο� �ִ� ��� �� ���� 0 ~ 1.0 ������ ������ ������<br>
	 * �� ���� 0�� ��� ���� ��, 1.0�� ��� ���� ���� ȸ�� �߽����� ����ϴ�.<br>
	 * �⺻���� 0.5(Layer�� �� ���)�Դϴ�.
	 */
	public double rotate_origin_x;
	
	/**
	 * ȸ�� �߽��� y��ġ�� �����մϴ�.<br>
	 * ȸ�� �߽��� Layer ���ο� �ִ� ��� �� ���� 0 ~ 1.0 ������ ������ ������<br>
	 * �� ���� 0�� ��� ���� ��, 1.0�� ��� �Ʒ��� ���� ȸ�� �߽����� ����ϴ�.<br>
	 * �⺻���� 0.5(Layer�� �� ���)�Դϴ�.
	 */
	public double rotate_origin_y;

	public RotatableLayer(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		
		rotate_origin_x = 0.5;
		rotate_origin_y = 0.5;
	}

	public RotatableLayer(int x, int y, int width, int height, double view_width, double view_height)
	{
		super(x, y, width, height, view_width, view_height);
		
		rotate_origin_x = 0.5;
		rotate_origin_y = 0.5;
	}

	public RotatableLayer(int x, int y, int width, int height, double angle, double rotate_origin_x, double rotate_origin_y)
	{
		super(x, y, width, height);
		
		this.angle = angle;
		this.rotate_origin_x = rotate_origin_x;
		this.rotate_origin_y = rotate_origin_y;
	}

	public RotatableLayer(int x, int y, int width, int height, double view_width, double view_height, double angle, double rotate_origin_x, double rotate_origin_y)
	{
		super(x, y, width, height, view_width, view_height);
		
		this.angle = angle;
		this.rotate_origin_x = rotate_origin_x;
		this.rotate_origin_y = rotate_origin_y;
	}
	
	public RotatableLayer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		
		rotate_origin_x = 0.5;
		rotate_origin_y = 0.5;
	}
	
	public RotatableLayer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, double view_width, double view_height)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y, view_width, view_height);
		
		rotate_origin_x = 0.5;
		rotate_origin_y = 0.5;
	}
	
	public RotatableLayer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, double angle, double rotate_origin_x, double rotate_origin_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		
		this.angle = angle;
		this.rotate_origin_x = rotate_origin_x;
		this.rotate_origin_y = rotate_origin_y;
	}
	
	public RotatableLayer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, double view_width, double view_height, double angle, double rotate_origin_x, double rotate_origin_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y, view_width, view_height);
		
		this.angle = angle;
		this.rotate_origin_x = rotate_origin_x;
		this.rotate_origin_y = rotate_origin_y;
	}

	/**
	 * ���� Layer�� ������ ������ ���� ��ҵ��� �׸��ϴ�.
	 * 
	 * @param g_origin
	 * 		GameFrame���� g ��� �ʵ尡 ��� �ֽ��ϴ�.<br>
	 * 		�������� Draw(g)�� ���� ȣ���� ��(���� ȭ�鿡 ���� �׸� ��)�� �� ������� ���� �� �ʵ带 �׳� ������ �˴ϴ�.
	 */
	@Override
	public void Draw(Graphics2D g_origin)
	{
		Graphics2D g = (Graphics2D)g_origin.create();
		
		//��ȯ ��� �� ����� �ۼ�
		transform_out.setToIdentity();
		transform_out.translate(x, y);
		transform_out.rotate(angle, rotate_origin_x * width, rotate_origin_y * height);
		transform_out.scale(width / view_width, height / view_height);
		transform_out.translate(view_width * view_origin_x, view_height * view_origin_y);

		transform_in.setToIdentity();
		transform_in.translate(-view_width * view_origin_x, -view_height * view_origin_y);
		transform_in.scale(view_width / width, view_height / height);					//'a/b��'�� �������� 'b/a��' 
		transform_in.rotate(-angle, rotate_origin_x * width, rotate_origin_y * height);	//'a��ŭ ȸ��'�� �������� '-a��ŭ ȸ��'
		transform_in.translate(-x, -y);													//'(a, b)��ŭ �̵�'�� �������� '(-a, -b)��ŭ �̵�'

		//�׸��� �۾��� ���� ��ȯ ����� g�� ����
		g.transform(transform_out);
	
		for ( Iterator<VisualObject> iterator = children.iterator(); iterator.hasNext(); )
		{
			VisualObject child = iterator.next();
			
			if ( child.trigger_remove == true )
			{
				iterator.remove();
				continue;
			}
			
			if ( child.trigger_hide == true )
				continue;
			
			child.Draw(g);
		}
		
		g.dispose();
	}
	
	/**
	 * �־��� degree �������� �ش��ϴ� radian �������� ��ȯ�մϴ�.<br>
	 * ����: �� �޼���� '��ȿ�� ���� ����' �������� �ƴ� �׳� �ִ� �״���� �������� ��ȯ�մϴ�.<br>
	 * �ʿ��� ��� '��ȯ�� %= 2.0 * Math.PI' ����� ������ �����Ͽ� �������� ũ�⸦ ������ �۰� ������ ����ϼ���.
	 */
	public static double GetRadianFromDegree(double angle_degree)
	{
		return angle_degree / 180.0 * Math.PI;
	}
	
	/**
	 * �־��� radian �������� �ش��ϴ� degree �������� ��ȯ�մϴ�.<br> 
	 * ����: �� �޼���� '��ȿ�� ���� ����' �������� �ƴ� �׳� �ִ� �״���� �������� ��ȯ�մϴ�.<br>
	 * �ʿ��� ��� '��ȯ�� %= 360.0' ����� ������ �����Ͽ� �������� ũ�⸦ ������ �۰� ������ ����ϼ���.
	 */
	public static double GetDegreeFromRadian(double angle_radian)
	{
		return angle_radian / Math.PI * 180.0;
	}
	
	@Override
	public boolean HitTest3D(double pos_x, double pos_y, double pos_z)
	{
		Point pos_internal = TransformTo2DPosition(pos_x, pos_y, pos_z); 
		
		for ( VisualObject child : children )
			if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest(pos_internal.x, pos_internal.y) == true )
				return true;
		
		return false;
	}
}
