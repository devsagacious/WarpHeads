package com.Sagacious_.WarpHeads.api.hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class VaultHook {
	private static Economy eco;
	
	public VaultHook() {
		 RegisteredServiceProvider<Economy> ecoProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		    if (ecoProvider != null) {
		        eco = ecoProvider.getProvider();
		    }
	}
	
	public static boolean canAfford(Player p, double value) {
		return getBalance(p)>=value;
	}
	
	public static double getBalance(Player p) {
		return eco.getBalance(p);
	}
	
	public static void setBalance(Player p, double value) {
		double bal = getBalance(p);
		if(value<bal) {
			eco.withdrawPlayer(p, bal-value);
		}else {
			eco.depositPlayer(p, bal+value);
		}
	}

}
