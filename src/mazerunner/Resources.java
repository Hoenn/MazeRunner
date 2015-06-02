package mazerunner;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.*;
public class Resources
{
	private static Map<String, Image> images;
	private static Map<String, Sound> sounds;
	public Resources() throws SlickException
	{
		images = new HashMap<String, Image>();
		sounds = new HashMap<String, Sound>();
		images.put("player1", loadImage("res/playerAni_1.png"));
		images.put("player2", loadImage("res/playerAni_2.png"));
	}
	public static Image loadImage(String path) throws SlickException
	{
		return new Image(path, false, 2);
	}
	public static Image getImage(String name)
	{
		return (Image)images.get(name);
	}
	public static Sound loadSound(String path) throws SlickException
	{
		return new Sound(path);
	}
	public static Sound getSound(String name)
	{
		return (Sound)sounds.get(name);
	}
}
