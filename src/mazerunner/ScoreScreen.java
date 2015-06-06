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

public class ScoreScreen extends BasicGameState
{
	private final int id;
		
	private String highScores_Arcade;
	private String dates_Arcade;
	private String highScores_Zen;
	private String dates_Zen;
	private String highScores_Maze;
	private String dates_Maze;
	
	private int currentPage;
	
	public ScoreScreen(int state)
	{
		id = state;
	}

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException
	{

	}
	

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		currentPage=0;
		
		highScores_Arcade = Game.hsm.getHighscoreString_Arcade();
        dates_Arcade = Game.hsm.getDateString_Arcade();
        highScores_Zen = Game.hsm.getHighscoreString_Zen();
        dates_Zen = Game.hsm.getDateString_Zen();
        highScores_Maze = Game.hsm.getHighscoreString_Maze();
        dates_Maze = Game.hsm.getDateString_Maze();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		g.setColor(Color.white);
 
		if(currentPage==0)
		{
			g.drawString("HighScores: Arcade", Tools.centerTextX("HighScores: Arcade", gc.getWidth()/2), 50);
			g.setColor(Color.white);
			g.drawString(highScores_Arcade, gc.getWidth() / 3.5f, gc.getHeight() / 4f);
			g.drawString(dates_Arcade, gc.getWidth() /  2f, gc.getHeight() / 4f);
		}
		else if(currentPage==1)
		{
			g.drawString("HighScores: Zen", Tools.centerTextX("HighScores: Zen", gc.getWidth()/2), 50);
			g.setColor(Color.white);
			g.drawString(highScores_Zen, gc.getWidth() / 3.5f, gc.getHeight() / 4f);
			g.drawString(dates_Zen, gc.getWidth() /  2f, gc.getHeight() / 4f);
		}
		else if(currentPage==2)
		{
			g.drawString("HighScores: Maze", Tools.centerTextX("HighScores: Maze", gc.getWidth()/2), 50);
			g.setColor(Color.white);
			g.drawString(highScores_Maze, gc.getWidth() / 3.5f, gc.getHeight() / 4f);
			g.drawString(dates_Maze, gc.getWidth() /  2f, gc.getHeight() / 4f);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{

		if(Game.input.isKeyPressed(Input.KEY_ESCAPE))
		{

			sbg.enterState(0, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
		}
		if(Game.input.isKeyPressed(Input.KEY_LEFT))
		{
			if(currentPage==0)
			{
				currentPage=2;
			}
			else
				currentPage--;
		}
		if(Game.input.isKeyPressed(Input.KEY_RIGHT))
		{
			if(currentPage==2)
			{
				currentPage=0;
			}
			else 
				currentPage++;
		}

	}

	@Override
	public int getID()
	{
		return id;
	}

}
