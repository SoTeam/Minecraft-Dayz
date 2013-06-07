package org.kapip.dayz.game.thread;

import org.kapip.dayz.PLUGIN;

public class RemovePVPLogged implements Runnable {
	private String name;
	
	public RemovePVPLogged(String name){
		this.name = name;
	}
	
	@Override
	public void run() {
		PLUGIN.PVP_LOGGED.remove(name);
	}
}
