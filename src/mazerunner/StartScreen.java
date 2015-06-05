package mazerunner;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class StartScreen extends BasicGameState
{
	private int id;
	private Rectangle arcade;
	private Rectangle zen;
	private Rectangle maze;
	private Rectangle playerChoice;
	private Rectangle settings;
	private Rectangle scores;
	
	public StartScreen(int stateID)
	{
		id=stateID;
	}
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException
	{
		Game.gameType=1;
		arcade=new Rectangle(0.0f, 0.0f, 250f, 350f);
		zen = new Rectangle(250f, 0.0f, 250f, 350f);
		maze= new Rectangle(500f, 0.0f, 250f, 350f);
		settings = new Rectangle(0, 350, 375, 150);
		scores = new Rectangle(375, 350, 375, 150);
	}
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		Music m = Resources.getMusic("titleScreen");
		if(!m.playing())
			m.loop();
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setColor(Resources.getColor("myRed"));
		g.fill(arcade);
		g.setColor(Resources.getColor("myBlue"));
		g.fill(zen);
		g.setColor(Resources.getColor("myGreen"));
		g.fill(maze);
		g.setColor(Color.gray);
		g.fill(settings);
		g.setColor(Color.white);
		g.fill(scores);

		g.setColor(Color.black);
		g.drawString("ARCADE", Tools.centerTextX("ARCADE",  125), 175); 
		g.drawString("ZEN", Tools.centerTextX("ZEN", 375), 175); 
		g.drawString("MAZE", Tools.centerTextX("MAZE",  625), 175);
		g.drawString("SETTINGS", Tools.centerTextX("SETTINGS", 187), 425);
		g.drawString("SCORES", Tools.centerTextX("SCORES", 563), 425);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(Game.input.isMousePressed(0) || Game.input.isMousePressed(1)) 
		{ 
			playerChoice = new Rectangle(Game.input.getMouseX(), Game.input.getMouseY(), 1.0F, 1.0F); 
			if(playerChoice.intersects(arcade)) 
				startGame(1, sbg);
			else if(playerChoice.intersects(zen)) 
				startGame(2, sbg);
			else if(playerChoice.intersects(maze)) 
				startGame(3, sbg);
			else if(playerChoice.intersects(settings))
				sbg.enterState(States.SETTINGS, new FadeOutTransition(Color.black, 500), null);
			else if(playerChoice.intersects(scores))
				sbg.enterState(States.SCORESCREEN, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
			
		} 
		if(Game.input.isMousePressed(2)) 
			Game.debug = !Game.debug; 
		if(Game.input.isKeyPressed(1)) 
			gc.exit();
		if(Game.input.isKeyPressed(Input.KEY_S)) 
			sbg.enterState(States.SCORESCREEN, new FadeOutTransition(Color.black,500), new FadeInTransition(Color.black, 500));
	}
	public void startGame(int gameType, StateBasedGame sbg)
	{
		Game.gameType=gameType;
		sbg.enterState(States.LEVEL, new FadeOutTransition(Color.black, 500), null); 
	}
	public int getID()
	{
		return id;
	}
	
}
