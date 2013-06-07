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
	
	/*weapons*/
	
	public static boolean hasPistol1(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_HOE);
	}
	public static boolean hasPistol2(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_HOE);
	}
	public static boolean hasPistol3(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_HOE);
	}
	public static boolean hasPistol4(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_HOE);
	}
	
	public static boolean hasCombatRifle1(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_AXE);
	}
	public static boolean hasCombatRifle2(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_AXE);
	}
	public static boolean hasCombatRifle3(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_AXE);
	}
	public static boolean hasCombatRifle4(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_AXE);
	}
	
	public static boolean hasShotgun1(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_SPADE);
	}
	public static boolean hasShotgun2(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_SPADE);
	}
	public static boolean hasShotgun3(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_SPADE);
	}
	public static boolean hasShotgun4(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_SPADE);
	}
	
	public static boolean hasMachineGun1(Player p){
		return p.getItemInHand().getType().equals(Material.WOOD_PICKAXE);
	}
	public static boolean hasMachineGun2(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_PICKAXE);
	}
	public static boolean hasMachineGun3(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_PICKAXE);
	}
	public static boolean hasMachineGun4(Player p){
		return p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE);
	}
	
	public static boolean hasSniper(Player p){
		return p.getItemInHand().getType().equals(Material.BOW);
	}
	
	public static boolean hasBandage(Player p){
		return p.getItemInHand().getType().equals(Material.PAPER);
	}
	
	public static boolean hasBloodBag(Player p){
		return p.getItemInHand().getType().equals(Material.SADDLE);
	}
	
	
	public static boolean hasCrowbar(Player p){
		return p.getItemInHand().getType().equals(Material.STONE_SWORD);
	}
	
	public static boolean hasAxe(Player p){
		return p.getItemInHand().getType().equals(Material.IRON_SWORD);
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
