package loot.graphics;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * 3���� ���� ��ǥ�踦 ����� �̸� ���� ���� ��ҵ��� �ڽ��� view ������ �׸��� Ŭ�����Դϴ�.<br>
 * ����: Viewport�� ��ġ ��� ��� �ڽ��� ���� �ȿ� ���� ���� ���� ��ҵ��� �ƿ� �׸��� ������<br>
 * �׻� �ڽ��� ���� �ȿ����� �׸��� �۾��� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class Viewport extends Layer
{
	public double pointOfView_x;
	public double pointOfView_y;
	public double pointOfView_z;
	
	public double view_baseDistance;
	public double view_minDistance;
	public double view_maxDistance;
	
	public double view_origin_3D_x;
	public double view_origin_3D_y;
	public double view_exponent_z;
	
	
	public ArrayList<VisualObject3D> children_3d_sorted = new ArrayList<VisualObject3D>();

	public ArrayList<VisualObject> children_2d = new ArrayList<>();

	public Viewport(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		
		pointOfView_x = 0;
		pointOfView_y = 0;
		pointOfView_z = 1;
		
		view_baseDistance = 1;
		view_minDistance = 0;
		view_maxDistance = Double.POSITIVE_INFINITY;
		
		view_origin_3D_x = 0.5;
		view_origin_3D_y = 0.5;
		view_exponent_z = 1;
	}
	
	public Viewport(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y)
	{
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		
		pointOfView_x = 0;
		pointOfView_y = 0;
		pointOfView_z = 1;
		
		view_baseDistance = 1;
		view_minDistance = 0;
		view_maxDistance = Double.POSITIVE_INFINITY;
		
		view_origin_3D_x = 0.5;
		view_origin_3D_y = 0.5;
		view_exponent_z = 1;
	}

	/**
	 * ���� Viewport�� ������ ������ ���� ��ҵ��� �׸��ϴ�.<br>
	 * ���� 1: Viewport�� ��ġ ��� ��� �ڽ��� ���� �ȿ� ���� ���� ���� ��ҵ��� �ƿ� �׸��� ������<br>
	 * �׻� �ڽ��� ���� �ȿ����� �׸��� �۾��� �����մϴ�.<br>
	 * ���� 2: Viewport�� ���� 3���� ��ҵ��� �׸� ���� 2���� ��ҵ��� �׸��ϴ�.<br>
	 * ���� 2���� ��ҵ��� �׻� ���� ���� �ִ� ��ó�� ���̰� �˴ϴ�.
	 * 
	 * @param g_origin
	 * 		GameFrame���� g ��� �ʵ尡 ��� �ֽ��ϴ�.<br>
	 * 		�������� Draw(g)�� ���� ȣ���� ��(���� ȭ�鿡 ���� �׸� ��)�� �� ������� ���� �� �ʵ带 �׳� ������ �˴ϴ�.
	 */
	@Override
	public void Draw(Graphics2D g_origin)
	{
		Graphics2D g = (Graphics2D)g_origin.create();
		
		g.clipRect(x, y, width, height);

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
		
		children_3d_sorted.clear();
		children_2d.clear();
		
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
			
			if ( VisualObject3D.class.isInstance(child) == true )
			{
				VisualObject3D child_3d = (VisualObject3D)child;

				if ( child_3d.trigger_coordination == true )
				{
					double factor_z = pointOfView_z - child_3d.pos_z;
					
					if ( factor_z >= view_minDistance && factor_z <= view_maxDistance )
					{
						factor_z /= view_baseDistance;
						factor_z = Math.pow(factor_z, view_exponent_z);
							
						child_3d.x = (int)( view_width * view_origin_3D_x + ( child_3d.pos_x - pointOfView_x - child_3d.radius_x ) / factor_z );
						child_3d.y = (int)( view_height * view_origin_3D_y - ( child_3d.pos_y - pointOfView_y + child_3d.radius_y ) / factor_z );
						child_3d.width = (int)( child_3d.radius_x / factor_z * 2 );
						child_3d.height = (int)( child_3d.radius_y / factor_z * 2 );
						
						children_3d_sorted.add(child_3d);
					}
					
					continue;
				}
			}

			children_2d.add(child);
		}

		SortChildren();
		
		for ( ListIterator<VisualObject3D> iterator_3d = children_3d_sorted.listIterator(children_3d_sorted.size()); iterator_3d.hasPrevious() == true; )
			iterator_3d.previous().Draw(g);
		
		for ( ListIterator<VisualObject> iterator_2d = children_2d.listIterator(children_2d.size()); iterator_2d.hasPrevious() == true;  )
			iterator_2d.previous().Draw(g);
		
		g.dispose();		
	}
	
	/**
	 * Viewport.Draw(g) �ȿ��� 3���� ����� �׸��� ������ �����ϱ� ���� children_3d_sorted ����� �����ϴ� �޼����Դϴ�.<br>
	 * �� �޼���� Viewport ���ο��� �ڵ����� ȣ��Ǹ� �������� �� �޼��带 ����� �� �����ϴ�.
	 */
	private void SortChildren()
	{
		int length = children_3d_sorted.size();
		
		if ( length < 2 )
			return;
		
		ArrayList<VisualObject3D> buffer = new ArrayList<>(length);
		
		for ( int iChild = 0; iChild < length; ++iChild )
			buffer.add(null);
		
		for ( int block_size = 1; block_size <= length; block_size *= 2 )
		{
			int pos_buffer = 0;
			
			for ( int start_offset = 0; start_offset < length; start_offset += block_size * 2 )
			{
				int middle_offset = start_offset + block_size;
				
				if ( middle_offset > length )
					middle_offset = length;
				
				int end_offset = middle_offset + block_size;
				
				if ( end_offset > length )
					end_offset = length;
				
				int pos_left = start_offset;
				int pos_right = middle_offset;
				
				while ( pos_left < middle_offset && pos_right < end_offset )
				{
					VisualObject3D child_left = children_3d_sorted.get(pos_left);
					VisualObject3D child_right = children_3d_sorted.get(pos_right);
					
					if ( child_left.pos_z >= child_right.pos_z )
					{
						buffer.set(pos_buffer, child_left);
						++pos_buffer;
						++pos_left;
					}
					else
					{
						buffer.set(pos_buffer, child_right);
						++pos_buffer;
						++pos_right;
					}
				}
				
				while ( pos_left < middle_offset )
				{
					buffer.set(pos_buffer, children_3d_sorted.get(pos_left));
					++pos_buffer;
					++pos_left;					
				}
				
				while ( pos_right < end_offset )
				{
					buffer.set(pos_buffer, children_3d_sorted.get(pos_right));
					++pos_buffer;
					++pos_right;
				}
			}
			
			ArrayList<VisualObject3D> temp = children_3d_sorted;
			children_3d_sorted = buffer;
			buffer = temp;
		}
	}
	
	
	/* ---------------------------------------------------
	 * 
	 * ���� �׽�Ʈ�� ���� �޼����
	 * 
	 */

	/**
	 * ���� Viewport�� view ��鿡 �ش� 3���� ��ǥ�� ���̴��� ���θ� ��ȯ�մϴ�. 
	 * 
	 * @param pos_x_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public boolean CheckVisibilityOfPosition3D(double pos_x_internal, double pos_y_internal, double pos_z_internal)
	{
		double factor_z = pointOfView_z - pos_z_internal;
		
		if ( factor_z >= view_minDistance && factor_z <= view_maxDistance )
		{
			factor_z /= view_baseDistance;
			
			int x_internal = (int)( view_width / 2 + ( pos_x_internal - pointOfView_x ) / factor_z );
			int y_internal = (int)( view_height / 2 - ( pos_y_internal - pointOfView_y ) / factor_z );
			
			return	x_internal >= 0 && x_internal <= width &&
					y_internal >= 0 && y_internal <= height;
		}
		
		return false;
	}

	/**
	 * ���� Viewport�� view ��鿡 �ش� 3���� ��ǥ�� ���̴��� ���θ� ��ȯ�մϴ�. 
	 * 
	 * @param pos_x_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���ü� ���θ� ��ȯ��, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 * @param radius_z ���ü� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	public boolean CheckVisibilityOfPosition3D(double pos_x_internal, double pos_y_internal, double pos_z_internal, double radius_z)
	{
		double factor_z = pointOfView_z - pos_z_internal;
		
		if ( factor_z + radius_z >= view_minDistance && factor_z - radius_z <= view_maxDistance )
		{
			factor_z /= view_baseDistance;
			
			int x_internal = (int)( view_width / 2 + ( pos_x_internal - pointOfView_x ) / factor_z );
			int y_internal = (int)( view_height / 2 - ( pos_y_internal - pointOfView_y ) / factor_z );
			
			return	x_internal >= 0 && x_internal <= width &&
					y_internal >= 0 && y_internal <= height;
		}
		
		return false;
	}
	
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
			Point pos_internal = TransformToInternalPosition(x, y);
			
			for ( VisualObject child_2d : children_2d )
				if ( child_2d.trigger_hide == false && child_2d.trigger_ignoreDuringHitTest == false && child_2d.HitTest(pos_internal.x, pos_internal.y) == true )
					return true;
			
			for ( VisualObject3D child_3d : children_3d_sorted )
				if ( child_3d.trigger_hide == false && child_3d.trigger_ignoreDuringHitTest == false && child_3d.HitTest(pos_internal.x, pos_internal.y) == true )
					return true;
		}
			
		return false;
	}
	
	/**
	 * <b>Viewport ������</b> 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: double ������ Ư���� �� �޼���� radius_z�� ������ ������ ����ϴ� ���� �� �����մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �Ϲ����� �޼����� �ٸ��� Viewport.HitTest3D()�� Viewport�� ���� �ܺ� ��ǥ�谡 �ƴ� Viewport �ڽ��� �ٷ�� ���� ��ǥ���� ��ǥ�� argument�� �޽��ϴ�. 
	 * ���� �ʿ��� ��� Viewport.TransformToInternal3DPosition()�� ȣ���Ͽ� �ܺ� 2���� ��ǥ�� ���� 3���� ��ǥ�� ��ȯ�� ���� �� �޼��带 ȣ���ؾ� �մϴ�. 
	 * �ٸ�, ���� Viewport �ȿ� �ٸ� Viewport�� ��� �ִ� ��쿡�� �� ������ �ڵ����� ������ �ֹǷ� 
	 * �������� �̷��� ������ ���� ������ �� ���� �Ƹ��� ���� �߻����� ���� ���Դϴ�. 
	 * 
	 * @param pos_x_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	@Override
	public boolean HitTest3D(double pos_x_internal, double pos_y_internal, double pos_z_internal)
	{
		if ( CheckVisibilityOfPosition3D(pos_x_internal, pos_y_internal, pos_z_internal) == true )
		{
			for ( VisualObject3D child : children_3d_sorted )
			{
				//���� �ش� ���� ��Ұ� Viewport�� ��� �߰� ��ǥ ��ȯ�� ���� ���� Viewport�� ���� ���� 3���� ��ǥ�� ��ȯ�Ͽ� HitTest3D() ȣ��
				if ( Viewport.class.isInstance(child) == true )
				{
					Viewport child_viewport = (Viewport)child;
					Point pos_internal_2d = TransformToInternal2DPositionFromInternal3D(pos_x_internal, pos_y_internal, pos_z_internal);
					Point3D pos_inChild_3d = child_viewport.TransformToInternal3DPosition(pos_internal_2d.x, pos_internal_2d.y);
					
					if ( child_viewport.trigger_hide == false && child_viewport.trigger_ignoreDuringHitTest == false && child_viewport.HitTest3D(pos_inChild_3d.x, pos_inChild_3d.y, pos_inChild_3d.z) == true )
						return true;
				}
				//�׷��� ���� ��� ���� 3���� ���� ��ǥ�� �״�� ����Ͽ� ���� ����� HitTest3D() ȣ��
				else
				{
					if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest3D(pos_x_internal, pos_y_internal, pos_z_internal) == true )
						return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * <b>Viewport ������</b> 3���� ���� �ȿ���, �� ��Ұ� �����ϴ� ���� ���� �ش� ��ǥ�� �ִ���(���콺 Ŀ���� ���� ���, Ŀ���� �� ��� ���� �ִ���) ���θ� ��ȯ�մϴ�.<br>
	 * ����: LOOT���� ��� ��Ҵ� ���������� 2���� ����̹Ƿ�<br>
	 * ���⼭�� ���� �׽�Ʈ�� �����ϰ� �ϱ� ���� z�� ���� �������� �����ϸ�<br>
	 * ����� ���� z��ǥ�� �Է¹��� z��ǥ ������ �Ÿ��� �ش� ���������� �۰ų� ���� ��� ������ ������ �����մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �Ϲ����� �޼����� �ٸ��� Viewport.HitTest3D()�� Viewport�� ���� �ܺ� ��ǥ�谡 �ƴ� Viewport �ڽ��� �ٷ�� ���� ��ǥ���� ��ǥ�� argument�� �޽��ϴ�. 
	 * ���� �ʿ��� ��� Viewport.TransformToInternal3DPosition()�� ȣ���Ͽ� �ܺ� 2���� ��ǥ�� ���� 3���� ��ǥ�� ��ȯ�� ���� �� �޼��带 ȣ���ؾ� �մϴ�. 
	 * �ٸ�, ���� Viewport �ȿ� �ٸ� Viewport�� ��� �ִ� ��쿡�� �� ������ �ڵ����� ������ �ֹǷ� 
	 * �������� �̷��� ������ ���� ������ �� ���� �Ƹ��� ���� �߻����� ���� ���Դϴ�. 
	 * 
	 * @param pos_x_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	@Override
	public boolean HitTest3D(double pos_x_internal, double pos_y_internal, double pos_z_internal, double radius_z)
	{
		if ( CheckVisibilityOfPosition3D(pos_x_internal, pos_y_internal, pos_z_internal, radius_z) == true )
		{
			for ( VisualObject3D child : children_3d_sorted )
			{
				//���� �ش� ���� ��Ұ� Viewport�� ��� �߰� ��ǥ ��ȯ�� ���� ���� Viewport�� ���� ���� 3���� ��ǥ�� ��ȯ�Ͽ� HitTest3D() ȣ��
				if ( Viewport.class.isInstance(child) == true )
				{
					Viewport child_viewport = (Viewport)child;
					Point pos_internal_2d = TransformToInternal2DPositionFromInternal3D(pos_x_internal, pos_y_internal, pos_z_internal);
					Point3D pos_inChild_3d = child_viewport.TransformToInternal3DPosition(pos_internal_2d.x, pos_internal_2d.y);
					
					if ( child_viewport.trigger_hide == false && child_viewport.trigger_ignoreDuringHitTest == false && child_viewport.HitTest3D(pos_inChild_3d.x, pos_inChild_3d.y, pos_inChild_3d.z, radius_z) == true )
						return true;
				}
				//�׷��� ���� ��� ���� 3���� ���� ��ǥ�� �״�� ����Ͽ� ���� ����� HitTest3D() ȣ��
				else
				{
					if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest3D(pos_x_internal, pos_y_internal, pos_z_internal, radius_z) == true )
						return true;
				}
			}
		}

		return false;
	}
	
	/**
	 * �ش� ��ǥ ���� �ִ� ���� ��Ҹ� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ��� null�� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �� �� �̻��� ��Ұ� �ִ� ��� ���� ���߿� �׷���(���� ���� ȭ�鿡 ���̴�) ��Ҹ� ��ȯ�մϴ�.<br>
	 * 
	 * @param x ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y ���� ���θ� �˻���, �� ��Ұ� ���� �ִ� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	@Override
	public VisualObject GetObjectAt(int x, int y)
	{
		if ( HitTest_Base(x, y) == true )
		{
			Point pos_internal = TransformToInternalPosition(x, y);
			
			for ( VisualObject child_2d : children_2d )
				if ( child_2d.trigger_hide == false && child_2d.trigger_ignoreDuringHitTest == false && child_2d.HitTest(pos_internal.x, pos_internal.y) == true )
					return child_2d;
			
			for ( VisualObject3D child_3d : children_3d_sorted )
				if ( child_3d.trigger_hide == false && child_3d.trigger_ignoreDuringHitTest == false && child_3d.HitTest(pos_internal.x, pos_internal.y) == true )
					return child_3d;
		}
			
		return null;
	}
	
	/**
	 * <b>Viewport ������</b> 3���� ���� �ȿ���, �ش� ��ǥ ���� �ִ� ���� ��Ҹ� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ��� null�� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �� �� �̻��� ��Ұ� �ִ� ��� ���� ���߿� �׷���(���� ���� ȭ�鿡 ���̴�) ��Ҹ� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �Ϲ����� �޼����� �ٸ��� Viewport.GetObjectAt3D()�� Viewport�� ���� �ܺ� ��ǥ�谡 �ƴ� Viewport �ڽ��� �ٷ�� ���� ��ǥ���� ��ǥ�� argument�� �޽��ϴ�. 
	 * ���� �ʿ��� ��� Viewport.TransformToInternal3DPosition()�� ȣ���Ͽ� �ܺ� 2���� ��ǥ�� ���� 3���� ��ǥ�� ��ȯ�� ���� �� �޼��带 ȣ���ؾ� �մϴ�. 
	 * �ٸ�, ���� Viewport �ȿ� �ٸ� Viewport�� ��� �ִ� ��쿡�� �� ������ �ڵ����� ������ �ֹǷ� 
	 * �������� �̷��� ������ ���� ������ �� ���� �Ƹ��� ���� �߻����� ���� ���Դϴ�.
	 * 
	 * @param pos_x_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public VisualObject3D GetObjectAt3D(double pos_x_internal, double pos_y_internal, double pos_z_internal)
	{
		if ( CheckVisibilityOfPosition3D(pos_x_internal, pos_y_internal, pos_z_internal) == true )
		{
			for ( VisualObject3D child : children_3d_sorted )
			{
				//���� �ش� ���� ��Ұ� Viewport�� ��� �߰� ��ǥ ��ȯ�� ���� ���� Viewport�� ���� ���� 3���� ��ǥ�� ��ȯ�Ͽ� HitTest3D() ȣ��
				if ( Viewport.class.isInstance(child) == true )
				{
					Viewport child_viewport = (Viewport)child;
					Point pos_internal_2d = TransformToInternal2DPositionFromInternal3D(pos_x_internal, pos_y_internal, pos_z_internal);
					Point3D pos_inChild_3d = child_viewport.TransformToInternal3DPosition(pos_internal_2d.x, pos_internal_2d.y);
					
					if ( child_viewport.trigger_hide == false && child_viewport.trigger_ignoreDuringHitTest == false && child_viewport.HitTest3D(pos_inChild_3d.x, pos_inChild_3d.y, pos_inChild_3d.z) == true )
						return child;
				}
				//�׷��� ���� ��� ���� 3���� ���� ��ǥ�� �״�� ����Ͽ� ���� ����� HitTest3D() ȣ��
				else
				{
					if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest3D(pos_x_internal, pos_y_internal, pos_z_internal) == true )
						return child;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * <b>Viewport ������</b> 3���� ���� �ȿ���, �ش� ��ǥ ���� �ִ� ���� ��Ҹ� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ��� null�� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� �� �� �̻��� ��Ұ� �ִ� ��� ���� ���߿� �׷���(���� ���� ȭ�鿡 ���̴�) ��Ҹ� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �Ϲ����� �޼����� �ٸ��� Viewport.GetObjectAt3D()�� Viewport�� ���� �ܺ� ��ǥ�谡 �ƴ� Viewport �ڽ��� �ٷ�� ���� ��ǥ���� ��ǥ�� argument�� �޽��ϴ�. 
	 * ���� �ʿ��� ��� Viewport.TransformToInternal3DPosition()�� ȣ���Ͽ� �ܺ� 2���� ��ǥ�� ���� 3���� ��ǥ�� ��ȯ�� ���� �� �޼��带 ȣ���ؾ� �մϴ�. 
	 * �ٸ�, ���� Viewport �ȿ� �ٸ� Viewport�� ��� �ִ� ��쿡�� �� ������ �ڵ����� ������ �ֹǷ� 
	 * �������� �̷��� ������ ���� ������ �� ���� �Ƹ��� ���� �߻����� ���� ���Դϴ�.
	 * 
	 * @param pos_x_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal ���� ���θ� �˻���, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 * @param radius_z ���� ���θ� �˻��� �� �����ϴ� z�� ���� �������Դϴ�.
	 */
	public VisualObject3D GetObjectAt3D(double pos_x_internal, double pos_y_internal, double pos_z_internal, double radius_z)
	{
		if ( CheckVisibilityOfPosition3D(pos_x_internal, pos_y_internal, pos_z_internal, radius_z) == true )
		{
			for ( VisualObject3D child : children_3d_sorted )
			{
				//���� �ش� ���� ��Ұ� Viewport�� ��� �߰� ��ǥ ��ȯ�� ���� ���� Viewport�� ���� ���� 3���� ��ǥ�� ��ȯ�Ͽ� HitTest3D() ȣ��
				if ( Viewport.class.isInstance(child) == true )
				{
					Viewport child_viewport = (Viewport)child;
					Point pos_internal_2d = TransformToInternal2DPositionFromInternal3D(pos_x_internal, pos_y_internal, pos_z_internal);
					Point3D pos_inChild_3d = child_viewport.TransformToInternal3DPosition(pos_internal_2d.x, pos_internal_2d.y);
					
					if ( child_viewport.trigger_hide == false && child_viewport.trigger_ignoreDuringHitTest == false && child_viewport.HitTest3D(pos_inChild_3d.x, pos_inChild_3d.y, pos_inChild_3d.z, radius_z) == true )
						return child;
				}
				//�׷��� ���� ��� ���� 3���� ���� ��ǥ�� �״�� ����Ͽ� ���� ����� HitTest3D() ȣ��
				else
				{
					if ( child.trigger_hide == false && child.trigger_ignoreDuringHitTest == false && child.HitTest3D(pos_x_internal, pos_y_internal, pos_z_internal) == true )
						return child;
				}
			}
		}
		
		return null;
	}
	
	
	/* ---------------------------------------------------
	 * 
	 * �����ǥ ��� �� ��ǥ ��ȯ�� ���� �޼����
	 * 
	 */
	
	/**
	 * �ش� ���� ��Ҹ� �������� �ϴ�, Viewport�� ���� �ִ� 2���� ��� �� ��ǥ�� ����, Viewport�� �ٷ�� 3���� ���� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * ����: �� ��ҿ� ���� ��� ���� �ִ� ��ǥ�� ������� �����Ƿ� ������� �����ǥ�� pox_z ���� �׻� 0�� �˴ϴ�.
	 * 
	 * @param origin 3���� �����ǥ�� ����� ������ �Ǵ� ����Դϴ�.
	 * @param x 3���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y 3���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */	
	public Point3D GetRelativePosition3DFrom(VisualObject3D origin, int x, int y)
	{
		//�ش� ��Ұ� Viewport�� ���� ��Ұ� �ƴ� ��� ����
		if ( children_3d_sorted.contains(origin) == false )
			return null;
		
		Point pos_internal = TransformToInternalPosition(x, y);
		
		//�ش� ��Ұ� Viewport�� ���� ����� ��� �׳� �ش� ��ҿ� ���� VisualObject3D.GetRelativePosition3D() ���� ȣ��
		//--> 3D to 2D ��ǥ ��ȯ�� �� ��� ȥ�ڼ��� ��� �� �� �� ����
		return origin.GetRelativePosition3D(pos_internal.x, pos_internal.y);
	}

	/**
	 * �ش� ���� ��Ҹ� �������� �ϴ�, <b>Viewport ������</b> 3���� ���� �� ��ǥ�� ����, Viewport�� �ٷ�� 2���� ��� �� �����ǥ�� ��ȯ�մϴ�.<br>
	 * �̴� ��, 2���� view ��鿡 3���� ��ҵ��� ��ġ�ϵ� ���������� 2���� ������ �ش� ���� ��Ҹ� view ������� ��� �־��� ��ǥ�� project�Ͽ� ��ġ�� 2���� ��ǥ�� ������� �ǹ��մϴ�.<br>
	 * <br>
	 * <b>����:</b><br>
	 * �Ϲ����� �޼����� �ٸ��� Viewport.GetRelativePositionFrom()�� Viewport�� ���� �ܺ� ��ǥ�谡 �ƴ� Viewport �ڽ��� �ٷ�� ���� ��ǥ���� ��ǥ�� argument�� �޽��ϴ�.
	 * ���� �ʿ��� ��� Viewport.TransformToInternal3DPosition()�� ȣ���Ͽ� �ܺ� 2���� ��ǥ�� ���� 3���� ��ǥ�� ��ȯ�� ���� �� �޼��带 ȣ���ؾ� �մϴ�.
	 * 
	 * @param origin 2���� �����ǥ�� ����� ������ �Ǵ� ����Դϴ�.
	 * @param pos_x_internal 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal 2���� �����ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� ���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public Point GetRelativePositionFrom(VisualObject3D origin, double pos_x_internal, double pos_y_internal, double pos_z_internal)
	{
		//�ش� ��Ұ� Viewport�� ���� ��Ұ� �ƴ� ��� ����
		if ( children_3d_sorted.contains(origin) == false )
			return null;
		
		double factor_z = ( origin.pos_z - pos_z_internal ) / view_baseDistance;
		
		//Viewport.Draw(g)�� �޸� ���⼱ factor_z�� ������ �� �����Ƿ�(��� ��ǥ�� ���� ��Һ��� �� '�ָ�' �ִ� ���) ���밪�� ����Ͽ� ���
		if ( factor_z < 0 )
			factor_z = -factor_z;
		
		double pos_x_projected = ( pos_x_internal - origin.pos_x ) / factor_z + origin.pos_x;
		double pos_y_projected = ( pos_y_internal - origin.pos_y ) / factor_z + origin.pos_y;
		
		//����������, project�� 3���� ��ǥ�� 2���� ��ǥ�� ��ȯ�Ͽ� ��ȯ
		return TransformToInternal2DPositionFromInternal3D(pos_x_projected, pos_y_projected, origin.pos_z);
	}

	/**
	 * Viewport�� ���� �ִ� 2���� ��� ���� ��ǥ�� ����, Viewport�� �ٷ�� 3���� ���� �� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� ���� ��ҵ� �� �ϳ� �̻��� �ִ� ��� �� ��ǥ�� �ش� ��ҿ� ���� pos_z���� ������ �˴ϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ���(�ش� ��ǥ�� view ��� ������ ��� ��� ����), �Ǵ� �ش� ��ǥ ���� 2���� ��Ұ� ���� �ִ� ��� null�� ��ȯ�մϴ�.
	 * 
	 * @param x 3���� ��ǥ�� ����� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y 3���� ��ǥ�� ����� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point3D TransformToInternal3DPosition(int x, int y)
	{
		//�ش� ��ǥ ���� � ���� ��Ұ� ���� �ִ��� ���� Ž��
		VisualObject target = GetObjectAt(x, y);
		
		//�ش� ��ǥ ���� �ƹ� ��ҵ� ���ų� �ֱ� �ѵ� 2���� ����� ��� ����
		if ( target == null || VisualObject3D.class.isInstance(target) == false )
			return null;
		
		VisualObject3D target3D = (VisualObject3D)target;
		
		//�ش� ��ǥ ���� 3���� ��Ұ� ���� ������ ���� 2���� ���� ������� ��� ���� ����
		if ( target3D.trigger_coordination == false )
			return null;
		
		//�ܺ� ��ǥ�� ���� ��ǥ�� ��ȯ
		Point pos_internal = TransformToInternalPosition(x, y);
		
		//���� ��ǥ�� ���� �ش� ��ҿ� ���� pos_z���� ���� 3���� ��ǥ�� ����� ��ȯ 
		return target3D.TransformTo3DPosition(pos_internal.x, pos_internal.y);
	}
	
	/**
	 * Viewport�� �ٷ�� 2���� ��� ���� ��ǥ�� ����, Viewport�� �ٷ�� 3���� ���� �� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * ���� �ش� ��ǥ ���� ���� ��ҵ� �� �ϳ� �̻��� �ִ� ��� �� ��ǥ�� �ش� ��ҿ� ���� pos_z���� ������ �˴ϴ�.<br>
	 * ���� �ش� ��ǥ ���� �ƹ� ��ҵ� ���� ���(�ش� ��ǥ�� view ��� ������ ��� ��� ����), �Ǵ� �ش� ��ǥ ���� 2���� ��Ұ� ���� �ִ� ��� null�� ��ȯ�մϴ�.
	 * 
	 * @param x_internal 3���� ��ǥ�� ����� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param y_internal 3���� ��ǥ�� ����� �ϴ�, Viewport�� ���� �ִ� 2���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 */
	public Point3D TransformToInternal3DPositionFromInternal2D(int x_internal, int y_internal)
	{
		//�ش� ��ǥ ���� � ���� ��Ұ� ���� �ִ��� ���� Ž��
		VisualObject target = GetObjectAt(TransformToExternalPosition(x_internal, y_internal));
		
		//�ش� ��ǥ ���� �ƹ� ��ҵ� ���ų� �ֱ� �ѵ� 2���� ����� ��� ����
		if ( target == null || VisualObject3D.class.isInstance(target) == false )
			return null;
		
		VisualObject3D target3D = (VisualObject3D)target;
		
		//�ش� ��ǥ ���� 3���� ��Ұ� ���� ������ ���� 2���� ���� ������� ��� ���� ����
		if ( target3D.trigger_coordination == false )
			return null;
		
		//���� ��ǥ�� ���� �ش� ��ҿ� ���� pos_z���� ���� 3���� ��ǥ�� ����� ��ȯ 
		return target3D.TransformTo3DPosition(x_internal, y_internal);
	}
	
	/**
	 * Viewport�� �ٷ�� 3���� ���� ���� ��ǥ�� ����, Viewport�� �ٷ�� 2���� ��� �� ��ǥ�� ����� ��ȯ�մϴ�.<br>
	 * ���� Viewport�� ���� �ش� ��ǥ�� �� �� ���� ��� null�� ��ȯ�մϴ�.
	 * 
	 * @param pos_x_internal 2���� ��ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� 3���� ��ǥ�� �ȿ����� x��ǥ�Դϴ�.
	 * @param pos_y_internal 2���� ��ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� 3���� ��ǥ�� �ȿ����� y��ǥ�Դϴ�.
	 * @param pos_z_internal 2���� ��ǥ�� ��ȯ�Ϸ� �ϴ�, �� Viewport�� �ٷ�� 3���� ��ǥ�� �ȿ����� z��ǥ�Դϴ�.
	 */
	public Point TransformToInternal2DPositionFromInternal3D(double pos_x_internal, double pos_y_internal, double pos_z_internal)
	{
		double factor_z = pointOfView_z - pos_z_internal;
		
		if ( factor_z >= view_minDistance && factor_z <= view_maxDistance )
		{
			factor_z /= view_baseDistance;
			
			int x_internal = (int)( view_width / 2 + ( pos_x_internal - pointOfView_x ) / factor_z );
			int y_internal = (int)( view_height / 2 - ( pos_y_internal - pointOfView_y ) / factor_z );
			
			if ( x_internal >= 0 && x_internal <= width && y_internal >= 0 && y_internal <= height )
			{
				return new Point(x_internal, y_internal);
			}
		}
		
		return null;
	}
}
