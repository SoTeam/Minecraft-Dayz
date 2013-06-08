package org.kapip.dayz.event;

import java.util.Random;

import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.Packet18ArmAnimation;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.kapip.dayz.game.Game;
import org.kapip.dayz.game.User;
import org.kapip.dayz.game.Weapons;

public class Projectiles implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileLaunch(ProjectileLaunchEvent e){
		Entity ent = e.getEntity();
		
		if(e.getEntity().getShooter() instanceof Player && e.getEntityType().equals(EntityType.ARROW)){
			Player p = (Player)e.getEntity().getShooter();

			EntityPlayer cp = ((CraftPlayer)p).getHandle();
			
			if(Weapons.LAST_FIRED.contains(p.getName())){
				if(User.hasSniper(p)){
					p.sendMessage(ChatColor.DARK_GRAY+"Reloading sniper...");
					e.setCancelled(true);
					return;
				}
			}
			
			if(User.hasShotgun(p)){
			    Random random = new Random();
			    ent.setVelocity(new Vector(
			    		(ent.getVelocity().getX()*1.8)+((random.nextFloat()-random.nextFloat())/2.2),
			    		(ent.getVelocity().getY()*1.97),
			    		(ent.getVelocity().getZ()*1.8)+((random.nextFloat()-random.nextFloat())/2.2)
			    ));
				cp.playerConnection.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasCombatRifle(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
				cp.playerConnection.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasSniper(p)){
				if(p.getInventory().contains(Material.ARROW, 1)){
					p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
					cp.playerConnection.sendPacket(new Packet18ArmAnimation(cp, 1));
					Game.arrowsFired.add(ent);
					Weapons.fireSniper(p.getName());
					p.sendMessage(ChatColor.DARK_GRAY+"Reloading sniper...");
				}
				else
					e.setCancelled(true);
				
				return;
			}
			else if(User.hasPistol(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*1.85,
			    		ent.getVelocity().getY()*1.9,
			    		ent.getVelocity().getZ()*1.85));
				cp.playerConnection.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				return;
			}
			else if(User.hasMachineGun(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
			    p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				cp.playerConnection.sendPacket(new Packet18ArmAnimation(cp, 1));
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent e){
		Game.arrowsFired.remove(e.getEntity());
		e.getEntity().remove();
	}
}
