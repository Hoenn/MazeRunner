package mazerunner;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



public class Settings extends BasicGameState
{
	private int id;
	
	private Rectangle playerChoice;
	
	private Rectangle volumeUp;
	private Rectangle volumeDown;
	private Rectangle mute;

	private TextField rValueInput;
	private int newRValue;

	private TextField bValueInput;
	private int newBValue;
	
	private TextField gValueInput;
	private int newGValue;
	
	private int currentRGB;
	
	private Rectangle choiceLine;
	private Rectangle choiceDot;
	
	private Rectangle backButton;
	
	private Rectangle colorPreview;
	public Settings(int stateID)
	{
		id=stateID;
	}
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		
		mute = new Rectangle(100, 0, 183, 100);
		volumeDown = new Rectangle(283, 0, 183, 100);
		volumeUp= new Rectangle(466, 0, 183, 100);
		
		rValueInput = new TextField(gc, Game.gameFont, 300, 150, 150, 50);
		rValueInput.setAcceptingInput(true);
		rValueInput.setMaxLength(3);
		rValueInput.setBackgroundColor(Color.white);
		rValueInput.setInput(Game.input);
        rValueInput.setTextColor(Color.black);
        newRValue=Game.aimAssistColor.getRed();
			
		bValueInput = new TextField(gc, Game.gameFont, 300, 250, 150, 50);
		bValueInput.setAcceptingInput(true);
		bValueInput.setMaxLength(3);
		bValueInput.setBackgroundColor(Color.white);
		bValueInput.setInput(Game.input);
        bValueInput.setTextColor(Color.black);
        newBValue=Game.aimAssistColor.getBlue();
		
		gValueInput = new TextField(gc, Game.gameFont, 300, 350, 150, 50);
		gValueInput.setAcceptingInput(true);
		gValueInput.setMaxLength(3);
		gValueInput.setBackgroundColor(Color.white);
		gValueInput.setInput(Game.input);
        gValueInput.setTextColor(Color.black);
        newGValue=Game.aimAssistColor.getGreen();
        
        choiceLine = new Rectangle(0, 425, 100, 75);
        choiceDot = new Rectangle(650, 425, 100, 75);
        
        backButton = new Rectangle(0, 0, 100, 100);
        
        colorPreview = new Rectangle(150, 450, 450, 50);
        
	}
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		newRValue=Game.aimAssistColor.getRed();
		rValueInput.setText(Integer.toString(newRValue));
		rValueInput.setCursorPos(3);


		newBValue=Game.aimAssistColor.getBlue();
		bValueInput.setText(Integer.toString(newBValue));
		bValueInput.setCursorPos(3);


		newGValue=Game.aimAssistColor.getGreen();
		gValueInput.setText(Integer.toString(newGValue));
		gValueInput.setCursorPos(3);

		currentRGB=-1;
		
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		g.setColor(Color.lightGray);
		g.fill(mute);
		g.setColor(Color.gray);
		g.fill(volumeDown);
		g.setColor(Color.darkGray);
		g.fill(volumeUp);
		
		g.setColor(Color.black);
		g.drawString("Mute", Tools.centerTextX("Mute", 192), 40);
		g.drawString(" - ", Tools.centerTextX(" - ", 375), 40);
		g.drawString(" + ", Tools.centerTextX(" + ", 558), 40);
		
		
		g.setColor(Color.red);
		rValueInput.render(gc, g);
		g.setColor(Color.blue);
		bValueInput.render(gc, g);
		g.setColor(Color.green);
		gValueInput.render(gc, g);
		
		g.setColor(Game.aimAssistColor);
		g.fill(colorPreview);

		g.setColor(Color.white);
		g.draw(backButton);
		g.drawString("Back", Tools.centerTextX("Back", 50), 40);
		g.draw(choiceDot);
		g.draw(choiceLine);
		g.drawString("Line", Tools.centerTextX("Line", 50), 440);
		g.drawString("Dot", Tools.centerTextX("Dot", 700), 440);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		if(Game.input.isMousePressed(0)||Game.input.isMousePressed(1))
		{
			playerChoice = new Rectangle(Game.input.getMouseX(), Game.input.getMouseY(), 1.0F, 1.0F);
			if(playerChoice.intersects(mute))
			{
				if(gc.getMusicVolume()>0)
				{
					gc.setMusicVolume(0);
					gc.setSoundVolume(0);
				}
				else
				{
					gc.setMusicVolume(Game.defaultMusicVol);
					gc.setSoundVolume(Game.defaultSoundVol);
				}
			}
			if(playerChoice.intersects(volumeDown))
			{
				gc.setMusicVolume(gc.getMusicVolume()-.01f);
				gc.setSoundVolume(gc.getSoundVolume()-.03f);
				Resources.getSound("scoreUp").play();
			}
			if(playerChoice.intersects(volumeUp))
			{
				gc.setMusicVolume(gc.getMusicVolume()+.01f);
				gc.setSoundVolume(gc.getSoundVolume()+.03f);
				Resources.getSound("scoreUp").play();
			}
			if(playerChoice.intersects(backButton))
			{
				sbg.enterState(0, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
			}
			if(playerChoice.intersects(choiceLine))
			{
				Game.setAimAssistLine();
			}
			if(playerChoice.intersects(choiceDot))
			{
				Game.setAimAssistDot();
			}
		}

		if(Game.input.isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(0, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
		}
		if (Game.input.isKeyPressed(Input.KEY_TAB))
		{
			currentRGB++;
			if(currentRGB>2)
				currentRGB=0;
			switch(currentRGB)
			{
				case 0: rValueInput.setFocus(true);
						break;
				case 1: bValueInput.setFocus(true);
						break;
				case 2: gValueInput.setFocus(true);
						break;
						
			}
			
		
		}
		if (Game.input.isKeyPressed(Input.KEY_ENTER))
		{
			switch(currentRGB)
			{
				case 0: if(isNumeric(rValueInput.getText()))
						{
							Game.aimAssistColor.r=Float.parseFloat(rValueInput.getText())/255;
							newRValue=Game.aimAssistColor.getRed();
						}
						break;
				case 1: if(isNumeric(rValueInput.getText()))
						{
							Game.aimAssistColor.b=Float.parseFloat(bValueInput.getText())/255;
							newBValue=Game.aimAssistColor.getBlue();
						}
						break;
				case 2: if(isNumeric(rValueInput.getText()))
						{
							Game.aimAssistColor.g=Float.parseFloat(gValueInput.getText())/255;
							newGValue=Game.aimAssistColor.getGreen();
						}
						break;
			}
		}
	}
	private boolean isNumeric(String str)
	{
		  return str.matches("\\d+(\\.\\d+)?");  
	}
	@Override
	public int getID()
	{
		return id;
	}

}
