package fr.skayizen.azuria.Utils.Timers.Kits;


import fr.skayizen.azuria.Commandes.Kits.Kits;
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

import static fr.skayizen.azuria.Commandes.Kits.Kits.*;

public class KitsPaidTimer implements Listener {


    static String CmdMessage = "§9Azuria-SkayiZen §f: §9";


    public static void enablePl() {
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            final File file = new File(Main.instance.getDataFolder(), "Players/cooldown.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            String key = "Players." + p.getName()  +".KitsP";

            if (configuration.get(key + ".Hours") != null) {
                loadFile((Player) p);
            }
        }
    }

    public static void disablePl(){
        for(Player p : kitsPaidCooldownHours.keySet()){
            saveFile(p);

        }
    }

    public static void saveFile(Player p) {
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".KitsP";
        configuration.set(key + ".Hours", kitsPaidCooldownHours.get(p));
        configuration.set(key + ".Minutes", kitsPaidCooldownMinuts.get(p));
        configuration.set(key+ ".Secondes", kitsPaidCooldownSeconds.get(p));

        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println(CmdMessage + e);
        }


        final int timeH = configuration.getInt(key + ".Hours");
        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        kitsPaidCooldownHours.put(p, timeH);
        kitsPaidCooldownMinuts.put(p, timeM);
        kitsPaidCooldownSeconds.put(p, timeS);

    }

    public static void loadFile(Player p){
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".KitsP";

        final int timeH = configuration.getInt(key + ".Hours");
        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        kitsPaidCooldownHours.put(p, timeH);
        kitsPaidCooldownMinuts.put(p, timeM);
        kitsPaidCooldownSeconds.put(p, timeS);

        Kits.setKitsPaidCooldown(p, true);

    }

    @EventHandler
    void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".KitsP";
        if (configuration.get(key + ".Hours") != null) {
            loadFile(p);
        }



    }
    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".KitsP";
        if (configuration.get(key + ".Hours") != null) {
            saveFile(p);
        }



    }

}