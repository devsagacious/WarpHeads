package com.Sagacious_.WarpHeads.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.api.event.WarpDeleteEvent;

public class Warp {
	private String id;
	private UUID owner;
	private String owner_name;
	private String name;
	private double cost;
	
	private Location location;

	public Warp(String id, UUID owner, String owner_name, String name, double cost, Location location) {
		this.id = id;
		this.owner = owner;
		this.owner_name = owner_name;
		this.name = name;
		this.cost = cost;
		this.location = location;
	}
	
	public void save() {
		File f = new File(Core.getInstance().getDataFolder(), "warps/" + id + ".yml");
		if(!f.exists()) {
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(f));
				pw.println("owner: '" + owner.toString() + "'");
				pw.println("owner_name: '" + owner_name + "'");pw.println("name: '" + name + "'");
				pw.println("cost: " + cost);String l[] = new String[] {location.getWorld().getName(),""+location.getX(),""+location.getY(),""+location.getZ()};
				pw.println("location: '" + String.join(",", l) + "'");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
			conf.set("name", name); 
			conf.set("owner_name", owner_name); conf.set("cost", cost);
			String l[] = new String[] {location.getWorld().getName(),""+location.getX(),""+location.getY(),""+location.getZ()};
			conf.set("location", String.join(",", l));
			try {
				conf.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete() {
		WarpDeleteEvent e = new WarpDeleteEvent(this);
		Bukkit.getPluginManager().callEvent(e);
		if(!e.isCancelled()) {
		Core.getInstance().wm.getWarps().remove(this);
		File f = new File(Core.getInstance().getDataFolder(), "warps/" + id + ".yml");
		if(f.exists()) {
			f.delete();
		}
	}
	}
	
	public boolean isBlockBelow(Block b) {
		return location.getWorld().getBlockAt(new Location(location.getWorld(), location.getX(), location.getY()-1, location.getZ())).equals(b);
	}
	
	public String getID() {
		return id;
	}
	
	public UUID getUniqueId() {
		return owner;
	}
	
	public String getOwnerName() {
		return owner_name;
	}
	
	public void setOwnerName(String name) {
		this.owner_name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location loc) {
		this.location = loc;
	}
}
