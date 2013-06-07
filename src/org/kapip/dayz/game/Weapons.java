package org.kapip.dayz.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.thread.weapon.FireShotDelay;
import org.kapip.dayz.game.thread.weapon.RemoveLastFired;

public class Weapons {
	public static ArrayList<String> LAST_FIRED = new ArrayList<String>();

	
	//pistol fire rates
	public static void firePistol(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 15);
	}
	public static void firePistol1(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 15);
	}
	public static void firePistol2(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 13);
	}
	public static void firePistol3(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 11);
	}
	public static void firePistol4(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 9);
	}
	
	
	//shotgun fire rates
	public static void fireShotgun(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 30);
	}
	public static void fireShotgun1(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 30);
	}
	public static void fireShotgun2(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 25);
	}
	public static void fireShotgun3(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 20);
	}
	public static void fireShotgun4(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 15);
	}
	
	//assault rifle fire rates
	public static void fireCombatRifle(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 10);
	}
	public static void fireCombatRifle1(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 10);
	}
	public static void fireCombatRifle2(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 8);
	}
	public static void fireCombatRifle3(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 7);
	}
	public static void fireCombatRifle4(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 6);
	}
	
	//LMG fire rates
	public static void fireMachineGun(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 8);
	}
	public static void fireMachineGun1(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 8);
	}
	public static void fireMachineGun2(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 6);
	}
	public static void fireMachineGun3(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 4);
	}
	public static void fireMachineGun4(Player p){
		String name = p.getName();
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 2);
	}
	
	public static void fireSniper(String name){
		LAST_FIRED.add(name);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemoveLastFired(name), 40);
	}
}
