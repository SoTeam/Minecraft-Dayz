package org.kapip.dayz.game.thread;

import org.kapip.dayz.game.Game;

public class CallSpawn implements Runnable {

	@Override
	public void run() {
		Game.spawnZombies();
	}
}
