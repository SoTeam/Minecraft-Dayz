package org.kapip.dayz.game.thread.weapon;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class FireShotDelay implements Runnable {
	private Player p;
	
	public FireShotDelay(Player p){
		this.p = p;
	}
	
	@Override
	public void run() {
		p.launchProjectile(Arrow.class);
	}
}
