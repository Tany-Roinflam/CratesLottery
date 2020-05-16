package com.tany.crateslottery.task;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
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
        FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
        FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
        FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
			if(config2.getString("Info."+Crate+".ninetype").equals("normal"))
			Preset.ninewing(player, Crate);
    		else if(config2.getString("Info."+Crate+".ninetype").equals("random"))
    		Preset.randomninewing(player, Crate);
    		else if(config2.getString("Info."+Crate+".ninetype").equals("order"))
    		Preset.orderninewing(player, Crate);
    		else if(config2.getString("Info."+Crate+".ninetype").equals("gradient"))
    		Preset.gradientwing(player, Crate);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("WingMessage")));
			player.playSound(player.getLocation(), Sound.valueOf(config1.getString("SoundsName")), 2f, 2f);
			cancel();
			if(config2.getBoolean("Info."+Crate+".nineinfo")){
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
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NineInfoMessage").replace("[player]", player.getName()).replace("[crate]", config2.getString("Info."+Crate+".color")+Crate).replace("[item]", info)));
			}
	}
}
