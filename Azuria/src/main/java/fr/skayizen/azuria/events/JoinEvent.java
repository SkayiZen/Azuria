package fr.skayizen.azuria.events;

import fr.skayizen.azuria.Main;
import fr.skayizen.azuria.utils.money.Money;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


public class JoinEvent implements Listener {

    private HashMap<Player, Integer> timedEvent = new HashMap<>();




    @EventHandler
    void joinEvent (PlayerJoinEvent e){
        setTimedEvent(e.getPlayer(), true);

    }

    @EventHandler
    void onquit(PlayerQuitEvent e){
        setTimedEvent(e.getPlayer(), false);
    }


    private void setTimedEvent(Player player, Boolean mode){
       if(mode){
           new BukkitRunnable(){
               @Override
               public void run() {
                   if(player.isOnline()){
                       if(timedEvent.containsKey(player)){
                          if(mode) {
                               if (timedEvent.get(player) == 3600) {
                                   Money.addMoney(player, 5000);
                                   timedEvent.replace(player, 3600, 0);
                                   player.sendMessage("§eVous avez gagné §f5000$ §epour avoir jouer 1h sur notre serveur !");
                               } else {
                                   timedEvent.replace(player, timedEvent.get(player), (timedEvent.get(player) + 1));
                               }
                           } else {
                               cancel();
                               }
                       } else {
                           timedEvent.put(player, 0);
                       }
                   } else {
                       cancel();
                   }
                   }
               }.runTaskTimer(Main.instance, 20L, 20L);

       } else {
           timedEvent.remove(player);
       }
    }


}
