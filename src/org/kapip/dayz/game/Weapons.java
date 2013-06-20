package org.kapip.dayz.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.thread.weapon.RemoveLastFired;

public class Weapons {
	public static ArrayList<String> LAST_FIRED = new ArrayList<String>();
	
	//pistol fire rates
	public static void firePistol(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 15);
	}

	//shotgun fire rates
	public static void fireShotgun(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 35);
	}
	
	//assault rifle fire rates
	public static void fireCombatRifle(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 10);
	}
	
	//LMG fire rates
	public static void fireMachineGun(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 12);
	}
	
	public static void fireSniper(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 60);
	}
}
