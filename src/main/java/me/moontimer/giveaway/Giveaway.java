package me.moontimer.giveaway;

import me.moontimer.giveaway.commands.GiveawayCommand;
import me.moontimer.giveaway.listener.PlayerQuitListener;
import me.moontimer.giveaway.manager.GiveawayManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Giveaway extends JavaPlugin {

    private static Giveaway instance;
    private GiveawayManager giveawayManager;
    private static final String prefix = "§7[§d§lGewinnspiel§7] ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        giveawayManager = new GiveawayManager();
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getCommand("giveaway").setExecutor(new GiveawayCommand());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                giveawayManager.createGiveaway();
            }
        }, 14400*20,14400*20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Giveaway getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public GiveawayManager getGiveawayManager() {
        return giveawayManager;
    }
}
