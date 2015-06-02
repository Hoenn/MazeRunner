package mazerunner;

import org.newdawn.slick.geom.Rectangle;
import mazerunner.Tools;

public class Wall
{
	private Rectangle top;
	private Rectangle bottom;
	private int wallWidth;
	private boolean removable;
	private boolean passed;
	private int heightGenerator;
	public Wall(int ww, int spacer)
	{
		wallWidth=ww;
		passed=false;
		heightGenerator = Tools.r.nextInt(500-spacer);
		top = new Rectangle(750f, 0.0f, wallWidth, heightGenerator);
		bottom = new Rectangle(750f, top.getHeight()+(float)spacer, wallWidth, 500f-top.getHeight()-(float)spacer);
	}
	public void wallUpdate(float speed, int delta)
	{
		top.setX(top.getX()-speed*(float)delta);
		bottom.setX(bottom.getX()-speed*(float)delta);
		if(top.getX()<=(float)(-wallWidth))
		{
			removable=true;
		}
	}
	public void passed()
	{
		passed=true;
	}
	public boolean checkPassed()
	{
		return passed;
	}
	public Rectangle getTop()
	{
		return top;
	}
	public Rectangle getBottom()
	{
		return bottom;
	}
	public boolean isRemovable()
	{
		return removable;
	}
}
