package com.tany.crateslottery.placeholderapi;
import org.bukkit.entity.Player;

import com.tany.crateslottery.Main;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPI extends PlaceholderExpansion {
	
    public PlaceholderAPI(Main main){
        super();
    }
    
    @Override
    public String onPlaceholderRequest(Player player,String string) {
    		if(string.equalsIgnoreCase("CrateNumber")) {
    			return Main.cratenumber+"";
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
