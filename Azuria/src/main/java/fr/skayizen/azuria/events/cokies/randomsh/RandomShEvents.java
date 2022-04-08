package fr.skayizen.azuria.events.cokies.randomsh;

import fr.skayizen.azuria.utils.classes.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RandomShEvents implements Listener, CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {


        if (cmd.getName().equalsIgnoreCase("cookie")) {
            if (!sender.isOp() || !(sender instanceof ConsoleCommandSender)) {
                return false;
            } else {
                //Cookie 1

                ItemStack cookie = new ItemBuilder(Material.COOKIE)
                        .withDisplayNameColoured("§6Cookie Random Shiny")
                        .withEnchantment(Enchantment.DURABILITY, 1)
                        .build();


                ItemMeta cookieMeta = cookie.getItemMeta();
                cookieMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cookie.setItemMeta(cookieMeta);

                //Cookie 2

                ItemStack cookie2 = new ItemBuilder(Material.COOKIE)
                        .withDisplayNameColoured("§6Cookie Random Légendaire Shiny")
                        .withEnchantment(Enchantment.DURABILITY, 1)
                        .build();


                ItemMeta cookieMeta2 = cookie2.getItemMeta();
                cookieMeta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cookie2.setItemMeta(cookieMeta2);

                //Cookie 3

                ItemStack cookie3 = new ItemBuilder(Material.COOKIE)
                        .withDisplayNameColoured("§6Cookie Random Légendaire")
                        .withEnchantment(Enchantment.DURABILITY, 1)
                        .build();


                ItemMeta cookieMeta3 = cookie3.getItemMeta();
                cookieMeta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                cookie3.setItemMeta(cookieMeta3);


                if (args.length == 1) {
                    Player p = (Player) sender;
                    if (args[0].equalsIgnoreCase("Shiny")) {

                        p.getInventory().addItem(cookie);

                    } else if (args[0].equalsIgnoreCase("Legendaire")) {

                        p.getInventory().addItem(cookie2);

                    } else if (args[0].equalsIgnoreCase("ShinyLegendaire")) {

                        p.getInventory().addItem(cookie3);

                    }

                    p.sendMessage("§6Cookie §f» Vous avez reçu §c1 §6Cookie Random Shiny");
                } else if (args.length == 2) {
                    String targetNAME = args[1];
                    Player target = Bukkit.getPlayer(targetNAME);

                    if (args[0].equalsIgnoreCase("Shiny")) {

                        target.getInventory().addItem(cookie);

                    } else if (args[0].equalsIgnoreCase("Legendaire")) {

                        target.getInventory().addItem(cookie2);

                    } else if (args[0].equalsIgnoreCase("ShinyLegendaire")) {

                        target.getInventory().addItem(cookie3);

                    }

                    target.sendMessage("§6Cookie §f» Vous avez reçu §c1 §6Cookie ");
                }

            }
        }
        return false;
    }



    @EventHandler
    public void click(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action click = e.getAction();
        ItemStack it = e.getItem();

        if (it == null) return;

        if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Cookie Random Shiny")) {
            if (click == Action.RIGHT_CLICK_BLOCK || click == Action.RIGHT_CLICK_AIR) {
                e.setCancelled(true);
                RandomSH.INVENTORY.open(p);


            }
        }
    }
}
