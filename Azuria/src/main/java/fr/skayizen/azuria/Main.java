package fr.skayizen.azuria;

import fr.skayizen.azuria.commandes.casino.Casino;
import fr.skayizen.azuria.commandes.kits.Kits;
import fr.skayizen.azuria.commandes.me.Me;
import fr.skayizen.azuria.commandes.randomtp.RandomTP;
import fr.skayizen.azuria.commandes.spawn.Spawn;
import fr.skayizen.azuria.events.ChatStaff;
import fr.skayizen.azuria.events.cokies.randomlegendary.RandomLegendaryEvents;
import fr.skayizen.azuria.events.cokies.randomlegendarysh.RandomLegendaryShEvents;
import fr.skayizen.azuria.events.cokies.randomsh.RandomShEvents;
import fr.skayizen.azuria.events.JoinEvent;
import fr.skayizen.azuria.events.Protections;
import fr.skayizen.azuria.utils.timers.casino.CasinoTimer;
import fr.skayizen.azuria.utils.timers.kits.KitsPaidTimer;
import fr.skayizen.azuria.utils.timers.kits.KitsTimer;
import fr.skayizen.azuria.utils.timers.me.MeTimer;
import fr.skayizen.azuria.utils.timers.rtp.RtpTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;




public class Main extends JavaPlugin {


    public static Main instance;
    public static Main getInstance() {return instance;}


    @Override
    public void onEnable() {
        instance = this;
        registerEvents();
        registerCommands();
        onEnableFile();
    }

    @Override
    public void onDisable() {
        onDisableFile();
    }

    private void registerCommands(){
        getCommand("rtp").setExecutor(new RandomTP());
        getCommand("kit").setExecutor(new Kits());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("cookie").setExecutor(new RandomShEvents());
        getCommand("me").setExecutor(new Me());
        getCommand("casino").setExecutor(new Casino());
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Protections(), this);
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new ChatStaff(), this);
        //Cookies
        pm.registerEvents(new RandomShEvents(), this);
        pm.registerEvents(new RandomLegendaryShEvents(), this);
        pm.registerEvents(new RandomLegendaryEvents(), this);
        //Timers
        pm.registerEvents(new RandomTP(), this);
        pm.registerEvents(new RtpTimer(), this);
        pm.registerEvents(new KitsTimer(), this);
        pm.registerEvents(new CasinoTimer(), this);
        pm.registerEvents(new MeTimer(), this);
        pm.registerEvents(new KitsPaidTimer(), this);



    }

    private void onEnableFile(){
        RtpTimer.enablePl();
        KitsTimer.enablePl();
        CasinoTimer.enablePl();
        MeTimer.enablePl();
        KitsPaidTimer.enablePl();

    }

    private void onDisableFile(){
        RtpTimer.disablePl();
        KitsTimer .disablePl();
        CasinoTimer.disablePl();
        MeTimer.disablePl();
        KitsPaidTimer.disablePl();
      }





}
