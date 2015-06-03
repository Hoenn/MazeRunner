package mazerunner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
public class Resources
{
	private static Map<String, Image> images;
	private static Map<String, Sound> sounds;
	public static Map<String, Color> colors;
	public Resources() throws SlickException
	{
		images = new HashMap<String, Image>();
		sounds = new HashMap<String, Sound>();
		images.put("player1", loadImage("res/playerAni_1.png"));
		images.put("player2", loadImage("res/playerAni_2.png"));
		images.put("wallbg", loadImage("res/swordWall.png"));
		sounds.put("hit", loadSound("res/hit.wav"));
		sounds.put("spawnArcade", loadSound("res/spawn.wav"));
		sounds.put("spawnZen", loadSound("res/spawnZen.wav"));
		sounds.put("spawnMaze", loadSound("res/spawnMaze.wav"));
		Sequencer sequencer =null;
		try
		{
			sequencer = MidiSystem.getSequencer();
		} catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			sequencer.open();
		} catch (MidiUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is =null;
		try
		{
			is = new BufferedInputStream(new FileInputStream(new File("res/DuckTales_the_moon.mid")));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			sequencer.setSequence(is);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//StartsPlayingMusic 
		//sequencer.start();
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
}
