package mazerunner;

import mazerunner.Game;
import mazerunner.Tools;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState
{
	private int id;
	private Rectangle choiceYes;
	private Rectangle choiceNo;
	private Rectangle playerChoice;
	private Color yesColor;
	private Color noColor;
	
	private String contMsg="Continue?";
	private String dirMsg="Hold both mouse buttons to select";
	
	public GameOver(int stateId)
	{
		id=stateId;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		playerChoice=null;
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		choiceYes= new Rectangle(0.0f, 0.0f, 374f, 500f);
		choiceNo = new Rectangle(375f, 0.0f, 376f, 500f);
		yesColor = new Color(34f, 101f, 202f, 0.8f);
		noColor = new Color(210f, 50f, 50f, 0.5f);
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setColor(yesColor);
		g.fill(choiceYes);
		g.setColor(noColor);
		g.fill(choiceNo);
		g.setColor(Color.black);
		g.drawString(contMsg, Tools.centerTextX(contMsg, 375), 100F);
		g.drawString(dirMsg, Tools.centerTextX(dirMsg, 375), 125F);
		g.drawString("Yes", Tools.centerTextX("Yes", 187), 250F);
		g.drawString("No", Tools.centerTextX("No", 187) +375 , 250F);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(Game.input.isMousePressed(0)||Game.input.isMousePressed(1))
		{
			playerChoice = new Rectangle(Game.input.getMouseX(), Game.input.getMouseY(), 1.0f, 1.0f);
			if(playerChoice.intersects(choiceYes))
			{
				sbg.enterState(States.LEVEL);
			}
			else if(playerChoice.intersects(choiceNo))
			{
				sbg.enterState(States.STARTSCREEN);
			}
		}
	}
	public int getID()
	{
		return id;
	}
}
