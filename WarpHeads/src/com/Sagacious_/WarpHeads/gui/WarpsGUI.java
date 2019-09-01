package com.Sagacious_.WarpHeads.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.Sagacious_.WarpHeads.Configuration;
import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.api.hook.VaultHook;
import com.Sagacious_.WarpHeads.data.Warp;
import com.Sagacious_.WarpHeads.util.WHUtil;

public class WarpsGUI implements Listener{
	public HashMap<Player, Warp> teleporting = new HashMap<Player, Warp>();

	private HashMap<Player, Integer> opened = new HashMap<Player, Integer>();
	public WarpsGUI() {
		Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
	}
	
	public Inventory inventory(Player p, int page, boolean open) {
		Inventory inv = Bukkit.createInventory(null, 54, Configuration.TITLE.replaceAll("%page%", ""+page));
		
		for(int i = 45*(page-1); i<Core.getInstance().wm.getWarps().size()&&i-(45*(page-1))<45; i++) {
			Warp w = Core.getInstance().wm.getWarps().get(i);
			List<String> l = new ArrayList<String>();
			for(String s : Configuration.LORE) {
				l.add(s.replaceAll("%world%", w.getLocation().getWorld().getName()).replaceAll("%blockx%", ""+w.getLocation().getBlockX()).replaceAll("%blocky%", ""+w.getLocation().getBlockY())
						.replaceAll("%blockz%", ""+w.getLocation().getBlockZ()).replaceAll("%name%", w.getName()).replaceAll("%cost%", ""+w.getCost()).replaceAll("%owner%", w.getOwnerName())
						.replaceAll("%x%", ""+w.getLocation().getX()).replaceAll("%y%", ""+w.getLocation().getY()).replaceAll("%z%", ""+w.getLocation().getZ()));
			}
			ItemStack is = WHUtil.createSkullItem(w.getOwnerName(), ChatColor.getByChar(Configuration.NAME_COLOR) + w.getName(), l, new ItemFlag[] {});
			l.clear();
			inv.setItem(i-(45*(page-1)), is);
		}
		inv.setItem(46, WHUtil.createSkullItem(p.getName(), Configuration.MY_WARPS, new ArrayList<String>(), new ItemFlag[] {}));
		if(page>1) {
			inv.setItem(48, WHUtil.createSkullItem("MHF_ArrowLeft", Configuration.PREVIOUS, new ArrayList<String>(), new ItemFlag[] {}));
		}
		if(Core.getInstance().wm.getWarps().size()>(44*(page-1)+45)) {
			inv.setItem(50, WHUtil.createSkullItem("MHF_ArrowRight", Configuration.NEXT, new ArrayList<String>(), new ItemFlag[] {}));
		}
		for(int i = 45; i < 54; i++) {
			if(inv.getItem(i)==null||inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, WHUtil.createItem(Material.STAINED_GLASS_PANE, 1, Short.valueOf((short)7), "§7   "));
			}
		}
		if(open) {
			opened.put(p, page);
			p.openInventory(inv);
		}
		return inv;
	}
	
	private void updatePersonal(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54, Configuration.MY_WARPS_TITLE);
		for(int i = 0; i<Core.api.getWarps(p).size()&&i<45; i++) {
			Warp w = Core.getInstance().wm.getWarps().get(i);
			List<String> l = new ArrayList<String>();
			for(String s : Configuration.LORE) {
				l.add(s.replaceAll("%world%", w.getLocation().getWorld().getName()).replaceAll("%blockx%", ""+w.getLocation().getBlockX()).replaceAll("%blocky%", ""+w.getLocation().getBlockY())
						.replaceAll("%blockz%", ""+w.getLocation().getBlockZ()).replaceAll("%name%", w.getName()).replaceAll("%cost%", ""+w.getCost()).replaceAll("%owner%", w.getOwnerName())
						.replaceAll("%x%", ""+w.getLocation().getX()).replaceAll("%y%", ""+w.getLocation().getY()).replaceAll("%z%", ""+w.getLocation().getZ()));
			}
			ItemStack is = WHUtil.createSkullItem(w.getOwnerName(), ChatColor.getByChar(Configuration.NAME_COLOR) + w.getName(), l, new ItemFlag[] {});
			l.clear();
			inv.setItem(i, is);
			
		}
		inv.setItem(49, WHUtil.createSkullItem("MHF_Exclamation", Configuration.ALL_WARPS, new ArrayList<String>(), new ItemFlag[] {}));
		for(int i = 45; i < 54; i++) {
			if(inv.getItem(i)==null||inv.getItem(i).getType().equals(Material.AIR)) {
				inv.setItem(i, WHUtil.createItem(Material.STAINED_GLASS_PANE, 1, Short.valueOf((short)7), "§7   "));
			}
		}
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(opened.containsKey(e.getPlayer())) {
			opened.remove(e.getPlayer());
		}
		if(teleporting.containsKey(e.getPlayer())) {
			teleporting.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer()instanceof Player) {
		Player p = (Player)e.getPlayer();
		if(opened.containsKey(p)) {
		   opened.remove(p);	
		}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(!e.getFrom().equals(e.getTo())) {
			if(teleporting.containsKey(e.getPlayer())) {
				teleporting.remove(e.getPlayer());
				e.getPlayer().sendMessage(Configuration.TELEPORTATION_CANCELLED);
			}
		}
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			if(e.getInventory().getTitle().equalsIgnoreCase(Configuration.MY_WARPS_TITLE)) {
				if(e.getCurrentItem()!=null&&!e.getCurrentItem().getType().equals(Material.AIR)) {
					e.setCancelled(true);
					if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
						if(e.getSlot()==49) {
							p.closeInventory();
							inventory(p, 1, true);
						}else {
						p.closeInventory();
						Warp w = Core.api.getWarps().get(e.getSlot());
						p.sendMessage(Configuration.TELEPORTATION_COMMENCING);
						if(Configuration.TP_DELAY<1) {
						p.teleport(w.getLocation());
						p.sendMessage(Configuration.WARP_FREE.replaceAll("%name%", w.getName()));
						if(Configuration.TP_SOUND!=null) {
							p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
						}
						}else {
							teleporting.put(p, w);
							Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
								public void run() {
									if(p!=null&&p.isOnline()&&teleporting.containsKey(p)&&teleporting.get(p).equals(w)) {
										teleporting.remove(p);
									p.teleport(w.getLocation());
									p.sendMessage(Configuration.WARP_FREE.replaceAll("%name%", w.getName()));
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
			else if(opened.containsKey(p)) {
				if(e.getCurrentItem()!=null&&!e.getCurrentItem().getType().equals(Material.AIR)) {
					e.setCancelled(true);
					if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
						int currpage=opened.get(p);
						p.closeInventory();
						if(e.getSlot()<45) {
							Warp w = Core.api.getWarp(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
							if(w.getCost()>0) {
								if(VaultHook.canAfford(p, w.getCost())) {
									p.sendMessage(Configuration.TELEPORTATION_COMMENCING);
									if(Configuration.TP_DELAY<1) {
										VaultHook.setBalance(p, VaultHook.getBalance(p)-w.getCost());
									p.teleport(w.getLocation());
									p.sendMessage(Configuration.WARP_PAID.replaceAll("%balance%", ""+w.getCost()).replaceAll("%name%", w.getName()));
									if(Configuration.TP_SOUND!=null) {
										p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
									}
									}else {
										teleporting.put(p, w);
										Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
											public void run() {
												if(p!=null&&p.isOnline()&&teleporting.containsKey(p)&&teleporting.get(p).equals(w)) {
													teleporting.remove(p);
													VaultHook.setBalance(p, VaultHook.getBalance(p)-w.getCost());
												p.teleport(w.getLocation());
												p.sendMessage(Configuration.WARP_PAID.replaceAll("%balance%", ""+w.getCost()).replaceAll("%name%", w.getName()));
												if(Configuration.TP_SOUND!=null) {
													p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
												}
											}
											}
										}, 20L*Configuration.TP_DELAY);
									}
								}else {
									p.sendMessage(Configuration.INSUFFICIENT_BALANCE_USE.replaceAll("%balance%", ""+w.getCost()));
								}
								return;
							}
							p.sendMessage(Configuration.TELEPORTATION_COMMENCING);
							if(Configuration.TP_DELAY<1) {
							p.teleport(w.getLocation());
							p.sendMessage(Configuration.WARP_FREE.replaceAll("%name%", w.getName()));
							if(Configuration.TP_SOUND!=null) {
								p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
							}
							}else {
								teleporting.put(p, w);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
									public void run() {
										if(p!=null&&p.isOnline()&&teleporting.containsKey(p)&&teleporting.get(p).equals(w)) {
											teleporting.remove(p);
										p.teleport(w.getLocation());
										p.sendMessage(Configuration.WARP_FREE.replaceAll("%name%", w.getName()));
										if(Configuration.TP_SOUND!=null) {
											p.playSound(p.getLocation(), Configuration.TP_SOUND, 0.3F, 0.3F);
										}
									}
									}
								}, 20L*Configuration.TP_DELAY);
							}
						}else if(e.getSlot()==46) {
							updatePersonal(p);
						}else if(e.getSlot()==48) {
							int page = currpage-1;
							p.closeInventory();
							inventory(p, page, true);
							opened.put(p, page);
						}else if(e.getSlot()==50) {
							int page = currpage+1;
							p.closeInventory();
							inventory(p, page, true);
							opened.put(p, page);
						}
					}
				}
			}
		}
	}
	
}
