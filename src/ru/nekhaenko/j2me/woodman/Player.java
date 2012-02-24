package ru.nekhaenko.j2me.woodman;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

public class Player extends Sprite{
	private TiledLayer map;
	private TiledLayer exit;
	private String DIRECTION;
	private boolean JUMP;
	public int points=0;
	public int lives=1;
	private int jump_pos=0;
	private Game game;
	private Coin[] coins;
	public Player(Image image, int frameWidth, int frameHeight) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	  }
	public Player(Image image, int frameWidth, int frameHeight,int x,int y,TiledLayer map) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	    this.setPosition(x, y);
	    this.map = map;
	    this.setPosition(x, y);
	  }
	public void setCoins(Coin[] coins)
	{
		this.coins = coins;
	}
	public void setExit(TiledLayer exit)
	{
		this.exit = exit;
	}
	public void setGeme(Game game)
	{
		this.game = game;
	}
	public void moveLeft()
	{
		if(!this.collidesWith(exit, false))
		{
			if(!JUMP)
			{
				this.move(-2, 0);
				this.setDirection("BACK");
				for(int i=0;i<coins.length;i++)
				{
					if(this.collidesWith(coins[i], true))
					{
						if(coins[i].active = true)
						{
							this.points += coins[i].price;
							this.coins[i].active = false;
							this.coins[i].setVisible(false);
						}
					}
				}
				if(this.collidesWith(map,false))
				{
					this.move(+2, 0);
				}
			}
		}
		else
		{
			game.gameOver();
		}
	}
	public void moveRight()
	{
		if(!this.collidesWith(exit, false))
		{
			if(!JUMP)
			{
				this.move(+2, 0);
				this.setDirection("FORWARD");
				for(int i=0;i<coins.length;i++)
				{
					if(this.collidesWith(coins[i], true))
					{
						if(coins[i].active = true)
						{
							this.points += coins[i].price;
							this.coins[i].active = false;
							this.coins[i].setVisible(false);
						}
					}
				}
				if(this.collidesWith(map,false))
				{
					this.move(-2, 0);
				}
			}
		}
		else
		{
			game.gameOver();
		}
	}
	public void startGravitation()
	{
		if(!JUMP)
		{
			for(int i=0;i<5;i++)
			{
				this.move(0, +1);
				if(this.collidesWith(map,false))
				{
					this.move(0, -1);
				}
			}
		}
		else
		{
			if(jump_pos<11)
			{
				if(DIRECTION == "FORWARD")
					this.move(0, -2);
				if(DIRECTION == "BACK")
					this.move(0, -2);
				if(this.collidesWith(map,false))
				{
					if(DIRECTION == "FORWARD")
						this.move(0, +2);
					if(DIRECTION == "BACK")
						this.move(0, +2);
				}
				
			}
			else
			{
				if(jump_pos<16 && jump_pos>=11)
				{
					if(DIRECTION == "FORWARD")
						this.move(+2, -1);
					if(DIRECTION == "BACK")
						this.move(-2, -1);
					if(this.collidesWith(map,false))
					{
						if(DIRECTION == "FORWARD")
							this.move(-2, +1);
						if(DIRECTION == "BACK")
							this.move(+2, +1);
					}
				}
				else
				{
					jump_pos = 0;
					JUMP = false;
				}
			}
			jump_pos++;
		}
		
	}
	public void setDirection(String direction)
	{
		if(direction == "FORWARD" || direction == "BACK")
		{
			this.DIRECTION = direction;
		}
			
	}
	public void changeSprite()
	{
		if(DIRECTION == "FORWARD")
		{
			this.setFrame(0);
		}
		if(DIRECTION == "BACK")
		{
			this.setFrame(1);
		}
		if(DIRECTION == "FORWARD" && JUMP)
		{
			this.setFrame(2);
		}
		if(DIRECTION == "BACK" && JUMP)
		{
			this.setFrame(3);
		}
	}
	public void Jump()
	{
		if(!JUMP && jump_pos==0 || !JUMP && jump_pos==1)
		{
			JUMP = true;
		}
	}
}