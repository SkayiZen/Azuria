package fr.skayizen.azuria;

import fr.skayizen.azuria.Commandes.Casino.Casino;
import fr.skayizen.azuria.Commandes.Kits.Kits;
import fr.skayizen.azuria.Commandes.Me.Me;
import fr.skayizen.azuria.Commandes.RandomTP.RandomTP;
import fr.skayizen.azuria.Commandes.Spawn.Spawn;
import fr.skayizen.azuria.Events.ChatStaff;
import fr.skayizen.azuria.Events.Cookies.RandomLegendary.RandomLegendaryEvents;
import fr.skayizen.azuria.Events.Cookies.RandomLegendaySH.RandomLegendaryShEvents;
import fr.skayizen.azuria.Events.Cookies.RandomSH.RandomShEvents;
import fr.skayizen.azuria.Events.JoinEvent;
import fr.skayizen.azuria.Events.Protections;
import fr.skayizen.azuria.Utils.Timers.Casino.CasinoTimer;
import fr.skayizen.azuria.Utils.Timers.Kits.KitsPaidTimer;
import fr.skayizen.azuria.Utils.Timers.Kits.KitsTimer;
import fr.skayizen.azuria.Utils.Timers.Me.MeTimer;
import fr.skayizen.azuria.Utils.Timers.Rtp.RtpTimer;
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
