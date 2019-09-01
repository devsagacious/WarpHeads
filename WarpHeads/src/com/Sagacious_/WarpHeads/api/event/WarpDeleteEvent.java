package com.Sagacious_.WarpHeads.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Sagacious_.WarpHeads.data.Warp;

public class WarpDeleteEvent extends Event implements Cancellable{
    private static final HandlerList handlers = new HandlerList();
    
    private Warp w;
    private boolean cancelled=false;
    
    public WarpDeleteEvent(Warp w) {
		this.w= w;
	}
    
    public Warp getWarp() {
    	return w;
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
