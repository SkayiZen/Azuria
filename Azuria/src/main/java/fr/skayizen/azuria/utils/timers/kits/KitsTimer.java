package fr.skayizen.azuria.utils.timers.kits;

import fr.skayizen.azuria.commandes.kits.Kits;
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

import static fr.skayizen.azuria.commandes.kits.Kits.*;

public class KitsTimer implements Listener {



    static String CmdMessage = "§9Azuria-SkayiZen §f: §9";


    public static void enablePl() {
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            final File file = new File(Main.instance.getDataFolder(), "Players/cooldown.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            String key = "Players." + p.getName()  +".Kits";

            if (configuration.get(key + ".Hours") != null) {
                loadFile((Player) p);
            }
        }
    }

    public static void disablePl(){
        for(Player p : kitsCooldownHours.keySet()){
            saveFile(p);

        }
    }

    public static void saveFile(Player p) {
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Kits";
        configuration.set(key + ".Hours", kitsCooldownHours.get(p));
        configuration.set(key + ".Minutes", kitsCooldownMinuts.get(p));
        configuration.set(key+ ".Secondes", kitsCooldownSeconds.get(p));

        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println(CmdMessage + e);
        }


        final int timeH = configuration.getInt(key + ".Hours");
        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        kitsCooldownHours.put(p, timeH);
        kitsCooldownMinuts.put(p, timeM);
        kitsCooldownSeconds.put(p, timeS);

    }

    public static void loadFile(Player p){
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Kits";

        final int timeH = configuration.getInt(key + ".Hours");
        final int timeM = configuration.getInt(key + ".Minutes");
        final int timeS = configuration.getInt(key + ".Secondes");

        kitsCooldownHours.put(p, timeH);
        kitsCooldownMinuts.put(p, timeM);
        kitsCooldownSeconds.put(p, timeS);

        Kits.setKitsCooldown(p, true);

    }

    @EventHandler
    void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Kits";
        if (configuration.get(key + ".Hours") != null) {
            loadFile(p);
        }



    }
    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Kits";
        if (configuration.get(key + ".Hours") != null) {
            saveFile(p);
        }



    }
}
