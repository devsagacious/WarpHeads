package com.Sagacious_.WarpHeads.command.sub;

import org.bukkit.entity.Player;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.data.Warp;

public class WarpDelete {

	public static void execute(Player p, String[] args) {
	if(args.length!=2) {p.sendMessage(Configuration.INVALID_SYNTAX.replaceAll("%args%", "/warp delete <name>")); return;}
	Warp w = Core.getInstance().wm.getWarpByName(args[1]);
	if(w==null) {p.sendMessage(Configuration.UNKNOWN_WARP);return;}
	if(!w.getUniqueId().equals(p.getUniqueId())) {p.sendMessage(Configuration.NOT_OWNED);return;}
	w.delete();
	p.sendMessage(Configuration.DELETED_WARP);
	}
}
