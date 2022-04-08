package fr.skayizen.azuria.Utils.Money;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



public class Money {


    public static void addMoney(Player player, int argent) {
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "/givemoney " + player.getName() + argent);
        player.sendMessage("§bArgent §7» §fVous avez reçu §a" + argent + "§f d'argent !");

    }

}
