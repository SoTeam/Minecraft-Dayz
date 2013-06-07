package org.kapip.dayz.game.thread;

import org.bukkit.entity.Player;

public class SendMessage implements Runnable {
	private Player p;
	private String msg;
	
	public SendMessage(Player p, String msg){
		this.p = p;
		this.msg = msg;
	}
	
	@Override
	public void run() {
		p.sendMessage(msg);
	}
}
