package com.tany.crateslottery.task;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Preset;

public class NineWingTask extends BukkitRunnable  {
	int a=0;
	int b=0;
	public Player player;
	public String Crate;
	public NineWingTask(Player p,String s,int n) {
		player = p;
		Crate = s;
		b=n-1;
	}
	@Override
	public void run() {
		if(a==b) {
			if(Other.data.getString("Info."+Crate+".ninetype").equals("normal"))
			Preset.ninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("random"))
    		Preset.randomninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("order"))
    		Preset.orderninewing(player, Crate);
    		else if(Other.data.getString("Info."+Crate+".ninetype").equals("gradient"))
    		Preset.gradientwing(player, Crate);
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
		player.playSound(player.getLocation(), Sound.valueOf(Other.config.getString("SoundName")), 2f, 2f);
		if(Other.data.getString("Info."+Crate+".ninetype").equals("normal"))
		Preset.ninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("random"))
		Preset.randomninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("order"))
		Preset.orderninewinging(player, Crate);
		else if(Other.data.getString("Info."+Crate+".ninetype").equals("gradient"))
		Preset.gradientwinging(player, Crate);
	}
}
