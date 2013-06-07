package org.kapip.dayz.event;

import java.util.Random;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet18ArmAnimation;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPlayer;
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
	/* Shotgun */
			if(User.hasShotgun1(p)){
			    Random random = new Random();
			    ent.setVelocity(new Vector(
			    		(ent.getVelocity().getX()*1.8)+((random.nextFloat()-random.nextFloat())/2.2),
			    		(ent.getVelocity().getY()*1.97),
			    		(ent.getVelocity().getZ()*1.8)+((random.nextFloat()-random.nextFloat())/2.2)
			    ));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasShotgun2(p)){
			    Random random = new Random();
			    ent.setVelocity(new Vector(
			    		(ent.getVelocity().getX()*1.8)+((random.nextFloat()-random.nextFloat())/2.2),
			    		(ent.getVelocity().getY()*1.97),
			    		(ent.getVelocity().getZ()*1.8)+((random.nextFloat()-random.nextFloat())/2.2)
			    ));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasShotgun3(p)){
			    Random random = new Random();
			    ent.setVelocity(new Vector(
			    		(ent.getVelocity().getX()*1.8)+((random.nextFloat()-random.nextFloat())/2.2),
			    		(ent.getVelocity().getY()*1.97),
			    		(ent.getVelocity().getZ()*1.8)+((random.nextFloat()-random.nextFloat())/2.2)
			    ));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasShotgun4(p)){
			    Random random = new Random();
			    ent.setVelocity(new Vector(
			    		(ent.getVelocity().getX()*1.8)+((random.nextFloat()-random.nextFloat())/2.2),
			    		(ent.getVelocity().getY()*1.97),
			    		(ent.getVelocity().getZ()*1.8)+((random.nextFloat()-random.nextFloat())/2.2)
			    ));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
	/*Assault Rifles*/
			else if(User.hasCombatRifle1(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasCombatRifle2(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasCombatRifle3(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
			else if(User.hasCombatRifle4(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
			    return;
			}
	/* Sniper */
			else if(User.hasSniper(p)){
				if(p.getInventory().contains(Material.ARROW, 1)){
					p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
					cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
					Game.arrowsFired.add(ent);
					Weapons.fireSniper(p.getName());
					p.sendMessage(ChatColor.DARK_GRAY+"Reloading sniper...");
				}
				else
					e.setCancelled(true);
				
				return;
			}
			
	/* Pistols */
			else if(User.hasPistol1(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*1.85,
			    		ent.getVelocity().getY()*1.9,
			    		ent.getVelocity().getZ()*1.85));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				return;
			}
			else if(User.hasPistol2(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*1.85,
			    		ent.getVelocity().getY()*1.9,
			    		ent.getVelocity().getZ()*1.85));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				return;
			}
			else if(User.hasPistol3(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*1.85,
			    		ent.getVelocity().getY()*1.9,
			    		ent.getVelocity().getZ()*1.85));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				return;
			}
			else if(User.hasPistol4(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*1.85,
			    		ent.getVelocity().getY()*1.9,
			    		ent.getVelocity().getZ()*1.85));
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				return;
			}
			
	/* LMG's */
			else if(User.hasMachineGun1(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
			    p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				return;
			}
			else if(User.hasMachineGun2(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
			    p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				return;
			}
			else if(User.hasMachineGun3(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
			    p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				return;
			}
			else if(User.hasMachineGun4(p)){
			    ent.setVelocity(new Vector(
			    		ent.getVelocity().getX()*2.7,
			    		ent.getVelocity().getY()*2.9,
			    		ent.getVelocity().getZ()*2.7));
			    p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 3);
				cp.netServerHandler.sendPacket(new Packet18ArmAnimation(cp, 1));
				return;
			}
		}
		
		e.getEntity().remove();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent e){
		Game.arrowsFired.remove(e.getEntity());
		e.getEntity().remove();
	}
}
