package com.tany.crateslottery.task;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Preset;

public class NineWingTask extends BukkitRunnable  {
	int a=0;
	int b=0;
	int c=0;
	public Player player;
	public String Crate;
	public NineWingTask(Player p,String s,int n) {
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
			if(Other.data.getString("Info."+Crate+".ninetype").equals("normal"))
			Preset.ninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("random"))
    		Preset.randomninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("order"))
    		Preset.orderninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("gradient"))
    		Preset.gradientwing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("repeatedly"))
        		Preset.repeatedlyninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("show"))
        		Preset.showninewing(player, Crate);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WingMessage")));
			player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundsName")), 2f, 2f);
			a=0;
			b=0;
			cancel();
			if(Other.data.getBoolean("Info."+Crate+".nineinfo")){
				int size = player.getOpenInventory().getBottomInventory().getSize()-1;
				int location=0;
				String info = "";
				while(location<size) {
					ItemStack item = player.getOpenInventory().getItem(location);
					if(item == null||item.getType() == Material.AIR) {
						location++;
						continue;
					}
					if(item.hasItemMeta()&&item.getItemMeta().hasDisplayName()) {
						if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("领取你的战利品吧！")) {
							location++;
							continue;
						}
						if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("§2仅展示")) {
							location++;
							continue;
						}
						if(item.getAmount()==1)
						info = info+item.getItemMeta().getDisplayName()+" ";
						else
						info = info+item.getItemMeta().getDisplayName()+"*"+item.getAmount()+" ";
						location++;
						continue;
					}else {
						if(item.getAmount()==1)
						info = info+item.getType().name()+" ";
						else
						info = info+item.getType().name()+"*"+item.getAmount()+" ";
						location++;
						continue;
					}
				}
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineInfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info."+Crate+".color")+Crate).replace("[item]", info)));
			}
			return;
		}
		a++;
		if(Other.data.getString("Info."+Crate+".ninetype").equals("repeatedly")||Other.data.getString("Info."+Crate+".ninetype").equals("show")) {
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
		if(Other.config.getBoolean("ChangeSoundTimbre"))
			player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundName")), Other.config.getInt("SoundSize"), c);
		else
			player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundName")), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
		if(Other.data.getString("Info."+Crate+".ninetype").equals("normal"))
		Preset.ninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("random"))
		Preset.randomninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("order"))
		Preset.orderninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("gradient"))
		Preset.gradientwinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("repeatedly"))
			Preset.repeatedlyninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("show"))
			Preset.showninewinging(player, Crate);
	}
}
