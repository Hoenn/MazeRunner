package mazerunner;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
public class Resources
{
	private static Map<String, Image> images;
	private static Map<String, Sound> sounds;
	private static Map<String, Music> music;
	private static Map<String, Color> colors;
	public Resources() throws SlickException
	{
		images = new HashMap<String, Image>();
		sounds = new HashMap<String, Sound>();
		music  = new HashMap<String, Music>();
		images.put("player1", loadImage("res/playerAni_1.png"));
		images.put("player2", loadImage("res/playerAni_2.png"));
		images.put("wallbg1", loadImage("res/swordWall.png"));
		images.put("wallbg2", loadImage("res/swordWall2.png"));
		sounds.put("hit", loadSound("res/hit.ogg"));
		sounds.put("scoreUp", loadSound("res/scoreUp.wav"));
		sounds.put("spawnArcade", loadSound("res/spawn.ogg"));
		sounds.put("spawnZen", loadSound("res/spawnZen.ogg"));
		sounds.put("spawnMaze", loadSound("res/spawnMaze.ogg"));
		music.put("titleScreen", loadMusic("res/titleScreen.ogg"));
		music.put("arcade", loadMusic("res/arcadeMode.ogg"));
		music.put("zen", loadMusic("res/zenMode.ogg"));
		music.put("maze", loadMusic("res/mazeMode.ogg"));

		colors = new HashMap<String, Color>();
		colors.put("myRed", new Color(217, 27, 12));
		colors.put("myBlue", new Color(15, 57, 224));
		colors.put("myGreen", new Color(15, 192, 42));

		
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
	public static Music loadMusic(String path) throws SlickException
	{
		return new Music(path, true);
	}
	public static Music getMusic(String name)
	{
		return (Music)music.get(name);
	}
	public static Color getColor(String name)
	{
		return (Color)colors.get(name);
	}
}
