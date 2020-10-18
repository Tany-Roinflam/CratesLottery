package pers.tany.crateslottery.task;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pers.tany.crateslottery.CommonlyWay;
import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Preset;

public class WingTask extends BukkitRunnable  {
	int a=0;
	int b=0;
	int c=0;
	public Player player;
	public String Crate;
	public WingTask(Player p,String s,int n) {
		player = p;
		Crate = s;
		b=n-1;
	}
	@Override
	public void run() {
		if(c==5)
			c=0;
		c++;
		if(a>=b) {
			try {
				player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
			} catch (Exception a) {
				player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
			}
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
			a=0;
			b=0;
			cancel();
			List<String> list = Other.data.getStringList("Info."+Crate+".data");
			if(Other.data.getBoolean("Info."+Crate+".info")) {
				if(CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).hasItemMeta()&&CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().hasDisplayName()){
					if(CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName()+"*"+CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
				}else {
					if(CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getAmount()==1)
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getType().name())));
					else
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getType().name()+"*"+CommonlyWay.GetItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
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
			  	    File file=new File(Main.plugin.getDataFolder(),"data.yml");
			  		try {
			  			Other.data.save(file);
			  		} catch (IOException e) {
			  			e.printStackTrace();
			    	}
			  		Preset.location=0;
					break;
				}
			}
			return;
		}
		if(Other.config.getBoolean("ChangeSoundTimbre")) {
			try {
				player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), c);
			} catch (Exception a) {
				player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), c);
			}
		} else {
			try {
				player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
			} catch (Exception a) {
				player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
			}
		}
		a++;
		if(Other.data.getString("Info."+Crate+".type").equals("repeatedly")||Other.data.getString("Info."+Crate+".type").equals("show")) {
			Random random = new Random();
		    int randoms = random.nextInt(3)+1;
		    if(randoms==2){
		    	a++;
		    }
		    else if(randoms==1)
		    {
		    	a--;
		    }
		}
		if(Other.data.getString("Info."+Crate+".type").equals("normal"))
			Preset.winging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("random"))
			Preset.randomwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("order"))
			Preset.orderwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("embellishment"))
			Preset.embellishmentwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("repeatedly"))
			Preset.repeatedlywinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".type").equals("show"))
			Preset.showwinging(player, Crate);
	}
}
