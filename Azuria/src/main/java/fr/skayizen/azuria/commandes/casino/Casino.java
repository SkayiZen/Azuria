package fr.skayizen.azuria.commandes.casino;

import fr.skayizen.azuria.Main;
import fr.skayizen.azuria.utils.timers.casino.CasinoTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Casino implements CommandExecutor {

    public static HashMap<Player, Integer> casinoCooldown = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if ((sender instanceof Player)) {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("Casino")) {
                if (casinoCooldown.containsKey(p)) {
                    if(args.length >= 1){
                        if (args[0].equalsIgnoreCase("reset")) {
                            if (p.isOp()) {
                                setCasinoCooldown(p, false);
                            }
                        } else {
                            casinoError(p);
                        }
                } else {
                       casinoError(p);
                    }

                } else {
                    if (args.length >= 1) {
                        StringBuilder CasinoMessage = new StringBuilder();
                        for (String arg : args) {
                            CasinoMessage.append(arg).append(" ");
                        }
                        CasinoMessage = new StringBuilder(CasinoMessage.toString().trim());


                        Bukkit.broadcastMessage("§6Casino " + p.getName() + "§7» §f" + CasinoMessage);
                        casinoCooldown.put(p, 300);
                        setCasinoCooldown(p, true);

                    } else {
                        p.sendMessage("§6Casino §7» §fVeuillez mettre un message.");
                    }
                }

            }
        }

        return false;
    }

    public void casinoError(Player p){
        if (casinoCooldown.get(p) == 1) {
            p.sendMessage("§6Casino §7» §fIl vous reste §e" + casinoCooldown.get(p) + " seconde");
        } else {
            p.sendMessage("§6Casino §7» §fIl vous reste §e" + casinoCooldown.get(p) + " secondes");
        }

    }

    public static void setCasinoCooldown (Player p, Boolean mode){
        new BukkitRunnable() {
            public void run() {
                if (!mode) {
                    cancel();
                    casinoCooldown.remove(p);
                    CasinoTimer.saveFile(p);

                } else {
                    if (casinoCooldown.containsKey(p)) {
                        CasinoTimer.saveFile(p);
                        if (casinoCooldown.get(p) <= 1) {
                            cancel();
                            casinoCooldown.remove(p);
                            p.sendMessage("§6Casino §7» §fVous pouvez refaire votre annonce de §6Casino§f !");

                        } else {
                            casinoCooldown.replace(p, casinoCooldown.get(p), casinoCooldown.get(p) - 1);

                        }
                    } else {
                        cancel();
                    }
                }


            }

        }.runTaskTimer(Main.instance, 0, 20L);
    }


}
