package com.tany.crateslottery.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Preset;

public class WingTask extends BukkitRunnable  {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file1=new File(config.getDataFolder(),"data.yml");
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
		if(a==b) {
			player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundsName")), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
			if(Other.data.getString("Info."+Crate+".type").equals("normal"))
			Preset.wing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("random"))
			Preset.randomwing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("order"))
			Preset.orderwing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("embellishment"))
			Preset.embellishmentwing(player, Crate);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WingMessage")));
			a=0;
			b=0;
			cancel();
			List<String> list = Other.data.getStringList("Info."+Crate+".data");
			if(Other.data.getBoolean("Info."+Crate+".info")) {
				if(GetItemStack(list.get(Preset.location).split(":")[1]).hasItemMeta()&&GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().hasDisplayName()){
					if(GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName()+"*"+GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
				}else {
					if(GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getType().name())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", GetItemStack(list.get(Preset.location).split(":")[1]).getType().name()+"*"+GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
				}
			}
			if(Other.data.getBoolean("Info."+Crate+".clear")){
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
			  		Other.data.set("Info."+Crate+".data", list);
			  		if(Other.data.getBoolean("Info."+Crate+".backup")) {
				  		int a=0;
				  		for(String backup:list) {
				  			if(!backup.split(":")[1].equals("null")) {
				  				break;
				  			}
				  			a++;
				  			if(a==list.size()) {
				  				List<String> back = Other.data.getStringList("backup."+Crate);
				  				Other.data.set("Info."+Crate+".data", back);
				  				break;
				  			}
				  		}
				  		a=0;
			  		}
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
			    	}
			  		Preset.location=0;
					break;
				}
			}
			return;
		}
		player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundName")), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
		a++;
		if(Other.data.getString("Info."+Crate+".type").equals("normal"))
		Preset.winging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("random"))
		Preset.randomwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("order"))
		Preset.orderwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("embellishment"))
		Preset.embellishmentwinging(player, Crate);
	}
//	String转ItemStack
	public ItemStack GetItemStack(String data) {
		try {
			return new StreamSerializer().deserializeItemStack(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
