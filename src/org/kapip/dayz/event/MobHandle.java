package org.kapip.dayz.event;

import java.util.Random;

import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.Navigation;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.Game;

public class MobHandle implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onZombieBurn(EntityCombustEvent e){
		if(e.getEntityType().equals(EntityType.ZOMBIE))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onZombieSpawn(CreatureSpawnEvent e){
		((LivingEntity)e.getEntity()).setMaximumNoDamageTicks(5);
		
		int spawnx = e.getLocation().getBlockX(), x = Game.spawnLocation.getBlockX(),
				spawnz = e.getLocation().getBlockZ(), z = Game.spawnLocation.getBlockZ(), radius = 10;
		
		if(x > spawnx-radius && x < spawnx+radius && z > spawnz-radius && z < spawnz+radius){
			e.setCancelled(true);
			e.getEntity().remove();
			return;
		}
		
		for(Entity ent : e.getEntity().getNearbyEntities(7, 6, 7)){
			if(ent instanceof Zombie && e.getEntity() instanceof Zombie){
				e.setCancelled(true);
				e.getEntity().remove();
				return;
			}
		}
		
		for(Entity ent : e.getEntity().getNearbyEntities(20, 20, 20)){
			if(ent instanceof Player && e.getEntity() instanceof Zombie){
				e.setCancelled(true);
				e.getEntity().remove();
				return;
			}
		}
		
		if(!e.getEntityType().equals(EntityType.ZOMBIE)){
			e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.ZOMBIE);
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onZombieTarget(EntityTargetEvent e){
		e.setCancelled(true);
		
		if(PLUGIN.DEBUGMODE)
			return;	// Test code
		
		if(e.getEntityType().equals(EntityType.ZOMBIE) && e.getTarget() != null && e.getTarget() instanceof Player){
			CraftCreature z = (CraftCreature)((Zombie)e.getEntity());

			Navigation nav = ((EntityCreature)z.getHandle()).getNavigation();
			nav.a((EntityLiving)((CraftEntity)e.getTarget()).getHandle(), Game.zombieSpeed);
			
			for(Entity ent : z.getNearbyEntities(.85, .85, .85)){
				if(ent instanceof Player){
					Player p = (Player)ent;
					if(Game.gen.nextInt(7) == 1 && ((LivingEntity) e.getEntity()).hasLineOfSight(p))
						p.damage(Game.gen.nextInt(4)+1, e.getEntity());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onZombieDie(EntityDeathEvent e){
		e.setDroppedExp(0);
		
		if(e.getEntity() instanceof Zombie){
			e.getDrops().clear();
			
			Random r = new Random();
			int chainChance = 190, leatherChance = 110, swordMaxChance = 180;
			
			/* WEAPON SPAWNING */
			
			
			if(r.nextInt(190) == 50)
				e.getDrops().add(new ItemStack(Material.IRON_HOE));
			if(r.nextInt(150) == 23)
				e.getDrops().add(new ItemStack(Material.STONE_HOE));
			if(r.nextInt(90) == 23)
				e.getDrops().add(new ItemStack(Material.WOOD_HOE));
			
			if(r.nextInt(220) == 50)
				e.getDrops().add(new ItemStack(Material.IRON_SPADE));
			if(r.nextInt(200) == 50)
				e.getDrops().add(new ItemStack(Material.STONE_SPADE));
			if(r.nextInt(180) == 50)
				e.getDrops().add(new ItemStack(Material.WOOD_SPADE));
			
			if(r.nextInt(240) == 50)
				e.getDrops().add(new ItemStack(Material.IRON_AXE));
			if(r.nextInt(220) == 50)
				e.getDrops().add(new ItemStack(Material.STONE_AXE));
			if(r.nextInt(200) == 50)
				e.getDrops().add(new ItemStack(Material.WOOD_AXE));
			
			if(r.nextInt(260) == 50)
				e.getDrops().add(new ItemStack(Material.IRON_PICKAXE));
			if(r.nextInt(240) == 50)
				e.getDrops().add(new ItemStack(Material.STONE_PICKAXE));
			if(r.nextInt(220) == 50)
				e.getDrops().add(new ItemStack(Material.WOOD_PICKAXE));
			
			
			if(r.nextInt(swordMaxChance) == 15)
				e.getDrops().add(new ItemStack(Material.STONE_SWORD));
			
			/* ARMOR SPAWNING */
			
			if(r.nextInt(leatherChance) == 3)
				e.getDrops().add(new ItemStack(Material.LEATHER_HELMET));
			
			if(r.nextInt(leatherChance) == 3)
				e.getDrops().add(new ItemStack(Material.LEATHER_CHESTPLATE));
			
			if(r.nextInt(leatherChance) == 3)
				e.getDrops().add(new ItemStack(Material.LEATHER_LEGGINGS));
			
			if(r.nextInt(leatherChance) == 54)
				e.getDrops().add(new ItemStack(Material.LEATHER_BOOTS));
			
			if(r.nextInt(chainChance) == 3)
				e.getDrops().add(new ItemStack(Material.CHAINMAIL_HELMET));
			
			if(r.nextInt(chainChance) == 3)
				e.getDrops().add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			
			if(r.nextInt(chainChance) == 14)
				e.getDrops().add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			
			if(r.nextInt(chainChance) == 3)
				e.getDrops().add(new ItemStack(Material.CHAINMAIL_BOOTS));
			
			/* MISC SPAWNING */
			
			if(r.nextInt(9) == 2)
				e.getDrops().add(new ItemStack(Material.ARROW, (r.nextInt(5)+2)));
			
			if(r.nextInt(22) == 2)
				e.getDrops().add(new ItemStack(Material.SADDLE));
			
			if(r.nextInt(17) == 1)
				e.getDrops().add(new ItemStack(Material.BREAD, (r.nextInt(1)+1)));
			
			if(r.nextInt(20) == 3)
				e.getDrops().add(new ItemStack(Material.APPLE, (r.nextInt(1)+1)));
			
			if(r.nextInt(15) == 3)
				e.getDrops().add(new ItemStack(Material.PAPER, (r.nextInt(2)+1)));
			
			if(r.nextInt(80) == 1)
				e.getDrops().add(new ItemStack(Material.COMPASS));
			
			if(r.nextInt(120) == 1)
				e.getDrops().add(new ItemStack(Material.MAP));
			
			if(r.nextInt(90) == 1)
				e.getDrops().add(new ItemStack(Material.WATCH));
			
			if(r.nextInt(2000) == 3)
				e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 1));
		}
	}
}
