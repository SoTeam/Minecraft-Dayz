package org.kapip.dayz.game;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class User {
	public static HashMap<String, Integer> thirst = new HashMap<String, Integer>();
	public static int fullDrink = 25;
	
	public static void drinkFull(Player p){
		thirst.put(p.getName(), fullDrink);
	}
	
	public static void getDrink(Player p){
		try{
			p.sendMessage("Thirst: " + thirst.get(p.getName())*4);
		}
		catch(NullPointerException e){}
		
	}
	
	public static void drink(Player p){
			thirst.put(p.getName(), User.fullDrink);
			p.sendMessage(ChatColor.AQUA+"You drank water! Your thirst has been quenched!");
	}
	
	/* Weapons */
	
	public static boolean hasPistol(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_HOE);
	}
	
	public static boolean hasCombatRifle(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_HOE);
	}
	
	public static boolean hasShotgun(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_HOE);
	}

	public static boolean hasMachineGun(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_HOE);
	}
	
	public static boolean hasSniper(Player p){
		return p.getItemInHand().getType().equals(Material.BOW);
	}
	
	public static boolean hasBandage(Player p){
		return p.getItemInHand().getType().equals(Material.PAPER);
	}
	
	public static boolean hasMedkit(Player p){
		return p.getItemInHand().getType().equals(Material.SADDLE);
	}
	
	public static boolean hasKatana(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_AXE);
	}
	
	public static boolean hasAxe(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_AXE);
	}
	
	public static boolean hasCrowbar(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_AXE);
	}
	
	public static boolean hasBat(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_AXE);
	}
	
	public static boolean hasWaterBottle(Player p){
		ItemStack hand = p.getItemInHand();
		
		if(hand.getType() == Material.POTION){
			short data = hand.getDurability();
			
			if(data == 0)
				return true;
		}
		return false;
	}
	
	public static boolean hasBottle(Player p){
		ItemStack hand = p.getItemInHand();
		
		if(hand.getType() == Material.GLASS_BOTTLE)
				return true;
		
		return false;
	}
}
