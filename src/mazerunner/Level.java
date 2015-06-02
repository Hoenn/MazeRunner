package mazerunner;

import java.util.ArrayList;

import java.util.Iterator;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level extends BasicGameState
{
	private int id;
	private int playerX;
	private int playerY;
	private int playerHalfHeight;
	private Animation playerAni;
	private Image playerAniSet[];
	private int aniSpeed[]={100, 100};
	private Rectangle hurtBox;
	private Rectangle colliBox;
	private int score;
	private ArrayList<Wall> wallList;
	private int basicWallWidth;
	private int basicWallSpacer;
	private long wallTimer;
	private float wallSpawnTime;
	private boolean gameStart;
	private boolean lose;
	private boolean mButtonLeft;
	private boolean mButtonRight;
	private float mazeSpeedTime=0;
	private boolean mazeSpeedUp;
	
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
		hurtBox = new Rectangle((playerX+playerAni.getWidth())/2, (playerY+playerAni.getHeight())/2, 25f, 25f);
		colliBox = new Rectangle((playerX + playerAni.getWidth()) / 2, (playerY + playerAni.getHeight()) / 2, 30F, 1000F);
		gameStart=false;
		lose=false;
		wallList = new ArrayList<Wall>();
		wallTimer=0l;
		Wall.moveSpeed=0.35f;
		mazeSpeedUp=false;

	}
	public void resetArcade()
	{
		wallSpawnTime=1500f;
		Wall.moveSpeed=0.5f;
		basicWallSpacer=100;
		basicWallWidth=20;


	}
	public void resetZen()
	{
		Wall.moveSpeed=1.0f;
		wallSpawnTime=700f;
		basicWallSpacer=90;
		basicWallWidth=30;


	}
	public void resetMaze()
	{
		basicWallSpacer=70;
		wallSpawnTime=97f;
		basicWallWidth=50;
		hurtBox.setSize(20f, 20f);
		Wall.moveSpeed=1.025f;
		mazeSpeedUp=true;
	}
	public void mazeStartSpeedUp(int delta)
	{
		mazeSpeedTime+=delta;
		if(mazeSpeedTime>700L)
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
		score=0;
		wallList.clear();
		gameStart=false;
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
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setColor(Color.blue);
		Wall w;
		for(Iterator iterator = wallList.iterator(); iterator.hasNext();g.fill(w.getBottom()))
		{
			w = (Wall) iterator.next();
			g.fill(w.getTop());
		}
		g.drawAnimation(playerAni, playerX, playerY);//playerAni.draw(playerX, playerY);
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
						lose=true;
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
		mButtonLeft=Game.input.isMouseButtonDown(0);
		mButtonRight=Game.input.isMouseButtonDown(1);
		if(mButtonLeft || mButtonRight)
		{
			if(!gameStart)
				gameStart=true;
			updatePlayerPos();
			updateBoxes();
		}
		else if(!mButtonLeft || !mButtonRight && !Game.debug && gameStart)
		{
			lose=true;
		}
		if(gameStart&&lose)
		{
			sbg.enterState(States.GAMEOVER);;
			gc.setMouseGrabbed(false);
		}
		if(Game.input.isKeyPressed(1))
		{
			sbg.enterState(States.STARTSCREEN);
			gc.setMouseGrabbed(false);
		}
	}
	public void updatePlayerPos()
	{
		playerY=Game.input.getMouseY()-playerHalfHeight;
	}
	public void updateBoxes()
	{
		hurtBox.setX((float)(playerX + playerAni.getWidth() / 2) - hurtBox.getWidth() / 2.0F); 
		hurtBox.setY((float)(playerY + playerAni.getHeight() / 2) - hurtBox.getHeight() / 2.0F); 
		colliBox.setX((float)(playerX + playerAni.getWidth() / 2) - colliBox.getWidth() / 2.0F); 
		colliBox.setY((float)(playerY + playerAni.getHeight() / 2) - colliBox.getHeight() / 2.0F);
	}
	public void addScore()
	{
		score++;
		speedUp();
	}
	public int getID()
	{
		return id;
	}
}
