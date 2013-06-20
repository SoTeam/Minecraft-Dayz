package org.kapip.dayz.game.thread;

import org.bukkit.entity.Entity;
import org.kapip.dayz.game.Game;

public class TickArrows implements Runnable{
	@Override
	public void run() {
		for(Entity e : Game.arrowsFired){
			e.setVelocity(e.getVelocity().setY(e.getVelocity().getY()+.0872));
		}
	}
}
