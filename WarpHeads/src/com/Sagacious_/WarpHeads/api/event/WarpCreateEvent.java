package com.Sagacious_.WarpHeads.api.event;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarpCreateEvent extends Event{
     private static final HandlerList handlers = new HandlerList();
     
     private String id;
     private UUID owner;
     private String name;
     private double cost;
     private Location location;
     
     public WarpCreateEvent(String id, UUID owner, String name, double cost, Location location) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.cost = cost;
		this.location = location;
	}
     
     public String getID() {
    	 return id;
     }
     
     public UUID getOwner() {
    	 return owner;
     }
     
     public String getName() {
    	 return name;
     }
     
     public double getCost() {
    	 return cost;
     }
     
     public Location getLocation() {
    	 return location;
     }
     
     @Override
     public HandlerList getHandlers() {
    	 return handlers;
     }
     
     public static HandlerList getHandlerList() {
    	 return handlers;
     }
}
