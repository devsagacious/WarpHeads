package com.Sagacious_.WarpHeads.command;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.command.sub.WarpCreate;
import com.Sagacious_.WarpHeads.command.sub.WarpDelete;
import com.Sagacious_.WarpHeads.command.sub.WarpRename;
import com.Sagacious_.WarpHeads.command.sub.admin.WarpAdmindelete;
import com.Sagacious_.WarpHeads.data.Warp;

public class CommandWarp implements CommandExecutor{
	
	public CommandWarp() {
		Core.getInstance().getCommand("warp").setExecutor(this);
		Core.getInstance().getCommand("warp").setAliases(new ArrayList<String>(Arrays.asList("warps", "w")));
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cs instanceof Player) {
			Player p = (Player)cs;
			if(args.length==0) {
				Core.getInstance().gui.inventory(p, 1, true);
				return true;
			}else if(args.length>0) {
				if(args[0].equalsIgnoreCase("create")) {
					WarpCreate.execute(p, args);
					return true;
				}
				else if(args[0].equalsIgnoreCase("delete")) {
					WarpDelete.execute(p, args);
					return true;
				}
				else if(args[0].equalsIgnoreCase("rename")) {
					WarpRename.execute(p, args);
					return true;
				}
				else {
					Warp w = Core.api.getWarp(args[0]);
					if(w!=null) {
						if(Configuration.TP_DELAY<1) {
							p.teleport(w.getLocation());
							p.sendMessage(Configuration.WARP_PAID.replaceAll("%balance%", ""+w.getCost()).replaceAll("%name%", w.getName()));
							if(Configuration.TP_SOUND!=null) {
								p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
							}
							}else {
								p.sendMessage(Configuration.TELEPORTATION_COMMENCING);
						Core.getInstance().gui.teleporting.put(p, w);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
							public void run() {
								if(p!=null&&p.isOnline()&&Core.getInstance().gui.teleporting.containsKey(p)&&Core.getInstance().gui.teleporting.get(p).equals(w)) {
									Core.getInstance().gui.teleporting.remove(p);
								p.teleport(w.getLocation());
								p.sendMessage(Configuration.WARP_PAID.replaceAll("%balance%", ""+w.getCost()).replaceAll("%name%", w.getName()));
								if(Configuration.TP_SOUND!=null) {
									p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
								}
							}
							}
						}, 20L*Configuration.TP_DELAY);
					}
					}
				}
			}
		}
		if(args.length>0) {
			if(args[0].equalsIgnoreCase("admindelete") || args[0].equalsIgnoreCase("adelete")) {
				WarpAdmindelete.execute(cs, args);
				return true;
			}
		}
		return true;
	}

}
