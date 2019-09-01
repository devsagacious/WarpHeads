package com.Sagacious_.WarpHeads.command.sub.admin;

import org.bukkit.command.CommandSender;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.data.Warp;

public class WarpAdmindelete {
	
	public static void execute(CommandSender p, String[] args) {
		if(!p.hasPermission("warpheads.admin")) {p.sendMessage(Configuration.NO_PERMISSION); return;}
		if(args.length!=2) {p.sendMessage(Configuration.INVALID_SYNTAX.replaceAll("%args%", "/warp admindelete <name>")); return;}
		Warp w = Core.getInstance().wm.getWarpByName(args[1]);
		if(w==null) {p.sendMessage(Configuration.UNKNOWN_WARP);return;}
		w.delete();
		p.sendMessage(Configuration.ADMIN_DELETED_WARP.replaceAll("%player%", w.getOwnerName()));
		}

}
