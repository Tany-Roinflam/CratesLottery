package com.tany.crateslottery;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.tany.crateslottery.command.Commands;
import com.tany.crateslottery.listenevent.Event;
import com.tany.crateslottery.placeholderapi.PlaceholderAPI;
import com.tany.crateslottery.task.CrateTask;

public class Main extends JavaPlugin{
	   	private static Main instance;
		public static Plugin plugin = null;
		public static String title = null;
		public static int cratenumber = 0;
		public static HashMap<String, Integer> number = new HashMap<String, Integer>();
	    public static Main getInstance() {
	        return instance;
	    }
		public void onEnable() {
			
			Bukkit.getConsoleSender().sendMessage("§a[Crates§2Sale]§a插件已加载");
		    File file = new File(getDataFolder(), "config.yml");
		    if (!file.exists()) 
		    	saveDefaultConfig();
		    
		    File file1 = new File(getDataFolder(), "data.yml");
		    if (!file1.exists())
		    	saveResource("data.yml",false);
		    
		    File file2 = new File(getDataFolder(), "message.yml");
		    if (!file2.exists())
		    	saveResource("message.yml",false);
		    
		    reloadConfig();
		    plugin = this;
		    getCommand("crateslottery").setExecutor(new Commands());
		    getCommand("cl").setExecutor(new Commands());
		    getServer().getPluginManager().registerEvents(new Event(), this);
		    new CrateTask().runTaskTimer(this, 20, 20);
	        new PlaceholderAPI(this).register();
		}
		public void onDisable() {
			Bukkit.getConsoleSender().sendMessage("§a[Crates§2Sale]§c插件已卸载");
		}
}
