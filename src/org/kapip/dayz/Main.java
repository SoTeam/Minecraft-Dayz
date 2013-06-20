package org.kapip.dayz;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.kapip.dayz.event.DamageHandle;
import org.kapip.dayz.event.MobHandle;
import org.kapip.dayz.event.PlayerHandle;
import org.kapip.dayz.event.Projectiles;
import org.kapip.dayz.event.command.GameCommands;
import org.kapip.dayz.game.User;
import org.kapip.dayz.game.WorldHandle;
import org.kapip.dayz.game.thread.CallSpawn;
import org.kapip.dayz.game.thread.FillChests;
import org.kapip.dayz.game.thread.TickArrows;
import org.kapip.dayz.game.thread.WipeSlate;
import org.kapip.dayz.game.thread.drink.TickDrink;

public class Main extends JavaPlugin {
	public static BukkitScheduler sch;
	public static Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable(){
		super.onEnable();
		
		PLUGIN.A = this;

		sch = this.getServer().getScheduler();

		GameCommands gc = new GameCommands();
		getCommand("dump").setExecutor(gc);
		getCommand("stopforce").setExecutor(gc);
		getCommand("unstopforce").setExecutor(gc);
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new DamageHandle(), this);
		pm.registerEvents(new MobHandle(), this);
		pm.registerEvents(new PlayerHandle(), this);
		pm.registerEvents(new Projectiles(), this);
		
		sch.scheduleSyncRepeatingTask(this, new CallSpawn(), 60, 1300);
		sch.scheduleSyncRepeatingTask(this, new WipeSlate(), 40, 7400);
		sch.scheduleSyncRepeatingTask(this, new FillChests(), 40, 12000);
		sch.scheduleSyncRepeatingTask(this, new TickArrows(), 5, 2);
		sch.scheduleSyncRepeatingTask(this, new TickDrink(), 20, 1200);

		for(LivingEntity le : WorldHandle.MAIN_WORLD.getLivingEntities()){
			if(!(le instanceof Player))
				le.remove();
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
			User.thirst.put(p.getName(), User.fullDrink);
		}
	}
	
	@Override
	public void onDisable(){
		sch.cancelAllTasks();
		super.onDisable();
	}
}
