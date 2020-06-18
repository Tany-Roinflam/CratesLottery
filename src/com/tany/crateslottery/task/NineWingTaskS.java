package com.tany.crateslottery.task;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Preset;

public class NineWingTaskS extends BukkitRunnable  {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
	public Player player;
	public String Crate;
	public NineWingTaskS(Player p,String s) {
		player = p;
		Crate = s;
	}
	@Override
	public void run() {
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
	}
}
