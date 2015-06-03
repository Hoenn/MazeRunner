package mazerunner;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level extends BasicGameState
{
	private int id;
	private int playerX;
	private int playerY;
	private int playerHalfHeight, playerHalfWidth;
	private Animation playerAni;
	private Image playerAniSet[];
	private int aniSpeed[]={100, 100};
	private Rectangle hurtBox;
	private Rectangle colliBox;
	private Rectangle aimAssist;
	private int score;
	private ArrayList<Wall> wallList;
	private int basicWallWidth;
	private int basicWallSpacer;
	private long wallTimer;
	private float wallSpawnTime;
	private boolean lose;
	private boolean mButtonLeft;
	private boolean mButtonRight;
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
		aimAssist= new Rectangle((playerX+ gc.getWidth()/3), playerY+playerHalfHeight, 2, 2);
		lose=false;
		wallList = new ArrayList<Wall>();
		wallTimer=0l;
		mazeSpeedUp=false;
		backgrounds = new Image[2];
		backgrounds[0] = new Image("res/background.png");
		backgrounds[1] = new Image("res/background.png");


	}
	public void resetArcade()
	{
		wallSpawnTime=1500f;
		Wall.moveSpeed=0.5f;
		basicWallSpacer=68;
		basicWallWidth=50;
		Resources.getSound("spawnArcade").play();
	}
	public void resetZen()
	{
		Wall.moveSpeed=1.0f;
		wallSpawnTime=700f;
		basicWallSpacer=90;
		basicWallWidth=50;
		Resources.getSound("spawnZen").play();
	}
	public void resetMaze()
	{
		basicWallSpacer=70;
		wallSpawnTime=97f;
		basicWallWidth=50;
		hurtBox.setSize(20f, 20f);
		Wall.moveSpeed=1.025f;
		mazeSpeedUp=true;
		Resources.getSound("spawnMaze").play();
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
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		backgrounds[0].draw(background1_x, 0);
		backgrounds[1].draw(background2_x, 0);
		Wall w;
		for(Iterator iterator = wallList.iterator(); iterator.hasNext();g.fillRect(w.getBottom().getX(), w.getBottom().getY(), w.getBottom().getWidth(), w.getBottom().getHeight(), Resources.getImage("wallbg"), 0, 0))
		{
			w = (Wall) iterator.next();
			g.fillRect(w.getTop().getX(), w.getTop().getY(), w.getTop().getWidth(), w.getTop().getHeight(), Resources.getImage("wallbg"), 0, 0);
		}
		g.drawAnimation(playerAni, playerX, playerY);//playerAni.draw(playerX, playerY);
		g.setColor(Color.white);
		g.fill(aimAssist);
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
		mButtonLeft=Game.input.isMouseButtonDown(0);
		mButtonRight=Game.input.isMouseButtonDown(1);
		if(mButtonLeft || mButtonRight)
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
		hurtBox.setY((float)(playerY + playerAni.getHeight() / 2) - hurtBox.getHeight() / 2.0F); 
		colliBox.setY((float)(playerY + playerAni.getHeight() / 2) - colliBox.getHeight() / 2.0F);
		aimAssist.setY(playerY + playerAni.getHeight() / 2);
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
