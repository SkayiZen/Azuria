package fr.skayizen.azuria.commandes.randomtp;

import fr.skayizen.azuria.Main;
import fr.skayizen.azuria.utils.timers.rtp.RtpTimer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class RandomTP implements CommandExecutor, Listener {

        public static ArrayList<Player> rtp = new ArrayList<>();
        public static HashMap<Player, Integer> rtpCooldownMinuts = new HashMap<>();
        public static HashMap<Player, Integer> rtpCooldownSeconds = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if ((sender instanceof Player)) {
            Player p = (Player) sender;



            if(cmd.getName().equalsIgnoreCase("rtp")) {
                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("reset")) {
                        if(p.hasPermission("rtp.reset")) {
                            p.sendMessage("§6Rtp §7» §fVotre cooldown a été réinitialiser avec succès !");


                            final File file = new File(Main.instance.getDataFolder(), "Players/cooldown.yml");
                            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                            String key = "Players." + p.getName() + ".Rtp";
                            configuration.set(key, null);

                            setRtpCooldown(p, false);
                            try {
                                configuration.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            return true;
                        } else {
                            p.sendMessage("§6Rtp §7» §cVous n'avez pas la permission");
                        }
                    }
                    return false;
                }


                if (rtp.contains(p)) {
                    p.sendMessage("§6Rtp §7» §fVous avez déjà fait votre rtp !");
                } else {
                    if (rtpCooldownMinuts.containsKey(p)) {
                       if(rtpCooldownMinuts.get(p) == 1){
                           if(rtpCooldownSeconds.get(p) == 1){
                               p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownMinuts.get(p) + "§e Minute §fet §e" + rtpCooldownSeconds.get(p) + " §fseconde §f!");
                           } else {
                               p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownMinuts.get(p) + "§e Minute §fet §e" + rtpCooldownSeconds.get(p) + " §fsecondes §f!");
                           }
                       } else if(rtpCooldownMinuts.get(p) == 0){
                            if(rtpCooldownSeconds.get(p) == 1){
                                p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownSeconds.get(p) + " §fseconde §f!");
                            } else {
                                p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownSeconds.get(p) + " §fsecondes §f!");
                            }
                       } else if(rtpCooldownMinuts.get(p) > 1){
                           if(rtpCooldownSeconds.get(p) == 1){
                               p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownMinuts.get(p) + "§e Minutes §fet §e" + rtpCooldownSeconds.get(p) + " §fseconde §f!");
                           } else {
                               p.sendMessage("§6Rtp §7» §fIl vous reste §e" + rtpCooldownMinuts.get(p) + "§e Minutes §fet §e" + rtpCooldownSeconds.get(p) + " §fsecondes §f!");
                           }
                       }

                    } else {

                        rtp.add(p);

                        final int[] timer = {3};

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (rtp.contains(p)) {
                                    if (timer[0] == 0) {
                                        cancel();
                                    } else {
                                        particlePlayer(p);
                                    }
                                }
                            }
                        }.runTaskTimer(Main.instance, 0, 4);

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (rtp.contains(p)) {
                                    if (timer[0] == 0) {
                                        cancel();
                                        int RandomX = getRandomInt(20000);
                                        int RandomZ = getRandomInt(20000);

                                        Random r = new Random();
                                        int choice = r.nextInt(2);
                                        int choice2 = r.nextInt(2);

                                        if (choice == 0) {
                                            RandomX = -RandomX;
                                        } else {
                                            RandomX = RandomX;
                                        }

                                        if (choice2 == 0) {

                                            RandomZ = -RandomZ;
                                        } else {
                                            RandomZ = RandomZ;
                                        }

                                        int finalRandomX = RandomX;
                                        int finalRandomZ = RandomZ;
                                        rtp.remove(p);

                                        Location rtp = new Location(Bukkit.getWorld("Survie"), finalRandomX, 0, finalRandomZ);
                                        int CoordY = rtp.getWorld().getHighestBlockYAt(rtp);
                                        rtp.setY(CoordY + 3);

                                        p.teleport(rtp);
                                        p.sendMessage("§6Rtp §7» §fVous avez été téléporter en :");
                                        p.sendMessage("§6Rtp §7» §ex §f: §e" + finalRandomX + "§b y §f: §b" + p.getLocation().getY() + "§c z §f: §c" + finalRandomZ);

                                        rtpCooldownMinuts.put(p, 19);
                                        rtpCooldownSeconds.put(p, 59);
                                        setRtpCooldown(p, true);
                                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2.0f, 1.0f);
                                    } else {
                                        if (timer[0] == 1) {
                                            p.sendMessage("§6Rtp §7» §ftéléportation dans §e" + timer[0] + "§f seconde");
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§6Rtp §7» §fTéléportation dans §e" + timer[0] + "§f seconde").create());
                                        } else {
                                            p.sendMessage("§6Rtp §7» §ftéléportation dans §e" + timer[0] + "§f secondes");
                                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§6Rtp §7» §fTéléportation dans §e" + timer[0] + "§f secondes").create());
                                        }
                                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0f, 4.0f);
                                        timer[0]--;


                                    }
                                } else {
                                    cancel();
                                }
                            }
                        }.runTaskTimer(Main.instance, 0, 20L);


                    }
                }
            }
        }
        return false;
    }

    private void particlePlayer(Player p) {

        double y = 2;
        float radius = 1f;
        int particles = 20;
        for (int i2 = 0; i2 < 5; i2++) {
            Location location1 = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()+y, p.getLocation().getZ());


            for (int i = 0; i < particles; i++) {
                double angle, x, z;
                angle = 6 * Math.PI * i / particles;
                x = Math.cos(angle) * radius;
                z = Math.sin(angle) * radius;
                location1.add(x, 0, z);
                p.spawnParticle(Particle.TOWN_AURA, location1, 0, 0, 0, 0, 1);
                p.spawnParticle(Particle.ENCHANTMENT_TABLE, location1, 0, 0, 0, 0, 1);
                location1.subtract(x, 0, z);
            }

            y = y-0.4;
        }
        Location location2 = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()+2, p.getLocation().getZ());
        for (int i = 0; i < particles; i++) {

            double angle, x, z;
            angle = 6 * Math.PI * i / particles;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            location2.add(x, 0, z);
            p.spawnParticle(Particle.FLAME, location2, 0, 0, 0, 0, 1);
            location2.subtract(x, 0, z);
        }




    }


    @EventHandler
    void playerMoveEventRtp(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(rtp.contains(p)){
            if(!rtpCooldownMinuts.containsKey(p)) {
                if (e.getTo().getX() != p.getLocation().getX() || e.getTo().getY() != p.getLocation().getY() || e.getTo().getZ() != p.getLocation().getZ()) {
                    rtp.remove(p);
                    p.sendMessage("§cTéléportation annulé ! Vous avez bouger.");
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§cTéléportation annulé ! Vous avez bouger.").create());

                }
            }
        }
        }

    public static Integer getRandomInt(Integer max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }


    public static void setRtpCooldown(Player p, Boolean mode){
            new BukkitRunnable() {
                public void run() {
                    if (!mode) {
                        cancel();
                        rtpCooldownMinuts.remove(p);
                        rtpCooldownSeconds.remove(p);
                        rtp.remove(p);

                    } else {
                        if (rtpCooldownMinuts.containsKey(p)) {

                            if (rtpCooldownMinuts.get(p) <= 0 && rtpCooldownSeconds.get(p) == 0) {
                                rtpCooldownMinuts.remove(p);
                                rtpCooldownSeconds.remove(p);
                                p.sendMessage("§6Rtp §7» §fVous pouvez re-utiliser le §6Rtp§f !");
                                rtp.remove(p);
                                cancel();
                                setRtpCooldown(p, false);
                            } else {
                                if (rtpCooldownSeconds.get(p) >= 1) {
                                    rtpCooldownSeconds.replace(p, rtpCooldownSeconds.get(p), rtpCooldownSeconds.get(p) - 1);

                                } else if (rtpCooldownSeconds.get(p) == 0) {
                                    rtpCooldownSeconds.replace(p, rtpCooldownSeconds.get(p), 59);
                                    rtpCooldownMinuts.replace(p, rtpCooldownMinuts.get(p), rtpCooldownMinuts.get(p) - 1);
                                }
                            }
                            RtpTimer.saveFile(p);
                        } else {
                            cancel();
                        }


                            // debug p.sendMessage(rtpCooldownMinuts.get(p) + " // " + rtpCooldownSeconds.get(p));
                        }


                }

            }.runTaskTimer(Main.instance, 0,20L);



    }





}
