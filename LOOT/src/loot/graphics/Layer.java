package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 2���� ���� ��ǥ�踦 ����� �̸� ���� ���� ��ҵ��� �׸��� Ŭ�����Դϴ�.<br>
 * ���� 1: Layer�� ��ӹ޴� �ٸ� Ŭ�������� ���� �ٸ� ��ǥ�踦 �� ����� ����մϴ�.<br>
 * ���� 2: Layer ���� VisualObject3D�� ���� Ŭ�����̹Ƿ� �ٸ� Layer�� ���� ��Ұ� �� �� �ֽ��ϴ�.<br>
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
public class Layer extends VisualObject3D
{
	/**
	 * ���� ���Ե� ���� ��� ����Դϴ�.<br>
	 * Layer �� Layer�� ��ӹ޴� Ŭ�������� Draw(g)�� �� ��Ͽ� �ִ� ��ҵ��� �ڽ��� ������ �׸��ϴ�.
	 */
	public LinkedList<VisualObject> children;
	
	/**
	 * Layer�� �ٷ�� 2���� ���� ��ǥ���� �ʺ��Դϴ�.<br>
	 * <br>
	 * Layer �� 2���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ��������<br>
	 * (0, 0)�� �»�� ��ǥ�� ��� �ʺ� view_width, ���̰� view_height�� ���� ��ǥ�踦 �����<br>
	 * ���� ��Ҹ� �ڽ��� ����(x, y, width, height�� ������)�� �׸��ϴ�.<br>
	 * <br>
	 * 3���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ��������<br>
	 * (pointOfView_x, pointOfView_y, pointOfView_z + baseDistance)�� �������� ��� �ʺ� view_width, ���̰� view_height�� ���� ��ǥ�踦 �����<br>
	 * ���� ��Ҹ� �ڽ��� ����(x, y, width, height�� ������)�� �׸��ϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * Layer �� 2���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ�������� ���� ����� ���� ��ġ�� �ڽ��� ���� ���� �ִ��� �˻����� �ʽ��ϴ�.<br>
	 * ��κ��� ��Ȳ���� �� ������ ū ������ ���� �ʰ�����<br>
	 * ���� ���ɻ��� ������ '������ �ʴ� ���'�� �׸��� �ʵ��� �����ؾ� �Ѵٸ�<br>
	 * children ��Ͽ� �ִ� �� ��Ҹ� �������� üũ�Ͽ�<br>
	 * ������ ��� (�׸��� �� �̻� ������ ������ ����) ����� trigger_remove �ʵ带 true�� ���������ν�<br>
	 * Layer�� �̹� �����Ӻ��� �ش� ��Ҹ� �׸��� �ʵ��� ���� �� �ֽ��ϴ�.
	 */
	public double view_width;
	
	/**
	 * Layer�� �ٷ�� 2���� ���� ��ǥ���� �����Դϴ�.<br>
	 * <br>
	 * Layer �� 2���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ��������<br>
	 * (0, 0)�� �»�� ��ǥ�� ��� �ʺ� view_width, ���̰� view_height�� ���� ��ǥ�踦 �����<br>
	 * ���� ��Ҹ� �ڽ��� ����(x, y, width, height�� ������)�� �׸��ϴ�.<br>
	 * <br>
	 * 3���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ��������<br>
	 * (pointOfView_x, pointOfView_y, pointOfView_z + baseDistance)�� �������� ��� �ʺ� view_width, ���̰� view_height�� ���� ��ǥ�踦 �����<br>
	 * ���� ��Ҹ� �ڽ��� ����(x, y, width, height�� ������)�� �׸��ϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * Layer �� 2���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ�������� ���� ����� ���� ��ġ�� �ڽ��� ���� ���� �ִ��� �˻����� �ʽ��ϴ�.<br>
	 * ��κ��� ��Ȳ���� �� ������ ū ������ ���� �ʰ�����<br>
	 * ���� ���ɻ��� ������ '������ �ʴ� ���'�� �׸��� �ʵ��� �����ؾ� �Ѵٸ�<br>
	 * children ��Ͽ� �ִ� �� ��Ҹ� �������� üũ�Ͽ�<br>
	 * ������ ��� (�׸��� �� �̻� ������ ������ ����) ����� trigger_remove �ʵ带 true�� ���������ν�<br>
	 * Layer�� �̹� �����Ӻ��� �ش� ��Ҹ� �׸��� �ʵ��� ���� �� �ֽ��ϴ�.
	 */
	public double view_height;
	
	/**
	 * Layer�� �ٷ�� ���� ��ǥ���� ���� x��ǥ�� ȭ�� ���� ��ġ�� �����մϴ�.<br>
	 * ȸ�� �߽��� Layer ���ο� �ִ� ��� �� ���� 0 ~ 1.0 ������ ������ ������<br>
	 * �� ���� 0�� ��� ���� ��, 1.0�� ��� ���� ���� �ǹ��մϴ�.
	 */
	public double view_origin_x;
	
	/**
	 * Layer�� �ٷ�� ���� ��ǥ���� ���� y��ǥ�� ȭ�� ���� ��ġ�� �����մϴ�.<br>
	 * ȸ�� �߽��� Layer ���ο� �ִ� ��� �� ���� 0 ~ 1.0 ������ ������ ������<br>
	 * �� ���� 0�� ��� ���� ��, 1.0�� ��� ���� ���� �ǹ��մϴ�.
	 */
	public double view_origin_y;

	/**
	 * Layer�� ���� ��ǥ���� ��ǥ�� Layer�� �ٷ�� ���� ��ǥ�� ��ȯ�ϱ� ���� ��ȯ ����Դϴ�.<br>
	 * �� ��ȯ ��Ŀ��� ���� ��� �׸��⿡ ���� ��ȯ ����� ������� ��ϵ˴ϴ�.<br>
	 * �� �ʵ�� ���������� ���� �� ���Ǹ� �������� ���� �� �ʵ带 �� ���� �����ϴ�. 
	 */
	protected AffineTransform transform_in;
	
	/**
	 * Layer�� �ٷ�� ���� ��ǥ�� Layer�� ���� ��ǥ��(��������� �ܺο� �ִ� ��ǥ��)�� ��ǥ�� ��ȯ�ϱ� ���� ��ȯ ����Դϴ�.<br>
	 * �� ��ȯ ��Ŀ��� ���� ��� �׸��⿡ ���� ��ȯ ��� ������� ��ϵ˴ϴ�.<br>
	 * �� �ʵ�� ���������� ���� �� ���Ǹ� �������� ���� �� �ʵ带 �� ���� �����ϴ�. 
	 */
	protected AffineTransform transform_out;
	
	public Layer(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		children = new LinkedList<>();
		transform_in = new AffineTransform();
		transform_out = new AffineTransform();
		
		if ( width < 0 )
			view_width = -width;
		else		
			view_width = width;
		
		if ( height < 0 )
			view_height = -height;
		else
			view_height = height;
		
		view_origin_x = 0;
		view_origin_y = 0;
	}
	
	public Layer(int x, int y, int width, int height, double view_width, double view_height)
	{
		super(x, y, width, height);
		children = new LinkedList<>();
		transform_in = new AffineTransform();
		transform_out = new AffineTransform();
		this.view_width = view_width;
		this.view_height = view_height;
		
		view_origin_x = 0;
		view_origin_y = 0;
	}
	
	public Layer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		children = new LinkedList<>();
		transform_in = new AffineTransform();
		transform_out = new AffineTransform();
		
		if ( radius_x < 0 )
			view_width = -radius_x * 2;
		else		
			view_width = radius_x * 2;
		
		if ( radius_y < 0 )
			view_height = -radius_y * 2;
		else
			view_height = radius_y * 2;
		
		view_origin_x = 0;
		view_origin_y = 0;
	}
	
	public Layer(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, double view_width, double view_height)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		children = new LinkedList<>();
		transform_in = new AffineTransform();
		transform_out = new AffineTransform();
		this.view_width = view_width;
		this.view_height = view_height;
		
		view_origin_x = 0;
		view_origin_y = 0;
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
		transform_out.scale(width / view_width, height / view_height);
		transform_out.translate(view_width * view_origin_x, view_height * view_origin_y);

		transform_in.setToIdentity();
		transform_in.translate(-view_width * view_origin_x, -view_height * view_origin_y);
		transform_in.scale(view_width / width, view_height / height);	//'a/b��'�� �������� 'b/a��' 
		transform_in.translate(-x, -y);									//'(a, b)��ŭ �̵�'�� �������� '(-a, -b)��ŭ �̵�'

		//�׸��� �۾��� ���� ��ȯ ����� g�� ����
		g.transform(transform_out);
				
		for ( Iterator<VisualObject> iterator = children.descendingIterator(); iterator.hasNext(); )
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
	
	/* ---------------------------------------------------
	 * 
	 * ���� �׽�Ʈ�� ���� �޼����
	 * 
	 */

	/**
	 * �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.
	 * 
	 * @param x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	@Override
	public boolean HitTest(int x, int y)
	{
		if ( HitTest_Base(x, y) == true )
		{
			//�ܺ� ��ǥ�� ���� ��ǥ�� ��ȯ
			Point pos_internal = TransformToInternalPosition(x, y);
			
			for ( VisualObject child : children )
				if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest(pos_internal.x, pos_internal.y) == true )
					return true;
		}
		
		return false;
	}
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ���� 1: Layer �� 2���� ���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ�������� �ش� ��ǥ�� 2���� ���� ��ǥ�� ��ȯ�Ͽ� ����մϴ�.<br>
	 * ���� 2: double ������ Ư���� �� �޼���� radius_z�� ������ ������ ����ϴ� ���� �� �����մϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� z��ǥ�Դϴ�.
	 */
	@Override
	public boolean HitTest3D(double pos_x, double pos_y, double pos_z)
	{
		if ( HitTest3D_Base(pos_x, pos_y, pos_z) == true )
		{
			//�ܺ� 3���� ��ǥ�� ���� 2���� ��ǥ�� ��ȯ
			Point pos_external_2d = TransformTo2DPosition(pos_x, pos_y, pos_z);
			Point pos_internal = TransformToInternalPosition(pos_external_2d.x, pos_external_2d.y);
			
			for ( VisualObject child : children )
				if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest(pos_internal.x, pos_internal.y) == true )
					return true;
		}
		
		return false;
	}
	
	/**
	 * 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ���� 1: Layer �� 2���� ���� ��ǥ�踦 ����ϴ� Layer�� ���� Ŭ�������� �ش� ��ǥ�� 2���� ���� ��ǥ�� ��ȯ�Ͽ� ����մϴ�.<br>
	 * ���� 2: LOOT���� ��� ��Ҵ� ���������� 2���� ����̹Ƿ�<br>
	 * ���⼭�� ���� �׽�Ʈ�� �����ϰ� �ϱ� ���� z�� ���� �������� �����ϸ�<br>
	 * ����� ���� z��ǥ�� �Է¹��� z��ǥ ������ �Ÿ��� �ش� ���������� �۰ų� ���� ��� ������ ������ �����մϴ�.
	 * 
	 * @param pos_x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� x��ǥ�Դϴ�.
	 * @param pos_y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� y��ǥ�Դϴ�.
	 * @param pos_z ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 3���� z��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	@Override
	public boolean HitTest3D(double pos_x, double pos_y, double pos_z, double radius_z)
	{		
		if ( HitTest3D_Base(pos_x, pos_y, pos_z, radius_z) == true )
		{
			//�ܺ� 3���� ��ǥ�� ���� 2���� ��ǥ�� ��ȯ
			Point pos_external_2d = TransformTo2DPosition(pos_x, pos_y, pos_z);
			Point pos_internal = TransformToInternalPosition(pos_external_2d.x, pos_external_2d.y);
			
			for ( VisualObject child : children )
				if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest(pos_internal.x, pos_internal.y) == true )
					return true;
		}
		
		return false;
	}
	
	/**
	 * �ش� ��ǥ ���� �ִ� ���� ��Ҹ� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ��� null�� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �� �� �̻��� ��Ұ� �ִ� ��� ���� ���߿� �׷���(���� ���� ȭ�鿡 ���̴�) ��Ҹ� ��ȯ�մϴ�.
	 * 
	 * @param x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public VisualObject GetObjectAt(int x, int y)
	{
		if ( HitTest_Base(x, y) == true )
		{
			//�ܺ� ��ǥ�� ���� ��ǥ�� ��ȯ
			Point pos_internal = TransformToInternalPosition(x, y);

			for ( VisualObject child : children )
				if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest(pos_internal.x, pos_internal.y) == true )
					return child;
		}
		
		return null;
	}
	
	/**
	 * �ش� ��ǥ ���� �ִ� ���� ��Ҹ� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ��� null�� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �� �� �̻��� ��Ұ� �ִ� ��� ���� ���߿� �׷���(���� ���� ȭ�鿡 ���̴�) ��Ҹ� ��ȯ�մϴ�.
	 * 
	 * @param pos ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final VisualObject GetObjectAt(Point pos)
	{
		return GetObjectAt(pos.x, pos.y);
	}
	
	
	/* ---------------------------------------------------
	 * 
	 * �����ǥ ��� �� ��ǥ ��ȯ�� ���� �޼����
	 * 
	 */
	
	/**
	 * �־��� �ܺ� ��ǥ(�� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� ��ǥ)�� ���� ��ǥ(�� ��Ұ� ���� ���� ��ǥ�� �ȿ����� ��ǥ)�� ��ȯ�Ͽ� ��ȯ�մϴ�.
	 * 
	 * @param x ���� ��ǥ�� ��ȯ��, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ��ǥ�� ��ȯ��, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point TransformToInternalPosition(int x, int y)
	{
		//������� ����Ͽ� ��ǥ ��ȯ ����
		Point2D pos_internal = new Point2D.Double(x, y);
		transform_in.transform(pos_internal, pos_internal);

		return new Point((int)pos_internal.getX(), (int)pos_internal.getY());
	}
	
	/**
	 * �־��� �ܺ� ��ǥ(�� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� ��ǥ)�� ���� ��ǥ(�� ��Ұ� ���� ���� ��ǥ�� �ȿ����� ��ǥ)�� ��ȯ�Ͽ� ��ȯ�մϴ�.
	 * 
	 * @param pos ���� ��ǥ�� ��ȯ��, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final Point TransformToInternalPosition(Point pos)
	{
		return TransformToInternalPosition(pos.x, pos.y);
	}
	
	/**
	 * �־��� ���� ��ǥ(�� ��Ұ� ���� ���� ��ǥ�� �ȿ����� ��ǥ)�� �ܺ� ��ǥ(�� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� ��ǥ)�� ��ȯ�Ͽ� ��ȯ�մϴ�.
	 * 
	 * @param x_internal �ܺ� ��ǥ�� ��ȯ��, �� ��Ұ� ���� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y_internal �ܺ� ��ǥ�� ��ȯ��, �� ��Ұ� ���� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point TransformToExternalPosition(int x_internal, int y_internal)
	{
		//��ȯ ����� ����Ͽ� ��ǥ ��ȯ ����
		Point2D pos_external = new Point2D.Double(x_internal, y_internal);
		transform_out.transform(pos_external, pos_external);

		return new Point((int)pos_external.getX(), (int)pos_external.getY());
	}
	
	/**
	 * �־��� ���� ��ǥ(�� ��Ұ� ���� ���� ��ǥ�� �ȿ����� ��ǥ)�� �ܺ� ��ǥ(�� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� ��ǥ)�� ��ȯ�Ͽ� ��ȯ�մϴ�.
	 * 
	 * @param pos_internal �ܺ� ��ǥ�� ��ȯ��, �� ��Ұ� ���� ���� ��ǥ�� �ȿ����� 2���� ��ǥ�Դϴ�.
	 */
	public final Point TransformToExternalPosition(Point pos_internal)
	{
		return TransformToExternalPosition(pos_internal.x, pos_internal.y);
	}
}
