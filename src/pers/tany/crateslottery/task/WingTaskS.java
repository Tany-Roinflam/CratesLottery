package pers.tany.crateslottery.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.comphenix.protocol.utility.StreamSerializer;

import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Preset;

public class WingTaskS extends BukkitRunnable  {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file1=new File(config.getDataFolder(),"data.yml");
	public Player player;
	public String Crate;
	public WingTaskS(Player p,String s) {
		player = p;
		Crate = s;
	}
	@Override
	public void run() {
			if(Other.data.getString("Info."+Crate+".type").equals("normal"))
				Preset.wing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("random"))
				Preset.randomwing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("order"))
				Preset.orderwing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("embellishment"))
				Preset.embellishmentwing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("repeatedly"))
				Preset.repeatedlywing(player, Crate);
			else if(Other.data.getString("Info."+Crate+".type").equals("show"))
				Preset.showwing(player, Crate);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WingMessage")));
			player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundsName")), 2f, 2f);
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
