package com.Sagacious_.WarpHeads.api;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.Sagacious_.WarpHeads.Core;
import com.Sagacious_.WarpHeads.data.Warp;

public class WarpheadsAPI {
	
	public WarpheadsAPI() {}
	
	/**
	 * Returns all warps in the server
	 * @return
	 */
	public List<Warp> getWarps(){return Core.getInstance().wm.getWarps();}
	
	/**
	 * Returns warps owned by player, returns null if non existent
	 * @param p player
	 * @return
	 */
	public List<Warp> getWarps(Player p){return Core.getInstance().wm.getWarps(p.getUniqueId());}
	
	/**
	 * Returns warps owned by player, returns null if non existent
	 * @param uuid player
	 * @return
	 */
	public List<Warp> getWarps(UUID uuid){return Core.getInstance().wm.getWarps(uuid);}
	
	/**
	 * Returns warp that belongs to the given name, null if non existent
	 * @param warp warp name
	 * @return
	 */
	public Warp getWarp(String warp) {return Core.getInstance().wm.getWarpByName(warp);}

	/**
	 * Returns warp that belongs to the given id, null if non existent
	 * Don't see any reason to use this, but you still can
	 * @param id warp id
	 * @return
	 */
	public Warp getWarpById(String id) {return Core.getInstance().wm.getWarpById(id);}
	
}
