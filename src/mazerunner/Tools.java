package mazerunner;

import java.util.Random;
import mazerunner.Game;

public class Tools
{
	public static Random r = new Random();

	public static int centerTextX(String s, int xx)
	{
		int x = Game.gameFont.getWidth(s);
		x = xx - x /2;
		return x;
	}
}
