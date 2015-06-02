package mazerunner;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartScreen extends BasicGameState
{
	private int id;
	private Rectangle arcade;
	private Rectangle zen;
	private Rectangle maze;
	private Rectangle playerChoice;
	
	public StartScreen(int stateID)
	{
		id=stateID;
	}
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException
	{
		Game.gameType=3;
		arcade=new Rectangle(0.0f, 0.0f, 250f, 500f);
		zen = new Rectangle(250f, 0.0f, 250f, 500f);
		maze= new Rectangle(500f, 0.0f, 250f, 500f);
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setColor(Color.red);
		g.fill(arcade);
		g.setColor(Color.blue);
		g.fill(zen);
		g.setColor(Color.green);
		g.fill(maze);
		g.setColor(Color.black);
		g.drawString("ARCADE", Tools.centerTextX("ARCADE",  125), 250F); 
		g.drawString("ZEN", Tools.centerTextX("ZEN", 375), 250F); 
		g.drawString("MAZE", Tools.centerTextX("MAZE",  625), 250F);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(Game.input.isMousePressed(0) || Game.input.isMousePressed(1)) 
		{ 
			playerChoice = new Rectangle(Game.input.getMouseX(), Game.input.getMouseY(), 1.0F, 1.0F); 
			if(playerChoice.intersects(arcade)) 
				Game.gameType = 1; 
			else if(playerChoice.intersects(zen)) 
				Game.gameType = 2; 
			else if(playerChoice.intersects(maze)) 
				Game.gameType = 3; 
			sbg.enterState(States.LEVEL); 
		} 
		if(Game.input.isMousePressed(2)) 
			Game.debug = !Game.debug; 
		if(Game.input.isKeyPressed(1)) 
			gc.exit();
	}
	public int getID()
	{
		return id;
	}
	
}
