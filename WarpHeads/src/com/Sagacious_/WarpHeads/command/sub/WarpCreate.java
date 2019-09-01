package com.Sagacious_.WarpHeads.command.sub;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.api.hook.VaultHook;

public class WarpCreate {
	private static DecimalFormat df = new DecimalFormat("#####0.0##############");
	
	public static void execute(Player p, String[] args) {
		if(Core.api.getWarps(p).size()>Configuration.MAX_PLAYER_WARPS) {p.sendMessage(Configuration.MAX_WARPS_REACHED);return;}
		String syntax = "/warp create <name>"+(Configuration.ALLOW_PLAYER_CHARGING?" <cost>":"");
		if((Configuration.ALLOW_PLAYER_CHARGING&&args.length==3)||(!Configuration.ALLOW_PLAYER_CHARGING&&args.length==2)) {
			int cost=0;
			if(Configuration.ALLOW_PLAYER_CHARGING) {
				try {
					cost=Integer.parseInt(args[2]);
				}catch(NumberFormatException e) {
					p.sendMessage(Configuration.INVALID_NUMBER);
					return;}
			}
			boolean f = false;
			for(String s : Configuration.NAME_BLACKLIST) {
				if(args[1].contains(s)) {
					if(!f) {
						p.sendMessage(Configuration.NAME_BLACKLISTED);
					}
					f=true;
				}
			}
			if(cost>Configuration.PLAYER_MAX_CHARGE) {
				p.sendMessage(Configuration.TOO_EXPENSIVE);
				f=true;
			}
			if(Core.api.getWarp(args[1])!=null) {
				p.sendMessage(Configuration.ALREADY_EXISTS);
				return;
			}
			if(!f) {
				if(args[1].length()<Configuration.NAME_LENGTH_MINIMUM||args[1].length()>Configuration.NAME_LENGTH_MAXIMUM) {p.sendMessage(Configuration.NAME_LENGTH); return;}
				if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {p.sendMessage(Configuration.CANNOT_CREATE_WARP_IN_AIR); return;}
				if(Configuration.CHARGE_CREATE_WARP) {if(!VaultHook.canAfford(p, Configuration.TO_CHARGE)) {p.sendMessage(Configuration.INSUFFICIENT_BALANCE.replaceAll("%balance%", ""+VaultHook.getBalance(p)).replaceAll("%needed%", ""+(VaultHook.getBalance(p)-Configuration.TO_CHARGE))); return;}}
				if(Configuration.CHARGE_CREATE_WARP) {VaultHook.setBalance(p, VaultHook.getBalance(p)-Configuration.TO_CHARGE);}
				p.sendMessage(Configuration.CREATED_WARP.replaceAll("%name%", args[1]).replaceAll("%cost%", ""+cost));
				Core.getInstance().wm.createWarp(p.getUniqueId(), p.getName(), args[1], cost, new Location(p.getWorld(), Double.valueOf(df.format(p.getLocation().getX()).replaceAll(",", ".")), Double.valueOf(df.format(p.getLocation().getY()).replaceAll(",", ".")), Double.valueOf(df.format(p.getLocation().getZ()).replaceAll(",", "."))));
			}
		}else {
			p.sendMessage(Configuration.INVALID_SYNTAX.replaceAll("%args%", syntax));
		}
	}

}
