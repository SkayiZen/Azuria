package fr.skayizen.azuria.Commandes.Me;

import fr.skayizen.azuria.Main;
import fr.skayizen.azuria.Utils.Timers.Casino.CasinoTimer;
import fr.skayizen.azuria.Utils.Timers.Me.MeTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Me implements CommandExecutor {

    public static HashMap<Player, Integer> meCooldown = new HashMap<Player, Integer>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if ((sender instanceof Player)) {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("Me")) {
                if (meCooldown.containsKey(p)) {
                    if(args.length >= 1){
                        if (args[0].equalsIgnoreCase("reset")) {
                            if (p.isOp()) {
                                setMeCooldown(p, false);
                            }
                        } else {
                            meError(p);
                        }
                    } else {
                        meError(p);
                    }

                } else {
                    if (args.length >= 1) {
                        StringBuilder meMessage = new StringBuilder();
                        for (String arg : args) {
                            meMessage.append(arg).append(" ");
                        }
                        meMessage = new StringBuilder(meMessage.toString().trim());


                        Bukkit.broadcastMessage("§6Commerce " + p.getName() + "§7» §f" + meMessage);
                    meCooldown.put(p, 180);
                        setMeCooldown(p, true);

                    } else {
                        p.sendMessage("§6Commerce §7» §fVeuillez mettre un message.");
                    }
                }

            }
        }

        return false;
    }

    public void meError(Player p){
        if (meCooldown.get(p) == 1) {
            p.sendMessage("§6Commerce §7» §fIl vous reste §e" + meCooldown.get(p) + " seconde");
        } else {
            p.sendMessage("§6Commerce §7» §fIl vous reste §e" + meCooldown.get(p) + " secondes");
        }

    }

    public static void setMeCooldown (Player p, Boolean mode){
        new BukkitRunnable() {
            public void run() {
                if (!mode) {
                    cancel();
                    meCooldown.remove(p);
                    MeTimer.saveFile(p);

                } else {
                    if (meCooldown.containsKey(p)) {
                        MeTimer.saveFile(p);
                        if (meCooldown.get(p) <= 1) {
                            cancel();
                            meCooldown.remove(p);
                            p.sendMessage("§6Casino §7» §fVous pouvez refaire votre annonce de §6Commerce§f !");
                        } else {
                            meCooldown.replace(p, meCooldown.get(p), meCooldown.get(p) - 1);

                        }
                    } else {
                        cancel();
                    }
                }


            }

        }.runTaskTimer(Main.instance, 0, 20L);
    }
}
