package fr.skayizen.azuria.commandes.kits;

import fr.skayizen.azuria.Main;
import fr.skayizen.azuria.utils.timers.kits.KitsPaidTimer;
import fr.skayizen.azuria.utils.timers.kits.KitsTimer;
import fr.skayizen.azuria.utils.money.Money;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Kits implements CommandExecutor, Listener {

    public static HashMap<Player, Integer> kitsCooldownHours = new HashMap<>();
    public static HashMap<Player, Integer> kitsCooldownMinuts = new HashMap<>();
    public static HashMap<Player, Integer> kitsCooldownSeconds = new HashMap<>();

    public static HashMap<Player, Integer> kitsPaidCooldownHours = new HashMap<>();
    public static HashMap<Player, Integer> kitsPaidCooldownMinuts = new HashMap<>();
    public static HashMap<Player, Integer> kitsPaidCooldownSeconds = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {




        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            TextComponent TextComponent;

            List<String> permission = new ArrayList<>();
            permission.add("MaitreDresseur");
            permission.add("MegaDresseur");
            permission.add("DresseurPro");
            permission.add("DresseurConfirmer");
            permission.add("MegaDresseur+");
            permission.add("DresseurAmateur");
            permission.add("Dresseur");

            List<String> paidPermission = new ArrayList<>();
            paidPermission.add("Mvp");
            paidPermission.add("Vip+");
            paidPermission.add("Vip");

            if (cmd.getName().equalsIgnoreCase("kit")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("reset")) {
                        if (p.isOp()) {
                            setKitsCooldown(p, false);
                            setKitsPaidCooldown(p, false);
                        } else {
                            p.sendMessage("§cVous n'avez pas la permission de faire ceci");
                        }
                    } else {


                                for (String perm : permission) {
                                    if (args[0].equalsIgnoreCase(perm)) {
                                        if(!kitsCooldownHours.containsKey(p)) {
                                            kits(p, perm);
                                        } else {
                                            kitsError(p);
                                            break;
                                        }
                                    }
                                }


                                for (String permPaid : paidPermission) {
                                    if (args[0].equalsIgnoreCase(permPaid)) {
                                        if(!kitsPaidCooldownHours.containsKey(p)) {
                                            kitsPaid(p, permPaid);
                                        } else {
                                            kitsPaidError(p);
                                            break;
                                        }
                                    }
                                }
                            }






                } else {
                        for (String perm : permission) {
                            if(!p.isOp()){
                                if(p.hasPermission(perm)) {
                                    TextComponent kits = new TextComponent("§c● §6Kit §f" + perm + "§c ●");

                                    kits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c● §fClicker pour recevoir votre kit " + perm + "§c ●").create()));
                                    kits.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + perm));

                                    p.spigot().sendMessage(kits);

                                    break;
                                }
                                } else {
                                    TextComponent kits = new TextComponent("§c● §6Kit §f" + perm + "§c ●");

                                    kits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c● §fClicker pour recevoir votre kit " + perm + "§c ●").create()));
                                    kits.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + perm));

                                    p.spigot().sendMessage(kits);
                                }
                        }

                        for (String perm : paidPermission) {
                            if(!p.isOp()){
                                if(p.hasPermission(perm)) {
                                    TextComponent kits = new TextComponent("§c● §6Kit §f" + perm + "§c ●");

                                    kits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c● §fClicker pour recevoir votre kit " + perm + "§c ●").create()));
                                    kits.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + perm));

                                    p.spigot().sendMessage(kits);

                                    break;
                                }
                            } else {
                                TextComponent kits = new TextComponent("§c● §6Kit §f" + perm + "§c ●");

                                kits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c● §fClicker pour recevoir votre kit " + perm + "§c ●").create()));
                                kits.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + perm));

                                p.spigot().sendMessage(kits);
                            }
                        }
                    }
            }

        }
        return false;
    }



    public void kitsError(Player p){
        if (kitsCooldownHours.get(p) == 1) {
            if (kitsCooldownMinuts.get(p) == 1) {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure  §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure  §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }

            } else if (kitsCooldownMinuts.get(p) > 1) {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure  §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure  §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            } else {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heure §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }

        } else if (kitsCooldownHours.get(p) == 0) {
            if (kitsCooldownMinuts.get(p) == 1) {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }

            } else if (kitsCooldownMinuts.get(p) == 0) {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }


            } else {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }

        } else if (kitsCooldownHours.get(p) > 1) {
            if (kitsCooldownMinuts.get(p) == 1) {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heures §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heures §e" + kitsCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            } else {
                if (kitsCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heures §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsCooldownHours.get(p) + "§e Heures §e" + kitsCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }
        }
    }

    public void kitsPaidError(Player p){
        if (kitsPaidCooldownHours.get(p) == 1) {
            if (kitsPaidCooldownMinuts.get(p) == 1) {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure  §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure  §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }

            } else if (kitsPaidCooldownMinuts.get(p) > 1) {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure  §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure  §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            } else {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heure §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }

        } else if (kitsPaidCooldownHours.get(p) == 0) {
            if (kitsPaidCooldownMinuts.get(p) == 1) {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }

            } else if (kitsPaidCooldownMinuts.get(p) == 0) {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }


            } else {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }

        } else if (kitsPaidCooldownHours.get(p) > 1) {
            if (kitsPaidCooldownMinuts.get(p) == 1) {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heures §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heures §e" + kitsPaidCooldownMinuts.get(p) + "§e Minute §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            } else {
                if (kitsPaidCooldownSeconds.get(p) == 1) {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heures §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fseconde §f!");
                } else {
                    p.sendMessage("§6Kits §7» §fIl vous reste §e" + kitsPaidCooldownHours.get(p) + "§e Heures §e" + kitsPaidCooldownMinuts.get(p) + "§e Minutes §fet §e" + kitsPaidCooldownSeconds.get(p) + " §fsecondes §f!");
                }
            }
        }
    }

    public void kitsPaid(Player p, String kit){
        if(kit.equalsIgnoreCase("Vip")){
            pokeBall(p, 40, 40, 20);
            Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("Vip+")){
            pokeBall(p, 40, 40, 20);
            Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("Mvp")){
            pokeBall(p, 40, 40, 20);
            Money.addMoney(p, 15000);
        }

        kitsPaidCooldownHours.put(p, 23);
        kitsPaidCooldownMinuts.put(p, 59);
        kitsPaidCooldownSeconds.put(p, 59);

        setKitsPaidCooldown(p, true);

        p.sendMessage("§c● §6Kit §7» §fVous avez reçu votre kit " + kit);
    }

    public void kits(Player p, String kit){
        if(kit.equalsIgnoreCase("Dresseur")){
            pokeBall(p, 25, 25, 10);
             Money.addMoney(p, 10000);
        } else if(kit.equalsIgnoreCase("DresseurAmateur")){
            pokeBall(p, 30, 30, 15);
             Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("DresseurConfirmer")){
            pokeBall(p, 30, 30, 15);
             Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("DresseurPro")){
            pokeBall(p, 35, 35, 20);
             Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("MegaDresseur")){
            pokeBall(p, 40, 40, 20);
              Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("MegaDresseur+")){
            pokeBall(p, 64, 40, 25);
              Money.addMoney(p, 15000);
        } else if(kit.equalsIgnoreCase("MaitreDresseur")){
            pokeBall(p, 40, 40, 20);
             Money.addMoney(p, 15000);
        }
        kitsCooldownHours.put(p, 23);
        kitsCooldownMinuts.put(p, 59);
        kitsCooldownSeconds.put(p, 59);
        setKitsCooldown(p, true);
        p.sendMessage("§c● §6Kit §7» §fVous avez reçu votre kit " + kit);

    }

    public void pokeBall(Player p, int hyperBall, int pokeBall, int superBonbon) {
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + "pixelmon:poke_ball " + pokeBall);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " pixelmon:ultra_ball " + hyperBall);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "give " + p.getName() + " pixelmon:rare_candy " + superBonbon);
    }

    public static void setKitsPaidCooldown(Player p, Boolean mode) {
        new BukkitRunnable() {
            public void run() {
                if (!mode) {
                    cancel();
                    kitsPaidCooldownHours.remove(p);
                    kitsPaidCooldownMinuts.remove(p);
                    kitsPaidCooldownSeconds.remove(p);
                    KitsPaidTimer.saveFile(p);

                } else {
                    if (kitsPaidCooldownHours.containsKey(p)) {
                        KitsPaidTimer.saveFile(p);
                        if (kitsPaidCooldownHours.get(p) <= 0  && kitsPaidCooldownMinuts.get(p) <= 0 && kitsPaidCooldownSeconds.get(p) <= 1) {
                            kitsPaidCooldownHours.remove(p);
                            kitsPaidCooldownMinuts.remove(p);
                            kitsPaidCooldownSeconds.remove(p);
                            cancel();



                        } else {
                            if (kitsPaidCooldownHours.get(p) >= 1) {
                                if (kitsPaidCooldownMinuts.get(p) >= 1) {
                                    if (kitsPaidCooldownSeconds.get(p) >= 1) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), kitsPaidCooldownSeconds.get(p) - 1);

                                    } else if (kitsPaidCooldownSeconds.get(p) == 0) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), 59);
                                        kitsPaidCooldownMinuts.replace(p, kitsPaidCooldownMinuts.get(p), kitsPaidCooldownMinuts.get(p) - 1);
                                    }

                                } else {
                                    if (kitsPaidCooldownSeconds.get(p) >= 1) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), kitsPaidCooldownSeconds.get(p) - 1);

                                    } else if (kitsPaidCooldownSeconds.get(p) == 0) {
                                        kitsPaidCooldownHours.replace(p, kitsPaidCooldownHours.get(p), kitsPaidCooldownHours.get(p) - 1);
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), 59);
                                        kitsPaidCooldownMinuts.replace(p, kitsPaidCooldownMinuts.get(p), 59);
                                    }
                                }
                            } else {
                                if (kitsPaidCooldownMinuts.get(p) >= 1) {
                                    if (kitsPaidCooldownSeconds.get(p) >= 1) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), kitsPaidCooldownSeconds.get(p) - 1);

                                    } else if (kitsPaidCooldownSeconds.get(p) == 0) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), 59);
                                        kitsPaidCooldownMinuts.replace(p, kitsPaidCooldownMinuts.get(p), kitsPaidCooldownMinuts.get(p) - 1);
                                    }

                                } else {
                                    if (kitsPaidCooldownSeconds.get(p) >= 1) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), kitsPaidCooldownSeconds.get(p) - 1);

                                    } else if (kitsPaidCooldownSeconds.get(p) == 0) {
                                        kitsPaidCooldownSeconds.replace(p, kitsPaidCooldownSeconds.get(p), 59);
                                        kitsPaidCooldownMinuts.replace(p, kitsPaidCooldownMinuts.get(p), 59);
                                    }


                                }


                            }
                        }
                    } else {
                        cancel();
                    }



                    // debug p.sendMessage(kitsCooldownMinuts.get(p) + " // " + kitsCooldownSeconds.get(p));
                }


            }

        }.runTaskTimer(Main.instance, 0, 20L);
    }

    public static void setKitsCooldown(Player p, Boolean mode) {
        new BukkitRunnable() {
            public void run() {
                if (!mode) {
                    kitsCooldownHours.remove(p);
                    kitsCooldownMinuts.remove(p);
                    kitsCooldownSeconds.remove(p);
                    KitsTimer.saveFile(p);
                    cancel();

                } else {
                    if (kitsCooldownHours.containsKey(p)) {
                        KitsTimer.saveFile(p);
                        if (kitsCooldownHours.get(p) <= 0  && kitsCooldownMinuts.get(p) <= 0 && kitsCooldownSeconds.get(p) <= 1) {
                            cancel();
                            kitsCooldownHours.remove(p);
                            kitsCooldownMinuts.remove(p);
                            kitsCooldownSeconds.remove(p);


                        } else {
                            if (kitsCooldownHours.get(p) >= 1) {
                                if (kitsCooldownMinuts.get(p) >= 1) {
                                    if (kitsCooldownSeconds.get(p) >= 1) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), kitsCooldownSeconds.get(p) - 1);

                                    } else if (kitsCooldownSeconds.get(p) == 0) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), 59);
                                        kitsCooldownMinuts.replace(p, kitsCooldownMinuts.get(p), kitsCooldownMinuts.get(p) - 1);
                                    }

                                } else {
                                    if (kitsCooldownSeconds.get(p) >= 1) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), kitsCooldownSeconds.get(p) - 1);

                                    } else if (kitsCooldownSeconds.get(p) == 0) {
                                        kitsCooldownHours.replace(p, kitsCooldownHours.get(p), kitsCooldownHours.get(p) - 1);
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), 59);
                                        kitsCooldownMinuts.replace(p, kitsCooldownMinuts.get(p), 59);
                                    }
                                }
                            } else {
                                if (kitsCooldownMinuts.get(p) >= 1) {
                                    if (kitsCooldownSeconds.get(p) >= 1) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), kitsCooldownSeconds.get(p) - 1);

                                    } else if (kitsCooldownSeconds.get(p) == 0) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), 59);
                                        kitsCooldownMinuts.replace(p, kitsCooldownMinuts.get(p), kitsCooldownMinuts.get(p) - 1);
                                    }

                                } else {
                                    if (kitsCooldownSeconds.get(p) >= 1) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), kitsCooldownSeconds.get(p) - 1);

                                    } else if (kitsCooldownSeconds.get(p) == 0) {
                                        kitsCooldownSeconds.replace(p, kitsCooldownSeconds.get(p), 59);
                                        kitsCooldownMinuts.replace(p, kitsCooldownMinuts.get(p), 59);
                                    }


                                }


                            }
                        }
                        } else {
                        cancel();
                    }



                    // debug p.sendMessage(kitsCooldownMinuts.get(p) + " // " + kitsCooldownSeconds.get(p));
                }


            }

        }.runTaskTimer(Main.instance, 0, 20L);
    }
}
