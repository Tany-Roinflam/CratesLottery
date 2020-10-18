package pers.tany.crateslottery.placeholderapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;

public class PlaceholderAPI extends PlaceholderExpansion {
	
    public PlaceholderAPI(Main main){
        super();
    }
    
    @Override
    public String onPlaceholderRequest(Player player,String string) {
		if(string.equalsIgnoreCase("CrateNumber")) {
			return Other.data.getConfigurationSection("Info").getKeys(false).size()+"";
		}
		if(string.startsWith("PackBackID")) {
			int id = Integer.parseInt(string.split("\\.")[1]);
			short durability = Short.parseShort(string.split("\\.")[2]);
			int a;
			int b;
			a=0;
			b=0;
			while(a<=35) {
				if(player.getInventory().getItem(a)!=null&&player.getInventory().getItem(a).getType().getId()==id&&player.getInventory().getItem(a).getDurability()==durability) {
					b=b+player.getInventory().getItem(a).getAmount();
				}
				a++;
			}
			return b+"";
		}
		if(string.startsWith("PackBackItem")) {
			String id = string.split("\\.")[1];
			int a;
			int b;
			a=0;
			b=0;
			while(a<=35) {
				if(player.getInventory().getItem(a)!=null&&player.getInventory().getItem(a).getData().getItemType()==Material.valueOf(id)) {
					b=b+player.getInventory().getItem(a).getAmount();
				}
				a++;
			}
			return b+"";
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
		return "2.0";
	}

}
