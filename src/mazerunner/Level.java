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
	private float wallMoveSpeed;
	private boolean gameStart;
	private boolean lose;
	private boolean mButtonLeft;
	private boolean mButtonRight;
	
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
		basicWallSpacer = 70;
		basicWallWidth=20;
		wallTimer=0l;
		wallMoveSpeed=0.35f;
		wallSpawnTime=1000f;

	}
	public void resetArcade()
	{
		wallSpawnTime=1500f;
		wallMoveSpeed=0.5f;
	}
	public void resetZen()
	{
		wallMoveSpeed=1.0f;
		wallSpawnTime=700f;
	}
	public void resetMaze()
	{
		wallSpawnTime=4000f;
		basicWallWidth=50;
		hurtBox.setSize(20f, 20f);
		wallMoveSpeed=0.025f;
	}
	public void speedUp()
	{
		if(score%5 ==0 && wallMoveSpeed <1.0f)
		{
			wallMoveSpeed *= 1.100000001D;
			wallSpawnTime/= 1.10000001D;
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
		basicWallSpacer=70;
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
			w.wallUpdate(wallMoveSpeed, delta);
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
