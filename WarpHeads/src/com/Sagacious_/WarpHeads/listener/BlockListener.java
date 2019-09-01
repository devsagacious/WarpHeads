package com.Sagacious_.WarpHeads.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.data.Warp;

public class BlockListener implements Listener{
	
	@EventHandler
	public void onSafeblock(BlockBreakEvent e) {
		for(Warp w: Core.getInstance().wm.getWarps()) {
			if(w.isBlockBelow(e.getBlock())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(Configuration.CANT_BREAK_SAFE_BLOCK);
			}
		}
	}

}
