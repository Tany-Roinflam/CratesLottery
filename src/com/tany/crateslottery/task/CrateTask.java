package com.tany.crateslottery.task;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.tany.crateslottery.Main;

public class CrateTask extends BukkitRunnable{
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
	@Override
	public void run() {
        FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
        Main.cratenumber=config2.getConfigurationSection("Info").getKeys(false).size();
	}
}
