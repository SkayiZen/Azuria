package fr.skayizen.azuria.utils.timers.rtp;

import fr.skayizen.azuria.commandes.randomtp.RandomTP;
import fr.skayizen.azuria.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

import static fr.skayizen.azuria.commandes.randomtp.RandomTP.rtpCooldownMinuts;
import static fr.skayizen.azuria.commandes.randomtp.RandomTP.rtpCooldownSeconds;

public class RtpTimer implements Listener {


    static String CmdMessage = "§9Azuria-SkayiZen §f: §9";


    public static void enablePl() {
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            final File file = new File(Main.instance.getDataFolder(), "Players/cooldown.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            String key = "Players." + p.getName()  +".Rtp";
            if (configuration.get(key + ".Minutes") != null) {
                //LoadPlayerCooldown((Player) p);
                loadFile((Player) p);
            }
        }
    }

    public static void disablePl(){
        for(Player p : rtpCooldownMinuts.keySet()){
            saveFile(p);

        }
    }

    public static void saveFile(Player p) {
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Rtp";
        configuration.set(key + ".Minutes", rtpCooldownMinuts.get(p));
        configuration.set(key+ ".Secondes", rtpCooldownSeconds.get(p));

        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println(CmdMessage + e);
        }


        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        rtpCooldownMinuts.put(p, timeM);
        rtpCooldownSeconds.put(p, timeS);

    }

    public static void loadFile(Player p){
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Rtp";


        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        rtpCooldownMinuts.put(p, timeM);
        rtpCooldownSeconds.put(p, timeS);
        RandomTP.setRtpCooldown(p, true);

    }

    @EventHandler
    void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Rtp";
        if (configuration.get(key + ".Minutes") != null) {
            loadFile((Player) p);
        }


    }
    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Rtp";
        if (configuration.get(key + ".Minutes") != null) {
            saveFile((Player) p);

        }



    }
}
