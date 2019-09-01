package com.Sagacious_.WarpHeads.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.data.Warp;

public class ActivityListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		for(Warp w : Core.api.getWarps(e.getPlayer())) {
			w.setOwnerName(e.getPlayer().getName());
		}
	}

}
