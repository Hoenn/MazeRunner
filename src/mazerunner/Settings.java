package mazerunner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Settings extends BasicGameState
{
	private int id;
	public Settings(int stateID)
	{
		id=stateID;
	}
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		if(Game.input.isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(0, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
		}
	}

	@Override
	public int getID()
	{
		return id;
	}

}
