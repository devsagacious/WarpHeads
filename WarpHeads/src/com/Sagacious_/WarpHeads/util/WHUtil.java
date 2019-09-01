package com.Sagacious_.WarpHeads.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class WHUtil {

	public static ItemStack fromString(String[] s) {
 	 ItemStack is = new ItemStack(Material.valueOf(s[0]), 1, (s.length==4?Short.valueOf((short)Integer.parseInt(s[1])):0));
 	 ItemMeta im = is.getItemMeta();
 	 im.setDisplayName(s.length==4?s[2]:s[1]);
 	 String[] l = s.length==4?s[3].split("&"):s[2].split("&");
 	 im.setLore(new ArrayList<String>(Arrays.asList(l)));
 	 is.setItemMeta(im);
 	 return is;
	}
	
	public static ItemStack createSkullItem(String owner, String displayname, List<String> lore, ItemFlag... flags) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta sm = (SkullMeta)skull.getItemMeta();
		sm.setDisplayName(displayname);
		sm.setLore(lore);
		sm.addItemFlags(flags);
		sm.setOwner(owner);
		skull.setItemMeta(sm);
		return skull;
	}
	
	public static ItemStack createItem(Material type, int amount, short data, String displayname) {
		ItemStack item = new ItemStack(type, amount, Short.valueOf(data));
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(displayname);
		item.setItemMeta(im);
		return item;
	}
}
