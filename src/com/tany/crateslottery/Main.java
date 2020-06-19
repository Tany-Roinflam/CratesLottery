package com.tany.crateslottery;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.tany.crateslottery.command.Commands;
import com.tany.crateslottery.listenevent.Event;
import com.tany.crateslottery.placeholderapi.PlaceholderAPI;
public class Main extends JavaPlugin{
		public static Plugin plugin = null;
		public void onEnable() {
			
			Bukkit.getConsoleSender().sendMessage("§a[Crates§2Lottery]§a插件已加载");
		    if (!new File(getDataFolder(), "config.yml").exists()) 
		    	saveDefaultConfig();
		    
		    if (!new File(getDataFolder(), "data.yml").exists())
		    	saveResource("data.yml",false);
		    
		    if (!new File(getDataFolder(), "message.yml").exists())
		    	saveResource("message.yml",false);
		    
		    plugin = this;
		    getCommand("crateslottery").setExecutor(new Commands());
		    getCommand("cl").setExecutor(new Commands());
		    getServer().getPluginManager().registerEvents(new Event(), this);
	        new PlaceholderAPI(this).register();
		}
		public void onDisable() {
			Bukkit.getConsoleSender().sendMessage("§a[Crates§2Lottery]§c插件已卸载");
		}
}
