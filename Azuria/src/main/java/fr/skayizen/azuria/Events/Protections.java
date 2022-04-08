package fr.skayizen.azuria.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.entity.Player;

public class Protections implements Listener {


    //Map PixelMon
    @EventHandler
    void mobSpawning (EntitySpawnEvent e){
        if(e.getLocation().getWorld().equals("world")){
            e.setCancelled(true);
        }
    }
    @EventHandler
    void damageTaked (EntityDamageEvent e){
        if(e.getEntity()instanceof Player){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                e.setCancelled(true);
            }
        }
    }



}
