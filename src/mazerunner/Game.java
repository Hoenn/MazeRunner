package mazerunner;
import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

public class Game extends StateBasedGame
{
	public static final String name = "Maze Runner";
	public static final int screenWidth= 750;
	public static final int screenHeight= 500;
	public static Font gameFont;
	public static boolean debug;
	public static int gameType;
	public static int musicVolume;
	public static int soundVolume;
	public static Input input;
	public static HighScoreManager hsm;
	public static Color aimAssistColor;
	public static Shape aimAssistShape;
	public static float defaultSoundVol=.5f;
	public static float defaultMusicVol=.1f;
	public Game(String name)
	{
		super(name);
		addState(new StartScreen(States.STARTSCREEN));
		addState(new Level(States.LEVEL));
		addState(new GameOver(States.GAMEOVER));
		addState(new ScoreScreen(States.SCORESCREEN));
		addState(new Settings(States.SETTINGS));
	}
	public static void main(String[] args)
	{
		hsm= new HighScoreManager();

		File f = new File("natives");
		if(f.exists())
		{
			System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
		}
		try
		{
			AppGameContainer appgc = new AppGameContainer(new Game("Maze Runner"));
			appgc.setDisplayMode(750, 500, false);
			appgc.supportsAlphaInBackBuffer();
			appgc.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
	public void initStatesList(GameContainer gc) throws SlickException
	{
		aimAssistColor = new Color(255, 255, 255);
		setAimAssistDot();
		new Resources();
		gc.setMusicVolume(Game.defaultMusicVol);
		gc.setSoundVolume(Game.defaultSoundVol);
		gc.setMaximumLogicUpdateInterval(60);
		gc.setTargetFrameRate(60);
		gc.setAlwaysRender(true);
		gc.setVSync(true);
		gc.getGraphics().setAntiAlias(true);
		gc.setShowFPS(true);
		gameFont=gc.getGraphics().getFont();
		gc.setUpdateOnlyWhenVisible(false);
		debug=false;
		getState(States.STARTSCREEN).init(gc, this);
		getState(States.LEVEL).init(gc, this);
		getState(States.GAMEOVER).init(gc, this);
		getState(States.SCORESCREEN).init(gc, this);
		getState(States.SETTINGS).init(gc, this);
		enterState(States.STARTSCREEN, new FadeInTransition(Color.black, 500), null);
		input = gc.getInput();	
	}	
	public static void setAimAssistLine()
	{
		aimAssistShape = new Line(0, 0, 200, 0);

	}
	public static void setAimAssistDot()
	{
		aimAssistShape = new Rectangle(0,0, 2, 2);
	}

}
