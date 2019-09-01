package com.Sagacious_.WarpHeads.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Sagacious_.WarpHeads.data.Warp;

public class WarpRenameEvent extends Event implements Cancellable{
    private static final HandlerList handlers = new HandlerList();
    
    private Warp w;
    private String oldname;
    private String newname;
    private boolean cancelled=false;
    
    public WarpRenameEvent(Warp w, String oldname, String newname) {
		this.w= w;
		this.oldname = oldname;
		this.newname = newname;
	}
    
    public Warp getWarp() {
    	return w;
    }
    
    public String getOldName() {
    	return oldname;
    }
    
    public String getNewName() {
    	return newname;
    }
    
    @Override
    public HandlerList getHandlers() {
   	 return handlers;
    }
    
    public static HandlerList getHandlerList() {
   	 return handlers;
    }

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		this.cancelled=value;
	}
}
