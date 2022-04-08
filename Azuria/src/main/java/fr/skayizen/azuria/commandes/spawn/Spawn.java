package fr.skayizen.azuria.commandes.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if ((sender instanceof Player)) {
            Player p = (Player) sender;

                if(cmd.getName().equalsIgnoreCase("spawn")){
                    Location spawn = new Location(Bukkit.getWorld("world"), 686, 107, 118, 0, 0);
                    p.teleport(spawn);
                    p.sendMessage("§aSpawn §f» §7Vous avez été téléporté au spawn !");
                }

        }
            return false;
    }
}
