package com.Sagacious_.WarpHeads;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Configuration {
	private File dataFolder;
	private File config;
	
	private void setupDefaults(FileConfiguration conf) {
		try {
	    Reader def = new InputStreamReader(Core.getInstance().getResource("config.yml"), "UTF8");
	    if (def != null) {
	        YamlConfiguration defc = YamlConfiguration.loadConfiguration(def);
	        conf.setDefaults(defc);
	    }
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public Configuration() {
		dataFolder = Core.getInstance().getDataFolder();
		if(!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		config = new File(dataFolder, "config.yml");
		boolean newfile = false;
		if(!config.exists()) {
			try(InputStream in = Core.getInstance().getResource("config.yml")){
			    Files.copy(in, config.toPath());
			    newfile=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration conf = YamlConfiguration.loadConfiguration(config);
		setupDefaults(conf);
		conf.options().copyDefaults(true);
		if(!newfile) {
		CHARGE_CREATE_WARP = conf.getBoolean("charge-create-warp");
		TO_CHARGE = conf.getInt("to-charge");
		ALLOW_PLAYER_CHARGING = conf.getBoolean("allow-player-charging");
		PLAYER_MIN_CHARGE = conf.getDouble("player-min-charge");
		PLAYER_MAX_CHARGE = conf.getDouble("player-max-charge");
		MAX_PLAYER_WARPS = conf.getInt("max-player-warps");
		TP_DELAY = conf.getInt("tp-delay");
		if(conf.getString("tp-sound").equals("")) {
          	TP_SOUND = null;		
		}else {
			TP_SOUND = Sound.valueOf(conf.getString("tp-sound"));
		}
		
		NAME_LENGTH_MINIMUM = conf.getInt("name-length-minimum");
		NAME_LENGTH_MAXIMUM = conf.getInt("name-length-maximum");
		NAME_BLACKLIST = conf.getStringList("name-blacklist");
		
		PREFIX = colorize(conf.getString("prefix"));
		if(TP_DELAY>0) {
			TELEPORTATION_COMMENCING = PREFIX+colorize(conf.getString("teleportation-commencing"));
			TELEPORTATION_CANCELLED = PREFIX+colorize(conf.getString("teleportation-cancelled"));
		}
		NO_PERMISSION = PREFIX+colorize(conf.getString("no-permission"));
		INVALID_SYNTAX = PREFIX+colorize(conf.getString("invalid-syntax"));
		INVALID_NUMBER = PREFIX+colorize(conf.getString("invalid-number"));
		NAME_BLACKLISTED = PREFIX+colorize(conf.getString("name-blacklisted"));
		CANT_BREAK_SAFE_BLOCK = PREFIX+colorize(conf.getString("cant-break-safe-block"));
		// Create
		CANNOT_CREATE_WARP_IN_AIR = PREFIX + colorize(conf.getString("cannot-create-warp-in-air"));
		MAX_WARPS_REACHED = PREFIX + colorize(conf.getString("max-warps-reached"));
		INSUFFICIENT_BALANCE = PREFIX + colorize(conf.getString("insufficient-balance"));
		NAME_LENGTH = PREFIX+colorize(conf.getString("name-length"));
		CREATED_WARP = PREFIX+colorize(conf.getString("created-warp"));
		TOO_EXPENSIVE = PREFIX+colorize(conf.getString("too-expensive"));
		ALREADY_EXISTS = PREFIX+colorize(conf.getString("already-exists"));
		// Delete
		UNKNOWN_WARP = PREFIX+colorize(conf.getString("unknown-warp"));
		NOT_OWNED = PREFIX+colorize(conf.getString("not-owned"));
		DELETED_WARP = PREFIX+colorize(conf.getString("deleted-warp"));
		ADMIN_DELETED_WARP = PREFIX+colorize(conf.getString("admin-deleted-warp"));
		// Rename
		RENAMED = PREFIX+colorize(conf.getString("renamed"));
		// GUI
		TITLE = colorize(conf.getString("title"));
		NAME_COLOR = conf.getString("name-color").charAt(0);
		LORE = colorizeList(conf.getStringList("lore"));
		MY_WARPS = colorize(conf.getString("my-warps"));
		PREVIOUS = colorize(conf.getString("previous"));
		NEXT = colorize(conf.getString("next"));
		MY_WARPS_TITLE = colorize(conf.getString("my-warps-title"));
		ALL_WARPS = colorize(conf.getString("all-warps"));
		
		// USE
		INSUFFICIENT_BALANCE_USE = PREFIX + colorize(conf.getString("insufficient-balance-use"));
		WARP_PAID = PREFIX + colorize(conf.getString("warp-paid"));
		WARP_FREE = PREFIX + colorize(conf.getString("warp-free"));
		}
	}
	
	private List<String> colorizeList(List<String> msg){
		List<String> t = new ArrayList<String>();
		for(String s : msg) {
			t.add(colorize(s));
		}
		return t;
	}

	
	public static boolean CHARGE_CREATE_WARP = false;
	public static double TO_CHARGE = 250.0D;
	public static boolean ALLOW_PLAYER_CHARGING = false;
	public static double PLAYER_MIN_CHARGE = 0.0D;
	public static double PLAYER_MAX_CHARGE = 2000.0D;
	public static int MAX_PLAYER_WARPS = 3;
	public static int TP_DELAY = 3;
	public static Sound TP_SOUND = Sound.valueOf("NOTE_PLING");
	
	public static int NAME_LENGTH_MINIMUM = 3;
	public static int NAME_LENGTH_MAXIMUM = 16;
	public static List<String> NAME_BLACKLIST = new ArrayList<String>(Arrays.asList("shit", "dick", "fuck", "cancer"));
	
	
	
	// Messages
	public static String PREFIX = "§b§lWarpHeads §8| §r";
	public static String NO_PERMISSION = "You do not have access to that command!";
	public static String INVALID_SYNTAX = "Invalid command syntax, use §4%args%§r!";
	public static String INVALID_NUMBER = "Please specify a real number";
	public static String TELEPORTATION_COMMENCING = "Teleportation commencing in §c3 seconds... §rDo not move!";
	public static String TELEPORTATION_CANCELLED = "You have moved, teleportation §4cancelled§r!";
	public static String NAME_BLACKLISTED = "Please refrain from using such names";
	public static String CANT_BREAK_SAFE_BLOCK = "You can not break the block beneath a warp!";
	// Create
	public static String CANNOT_CREATE_WARP_IN_AIR = "You can not create warps while being in the air!";
	public static String MAX_WARPS_REACHED = "You already have the maximum amount of warps!";
	public static String INSUFFICIENT_BALANCE = "You are §c%needed%$ §rshort, you have §c%balance%$ §rand you need 250$!";
	public static String NAME_LENGTH = "Warp names have a minimum of §c3 characters &rand a maximum of §c16 characters&r!";
	public static String CREATED_WARP = "Created warp named §4%name%§r costing §4%cost%§r!";
	public static String TOO_EXPENSIVE = "You can not charge that much!";
	public static String ALREADY_EXISTS = "A warp with that name already exists!";
	// Delete
	public static String UNKNOWN_WARP = "That warp has not been found!";
	public static String NOT_OWNED = "You do not own that warp!";
	public static String DELETED_WARP = "You have deleted your warp!";
	public static String ADMIN_DELETED_WARP = "You have deleted §4%player%§r his warp!";
	// Rename
	public static String RENAMED = "You have renamed your warp to §4%name%§r!";
	// GUI
	public static String TITLE = "§bWarpHeads §8| §7Page %page%";
	public static char NAME_COLOR = 'b';
	public static List<String> LORE = new ArrayList<String>(Arrays.asList("§7Creator§8: §6%owner%", "§7Location§8: §6%world%, %blockx%, %blocky%, %blockz%"));
	public static String MY_WARPS = "§6My Warps";
	public static String PREVIOUS = "§8< §6Previous page";
	public static String NEXT = "§6Next page §8>";
	public static String MY_WARPS_TITLE = "§bWarpHeads §8| §7My Warps";
	public static String ALL_WARPS = "§bAll Warps";
	
	// USE
	public static String INSUFFICIENT_BALANCE_USE = "You need §4%balance% §rto use this warp!";
	public static String WARP_PAID = "You paid §4%balance% §rto teleport to §4%name%§r!";
	public static String WARP_FREE = "You teleported to §4%name%§r!";
	
	private static String colorize(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
