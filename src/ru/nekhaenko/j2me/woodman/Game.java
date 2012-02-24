package ru.nekhaenko.j2me.woodman;

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

public class Game extends GameCanvas implements Runnable {

	private TiledLayer gameMap; // карта игры
	private TiledLayer borders; // бардюры
	private TiledLayer background; // фон
	private Sprite top;
	private LayerManager layerManager;
	private int[] coinIndex1 = {
			5, 11, 6, 4, 11, 13, 15, 16, 17, 18 ,19 , 20, 21, 22, 23, 24, 25, 26, 27
	};//первый индекс монеток
	private int[] coinIndex2 = {
			3, 4, 6, 11, 8, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2
	};//первый индекс монеток
	private Player player;
	private boolean runing = false; // запущена ли игра
	
	public Game() throws IOException{
		super(true);
		gameMap = createGameMap();
		player = createPlayer();
		this.createGameExit();
		background = createBackground();
		layerManager = new LayerManager();
		Image image = Image.createImage("/top.png");
		top = new Sprite(image,240,24);
		layerManager.append(top);
		layerManager.append(player);
		this.createCoins();
		layerManager.append(gameMap);
		layerManager.append(background);
	}

	private Player createPlayer() throws IOException {
		Image image = Image.createImage("/player.png");
		Player pl = new Player(image,16,16,0,0,gameMap);
		pl.setGeme(this);
		return pl;
	}
	public void createCoins()
	{
		Coin[] coins = new Coin[coinIndex1.length];
		for(int i=0;i<coinIndex1.length;i++)
		{
			Random rand = new Random();
			int price = rand.nextInt(100);
			try {
				coins[i] = createCoin(coinIndex1[i], coinIndex2[i], price);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.player.setCoins(coins);
	}

	private TiledLayer createGameMap() throws IOException {
		Image image = Image.createImage("/map.png");
		TiledLayer tiledLayer = new TiledLayer(30, 15 , image, 20, 20);
		int[] map = {
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 2, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 2, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 2, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 2, 	0, 0, 0, 0, 2,	 2, 2, 2, 2, 2,	 2, 2, 2, 2, 2,	 2, 2, 2, 2, 2,
				2, 2, 2, 0, 0,	 2, 2, 2, 0, 2, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				
				0, 0, 0, 0, 2,	 2, 0, 0, 0, 2, 	0, 0, 2, 2, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 0, 0, 0, 2,	 0, 0, 2, 2, 2, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 2, 2, 2, 2,	 0, 2, 2, 0, 0, 	0, 0, 0, 0, 2,	 2, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,
				0, 0, 2, 0, 0,	 0, 2, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 2, 2, 2,	 2, 2, 2, 0, 2,
				2, 0, 2, 0, 2,	 2, 2, 0, 0, 0, 	0, 2, 2, 0, 0,	 0, 0, 0, 0, 0,	 0, 2, 2, 0, 0,	 2, 0, 0, 0, 2,
				
				0, 0, 2, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 2,	 2, 2, 0, 0, 0,	 2, 0, 2, 2, 2,
				0, 2, 2, 0, 0,	 0, 0, 0, 2, 2, 	0, 0, 0, 0, 0,	 2, 2, 2, 2, 2,	 0, 0, 0, 0, 0,	 2, 0, 2, 2, 2,
				0, 0, 0, 0, 2,	 0, 2, 0, 0, 0, 	0, 2, 2, 2, 2,	 2, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 2, 2, 2,
				0, 0, 0, 2, 2,	 2, 2, 2, 0, 0, 	2, 2, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 2, 2, 2, 2, 2,
				2, 2, 2, 2, 2,	 2, 2, 2, 2, 2, 	2, 2, 2, 2, 2,	 2, 2, 2, 2, 2,	 2, 2, 2, 2, 2,	 2, 2, 2, 2, 2
		};
		/*
		 * прорисовываем карту , используя массив (возможные значения 0-3)
		 */
		
			for (int i = 0; i < map.length; i++) 
			{
				int column = i % 30;
				int row = (i - column) / 30;
				tiledLayer.setCell(column, row, map[i]);
			}
		return tiledLayer;
	}
	private void createGameExit() throws IOException {
		Image image = Image.createImage("/map.png");
		TiledLayer tiledLayer = new TiledLayer(30, 15 , image, 20, 20);
		int[] map = {
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 1, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0
		};
		/*
		 * прорисовываем карту , используя массив (возможные значения 0-3)
		 */
		
			for (int i = 0; i < map.length; i++) 
			{
				int column = i % 30;
				int row = (i - column) / 30;
				tiledLayer.setCell(column, row, map[i]);
			}
		this.player.setExit(tiledLayer);
	}
	
	public Coin createCoin(int index1,int index2,int price) throws IOException
	{
		Image image = Image.createImage("/treasure.png");
		Coin coin = new Coin(image,20,20,index1*20,index2*20,price);
		this.layerManager.append(coin);
		return coin;
	}
	
	private TiledLayer createBackground() throws IOException {
		Image image = Image.createImage("/background.png");
		TiledLayer tiledLayer = new TiledLayer(1, 1 , image, 240, 291);
				tiledLayer.setCell(0, 0, 1);
		return tiledLayer;
	}
	private TiledLayer createGameOver() throws IOException {
		Image image = Image.createImage("/game_over.png");
		TiledLayer tiledLayer = new TiledLayer(1, 1 , image, 240, 291);
				tiledLayer.setCell(0, 0, 1);
		tiledLayer.setPosition(background.getX(), background.getY());
		return tiledLayer;
	}
	
	
	public void run() {
		Graphics g = getGraphics();
		int timeStep = 80;
	    int gr = 0;
	    while (runing) {
	      long start = System.currentTimeMillis();
	      
	      input();
	      render(g);
	      
	      long end = System.currentTimeMillis();
	      int duration = (int)(end - start);
	      
	      if (duration < timeStep) {
	        try { Thread.sleep(timeStep - duration); }
	        catch (InterruptedException ie) {}
	      }
	    }
	}
	
	private void render(Graphics g) {
		int w = getWidth();
	    int h = getHeight();

	    g.setColor(0xffffff);
	    g.fillRect(0, 0, w, h);
	    
	    int x = 0;
	    int y = 0;
	    player.changeSprite();
	    player.startGravitation();
	    
	    layerManager.paint(g, 0, 0);
	    this.moveCamera();
	    g.setColor(0x000000);
	    g.drawRect(x, y, w, h);
	    
	    flushGraphics();
	}

	public void start()
	{
		/*
		 * создаем поток и запускаем его
		 */
		runing = true;
	    Thread t = new Thread(this);
	    t.start();
	}
	private void input() {
	    int keyStates = getKeyStates();
	    if ((keyStates & LEFT_PRESSED) != 0)
	    {
	    	player.moveLeft();
	    }
	    else if ((keyStates & RIGHT_PRESSED) != 0)
	    {
	    	player.moveRight();
	    }
	    else if ((keyStates & UP_PRESSED) != 0)
	    {
	    	player.Jump();
	    }
	  }

	public void stop() {
		runing = false;
	}
	public void moveCamera()
	{
		background.setPosition(player.getX()-(getWidth()/2),  player.getY()-(getHeight()/2));
		layerManager.setViewWindow(player.getX()-(getWidth()/2), player.getY()-(getHeight()/2), getWidth(), getHeight());
		top.setPosition(background.getX(), background.getY()+getHeight()-15);
		Graphics g = getGraphics();
		g.setColor(0xFF0000);
		g.drawString("points: "+player.points, getWidth(), getHeight(),
		        Graphics.RIGHT | Graphics.BOTTOM);
		paint(g);
	}
	public void gameOver()
	{
		this.layerManager.remove(background);
		this.layerManager.remove(gameMap);
		this.layerManager.remove(player);
		this.layerManager.remove(top);
		try {
			this.layerManager.append(createGameOver());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
