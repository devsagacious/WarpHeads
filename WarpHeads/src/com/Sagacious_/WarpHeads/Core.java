package com.Sagacious_.WarpHeads;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.Sagacious_.WarpHeads.api.WarpheadsAPI;
import com.Sagacious_.WarpHeads.api.hook.VaultHook;
import com.Sagacious_.WarpHeads.command.CommandWarp;
import com.Sagacious_.WarpHeads.data.Warp;
import com.Sagacious_.WarpHeads.data.WarpManager;
import com.Sagacious_.WarpHeads.gui.WarpsGUI;
import com.Sagacious_.WarpHeads.listener.ActivityListener;
import com.Sagacious_.WarpHeads.listener.BlockListener;

public class Core extends JavaPlugin{
	public static WarpheadsAPI api;
	
	private static Core instance;
	public static Core getInstance() {
		return instance;
	}
	
	public WarpManager wm;
	public VaultHook vh = null;
	public WarpsGUI gui;
	
	@Override
	public void onEnable() {
		instance = this;
		new Configuration();wm = new WarpManager();new CommandWarp();gui=new WarpsGUI();
		Bukkit.getPluginManager().registerEvents(new ActivityListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			vh = new VaultHook();getLogger().info("Vault has been found, hooked into economy system.");
		}
		
		
		api = new WarpheadsAPI();
	}

	@Override
	public void onDisable() {
		for(Warp w : wm.getWarps()) {w.save();}
	}
}
