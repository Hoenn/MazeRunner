package mazerunner;
import java.io.File; 

import mazerunner.GameOver; 
import mazerunner.Level; 
import mazerunner.StartScreen; 

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame
{
	public static final String name = "Maze Runner";
	public static final int screenWidth= 750;
	public static final int screenHeight= 500;
	public static Font gameFont;
	public static boolean debug;
	public static int gameType;
	public static Input input;
	public Game(String name)
	{
		super(name);
		addState(new StartScreen(States.STARTSCREEN));
		addState(new Level(States.LEVEL));
		addState(new GameOver(States.GAMEOVER));
	}
	public static void main(String[] args)
	{
		File f = new File("natives");
		if(f.exists())
		{
			System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
		}
		try
		{
			AppGameContainer appgc = new AppGameContainer(new Game("Maze Runner"));
			appgc.setDisplayMode(750, 500, false);
			appgc.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
	public void initStatesList(GameContainer gc) throws SlickException
	{
		gc.setMaximumLogicUpdateInterval(60);
		gc.setTargetFrameRate(60);
		gc.setAlwaysRender(true);
		gc.setVSync(true);
		gc.setShowFPS(false);
		gameFont=gc.getGraphics().getFont();
		gc.setUpdateOnlyWhenVisible(false);
		new Resources();
		debug=false;
		getState(States.STARTSCREEN).init(gc, this);
		getState(States.LEVEL).init(gc, this);
		getState(States.GAMEOVER).init(gc, this);
		enterState(States.STARTSCREEN);
		input = gc.getInput();
		
		
		
	}	
	
}
