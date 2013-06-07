package org.kapip.dayz.game.thread;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.kapip.dayz.PLUGIN;
import org.kapip.dayz.game.Weapons;
import org.kapip.dayz.game.WorldHandle;

public class WipeSlate implements Runnable {

	@Override
	public void run() {
		for(LivingEntity le : WorldHandle.MAIN_WORLD.getLivingEntities()){
			if(!(le instanceof Player))
				le.remove();
		}
		
		PLUGIN.PVP_LOG.clear();
		Weapons.LAST_FIRED.clear();
		System.gc();
	}

}
