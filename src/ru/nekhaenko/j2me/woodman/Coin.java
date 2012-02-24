package ru.nekhaenko.j2me.woodman;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Coin extends Sprite {
	public Coin(Image image, int frameWidth, int frameHeight) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	  }
	public Coin(Image image, int frameWidth, int frameHeight,int x,int y,int price) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	    this.price = price;
	    setPosition(x, y);
	    this.setFrame(0);
	  }
	public boolean active = true; // можно ли подобрать монетку!
	public int price = 10; // номиналь монетки
}
