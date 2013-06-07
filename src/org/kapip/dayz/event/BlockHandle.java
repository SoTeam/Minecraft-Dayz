package org.kapip.dayz.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

public class BlockHandle implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBurn(BlockBurnEvent e){
		e.setCancelled(true);
	}
}
