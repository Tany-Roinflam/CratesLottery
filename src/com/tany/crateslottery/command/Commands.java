package com.tany.crateslottery.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Main;
import com.tany.crateslottery.Other;
import com.tany.crateslottery.gui.Gui;
import com.tany.crateslottery.task.NineWingTask;
import com.tany.crateslottery.task.NineWingTaskS;
import com.tany.crateslottery.task.WingTask;
import com.tany.crateslottery.task.WingTaskS;

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
					sender.sendMessage("§a[]==================§2[帮助§a界面<1/3>]==================[]");
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
				Other.data = YamlConfiguration.loadConfiguration(file1);
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
						  List<String> lore = Other.message.getStringList("CrateLore");
						  ArrayList<String> lores = new ArrayList<String>();
						  for(String loreadd:lore) {
							  lores.add(ChatColor.translateAlternateColorCodes('&', loreadd));
						  }
						  meta.setLore(lores);
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
						  List<String> lore = Other.message.getStringList("KeyLore");
						  ArrayList<String> lores = new ArrayList<String>();
						  for(String loreadd:lore) {
							  lores.add(ChatColor.translateAlternateColorCodes('&', loreadd));
						  }
						  meta.setLore(lores);
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
			if(args[0].equalsIgnoreCase("show")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能使用此指令");
					return true;
				}
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				a=0;
				Player player = (Player) sender;
				String name = args[1];
				if(!player.hasPermission("cl.showall")&&!player.hasPermission("cl.show."+name)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoShowMessage:").replace("[crate]", name)));
					return true;
				}
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				Gui.showcrate(player, name);
				return true;
			}
			if(args[0].equalsIgnoreCase("start")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能抽奖");
					return true;
				}
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				a=0;
				if(!sender.hasPermission("cl.startall")&&!sender.hasPermission("cl.start."+args[1])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoAuthorityMessage").replace("[crate]", args[1])));
					return true;
				}
				String name = args[1];
				Player player = (Player) sender;
				if(!player.hasPermission("cl.lottery")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
					return true;
				}
				if(!player.hasPermission("cl.allcrate")&&!player.hasPermission("cl.crate."+name)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
					return true;
				}
				List<String> itemlist = Other.data.getStringList("Info."+name+".data");
				int g=1;
				if(itemlist.size()==0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				for(String item:itemlist) {
				if(!item.split(":")[1].equals("null")) {
					break;
				}
				if(g==itemlist.size()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				g++;
				}
				g=1;
    			if(!Other.data.getString("Info."+name+".announcement").equals("无"))
    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", player.getName())));
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				if(Other.data.getBoolean("Info."+name+".animation")) {
					if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
					new WingTask(player, name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
					else
					new WingTask(player, name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
				}else {
					new WingTaskS(player, name).runTaskTimer(Main.plugin, 0, 0);
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("ninestart")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能抽奖");
					return true;
				}
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						a=0;
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						a=0;
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				if(!sender.hasPermission("cl.ninestartall")&&!sender.hasPermission("cl.ninestart."+args[1])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineAuthorityMessage").replace("[crate]", args[1])));
					return true;
				}
				String name = args[1];
				Player player = (Player) sender;
				if(!player.hasPermission("cl.ninelottery")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
					return true;
				}
				if(Other.data.getBoolean("Info."+name+".clear")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
					return true;
				}
				if(!player.hasPermission("cl.allcrate")&&!player.hasPermission("cl.crate."+name)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
					return true;
				}
				List<String> itemlist = Other.data.getStringList("Info."+name+".data");
				int g=1;
				if(itemlist.size()==0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				for(String item:itemlist) {
				if(!item.split(":")[1].equals("null")) {
					break;
				}
				if(g==itemlist.size()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				g++;
				}
				g=1;
				
    			if(!Other.data.getString("Info."+name+".nine").equals("无"))
    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", player.getName())));
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				if(Other.data.getBoolean("Info."+name+".nineanimation")) {
					if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
					new NineWingTask(player, name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
					else
					new NineWingTask(player, name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
				} else {
					new NineWingTaskS(player, name).runTaskTimer(Main.plugin, 0, 0);
				}
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
						sender.sendMessage("§a[]==================§2[帮助§a界面<1/3>]==================[]");
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
						sender.sendMessage("§a[]==================§2[帮助§a界面<2/3>]==================[]");
						sender.sendMessage("§a§l自定义相关");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl bc [箱子名称] [公告] ");
						sender.sendMessage("§a/cl 9nine [箱子名称] [公告] ");
						sender.sendMessage("§a/cl time §2[箱子名称] [变幻次数] [变幻时间] §6普通开箱单独设置时间");
						sender.sendMessage("§a/cl 9ninetime §2[箱子名称] [变幻次数] [变幻时间] §c九连抽§6开箱单独设置时间");
						sender.sendMessage("§6为这个箱子单独设置时间，细看config.yml里的“开箱部分”自行理解");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					if(number==3) {
						sender.sendMessage("§a[]==================§2[帮助§a界面<3/3>]==================[]");
						sender.sendMessage("§a§l指令开箱相关");
						sender.sendMessage("§a/cl help <页数> §6使用指令帮助中心");
						sender.sendMessage("");
						sender.sendMessage("§a/cl start §2[箱子名称]  §6让自己开始单抽这个箱子");
						sender.sendMessage("§a/cl ninestart §2[箱子名称]  §6让自己开始§c九连抽§6这个箱子");
						sender.sendMessage("§a/cl start §2[箱子名称] [玩家]  §6让玩家开始单抽这个箱子");
						sender.sendMessage("§a/cl ninestart §2[箱子名称] [玩家]  §6让玩家开始§c九连抽§6这个箱子");
						sender.sendMessage("§a/cl show §2[箱子名称]  §6查看这个箱子的奖池内容");
						sender.sendMessage("§a/cl show §2[箱子名称] [玩家]  §6让玩家查看这个奖池的内容");
						sender.sendMessage("§a[]=========================§2==========================[]");
						return true;
					}
					sender.sendMessage("§c没有这个页数！");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl 9ninetime §2[箱子名称] [变幻次数] [变幻时间] 为这个箱子的§c九连抽§2开箱单独设置时间");
					return true;
				}
				sender.sendMessage("§c你没有权限使用此指令");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("§a/cl time §2[箱子名称] [变幻次数] [变幻时间] 为这个箱子的普通开箱单独设置时间");
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
								a=0;
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("§c不存在这个抽奖箱");
								a=0;
								return true;
							}
						}
						if(Other.data.getString("CrateItem")==null) {
							sender.sendMessage("§c未设置箱子");
							return true;
						}
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
			return true;
		}
		if(args.length==3) {
			if(args[0].equalsIgnoreCase("show")) {
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				a=0;
				if(Bukkit.getPlayer(args[2])==null) {
					sender.sendMessage("§c这个玩家未在线");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("§c你没有权限让其他人抽奖");
					return true;
				}
				Player player = Bukkit.getPlayer(args[2]);
				String name = args[1];
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				Gui.showcrate(player, name);
				return true;
			}
			if(args[0].equalsIgnoreCase("start")) {
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						a=0;
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						a=0;
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				if(Bukkit.getPlayer(args[2])==null) {
					sender.sendMessage("§c这个玩家未在线");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("§c你没有权限让其他人抽奖");
					return true;
				}
				String name = args[1];
				Player player = Bukkit.getPlayer(args[2]);
				List<String> itemlist = Other.data.getStringList("Info."+name+".data");
				int g=1;
				if(itemlist.size()==0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				for(String item:itemlist) {
				if(!item.split(":")[1].equals("null")) {
					break;
				}
				if(g==itemlist.size()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				g++;
				}
				g=1;
    			if(!Other.data.getString("Info."+name+".announcement").equals("无"))
    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", player.getName())));
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				if(Other.data.getBoolean("Info."+name+".animation")) {
					if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
					new WingTask(player, name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
					else
					new WingTask(player, name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
				}else {
					new WingTaskS(player, name).runTaskTimer(Main.plugin, 0, 0);
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("ninestart")) {
				if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotAnyCrates")));
					return true;
				}
				int a=0;
				for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
					if(args[1].equals(name)) {
						a=0;
						break;
					}
					a++;
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==a) {
						a=0;
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NotCrates")));
						return true;
					}
				}
				if(Bukkit.getPlayer(args[2])==null) {
					sender.sendMessage("§c这个玩家未在线");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("§c你没有权限让其他人抽奖");
					return true;
				}
				String name = args[1];
				Player player = Bukkit.getPlayer(args[2]);
				List<String> itemlist = Other.data.getStringList("Info."+name+".data");
				int g=1;
				if(itemlist.size()==0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				for(String item:itemlist) {
				if(!item.split(":")[1].equals("null")) {
					break;
				}
				if(g==itemlist.size()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
					return true;
				}
				g++;
				}
				g=1;
    			if(!Other.data.getString("Info."+name+".nine").equals("无"))
    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", player.getName())));
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
				if(Other.data.getBoolean("Info."+name+".nineanimation")) {
					if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
					new NineWingTask(player, name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
					else
					new NineWingTask(player, name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
				} else {
					new NineWingTaskS(player, name).runTaskTimer(Main.plugin, 0, 0);
				}
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
					if(Other.data.getString("CrateItem")==null) {
						sender.sendMessage("§c未设置箱子");
						return true;
					}
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
			return true;
		}else {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				boolean a = false;
				if(player.hasPermission("cl.startall")) {
					for(String p:Other.data.getConfigurationSection("Info").getKeys(false)) {
						a=true;
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowStart").replace("[crate]", Other.data.getString("Info."+p+".color")+p)));
					}
				} else {
					for(PermissionAttachmentInfo p:sender.getEffectivePermissions()) {
						if(p.getPermission().startsWith("cl.start.")) {
							for(String b:Other.data.getConfigurationSection("Info").getKeys(false)) {
								if(b.equals(p.getPermission().split("\\.")[2])) {
									a=true;
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowStart").replace("[crate]", Other.data.getString("Info."+p.getPermission().split("\\.")[2])+".color")+p.getPermission().split("\\.")[2]));
								}
							}
						}
					}
				}
				int c = 0;
				if(player.hasPermission("cl.ninestartall")) {
					for(String p:Other.data.getConfigurationSection("Info").getKeys(false)) {
						c++;
						if(c==1&&a)
							sender.sendMessage("");
						a=true;
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowNineStart").replace("[crate]", Other.data.getString("Info."+p+".color")+p)));
					}
				} else {
					for(PermissionAttachmentInfo p:sender.getEffectivePermissions()) {
						if(p.getPermission().startsWith("cl.ninestart.")) {
							for(String b:Other.data.getConfigurationSection("Info").getKeys(false)) {
								if(b.equals(p.getPermission().split("\\.")[2])) {
									c++;
									if(c==1&&a)
										sender.sendMessage("");
									a=true;
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowNineStart").replace("[crate]", Other.data.getString("Info."+p.getPermission().split("\\.")[2])+".color")+p.getPermission().split("\\.")[2]));
								}
							}
						}
					}
				}
				c=1;
				if(player.hasPermission("cl.showall")) {
					for(String p:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(a&&c!=0) {
							sender.sendMessage("");
							c=0;
						}
						a=true;
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowMessage").replace("[crate]", Other.data.getString("Info."+p+".color")+p)));
					}
				} else {
					for(PermissionAttachmentInfo p:sender.getEffectivePermissions()) {
						if(p.getPermission().startsWith("cl.show.")) {
							for(String b:Other.data.getConfigurationSection("Info").getKeys(false)) {
								if(b.equals(p.getPermission().split("\\.")[2])) {
									if(a&&c!=0) {
										sender.sendMessage("");
										c=0;
									}
									a=true;
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowMessage").replace("[crate]", Other.data.getString("Info."+p.getPermission().split("\\.")[2])+".color")+p.getPermission().split("\\.")[2]));
								}
							}
						}
					}
				}
				if(!a)
				sender.sendMessage("§cNull");
				return true;
			}
			sender.sendMessage("§cNull");
			return true;
		}
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
