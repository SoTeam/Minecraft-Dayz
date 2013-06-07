package org.kapip.dayz.game.thread.weapon;

import org.kapip.dayz.game.Weapons;

public class RemoveLastFired implements Runnable {
	private String name;
	
	public RemoveLastFired(String name){
		this.name = name;
	}
	
	@Override
	public void run() {
		Weapons.LAST_FIRED.remove(name);
	}
}
