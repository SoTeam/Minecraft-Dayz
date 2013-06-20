package org.kapip.dayz.game;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kapip.dayz.PLUGIN;

public class Game {
	public static int spawnRate, spawnRadius = 25;	// Spawn rate
	public static float zombieSpeed = 0.32688F;
	public static ArrayList<Entity> arrowsFired = new ArrayList<Entity>();		// Track the sniper arrows fired
	public static ArrayList<Block> chestsEmptied = new ArrayList<Block>();		// Track emptied chests
	
	public static Random gen = new Random();		// Create a single generator for repeated use
	public static Location spawnLocation = PLUGIN.MAIN_WORLD.getSpawnLocation();		// Get the spawn location

	public static ItemStack waterBottle = new ItemStack(Material.POTION),
			waterBottleEmpty = new ItemStack(Material.GLASS_BOTTLE);		// What a water bottle is
	
	public static void initialize(){
		waterBottle.setDurability((short)0);	// Make this a water bottle
	}
	
	public static void spawnZombies(){		// Spawns zombies around every player. Called in org.kapip.dayz.game.thread.CallSpawn.java
		spawnRate = 50+(Bukkit.getOnlinePlayers().length*(Bukkit.getOnlinePlayers().length/3));		// Calculate a spawn rate
		int rNum01, rNum02, radius = 100;		// Define random numbers and a radius
		
		for(Player p : Bukkit.getOnlinePlayers()){		// Iterate through the players
			Location pLoc = p.getLocation();	// Get the block's location

			for(int x = pLoc.getBlockX()-radius;x < pLoc.getBlockX()+radius;x+=5){		// Iteration through nearby blocks
				rNum01 = gen.nextInt(spawnRate);		// Random spawning algorithm.
				for(int z = pLoc.getBlockZ()-radius;z < pLoc.getBlockZ()+radius;z+=5){
					rNum02 = gen.nextInt(spawnRate);		// Random spawning algorithm match.
					
					if(rNum01 == rNum02){		// If the random match is successful
						if(!((x < pLoc.getBlockX()-spawnRadius || x > pLoc.getBlockX()+spawnRadius)
								|| (z < pLoc.getBlockZ()-spawnRadius || z > pLoc.getBlockZ()+spawnRadius)))	// Keep zombies 25 blocks away
							continue;
						
						Block b = p.getWorld().getHighestBlockAt(x, z);		// Get the highest block here
						Location s1Loc = new Location(p.getWorld(), x, b.getY(), z),
								s2Loc = new Location(p.getWorld(), x, b.getY(), z),
								u1Loc = new Location(p.getWorld(), x, b.getY()-3, z),
								u2Loc = new Location(p.getWorld(), x, pLoc.getBlockY()+1, z),
								u3Loc = new Location(p.getWorld(), x, b.getY()-15, z);
						
						Material m;
						try{
							m = s1Loc.subtract(0, 1, 0).getBlock().getType();
						}
						catch(NullPointerException e){
							m = Material.AIR;
						}
						
						if(m != Material.LEAVES && m != Material.WATER){	// Check for leaves
							if(u1Loc.getBlock().isEmpty() && !u1Loc.getBlock().isLiquid()){
								WorldHandle.MAIN_WORLD.spawnEntity(u3Loc, EntityType.ZOMBIE);
							}
							else if(u2Loc.getBlock().isEmpty() && !u2Loc.getBlock().isLiquid()){
								WorldHandle.MAIN_WORLD.spawnEntity(u3Loc, EntityType.ZOMBIE);
							}
							else if(u3Loc.getBlock().isEmpty() && !u3Loc.getBlock().isLiquid()){
								WorldHandle.MAIN_WORLD.spawnEntity(u3Loc, EntityType.ZOMBIE);
							}
							
							if(!s1Loc.getBlock().isLiquid()){
								WorldHandle.MAIN_WORLD.spawnEntity(s2Loc, EntityType.ZOMBIE);
							}
						}	// Leaves check
					}
				}	
			}	// End triple for loop
			
		}
	}
	
	public static ItemStack setItemName(String name, ItemStack is){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        
        return is;
    }
	
	public static ArrayList<ItemStack> spawnRandomItems(int offset){
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		
		Random r = new Random();
		int ironArmor = 180, chainArmor = 140, leatherArmor = 80; 
		
		/* WEAPON SPAWNING */
		
		if(r.nextInt(220-offset) == 12)
			is.add(Game.setItemName("Sniper", new ItemStack(Material.BOW)));
			
		if(r.nextInt(100-offset) == 12)
			is.add(Game.setItemName("Assault Rifle", new ItemStack(Material.DIAMOND_HOE)));
		
		if(r.nextInt(80-offset) == 20)
			is.add(Game.setItemName("Shotgun", new ItemStack(Material.IRON_HOE)));
		
		if(r.nextInt(60-offset) == 2)
			is.add(Game.setItemName("Combat Rifle", new ItemStack(Material.STONE_HOE)));
		
		if(r.nextInt(40-offset) == 3)
			is.add(Game.setItemName("Handgun", new ItemStack(Material.WOOD_HOE)));
		
		if(r.nextInt(180-offset) == 12)
			is.add(Game.setItemName("Katana", new ItemStack(Material.DIAMOND_AXE)));
			
		if(r.nextInt(140-offset) == 20)
			is.add(Game.setItemName("Axe", new ItemStack(Material.IRON_AXE)));
			
		if(r.nextInt(100-offset) == 2)
			is.add(Game.setItemName("Crowbar", new ItemStack(Material.STONE_AXE)));
			
		if(r.nextInt(80-offset) == 3)
			is.add(Game.setItemName("Baseball Bat", new ItemStack(Material.WOOD_AXE)));
		
		/* ARMOR SPAWNING */
		
		if(r.nextInt(leatherArmor-offset) == 3)
			is.add(new ItemStack(Material.LEATHER_HELMET));
		
		if(r.nextInt(leatherArmor-offset) == 3)
			is.add(new ItemStack(Material.LEATHER_CHESTPLATE));
		
		if(r.nextInt(leatherArmor-offset) == 3)
			is.add(new ItemStack(Material.LEATHER_LEGGINGS));
		
		if(r.nextInt(leatherArmor-offset) == 34)
			is.add(new ItemStack(Material.LEATHER_BOOTS));
		
		if(r.nextInt(chainArmor-offset) == 3)
			is.add(new ItemStack(Material.CHAINMAIL_HELMET));
		
		if(r.nextInt(chainArmor-offset) == 3)
			is.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		
		if(r.nextInt(chainArmor-offset) == 14)
			is.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		
		if(r.nextInt(chainArmor-offset) == 3)
			is.add(new ItemStack(Material.CHAINMAIL_BOOTS));
		
		if(r.nextInt(ironArmor-offset) == 3)
			is.add(new ItemStack(Material.IRON_HELMET));
		
		if(r.nextInt(ironArmor-offset) == 3)
			is.add(new ItemStack(Material.IRON_CHESTPLATE));
		
		if(r.nextInt(ironArmor-offset) == 14)
			is.add(new ItemStack(Material.IRON_LEGGINGS));
		
		if(r.nextInt(ironArmor-offset) == 3)
			is.add(new ItemStack(Material.IRON_BOOTS));
		
		/* MISC SPAWNING */
		
		if(r.nextInt(6) == 1)
			is.add(Game.setItemName("Ammo", new ItemStack(Material.ARROW, (r.nextInt(12)+2))));
		
		if(r.nextInt(9) == 1)
			is.add(new ItemStack(Material.BREAD, (r.nextInt(3)+1)));
		
		if(r.nextInt(12) == 3)
			is.add(new ItemStack(Material.APPLE, (r.nextInt(3)+1)));
		
		if(r.nextInt(16) == 1)
			is.add(new ItemStack(Material.COOKED_CHICKEN, (r.nextInt(2)+1)));
		
		if(r.nextInt(22) == 1)
			is.add(new ItemStack(Material.COOKED_BEEF, (r.nextInt(2)+1)));
		
		if(r.nextInt(120) == 1)
			is.add(new ItemStack(Material.FISHING_ROD));
		
		if(r.nextInt(140) == 1)
			is.add(new ItemStack(Material.BOAT));
		
		if(r.nextInt(40) == 1)
			is.add(new ItemStack(Material.COMPASS));
		
		if(r.nextInt(70) == 1)
			is.add(new ItemStack(Material.MAP));
		
		if(r.nextInt(60) == 1)
			is.add(new ItemStack(Material.WATCH));
		
		if(r.nextInt(17) == 1){
			if(r.nextInt(5) == 1)
				is.add(waterBottle);
			else
				is.add(waterBottleEmpty);
		}
		
		if(r.nextInt(7) == 1)
			is.add(new ItemStack(Material.TORCH, (r.nextInt(2)+2)));
		
		if(r.nextInt(12) == 1)
			is.add(Game.setItemName("Bandage", new ItemStack(Material.PAPER, (r.nextInt(3)+1))));
		
		if(r.nextInt(30) == 1)
			is.add(Game.setItemName("Medkit", new ItemStack(Material.SADDLE)));
		
		return is;
	}
}
