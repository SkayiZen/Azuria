package fr.skayizen.azuria.Utils.Timers.Me;

import fr.skayizen.azuria.Commandes.Casino.Casino;
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

import static fr.skayizen.azuria.Commandes.Me.Me.meCooldown;

public class MeTimer implements Listener {

    static String CmdMessage = "§9Azuria-SkayiZen §f: §9";


    public static void enablePl() {
        for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            final File file = new File(Main.instance.getDataFolder(), "Players/cooldown.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            String key = "Players." + p.getName()  +".Me";
            if (configuration.get(key + ".Secondes") != null) {
                //LoadPlayerCooldown((Player) p);
                loadFile((Player) p);
            }
        }
    }

    public static void disablePl(){
        for(Player p : meCooldown.keySet()){
            saveFile(p);

        }
    }

    public static void saveFile(Player p) {
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Me";
        configuration.set(key+ ".Secondes", meCooldown.get(p));

        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println(CmdMessage + e);
        }

        final int timeS = configuration.getInt(key + ".Secondes");

        meCooldown.put(p, timeS);

    }

    public static void loadFile(Player p){
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Me";


        final int timeS = configuration.getInt(key + ".Secondes");

        meCooldown.put(p, timeS);
        Casino.setCasinoCooldown(p, true);

    }

    @EventHandler
    void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Me";
        if (configuration.get(key + ".Secondes") != null) {
            loadFile(p);
        }


    }
    @EventHandler
    void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        final File file = new File(Main.getInstance().getDataFolder(), "Players/cooldown.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = "Players." + p.getName()  +".Me";
        if (configuration.get(key + ".Secondes") != null) {
            saveFile(p);

        }



    }
}
