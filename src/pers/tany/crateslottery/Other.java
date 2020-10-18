package pers.tany.crateslottery;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Other {
    static File file=new File(Main.plugin.getDataFolder(),"config.yml");
    static File file1=new File(Main.plugin.getDataFolder(),"data.yml");
    static File file2=new File(Main.plugin.getDataFolder(),"message.yml");
	public static FileConfiguration config=(FileConfiguration) YamlConfiguration.loadConfiguration(file);
	public static FileConfiguration data=(FileConfiguration) YamlConfiguration.loadConfiguration(file1);
	public static FileConfiguration message=(FileConfiguration) YamlConfiguration.loadConfiguration(file2);
}
