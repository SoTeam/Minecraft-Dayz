package org.kapip.dayz.game.thread.drink;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kapip.dayz.game.User;

public class TickDrink implements Runnable {
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			String name = p.getName();
			
			if(User.thirst.containsKey(name)){
				Integer dr = User.thirst.get(name);
				
				if(dr-1 <= 0){
					if(!p.isDead())
					{
						p.sendMessage(ChatColor.RED+"You died of dehydration!");
						p.setHealth(0);
					}
				}
				else if(dr-1 == 2 || dr-1 == 1){
					p.sendMessage(ChatColor.DARK_RED+"You are dying of dehyrdation. Find water.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6000, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 6000, 1));
				}
				else if(dr-1 == 4){
					p.sendMessage(ChatColor.DARK_RED+"You need water soon, or you will die.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2397, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2397, 0));
				}
				else if(dr-1 == 6){
					p.sendMessage(ChatColor.DARK_RED+"You are becoming dehydrated.");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2397, 0));
				}
				else if(dr-1 == 8)
					p.sendMessage(ChatColor.RED+"You are really thirsty...");
				else if(dr-1 == 12)
					p.sendMessage(ChatColor.GOLD+"You are becoming thirsty.");
				else if(dr-1 == 16)
					p.sendMessage(ChatColor.DARK_GRAY+"You could go for some water right now.");
				
				User.thirst.put(name, dr-1);
			}
			else
				User.thirst.put(name, User.fullDrink);
		}
	}
}
