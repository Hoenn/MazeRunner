package mazerunner;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Level extends BasicGameState
{
	private int id;
	private int playerX ,playerY;
	private int playerHalfHeight, playerHalfWidth;
	private Animation playerAni;
	private Image playerAniSet[];
	private int aniSpeed[]={100, 100};
	private Rectangle hurtBox, colliBox;
	private int score;
	private ArrayList<Wall> wallList;
	private int basicWallWidth, basicWallSpacer;
	private long wallTimer;
	private float wallSpawnTime;
	private boolean lose;
	private float mazeSpeedTime=0;
	private boolean mazeSpeedUp;
	private Image[] backgrounds;
	private int background1_x=0;
	private int background2_x=500;
	
	public Level(int stateID)
	{
		id=stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		playerAniSet= new Image[] {Resources.getImage("player1"), Resources.getImage("player2")};
		playerAni = new Animation(playerAniSet, aniSpeed);
		playerX= gc.getWidth()/8;
		playerY= gc.getHeight()/2 -playerAni.getHeight();
		playerHalfHeight=playerAni.getHeight()/2;
		playerHalfWidth=playerAni.getWidth()/2;
		hurtBox = new Rectangle((playerX+playerHalfWidth)-12f, (playerY+playerHalfHeight), 24f, 24f);
		colliBox = new Rectangle((playerX + playerHalfWidth)-15f, (playerY + playerHalfHeight), 30F, 1000F);
		lose=false;
		wallList = new ArrayList<Wall>();
		wallTimer=0l;
		mazeSpeedUp=false;
		backgrounds = new Image[2];
		backgrounds[0] = Resources.getImage("background");
		backgrounds[1] = Resources.getImage("background");
	}
	
	public void resetArcade()
	{
		wallSpawnTime=1500f;
		Wall.moveSpeed=0.5f;
		basicWallSpacer=100;
		basicWallWidth=50;
		Resources.getSound("spawnArcade").play();
		Resources.getMusic("arcade").loop();
	}
	
	public void resetZen()
	{
		Wall.moveSpeed=1.0f;
		wallSpawnTime=700f;
		basicWallSpacer=90;
		basicWallWidth=50;
		Resources.getSound("spawnZen").play();
		Resources.getMusic("zen").loop();

	}
	
	public void resetMaze()
	{
		basicWallSpacer=100;
		wallSpawnTime=97f;
		basicWallWidth=50;
		hurtBox.setSize(20f, 20f);
		Wall.moveSpeed=1.025f;
		mazeSpeedUp=true;
		Resources.getSound("spawnMaze").play();
		Resources.getMusic("maze").loop();

	}
	
	public void mazeStartSpeedUp(int delta)
	{
		mazeSpeedTime+=delta;
		if(mazeSpeedTime>600L)
		{
			wallSpawnTime=2200f;
			Wall.moveSpeed=0.045f;
			mazeSpeedTime=0L;
			mazeSpeedUp=false;
		}
	}
	
	public void speedUp()
	{
		if(score%5 ==0 && Wall.moveSpeed <1.0f)
		{
			Wall.moveSpeed *= 1.05;
			wallSpawnTime/= 1.05;
		}
		if(score%10 ==0 && basicWallSpacer>40)
		{
			basicWallSpacer--;
		}
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		background1_x=0;
		background2_x=750;
		score=0;
		wallList.clear();
		mazeSpeedTime=0;
		wallTimer=0;
		lose=false;
		switch(Game.gameType)
		{
			case 1: resetArcade();
					break;
			case 2: resetZen();
					break;
			case 3: resetMaze();
					break;
		}
		if(!Game.debug)
		{
			gc.setMouseGrabbed(true);
			gc.setShowFPS(true);
		}
		else
		{
			gc.setMouseGrabbed(false);
			gc.setShowFPS(false);
		}
		if(Game.aimAssistShape instanceof Rectangle)
		{
			Game.aimAssistShape.setX(playerX+ gc.getWidth()/3);
		}
		if(Game.aimAssistShape instanceof Line)
		{
			Game.aimAssistShape.setX(playerX+playerAni.getWidth()+5);
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		backgrounds[0].draw(background1_x, 0);
		backgrounds[1].draw(background2_x, 0);

		for(Wall w: wallList)
		{
			g.rotate(w.getTop().getCenterX(), w.getTop().getCenterY(), 180);
			g.fillRect(w.getTop().getX(), w.getTop().getY(), w.getTop().getWidth(), w.getTop().getHeight(), Resources.getImage("wallbg2"), 0, 0);
			g.resetTransform();
			g.fillRect(w.getBottom().getX(), w.getBottom().getY(), w.getBottom().getWidth(), w.getBottom().getHeight(), Resources.getImage("wallbg1"), 0, 0);
		}
		g.drawAnimation(playerAni, playerX, playerY);//playerAni.draw(playerX, playerY);
		g.setColor(Game.aimAssistColor);
		if(Game.aimAssistShape instanceof Line)
			g.draw(Game.aimAssistShape);
		else
			g.fill(Game.aimAssistShape);
		
		g.setColor(Color.white);

		g.drawString(Integer.toString(score), Tools.centerTextX(Integer.toString(score), 375), 62f);
		if(Game.debug)
		{
			g.draw(colliBox);
			g.setColor(Color.red);
			g.draw(hurtBox);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(background1_x>-750)
		{
			background1_x--;
		}
		else
		{
			background1_x=750;
		}
		if(background2_x>-750)
		{
			background2_x--;
		}
		else
			background2_x=750;
		if(mazeSpeedUp)
		{
			mazeStartSpeedUp(delta);
		}
		
		if(wallTimer>20000L)
		{
			wallTimer=0;
		}
		wallTimer+=delta;
		if((float)wallTimer>wallSpawnTime)
		{
			wallList.add(new Wall(basicWallWidth, basicWallSpacer));
			wallTimer=0L;
		}
		for(int i=0; i<wallList.size();i++)
		{
			Wall w = wallList.get(i);
			w.wallUpdate(delta);
			if(w.getTop().intersects(colliBox)||w.getBottom().intersects(colliBox))
			{
				if(w.getTop().intersects(hurtBox)||w.getBottom().intersects(hurtBox))
				{
					if(!Game.debug)
					{
						Resources.getSound("hit").play();
						switch(Game.gameType)
						{
							case 1:	Game.hsm.addArcadeScore("You", score);
									break;
							case 2: Game.hsm.addZenScore("You", score);
									break;
							case 3: Game.hsm.addMazeScore("You", score);
									break;
						}
						lose=true;
					}
				}
				else if(!w.checkPassed())
				{
					addScore();
					w.passed();
				}
				
			}
			if(w.isRemovable())
				wallList.remove(i);
		}
		
		if(Game.input.isMouseButtonDown(0)|| Game.input.isMouseButtonDown(1))
		{

			updatePlayerPos();
			updateBoxes();
		}
		else if(!Game.debug  )
		{
			lose=true;
		}
		if(lose)
		{
			if(!mazeSpeedUp)
			{
				sbg.enterState(States.GAMEOVER, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
				gc.setMouseGrabbed(false);
			}
		}
		if(Game.input.isKeyPressed(1))
		{
			sbg.enterState(States.STARTSCREEN, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
			gc.setMouseGrabbed(false);
		}
	}
	
	public void updatePlayerPos()
	{
		playerY=Game.input.getMouseY()-playerHalfHeight;
	}
	
	public void updateBoxes()
	{
		float height= playerY+playerAni.getHeight()/2;
		hurtBox.setY(height - hurtBox.getHeight() / 2.0F); 
		colliBox.setY(height- colliBox.getHeight() / 2.0F);
		Game.aimAssistShape.setY(height);
	}
	
	public void addScore()
	{
		score++;
		Resources.getSound("scoreUp").play();
		speedUp();
	}
	
	public int getID()
	{
		return id;
	}
}
