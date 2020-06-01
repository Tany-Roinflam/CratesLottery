package com.tany.crateslottery.placeholderapi;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.tany.crateslottery.Main;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPI extends PlaceholderExpansion {
	
    public PlaceholderAPI(Main main){
        super();
    }
    
    @Override
    public String onPlaceholderRequest(Player player,String string) {
    		if(string.equalsIgnoreCase("CrateNumber")) {
    		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    		    File file1=new File(config.getDataFolder(),"data.yml");
    			FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
    			return config2.getConfigurationSection("Info").getKeys(false).size()+"";
    		}
            return null;
    }

	@Override
	public String getAuthor() {
		return "Tany";
	}

	@Override
	public String getIdentifier() {
		return "CratesLottery";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
