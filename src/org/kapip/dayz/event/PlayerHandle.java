package org.kapip.dayz.event;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.server.EntityCreature;
import net.minecraft.server.Navigation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.entity.CraftCreature;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.Game;
import org.kapip.dayz.game.User;
import org.kapip.dayz.game.Weapons;
import org.kapip.dayz.game.thread.RemovePVPLogged;
import org.kapip.dayz.game.thread.SendMessage;
import org.kapip.dayz.game.thread.drink.CheckDrink;

public class PlayerHandle implements Listener {
	private static void logMessage(String message){
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onRespawn(PlayerRespawnEvent e){
		e.setRespawnLocation(PLUGIN.MAIN_WORLD.getSpawnLocation());
		User.thirst.put(e.getPlayer().getName(), User.fullDrink);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e){
		if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)
				&& !e.getBlockPlaced().getType().equals(Material.TORCH))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e){
		if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)
				&& !e.getBlock().getType().equals(Material.TORCH))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDie(PlayerDeathEvent e){
		Player p = e.getEntity();
		PLUGIN.PVP_LOG.remove(p.getName());
		User.thirst.put(p.getName(), User.fullDrink);
		
		if(p.getKiller() != null){
			switch(Game.gen.nextInt(3)){
				case 0:
					e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" was killed.");
					break;
			}
			
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" was murdered.");

			PLUGIN.PVP_LOG.remove(p.getKiller().getName());	
		}
		else if(p.getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK)) {
			try{
				EntityDamageEvent ede = e.getEntity().getLastDamageCause();
				EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent)ede;
				
				if(edbe.getDamager() instanceof Zombie){
					e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" was eaten by a "+ChatColor.GRAY+ChatColor.ITALIC+"Zombie");
					logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" was eaten by a "+ChatColor.GRAY+"Zombie");
				}
			}
			catch(Exception err){
				e.setDeathMessage(null);
			}
		}
		else if(p.getLastDamageCause().getCause().equals(DamageCause.FALL)){
			e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" fell to their death");
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" fell to their death");
		}
		else if(p.getLastDamageCause().getCause().equals(DamageCause.FIRE)){
			e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" burned to a crisp");
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" burned to a crisp");
		}
		else if(p.getLastDamageCause().getCause().equals(DamageCause.STARVATION)){
			e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" starved to death");
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" starved to death");
		}
		else if(p.getLastDamageCause().getCause().equals(DamageCause.SUFFOCATION)){
			e.setDeathMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" suffocated");
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" suffocated");
		}
		else{
			e.setDeathMessage(null);
			logMessage(ChatColor.GOLD+p.getName()+ChatColor.GRAY+" died");
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Location l = p.getLocation();
		
		if(p.isSprinting()){
			for(Entity ent : p.getNearbyEntities(21, 6, 21)){
				if(!p.getNearbyEntities(8, 6, 8).contains(ent)){
					Location el = ent.getLocation();
					double px, pz;
					
					if(ent instanceof Zombie){
						if(l.getX() > el.getX())
							px = el.getX()+((l.getX()-el.getX())/2);
						else
							px = el.getX()-((el.getX()-l.getX())/2);
						
						if(l.getZ() > el.getZ())
							pz = el.getZ()+((l.getZ()-el.getZ())/2);
						else
							pz = el.getZ()-((el.getZ()-l.getZ())/2);
						
						CraftCreature z = (CraftCreature)((Zombie)ent);
						
						Navigation nav = ((EntityCreature)z.getHandle()).getNavigation();
						nav.a(px, l.getY(), pz, Game.zombieSpeed);
					}
				}
			}	// for loop
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerUse(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		String name = p.getName();
		
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(b.getType().equals(Material.BED_BLOCK) || b.getType().equals(Material.BED)){
				e.setCancelled(true);
				return;
			}
			else if(b.getType().equals(Material.CHEST)){
				if(!Game.chestsEmptied.contains(b)){
					
				    BlockState bs = b.getState();
				    
				    if(bs instanceof Chest) {
				    	ArrayList<ItemStack> random = Game.spawnRandomItems(10);
				    	Chest chest = (Chest) bs;
					    Inventory i = chest.getInventory();
					   
					    /**
					    i.clear();
					    if(b.getLocation().getZ() < -400){
							random = Game.spawnRandomItems(30);
						}
					    else if(b.getLocation().getZ() < -800){
							random = Game.spawnRandomItems(40);
						}
					    else if(b.getLocation().getZ() < -1200){
							random = Game.spawnRandomItems(50);
						}
						**/
					    
					    
						Collections.shuffle(random);
						
						for(ItemStack is : random){
							i.addItem(is);
						}
						
						p.updateInventory();
						Game.chestsEmptied.add(b);
				    }
				}
				return;
			}
			
			System.gc();
		}
		
		if((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			ItemStack is = p.getItemInHand();
			boolean use = false;
			
			try{
				Material m = b.getType();
				use = m.equals(Material.CHEST) || m.equals(Material.BED_BLOCK) || m.equals(Material.STONE_BUTTON)||
						m.equals(Material.LEVER) || m.equals(Material.WOOD_DOOR) || m.equals(Material.IRON_DOOR) ||
						m.equals(Material.IRON_DOOR_BLOCK) || m.equals(Material.MINECART);
			}
			catch(NullPointerException err){}

			/*So you don't shoot or drink water when you use something*/
			if(!use){
				if(User.hasWaterBottle(p))
					Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new CheckDrink(p, p.getInventory().getHeldItemSlot()), 32L);
				
			//pistol effect
				if(User.hasPistol1(p)){			
					if(p.getInventory().contains(Material.ARROW, 1) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
						p.launchProjectile(Arrow.class);
						Weapons.firePistol(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}	
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasPistol2(p)){			// Pistol effect
					if(p.getInventory().contains(Material.ARROW, 1) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
						p.launchProjectile(Arrow.class);
						Weapons.firePistol2(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}	
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasPistol3(p)){			// Pistol effect
					if(p.getInventory().contains(Material.ARROW, 1) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
						p.launchProjectile(Arrow.class);
						Weapons.firePistol3(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}	
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasPistol4(p)){			// Pistol effect
					if(p.getInventory().contains(Material.ARROW, 1) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
						p.launchProjectile(Arrow.class);
						Weapons.firePistol4(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}	
						
						p.updateInventory();
					}
					return;
				}
				
				if(User.hasShotgun1(p)){			// "Shotgun" effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						for(int i = 0;i < 6;i++){
							p.launchProjectile(Arrow.class);
						}
						Weapons.fireShotgun(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasShotgun2(p)){			// "Shotgun" effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						for(int i = 0;i < 6;i++){
							p.launchProjectile(Arrow.class);
						}
						Weapons.fireShotgun2(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasShotgun3(p)){			// "Shotgun" effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						for(int i = 0;i < 6;i++){
							p.launchProjectile(Arrow.class);
						}
						Weapons.fireShotgun3(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasShotgun4(p)){			// "Shotgun" effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						for(int i = 0;i < 6;i++){
							p.launchProjectile(Arrow.class);
						}
						Weapons.fireShotgun4(name);
						
						is.setDurability((short)(is.getDurability()+1));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				
				if(User.hasCombatRifle1(p)){	
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireCombatRifle(name);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasCombatRifle2(p)){	
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireCombatRifle2(name);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasCombatRifle3(p)){	
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireCombatRifle3(name);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasCombatRifle4(p)){	
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireCombatRifle4(name);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				
				if(User.hasMachineGun1(p)){			// Machine gun effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireMachineGun(p);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasMachineGun2(p)){			// Machine gun effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireMachineGun2(p);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasMachineGun3(p)){			// Machine gun effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireMachineGun3(p);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				if(User.hasMachineGun4(p)){			// Machine gun effect
					if(p.getInventory().contains(Material.ARROW) && !Weapons.LAST_FIRED.contains(p.getName())){
						p.getInventory().removeItem(new ItemStack(Material.ARROW));
						p.launchProjectile(Arrow.class);
	
						Weapons.fireMachineGun4(p);
						
						is.setDurability((short)(is.getDurability()+3));
						if(is.getDurability() > is.getType().getMaxDurability()){
							is.setDurability(is.getType().getMaxDurability());
							p.setItemInHand(new ItemStack(0));
						}
						
						p.updateInventory();
					}
					return;
				}
				
				if(User.hasBandage(p)){
					if(p.getHealth() < 20){
						p.getInventory().removeItem(new ItemStack(Material.PAPER, 1));
						
						if(p.hasPotionEffect(PotionEffectType.POISON)){
							p.removePotionEffect(PotionEffectType.POISON);
							p.sendMessage(ChatColor.GREEN+"You used bandages and stopped bleeding!");
						}
						else
							p.sendMessage(ChatColor.GREEN+"You used bandages!");
						
						if(p.getHealth() <= 18)
							p.setHealth(p.getHealth()+2);
						else
							p.setHealth(20);
						
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
					}
					else
						p.sendMessage(ChatColor.RED+"Your health is already full.");
					
					p.updateInventory();
				}
				/*
				if(User.hasBandage(p)){
					if(p.hasPotionEffect(PotionEffectType.POISON)){
						p.getInventory().removeItem(new ItemStack(Material.PAPER, 1));
						
						p.sendMessage(ChatColor.GRAY+"You used a bandage and are no longer bleeding.");
						
						p.removePotionEffect(PotionEffectType.POISON);
					}
					else
						p.sendMessage(ChatColor.GRAY+"You are not bleeding.");
					
					p.updateInventory();
				}
				*/
				
				return;
				
			}
			/*
			if(User.hasBloodBag(p)){
				if(p.hasPotionEffect(PotionEffectType.POISON)){
					p.sendMessage(ChatColor.GRAY + "You used a blood bag, but you are still bleeding.");
				}
				if(p.getHealth() < 20){
					p.getInventory().removeItem(new ItemStack(Material.SADDLE, 1));
					p.sendMessage(ChatColor.GRAY+"You used a blood bag.");
						
					if(p.getHealth() <= 10){
						p.setHealth(p.getHealth()+10);
					}
					else{
						p.setHealth(20);
					}
					
					p.removePotionEffect(PotionEffectType.REGENERATION);
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
					}
				else if(p.getHealth() >= 20)
					p.sendMessage(ChatColor.GRAY+"Your health is already full.");
				
				p.updateInventory();
			}
			*/
			return;
		}
	}
		
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		PlayerInventory i = p.getInventory();
		
		p.setGameMode(GameMode.SURVIVAL);
		p.setMaximumNoDamageTicks(35);
		
		if(!p.hasPlayedBefore())
			p.teleport(PLUGIN.MAIN_WORLD.getSpawnLocation());
		
		if(PLUGIN.PVP_LOGGED.contains(p.getName())){
			Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new SendMessage(p,
					ChatColor.GRAY+"[!] "+ChatColor.RED+"You PVP-logged and have lost all of your possessions."), 20);
			p.teleport(PLUGIN.MAIN_WORLD.getSpawnLocation());
			PLUGIN.PVP_LOGGED.remove(p.getName());
			
			i.clear();
			i.setHelmet(new ItemStack(Material.AIR));
			i.setChestplate(new ItemStack(Material.AIR));
			i.setLeggings(new ItemStack(Material.AIR));
			i.setBoots(new ItemStack(Material.AIR));
			p.updateInventory();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		try{
			if(PLUGIN.PVP_LOG.containsKey(p.getName()) && PLUGIN.PVP_LOG.get(p.getName()) > (System.currentTimeMillis()/1000)){
				PLUGIN.PVP_LOGGED.add(p.getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new RemovePVPLogged(p.getName()), 20000);
				
					for(ItemStack i : p.getInventory().getContents()){
						try{
							if(i != null)
								p.getWorld().dropItem(p.getLocation(), i);
						}
						catch(Exception err){}
					}

				e.setQuitMessage(null);
				if(p.getDisplayName() != "999giles" || p.getDisplayName() != "AGuyWhoSkis" || p.getDisplayName() != "TehHappy" || p.getDisplayName() != "Tennisball97" || p.getDisplayName() != "StealthDino"|| p.getDisplayName() != "ddempsey94"|| p.getDisplayName() != "cgrothaus"){
					Bukkit.broadcastMessage(ChatColor.RED+p.getName()+" is a coward.");
				}
				else if(p.getDisplayName() == "ddempsey94"){
					Bukkit.broadcastMessage(ChatColor.RED+p.getName()+" w3nt 2 g0 hav lotz uf secks");
				}
				
				else if(p.getDisplayName() == "StealthDino"){
					Bukkit.broadcastMessage(ChatColor.RED+p.getName()+"can do what he wants, and pvp-logged.");
				}
				else if(p.getDisplayName() == "cgrothaus"){
					Bukkit.broadcastMessage(ChatColor.RED+p.getName()+" is annoying and left because he is stupid.");
				}
				else{
					Bukkit.broadcastMessage(ChatColor.RED+p.getName()+" had to leave suddenly.");
				}
				
			}
		}
		catch(NullPointerException err){}
	}
}
