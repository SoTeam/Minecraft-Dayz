package org.kapip.dayz.game.thread;

import org.kapip.dayz.game.Game;

public class FillChests implements Runnable {
	@Override
	public void run() {
		Game.chestsEmptied.clear();
	}
}
