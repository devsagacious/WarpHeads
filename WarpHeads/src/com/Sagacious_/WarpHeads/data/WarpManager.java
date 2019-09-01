package com.Sagacious_.WarpHeads.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.api.event.WarpCreateEvent;

public class WarpManager {
	private Random r = new Random();
	private String id() {
		String s = "";
		String a = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ012456789";
		for(int i = 0; i < 16; i++) {
			s=s+a.charAt(r.nextInt(61));
		}
		return s;
	}
	
	
	private List<Warp> warps = new ArrayList<Warp>();
	
	public WarpManager() {
		File folder = new File(Core.getInstance().getDataFolder(), "warps");
		if(!folder.exists()) {
			folder.mkdir();
		}else {
			for(File f : folder.listFiles()) {
				FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
			    String[] l = conf.getString("location").split(",");
				Warp warp = new Warp(f.getName().replaceAll(".yml", ""), UUID.fromString(conf.getString("owner")), conf.getString("owner_name"), conf.getString("name"), conf.getDouble("cost"), new Location(Bukkit.getWorld(l[0]), Double.valueOf(l[1]), Double.valueOf(l[2]), Double.valueOf(l[3])));
			    warps.add(warp);
			}
		}
	}
	
	public List<Warp> getWarps(){
		return warps;
	}
	
	public List<Warp> getWarps(UUID owner){
		List<Warp> warp = new ArrayList<Warp>();
		for(Warp w : warps) {
			if(w.getUniqueId().equals(owner)) {
				warp.add(w);
			}
		}
		return warp;
	}
	
	public Warp getWarpByName(String name) {
		for(Warp w : warps) {
			if(w.getName().equalsIgnoreCase(name)) {
				return w;
			}
		}
		return null;
	}
	
	public Warp getWarpById(String id) {
		for(Warp w : warps) {
			if(w.getID().equals(id)) {
				return w;
			}
		}
		return null;
	}

	public void createWarp(UUID owner, String owner_name, String name, double cost, Location loc) {
		if(getWarpByName(name)==null) {
			Warp w = new Warp(id(), owner, owner_name, name, cost, loc);
			warps.add(w);
			Bukkit.getPluginManager().callEvent(new WarpCreateEvent(w.getID(), owner, name, cost, loc));
		}
	}
}
