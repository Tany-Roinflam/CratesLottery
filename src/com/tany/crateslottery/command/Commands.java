package com.tany.crateslottery.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Gui;

public class Commands implements CommandExecutor {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1) {
			if(args[0].equalsIgnoreCase("help")) {
				if(sender.isOp()) {
					sender.sendMessage("§a[]==================§2[帮助§a界面<1/4>]==================[]");
					sender.sendMessage("§a§l常用指令");
					sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
					sender.sendMessage("");
					sender.sendMessage("§a/cl gui  §6打开制作gui");
					sender.sendMessage("§a/cl reload  §6重载插件配置/语言文件");
					sender.sendMessage("§a/cl crate [箱子名称]  [玩家] [数量]  §6给予抽奖方块");
					sender.sendMessage("§a/cl key [箱子名称] [玩家] [数量]  §6给予抽奖钥匙");
					sender.sendMessage("§a/cl setcrate §6设置手上物品放置出的方块即为抽奖箱");
					sender.sendMessage("§a/cl setkey §6设置手上物品为抽奖钥匙");
					sender.sendMessage("§a[]=========================§2==========================[]");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				Other.config = YamlConfiguration.loadConfiguration(file);
				Other.message = YamlConfiguration.loadConfiguration(file2);
				sender.sendMessage("§a重载成功");
				return true;
			}
			if(args[0].equalsIgnoreCase("gui")) {
				if(sender.isOp()) {
					if(sender instanceof Player) {
						Gui.gui((Player) sender);
						return true;
					}
				sender.sendMessage("§c控制台不能使用此指令");
				return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9ninetime §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的§c九连抽§2开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl time §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的普通开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nine")) {
				if(sender.isOp()) {
					sender.sendMessage("§a写插件不带私货，那和咸鱼有什么区别~");
					sender.sendMessage("§a9Nine天下第一！！！！！");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl bc [箱子名称] [公告] ");
					sender.sendMessage("§2设置箱子开启时全服公告[如果写“无”则不启用]，[player]开箱玩家变量");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("set")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl set [箱子名称] [true/false] §2设置箱子的抽奖方式（true为带动画，默认true）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("clear")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl clear [箱子名称] [true/false] §2启用单抽箱子清理抽到的物品功能");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("backup")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl backup [箱子名称] [true/false] §6启用填充箱子物品功能");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineset")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9Nineset [箱子名称] [true/false] §2设置§c九连抽§2箱子的抽奖方式（true为带动画，默认true）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineinfo")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9nineinfo [箱子名称] [true/false] §2启用§c九连抽§2公告抽奖到的物品");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("info")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl info [箱子名称] [true/false] §2启用公告抽奖到的物品");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("setcrate")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						Player p = (Player) sender;
						ItemStack item=null;
							  if(p.getInventory().getItemInHand() == null || p.getInventory().getItemInHand().getType() == Material.AIR) {
								  p.sendMessage("§c手上请拿上要设置方块的物品状态的物品");
								  return true;
							  }
						  item = p.getInventory().getItemInHand();
						  ItemMeta meta = item.getItemMeta();
						  ItemMeta metas = item.getItemMeta();
						  meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")));
						  ArrayList<String> lore = new ArrayList<String>();
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLoreOne")));
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLoreTwo")));
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLoreThree")));
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLoreFour")));
						  meta.setLore(lore);
						  item.setItemMeta(meta);
						  int amount = item.getAmount();
						  item.setAmount(1);
						  Other.data.set("CrateItem", GetItemData(item));
					  		try {
					  			Other.data.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
						  item.setAmount(amount);
						  item.setItemMeta(metas);
						  sender.sendMessage("§a成功重新设置箱子");
						  return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				sender.sendMessage("§c控制台不能使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("setkey")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						Player p = (Player) sender;
						ItemStack item=null;
						if(p.getInventory().getItemInHand() == null || p.getInventory().getItemInHand().getType() == Material.AIR) {
						  p.sendMessage("§c手上不能为空气");
						  return true;
						  }
						  item = p.getInventory().getItemInHand();
						  ItemMeta meta = item.getItemMeta();
						  ItemMeta metas = item.getItemMeta();
						  meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")));
						  ArrayList<String> lore = new ArrayList<String>();
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("KeyLoreOne")));
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("KeyLoreTwo")));
						  lore.add(ChatColor.translateAlternateColorCodes('&', Other.message.getString("KeyLoreThree")));
						  meta.setLore(lore);
						  item.setItemMeta(meta);
						  int amount = item.getAmount();
						  item.setAmount(1);
						  Other.data.set("CrateKey", GetItemData(item));
					  		try {
					  			Other.data.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
					  	  item.setAmount(amount);
					  	  item.setItemMeta(metas);
						  sender.sendMessage("§a成功重新设置开箱钥匙");
						  return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				sender.sendMessage("§c控制台不能使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl key [箱子名称] [玩家] [数量]  §2给予抽奖钥匙");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl crate [箱子名称]  [玩家] [ 数量]  §2给予抽奖方块（设置时手握的）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			return true;
		}
		if(args.length==2) {
			if(args[0].equalsIgnoreCase("backup")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl backup [箱子名称] [true/false] §6启用填充因clear清空完内容的箱子内容");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("help")) {
				if(sender.isOp()) {
					int number = 1;
					try {
						number =Integer.parseInt(args[1]);
					}catch(NumberFormatException e) {
						sender.sendMessage("§c请输入数字");
						return true;
					}
					if(number<=0) {
						sender.sendMessage("§c没有这个页数！");
						return true;
					}
					if(number==1) {
						sender.sendMessage("§a[]==================§2[帮助§a界面<1/4>]==================[]");
						sender.sendMessage("§a§l常用指令");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl gui  §6打开制作gui");
						sender.sendMessage("§a/cl reload  §6重载插件配置/语言文件");
						sender.sendMessage("§a/cl crate [箱子名称]  [玩家] [数量]  §6给予抽奖方块");
						sender.sendMessage("§a/cl key [箱子名称] [玩家] [数量]  §6给予抽奖钥匙");
						sender.sendMessage("§a/cl setcrate §6设置手上物品放置出的方块即为抽奖箱");
						sender.sendMessage("§a/cl setkey §6设置手上物品为抽奖钥匙");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					if(number==2) {
						sender.sendMessage("§a[]==================§2[帮助§a界面<2/4>]==================[]");
						sender.sendMessage("§a§l箱子单独时间相关");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl time §2[箱子名称] [变幻次数] [变幻时间] §6普通开箱单独设置时间");
						sender.sendMessage("§a/cl 9ninetime §2[箱子名称] [变幻次数] [变幻时间] §c九连抽§6开箱单独设置时间");
						sender.sendMessage("§6为这个箱子单独设置时间，细看config.yml里的“开箱部分”自行理解");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					if(number==3) {
						sender.sendMessage("§a[]==================§2[帮助§a界面<3/4>]==================[]");
						sender.sendMessage("§a§l公告相关");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl bc [箱子名称] [公告] ");
						sender.sendMessage("§a/cl 9nine [箱子名称] [公告] ");
						sender.sendMessage("§6设置箱子§a[普通开启/§c九连抽]§6开启时全服公告");
						sender.sendMessage("§6注释：如果写“无”则不启用，[player]为开箱玩家变量");
						sender.sendMessage("");
						sender.sendMessage("§a/cl info [箱子名称] [true/false] §6启用公告抽奖到的物品");
						sender.sendMessage("§a/cl 9nineinfo [箱子名称] [true/false] §6启用§c九连抽§2公告抽奖到的物品");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					if(number==4) {
						sender.sendMessage("§a[]==================§2[帮助§a界面<4/4>]==================[]");
						sender.sendMessage("§a§l箱子的动画/特殊开箱相关");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl set [箱子名称] [true/false] §6启用箱子抽奖动画");
						sender.sendMessage("§a/cl 9nineset [箱子名称] [true/false] §6启用§c九连抽§6箱子抽奖动画");
						sender.sendMessage("§a/cl clear [箱子名称] [true/false] §6启用单抽箱子清理抽到的物品功能");
						sender.sendMessage("§a/cl backup [箱子名称] [true/false] §6启用填充因clear清空完内容的箱子内容");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					sender.sendMessage("§c没有这个页数！");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineinfo")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9nineinfo [箱子名称] [true/false] §2启用§c九连抽§2公告抽奖到的物品");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("info")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl info [箱子名称] [true/false] §2启用公告抽奖到的物品");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("clear")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl clear [箱子名称] [true/false] §2启用单抽箱子清理抽到的物品功能");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9ninetime §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的§c九连抽§2开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl time §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的普通开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineset")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9Nineset [箱子名称] [true/false] §2设置§c九连抽§2箱子的抽奖方式（true为带动画，默认true）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nine")) {
				if(sender.isOp()) {
					sender.sendMessage("§a我大调色板脚踩柚子社，拳打中二社，头顶八月社");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl bc [箱子名称] [公告] ");
					sender.sendMessage("§2设置箱子开启时全服公告[如果写“无”则不启用]，[player]开箱玩家变量");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
							sender.sendMessage("§c当前没有存在任何抽奖箱");
							return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						if(Other.data.getString("CrateItem")==null) {
							sender.sendMessage("§c未设置箱子");
							return true;
						}
						a=0;
						ItemStack item = GetItemStack(Other.data.getString("CrateItem"));
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
						item.setItemMeta(meta);
						((Player) sender).getInventory().addItem(item);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a抽奖箱成功"));
						return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				sender.sendMessage("§c不能给控制台物品");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
							sender.sendMessage("§c当前没有存在任何抽奖箱");
							return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						if(Other.data.getString("CrateKey") == null) {
							sender.sendMessage("§c未设置钥匙");
							return true;
						}
						a=0;
						ItemStack item = GetItemStack(Other.data.getString("CrateKey"));
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
						item.setItemMeta(meta);
						((Player) sender).getInventory().addItem(item);
						if(Other.config.getBoolean("KeyMessage"))
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("GetKeyMessage").replace("[key]", Other.data.getString("Info."+args[1]+".color")+args[1])));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a钥匙成功"));
						return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				sender.sendMessage("§c不能给控制台物品");
				return true;
			}
			if(args[0].equalsIgnoreCase("set")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl set [箱子名称] [true/false] §2设置箱子的抽奖方式（true为带动画，默认true）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9Nineset")) {	
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9Nineset [箱子名称] [true/false] §2设置§c九连抽§2箱子的抽奖方式（true为带动画，默认true）");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			return true;
		}
		if(args.length==3) {
			if(args[0].equalsIgnoreCase("info")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".info", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".info", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineinfo")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".nineinfo", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".nineinfo", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9ninetime §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的§c九连抽§2开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl time §2[箱子名称]  [变幻次数] [变幻时间] 为这个箱子的普通开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("set")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".animation", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".animation", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nineset")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".nineanimation", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".nineanimation", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("clear")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".clear", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".clear", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("backup")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(!args[2].equalsIgnoreCase("true")&&!args[2].equalsIgnoreCase("false")) {
						sender.sendMessage("§c请输入true或者false！");
						return true;
					}
					if(!Other.data.getBoolean("Info."+args[1]+".clear")) {
						sender.sendMessage("§c这个箱子没有开启一次性抽奖功能！");
						return true;
					}
					if(args[2].equalsIgnoreCase("true"))
					Other.data.set("Info."+args[1]+".backup", true);
					else
					if(args[2].equalsIgnoreCase("false"))
					Other.data.set("Info."+args[1]+".backup", false);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		sender.sendMessage("§a设置成功");
			  		return true;					
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(args[2].equals("无")) {
						sender.sendMessage("§a成功取消"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a开箱时的公告");
						Other.data.set("Info."+args[1]+".announcement", "无");
					}else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "§a成功设置"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a开箱时的公告为"+args[2]));
						Other.data.set("Info."+args[1]+".announcement", args[2]);
					}
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		return true;	
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9Nine")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("§c不存在这个抽奖箱");
							return true;
						}
					}
					a=0;
					if(args[2].equals("无")) {
						sender.sendMessage("§a成功取消"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§c九连§a开箱时的公告");
						Other.data.set("Info."+args[1]+".nine", "无");
					}else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "§a成功设置"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§c九连§a开箱时的公告为"+args[2]));
						Other.data.set("Info."+args[1]+".nine", args[2]);
					}
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		return true;	
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
						break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
						sender.sendMessage("§c不存在这个抽奖箱");
						return true;
						}
					}
					if(Other.data.getString("CrateItem")==null) {
						sender.sendMessage("§c未设置箱子");
						return true;
					}
					if(Bukkit.getServer().getPlayer(args[2]) == null) {
						sender.sendMessage("§c这个玩家不存在/不在线");
						return true;
					}
					Player player = Bukkit.getServer().getPlayer(args[2]);
					a=0;						ItemStack item = GetItemStack(Other.data.getString("CrateItem"));
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
					item.setItemMeta(meta);
					player.getInventory().addItem(item);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a抽奖箱成功"));
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						if(Other.data.getString("CrateKey")==null) {
							sender.sendMessage("§c未设置钥匙");
							return true;
						}
						if(Bukkit.getServer().getPlayer(args[2]) == null) {
							sender.sendMessage("§c这个玩家不存在/不在线");
							return true;
						}
						Player player = Bukkit.getServer().getPlayer(args[2]);
						a=0;
						ItemStack item = GetItemStack(Other.data.getString("CrateKey"));
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
						item.setItemMeta(meta);
						player.getInventory().addItem(item);
						if(Other.config.getBoolean("KeyMessage"))
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("GetKeyMessage").replace("[key]", Other.data.getString("Info."+args[1]+".color")+args[1])));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a钥匙成功"));
						return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
			}
			return true;
		}
		if(args.length==4) {
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						int number = 1;
						try {
							number =Integer.parseInt(args[2]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c次数只能为整数");
							return true;
						}
						double cd = 0.5;
						try {
							cd =Double.parseDouble(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c请输入数字");
							return true;
						}
						if(number<=0) {
							sender.sendMessage("§c次数不能小于等于0！");
							return true;
						}
						if(cd<=0) {
							sender.sendMessage("§c时间不能小于等于0！");
							return true;
						}
						Other.data.set("Info."+args[1]+".number", number);
						Other.data.set("Info."+args[1]+".cd", cd);
				  		try {
				  			Other.data.save(file1);
				  		} catch (IOException e) {
				  			e.printStackTrace();
			        	}
				  		sender.sendMessage("§a成功设置抽奖箱"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a的开箱时长次数为§2"+number+"§a次");
				  		sender.sendMessage("§a成功设置抽奖箱"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a的每次变幻相差秒数为§2"+cd+"§a秒");
				  		return true;
					}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						int number = 1;
						try {
							number =Integer.parseInt(args[2]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c次数只能为整数");
							return true;
						}
						double cd = 0.5;
						try {
							cd =Double.parseDouble(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c请输入数字");
							return true;
						}
						if(number<=0) {
							sender.sendMessage("§c次数不能小于等于0！");
							return true;
						}
						if(cd<=0) {
							sender.sendMessage("§c时间不能小于等于0！");
							return true;
						}
						Other.data.set("Info."+args[1]+".ninenumber", number);
						Other.data.set("Info."+args[1]+".ninecd", cd);
				  		try {
				  			Other.data.save(file1);
				  		} catch (IOException e) {
				  			e.printStackTrace();
			        	}
				  		sender.sendMessage("§a成功设置抽奖箱"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a的§c九连抽§a开箱时长次数为§2"+number+"§a次");
				  		sender.sendMessage("§a成功设置抽奖箱"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a的§c九连抽§a每次变幻相差秒数为§2"+cd+"§a秒");
				  		return true;
					}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								return true;
							}
						}
						if(Other.data.getString("CrateKey")==null) {
							sender.sendMessage("§c未设置钥匙");
							return true;
						}
						if(Bukkit.getServer().getPlayer(args[2]) == null) {
							sender.sendMessage("§c这个玩家不存在/不在线");
							return true;
						}
						int amount = 1;
						try {
							amount =Integer.parseInt(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("§c请输入数字");
							return true;
						}
						Player player = Bukkit.getServer().getPlayer(args[2]);
						a=0;
						ItemStack item = GetItemStack(Other.data.getString("CrateKey"));
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
						item.setItemMeta(meta);
						item.setAmount(amount);
						player.getInventory().addItem(item);
						if(Other.config.getBoolean("KeyMessage"))
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("GetKeyMessage").replace("[key]", Other.data.getString("Info."+args[1]+".color")+args[1])));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a钥匙成功"));
						return true;
					}
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("§c当前没有存在任何抽奖箱");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
						break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
						sender.sendMessage("§c不存在这个抽奖箱");
						return true;
						}
					}
					if(Other.data.getString("CrateItem")==null) {
						sender.sendMessage("§c未设置箱子");
						return true;
					}
					if(Bukkit.getServer().getPlayer(args[2]) == null) {
						sender.sendMessage("§c这个玩家不存在/不在线");
						return true;
					}
					int amount = 1;
					try {
						amount =Integer.parseInt(args[3]);
					}catch(NumberFormatException e) {
						sender.sendMessage("§c请输入数字");
						return true;
					}
					Player player = Bukkit.getServer().getPlayer(args[2]);
					a=0;						
					ItemStack item = GetItemStack(Other.data.getString("CrateItem"));
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
					item.setItemMeta(meta);
					item.setAmount(amount);
					player.getInventory().addItem(item);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('§', "§a给予§b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"§a抽奖箱成功"));
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			return true;
		}
		if(sender.isOp()) {
		sender.sendMessage("§a/cl help <页数> §2使用指令帮助中心");
		}else {
			sender.sendMessage("§cNull");
		}
		return true;
	}
//	ItemStack转String
	public String GetItemData(ItemStack item) {
		String a;
		try {
		    a = new StreamSerializer().serializeItemStack(item);
		} catch (Exception e) {
		    a = null;
		}
		return a;
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
