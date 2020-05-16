package com.tany.crateslottery.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.gui.Preset;

public class WingTask extends BukkitRunnable  {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
	int a=0;
	int b=0;
	public Player player;
	public String Crate;
	public WingTask(Player p,String s,int n) {
		player = p;
		Crate = s;
		b=n-1;
	}
	@Override
	public void run() {
        FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
        FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
        FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
		if(a==b) {
			player.playSound(player.getLocation(), Sound.valueOf(config1.getString("SoundsName")), config1.getInt("SoundSize"), config1.getInt("SoundTimbre"));
			if(config2.getString("Info."+Crate+".type").equals("normal"))
			Preset.wing(player, Crate);
			else if(config2.getString("Info."+Crate+".type").equals("random"))
			Preset.randomwing(player, Crate);
			else if(config2.getString("Info."+Crate+".type").equals("order"))
			Preset.orderwing(player, Crate);
			else if(config2.getString("Info."+Crate+".type").equals("embellishment"))
			Preset.embellishmentwing(player, Crate);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("WingMessage")));
			a=0;
			b=0;
			cancel();
			List<String> list = config2.getStringList("Info."+Crate+".data");
			if(config2.getBoolean("Info."+Crate+".info")) {
				if(GetItemStack(list.get(Preset.location).split(":")[1]).hasItemMeta()&&GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().hasDisplayName()){
					if(GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", config2.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", config2.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName()+"*"+GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
				}else {
					if(GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", config2.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getType().name())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", config2.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getType().name()+"*"+GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
				}
			}
			if(config2.getBoolean("Info."+Crate+".clear")){
				int size = player.getOpenInventory().getBottomInventory().getSize()-1;
				int location=0;
				while(location<size) {
					ItemStack item = player.getOpenInventory().getItem(location);
					if(item.hasItemMeta()&&item.getItemMeta().hasDisplayName()) {
						if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("领取你的奖励吧！")) {
							location++;
							continue;
						}
						if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("§2仅展示")) {
							location++;
							continue;
						}
					}
					list.remove(list.get(Preset.location));
					list.add(Preset.location+":"+"null");
			  		config2.set("Info."+Crate+".data", list);
			  		try {
			  			config2.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
			    	}
			  		Preset.location=0;
					break;
				}
			}
			return;
		}
		player.playSound(player.getLocation(), Sound.valueOf(config1.getString("SoundName")), config1.getInt("SoundSize"), config1.getInt("SoundTimbre"));
		a++;
		if(config2.getString("Info."+Crate+".type").equals("normal"))
		Preset.winging(player, Crate);
		else if(config2.getString("Info."+Crate+".type").equals("random"))
		Preset.randomwinging(player, Crate);
		else if(config2.getString("Info."+Crate+".type").equals("order"))
		Preset.orderwinging(player, Crate);
		else if(config2.getString("Info."+Crate+".type").equals("embellishment"))
		Preset.embellishmentwinging(player, Crate);
	}
	//	ItemStack转String
	public static String ItemData(ItemStack item) {
		StreamSerializer data = new StreamSerializer();
		String s;
		try {
		    s = data.serializeItemStack(item);
		} catch (Exception e) {
		    s = null;
		}
		return s;
	}
//	String转ItemStack
	public static ItemStack GetItemStack(String data) {
		StreamSerializer item = new StreamSerializer();
		try {
			return item.deserializeItemStack(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
