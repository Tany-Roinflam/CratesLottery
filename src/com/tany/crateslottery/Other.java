package com.tany.crateslottery;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Other {
    static Plugin configs = Bukkit.getPluginManager().getPlugin("CratesLottery");
    static File file=new File(configs.getDataFolder(),"config.yml");
    static File file1=new File(configs.getDataFolder(),"data.yml");
    static File file2=new File(configs.getDataFolder(),"message.yml");
    static FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
    static FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
    static FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
	public static FileConfiguration config=config1;
	public static FileConfiguration data=config2;
	public static FileConfiguration message=config3;
}
