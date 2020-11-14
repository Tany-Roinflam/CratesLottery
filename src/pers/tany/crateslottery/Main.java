package pers.tany.crateslottery;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pers.tany.crateslottery.command.Commands;
import pers.tany.crateslottery.listenevent.Event;
import pers.tany.crateslottery.placeholderapi.PlaceholderAPI;

import java.io.File;

public class Main extends JavaPlugin {
    public static Plugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage("§a[Crates§2Lottery]§a插件已加载");
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
        if (!new File(getDataFolder(), "data.yml").exists()) {
            saveResource("data.yml", false);
        }
        if (!new File(getDataFolder(), "message.yml").exists()) {
            saveResource("message.yml", false);
        }
        if (!new File(getDataFolder(), "log.yml").exists()) {
            saveResource("log.yml", false);
        }
        new BasicLibrary();
        getCommand("cl").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Event(), this);
        new PlaceholderAPI(this).register();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§a[Crates§2Lottery]§c插件已卸载");
    }
}
