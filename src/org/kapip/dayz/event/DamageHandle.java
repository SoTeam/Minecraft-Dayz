package org.kapip.dayz.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.Game;
import org.kapip.dayz.game.User;

public class DamageHandle implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Arrow){
			Arrow a = (Arrow)e.getDamager();
			if(a.getShooter() instanceof Player){
				Player shooter = (Player)a.getShooter();
				PLUGIN.PVP_LOG.put(((Player)a.getShooter()).getName(), (int)((System.currentTimeMillis()/1000)+5));
				
				if(User.hasSniper(shooter))
					e.setDamage(30);
				
				else if(User.hasCombatRifle1(shooter))
					e.setDamage(6);
				else if(User.hasCombatRifle2(shooter))
					e.setDamage(8);
				else if(User.hasCombatRifle3(shooter))
					e.setDamage(10);
				else if(User.hasCombatRifle4(shooter))
					e.setDamage(12);
				
				else if(User.hasShotgun1(shooter))
					e.setDamage(6);
				else if(User.hasShotgun2(shooter))
					e.setDamage(10);
				else if(User.hasShotgun3(shooter))
					e.setDamage(12);
				else if(User.hasShotgun4(shooter))
					e.setDamage(14);
				
				else if(User.hasPistol1(shooter))
					e.setDamage(4);
				else if(User.hasPistol2(shooter))
					e.setDamage(6);
				else if(User.hasPistol3(shooter))
					e.setDamage(8);
				else if(User.hasPistol4(shooter))
					e.setDamage(10);
				
				else if(User.hasMachineGun1(shooter))
					e.setDamage(6);
				else if(User.hasMachineGun2(shooter))
					e.setDamage(8);
				else if(User.hasMachineGun3(shooter))
					e.setDamage(10);
				else if(User.hasMachineGun4(shooter))
					e.setDamage(14);
			}
		}
		
		if(e.getDamager() instanceof Player){
			Player attacker = (Player)e.getDamager();
		
			int random = Game.gen.nextInt(4)-2;
			
			if(e.getEntity() instanceof Zombie){
				if(User.hasAxe(attacker))
					e.setDamage(14+random);
				else if(User.hasCrowbar(attacker))
					e.setDamage(10+random);
				
			}
			else{
				if(User.hasAxe(attacker))
					e.setDamage(10+random);
				else if(User.hasCrowbar(attacker))
					e.setDamage(8+random);
			}
		}
		
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();

			int spawnx = p.getLocation().getBlockX(), x = Game.spawnLocation.getBlockX(),
					spawnz = p.getLocation().getBlockZ(), z = Game.spawnLocation.getBlockZ(), radius = 12;
			
			if(x > spawnx-radius && x < spawnx+radius && z > spawnz-radius && z < spawnz+radius){
				e.setCancelled(true);
				return;
			}
			
			if(e.getDamager() instanceof Arrow){
				Arrow a = (Arrow)e.getDamager();
				if(a.getShooter() instanceof Player)
					PLUGIN.PVP_LOG.put(((Player)a.getShooter()).getName(), (int)((System.currentTimeMillis()/1000)+5));
			}
			else if(e.getDamager() instanceof Player)
				PLUGIN.PVP_LOG.put(((Player)e.getDamager()).getName(), (int)((System.currentTimeMillis()/1000)+5));
			
			PLUGIN.PVP_LOG.put(p.getName(), (int)((System.currentTimeMillis()/1000)+5));
			
			Random r = new Random();
			
			if(r.nextInt(28) == 2){
				p.setMaximumNoDamageTicks(35);
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
				p.sendMessage(ChatColor.RED+"You are bleeding! Use bandages!");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBleed(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(e.getCause().equals(DamageCause.POISON) && p.getHealth() == 1){
				p.setHealth(0);
				Bukkit.broadcastMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" bled to death");
			}
		}
	}
}
