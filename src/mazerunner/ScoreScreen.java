package mazerunner;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class ScoreScreen extends BasicGameState
{
	private final int id;
	
	private float mapX;

	private Font font;
	private TrueTypeFont trueTypeFont;
	
	private String highScores;
	private String dates;
	
	public ScoreScreen(int state)
	{
		id = state;
	}

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException
	{
		font = new Font("monospace", Font.BOLD, 35);
        trueTypeFont = new TrueTypeFont(font, false);
	}
	

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		highScores = Game.hsm.getHighscoreString();
        dates = Game.hsm.getDateString();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		g.setColor(new Color(0, 0, 0, 0.8f));
		g.setFont(trueTypeFont);
		g.drawString("HighScores", gc.getWidth() / 2.7f, 50);
		g.resetFont();
		g.setColor(new Color(0, 0, 0, 0.5f));
		g.fillRect(gc.getWidth() / 4.1f, gc.getHeight() / 4.2f, 400, 220);
		g.setColor(Color.white);
		g.drawString(highScores, gc.getWidth() / 3.9f, gc.getHeight() / 4f);
		g.drawString(dates, gc.getWidth() /  1.9f, gc.getHeight() / 4f);
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{

		if(Game.input.isKeyPressed(Input.KEY_ESCAPE))
		{

			sbg.enterState(0, new FadeOutTransition(Color.black, 1500), null);
		}

	}

	@Override
	public int getID()
	{
		return id;
	}

}
