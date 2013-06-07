package org.kapip.dayz.game.thread.drink;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.kapip.dayz.game.User;

public class CheckDrink implements Runnable {
	private Player p;
	private int slot;
	
	public CheckDrink(Player p, int slot){
		this.p = p;
		this.slot = slot;
	}
	
	@Override
	public void run() {
		if(p.getInventory().getHeldItemSlot() == slot
				&& p.getItemInHand().getType().equals(Material.GLASS_BOTTLE)){
			User.drink(p);
			p.removePotionEffect(PotionEffectType.SLOW);
			p.removePotionEffect(PotionEffectType.CONFUSION);
		}
	}
}
