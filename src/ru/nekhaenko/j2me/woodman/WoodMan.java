package ru.nekhaenko.j2me.woodman;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class WoodMan extends MIDlet{

	private Game game; // Game Canvas для нашей игры
	
	public WoodMan() {
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		if (game != null)
		      game.stop();
	}

	protected void pauseApp() {
	}

	protected void startApp() throws MIDletStateChangeException {
		if (game == null) {
				try {
					game = new Game();
					game.start();
				}
				catch (IOException ioe) {
					System.out.println(ioe);
				}
		}
		Display.getDisplay(this).setCurrent(game);
	}

}
