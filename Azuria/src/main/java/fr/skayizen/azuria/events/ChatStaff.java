package fr.skayizen.azuria.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatStaff implements Listener {
    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (e.getMessage().startsWith("*") && p.hasPermission("StaffChat")) {
            e.setCancelled(true);
            if (e.getMessage().length() != 1) {
                if (e.getMessage().substring(1).startsWith(" ")) {
                    Bukkit.getOnlinePlayers().stream().filter(p1 -> p.hasPermission("StaffChat")).forEach(p1 -> p1.sendMessage("§8[§dStaff-Chat§8] §c" + p1.getName() + "§7 »§c" + e.getMessage().substring(1).toString()));
                } else {
                    Bukkit.getOnlinePlayers().stream().filter(p1 -> p.hasPermission("StaffChat")).forEach(p1 -> p1.sendMessage("§8[§dStaff-Chat§8] §c" + p1.getName() + "§7 » §c" + e.getMessage().substring(1).toString()));
                }

            } else if (e.getMessage().length() == 1) {
                e.setCancelled(true);
                p.sendMessage("§8[§dStaff-Chat§8] §cMessage trop court");
            }

        }
    }


}
