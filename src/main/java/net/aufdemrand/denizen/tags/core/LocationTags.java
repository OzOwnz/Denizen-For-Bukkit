package net.aufdemrand.denizen.tags.core;

import net.aufdemrand.denizen.Denizen;
import net.aufdemrand.denizen.events.ReplaceableTagEvent;
import net.aufdemrand.denizen.utilities.Utilities;
import net.aufdemrand.denizen.utilities.arguments.Location;
import net.aufdemrand.denizen.utilities.arguments.aH;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class LocationTags implements Listener {

    public LocationTags(Denizen denizen) {
        denizen.getServer().getPluginManager().registerEvents(this, denizen);
    }

    @EventHandler
    public void locationTags(ReplaceableTagEvent event) {
        if (!event.matches("location")) return;

        String nameContext = event.getNameContext() != null ? event.getNameContext().toUpperCase() : "";
        String type = event.getType() != null ? event.getType().toUpperCase() : "";
        String subType = event.getSubType() != null ? event.getSubType().toUpperCase() : "";
        String typeContext = event.getTypeContext() != null ? event.getTypeContext().toUpperCase() : "";
        Location fromLocation = null;
        Location toLocation = null;
        
        if (aH.matchesLocation("location:" + nameContext))
        {
        	fromLocation = aH.getLocationFrom("location:" + nameContext);
        }
        else if (event.getPlayer() != null)
        {
        	fromLocation = new Location(event.getPlayer().getLocation());
        }
        else
        {
        	fromLocation = new Location (event.getNPC().getLocation());
        }
        
        if (aH.matchesLocation("location:" + typeContext))
        {
        	toLocation = aH.getLocationFrom("location:" + typeContext);
        }
        
        if (type.equals("BIOME"))
        {            
            if (subType.equals("DISPLAY"))
                event.setReplaced(fromLocation.getBlock().getBiome().name().toLowerCase().replace('_', ' '));
            else
                event.setReplaced(fromLocation.getBlock().getBiome().name());
        }
        
        else if (type.equals("DIRECTION"))
        {
            event.setReplaced(Utilities.getCardinal(Utilities.getYaw
            				 (toLocation.toVector().subtract
            				 (fromLocation.toVector()).normalize())));            
        }
        
        else if (type.equals("DISTANCE"))
        {
        	if (subType.equals("VERTICAL"))
        		event.setReplaced(String.valueOf(Math.abs(
        				fromLocation.getY() - toLocation.getY())));
        	else if (subType.equals("HORIZONTAL"))
        		event.setReplaced(String.valueOf(Math.sqrt(
        				Math.pow(fromLocation.getX() - toLocation.getX(), 2) +
        				Math.pow(fromLocation.getZ() - toLocation.getZ(), 2))));
        	else	
        		event.setReplaced(String.valueOf(fromLocation.distance(toLocation)));
        }
        
        else if (type.equals("FORMATTED"))
        {            
            event.setReplaced("X '" + fromLocation.getBlockX()
                    + "', Y '" + fromLocation.getBlockY()
                    + "', Z '" + fromLocation.getBlockZ()
                    + "', in world '" + fromLocation.getWorld().getName() + "'");
        }
        
        else if (type.equals("LIGHT"))
        {
        	if (subType.equals("BLOCKS"))
        		event.setReplaced(String.valueOf((int) fromLocation.getBlock().getLightFromBlocks()));
        	else if (subType.equals("SKY"))
        		event.setReplaced(String.valueOf((int) fromLocation.getBlock().getLightFromSky()));
        	else
        		event.setReplaced(String.valueOf((int) fromLocation.getBlock().getLightLevel()));
        }
        
        else if (type.equals("WORLD"))
        {            
        	event.setReplaced(fromLocation.getWorld().getName());
        }
        
        else if (type.equals("X"))
        {            
        	event.setReplaced(String.valueOf(fromLocation.getBlockX()));
        }
        
        else if (type.equals("Y"))
        {            
        	event.setReplaced(String.valueOf(fromLocation.getBlockY()));
        }
        
        else if (type.equals("Z"))
        {            
        	event.setReplaced(String.valueOf(fromLocation.getBlockZ()));
        }

    }

}