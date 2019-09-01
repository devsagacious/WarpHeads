package com.Sagacious_.WarpHeads.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.api.event.WarpRenameEvent;
import com.Sagacious_.WarpHeads.data.Warp;

public class WarpRename {

	public static void execute(Player p, String[] args) {
		if(args.length!=3) {p.sendMessage(Configuration.INVALID_SYNTAX.replaceAll("%args%", "/warp rename <name> <newname>")); return;}
		Warp w = Core.getInstance().wm.getWarpByName(args[1]);
		if(w==null) {p.sendMessage(Configuration.UNKNOWN_WARP);return;}
		if(!w.getUniqueId().equals(p.getUniqueId())) {p.sendMessage(Configuration.NOT_OWNED);return;}
		if(args[2].length()<Configuration.NAME_LENGTH_MINIMUM||args[1].length()>Configuration.NAME_LENGTH_MAXIMUM) {p.sendMessage(Configuration.NAME_LENGTH); return;}
		boolean f = false;
		for(String s : Configuration.NAME_BLACKLIST) {
			if(args[2].contains(s)) {
				if(!f) {
					p.sendMessage(Configuration.NAME_BLACKLISTED);
				}
				f=true;
			}
		}
		if(!f) {
			WarpRenameEvent e = new WarpRenameEvent(w, w.getName(), args[2]);
			Bukkit.getPluginManager().callEvent(e);
			if(!e.isCancelled()) {
			w.setName(args[2]);
			p.sendMessage(Configuration.RENAMED.replaceAll("%name%", args[2]));
		}
	}
	}
}
