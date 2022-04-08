package fr.skayizen.azuria.events.cokies.randomlegendary;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RandomLegendaryEvents implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action click = e.getAction();
        ItemStack it = e.getItem();

        if (it == null) return;

        if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Cookie Random Légendaire")) {
            if (click == Action.RIGHT_CLICK_BLOCK || click == Action.RIGHT_CLICK_AIR) {
                e.setCancelled(true);
                RandomLegendary.INVENTORY.open(p);


            }
        }
    }
}
