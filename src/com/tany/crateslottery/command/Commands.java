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
					sender.sendMessage("��a[]==================��2[������a����<1/3>]==================[]");
					sender.sendMessage("��a��l����ָ��");
					sender.sendMessage("��a/cl help <ҳ��> ��6ʹ��ָ���������");
					sender.sendMessage("");
					sender.sendMessage("��a/cl gui  ��6������gui");
					sender.sendMessage("��a/cl reload  ��6���ز������/�����ļ�");
					sender.sendMessage("��a/cl crate [��������]  [���] [����]  ��6����齱����");
					sender.sendMessage("��a/cl key [��������] [���] [����]  ��6����齱Կ��");
					sender.sendMessage("��a/cl setcrate ��6����������Ʒ���ó��ķ��鼴Ϊ�齱��");
					sender.sendMessage("��a/cl setkey ��6����������ƷΪ�齱Կ��");
					sender.sendMessage("��a[]=========================��2==========================[]");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				Other.config = YamlConfiguration.loadConfiguration(file);
				Other.data = YamlConfiguration.loadConfiguration(file1);
				Other.message = YamlConfiguration.loadConfiguration(file2);
				sender.sendMessage("��a���سɹ�");
				return true;
			}
			if(args[0].equalsIgnoreCase("gui")) {
				if(sender.isOp()) {
					if(sender instanceof Player) {
						Gui.gui((Player) sender);
						return true;
					}
				sender.sendMessage("��c����̨����ʹ�ô�ָ��");
				return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl 9ninetime ��2[��������]  [��ô���] [���ʱ��] Ϊ������ӵġ�c�������2���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl time ��2[��������]  [��ô���] [���ʱ��] Ϊ������ӵ���ͨ���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nine")) {
				if(sender.isOp()) {
					sender.sendMessage("��aд�������˽�����Ǻ�������ʲô����~");
					sender.sendMessage("��a9Nine���µ�һ����������");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl bc [��������] [����] ");
					sender.sendMessage("��2�������ӿ���ʱȫ������[���д���ޡ�������]��[player]������ұ���");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("setcrate")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						Player p = (Player) sender;
						ItemStack item=null;
							  if(p.getInventory().getItemInHand() == null || p.getInventory().getItemInHand().getType() == Material.AIR) {
								  p.sendMessage("��c����������Ҫ���÷������Ʒ״̬����Ʒ");
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
						  sender.sendMessage("��a�ɹ�������������");
						  return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				sender.sendMessage("��c����̨����ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("setkey")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						Player p = (Player) sender;
						ItemStack item=null;
						if(p.getInventory().getItemInHand() == null || p.getInventory().getItemInHand().getType() == Material.AIR) {
						  p.sendMessage("��c���ϲ���Ϊ����");
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
						  sender.sendMessage("��a�ɹ��������ÿ���Կ��");
						  return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				sender.sendMessage("��c����̨����ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl key [��������] [���] [����]  ��2����齱Կ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl crate [��������]  [���] [ ����]  ��2����齱���飨����ʱ���յģ�");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			return true;
		}
		if(args.length==2) {
			if(args[0].equalsIgnoreCase("show")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("��c����̨����ʹ�ô�ָ��");
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
					sender.sendMessage("��c����̨���ܳ齱");
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
    			if(!Other.data.getString("Info."+name+".announcement").equals("��"))
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
					sender.sendMessage("��c����̨���ܳ齱");
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
				
    			if(!Other.data.getString("Info."+name+".nine").equals("��"))
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
						sender.sendMessage("��c����������");
						return true;
					}
					if(number<=0) {
						sender.sendMessage("��cû�����ҳ����");
						return true;
					}
					if(number==1) {
						sender.sendMessage("��a[]==================��2[������a����<1/3>]==================[]");
						sender.sendMessage("��a��l����ָ��");
						sender.sendMessage("��a/cl help <ҳ��> ��6ʹ��ָ���������");
						sender.sendMessage("");
						sender.sendMessage("��a/cl gui  ��6������gui");
						sender.sendMessage("��a/cl reload  ��6���ز������/�����ļ�");
						sender.sendMessage("��a/cl crate [��������]  [���] [����]  ��6����齱����");
						sender.sendMessage("��a/cl key [��������] [���] [����]  ��6����齱Կ��");
						sender.sendMessage("��a/cl setcrate ��6����������Ʒ���ó��ķ��鼴Ϊ�齱��");
						sender.sendMessage("��a/cl setkey ��6����������ƷΪ�齱Կ��");
						sender.sendMessage("��a[]=========================��2==========================[]");
						return true;
					}
					if(number==2) {
						sender.sendMessage("��a[]==================��2[������a����<2/3>]==================[]");
						sender.sendMessage("��a��l�Զ������");
						sender.sendMessage("��a/cl help <ҳ��> ��6ʹ��ָ���������");
						sender.sendMessage("");
						sender.sendMessage("��a/cl bc [��������] [����] ");
						sender.sendMessage("��a/cl 9nine [��������] [����] ");
						sender.sendMessage("��a/cl time ��2[��������] [��ô���] [���ʱ��] ��6��ͨ���䵥������ʱ��");
						sender.sendMessage("��a/cl 9ninetime ��2[��������] [��ô���] [���ʱ��] ��c�������6���䵥������ʱ��");
						sender.sendMessage("��6Ϊ������ӵ�������ʱ�䣬ϸ��config.yml��ġ����䲿�֡��������");
						sender.sendMessage("��a[]=========================��2==========================[]");
						return true;
					}
					if(number==3) {
						sender.sendMessage("��a[]==================��2[������a����<3/3>]==================[]");
						sender.sendMessage("��a��lָ������");
						sender.sendMessage("��a/cl help <ҳ��> ��6ʹ��ָ���������");
						sender.sendMessage("");
						sender.sendMessage("��a/cl start ��2[��������]  ��6���Լ���ʼ�����������");
						sender.sendMessage("��a/cl ninestart ��2[��������]  ��6���Լ���ʼ��c�������6�������");
						sender.sendMessage("��a/cl start ��2[��������] [���]  ��6����ҿ�ʼ�����������");
						sender.sendMessage("��a/cl ninestart ��2[��������] [���]  ��6����ҿ�ʼ��c�������6�������");
						sender.sendMessage("��a/cl show ��2[��������]  ��6�鿴������ӵĽ�������");
						sender.sendMessage("��a/cl show ��2[��������] [���]  ��6����Ҳ鿴������ص�����");
						sender.sendMessage("��a[]=========================��2==========================[]");
						return true;
					}
					sender.sendMessage("��cû�����ҳ����");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl 9ninetime ��2[��������] [��ô���] [���ʱ��] Ϊ������ӵġ�c�������2���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl time ��2[��������] [��ô���] [���ʱ��] Ϊ������ӵ���ͨ���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9nine")) {
				if(sender.isOp()) {
					sender.sendMessage("��a�Ҵ��ɫ��Ų������磬ȭ���ж��磬ͷ��������");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl bc [��������] [����] ");
					sender.sendMessage("��2�������ӿ���ʱȫ������[���д���ޡ�������]��[player]������ұ���");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
							sender.sendMessage("��c��ǰû�д����κγ齱��");
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
								sender.sendMessage("��c����������齱��");
								a=0;
								return true;
							}
						}
						if(Other.data.getString("CrateItem")==null) {
							sender.sendMessage("��cδ��������");
							return true;
						}
						ItemStack item = GetItemStack(Other.data.getString("CrateItem"));
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
						item.setItemMeta(meta);
						((Player) sender).getInventory().addItem(item);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�齱��ɹ�"));
						return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				sender.sendMessage("��c���ܸ�����̨��Ʒ");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender instanceof Player) {
					if(sender.isOp()) {
						if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
							sender.sendMessage("��c��ǰû�д����κγ齱��");
							return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("��c����������齱��");
								return true;
							}
						}
						if(Other.data.getString("CrateKey") == null) {
							sender.sendMessage("��cδ����Կ��");
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
						sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��aԿ�׳ɹ�"));
						return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				sender.sendMessage("��c���ܸ�����̨��Ʒ");
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
					sender.sendMessage("��c������δ����");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("��c��û��Ȩ���������˳齱");
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
					sender.sendMessage("��c������δ����");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("��c��û��Ȩ���������˳齱");
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
    			if(!Other.data.getString("Info."+name+".announcement").equals("��"))
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
					sender.sendMessage("��c������δ����");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("��c��û��Ȩ���������˳齱");
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
    			if(!Other.data.getString("Info."+name+".nine").equals("��"))
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
					sender.sendMessage("��a/cl 9ninetime ��2[��������]  [��ô���] [���ʱ��] Ϊ������ӵġ�c�������2���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					sender.sendMessage("��a/cl time ��2[��������]  [��ô���] [���ʱ��] Ϊ������ӵ���ͨ���䵥������ʱ��");
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("bc")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("��c����������齱��");
							return true;
						}
					}
					a=0;
					if(args[2].equals("��")) {
						sender.sendMessage("��a�ɹ�ȡ��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a����ʱ�Ĺ���");
						Other.data.set("Info."+args[1]+".announcement", "��");
					}else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "��a�ɹ�����"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a����ʱ�Ĺ���Ϊ"+args[2]));
						Other.data.set("Info."+args[1]+".announcement", args[2]);
					}
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		return true;	
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9Nine")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
					}
					int a=0;
					for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
							break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
							sender.sendMessage("��c����������齱��");
							return true;
						}
					}
					a=0;
					if(args[2].equals("��")) {
						sender.sendMessage("��a�ɹ�ȡ��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��c������a����ʱ�Ĺ���");
						Other.data.set("Info."+args[1]+".nine", "��");
					}else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "��a�ɹ�����"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��c������a����ʱ�Ĺ���Ϊ"+args[2]));
						Other.data.set("Info."+args[1]+".nine", args[2]);
					}
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		return true;	
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
						break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
						sender.sendMessage("��c����������齱��");
						return true;
						}
					}
					if(Other.data.getString("CrateItem")==null) {
						sender.sendMessage("��cδ��������");
						return true;
					}
					if(Bukkit.getServer().getPlayer(args[2]) == null) {
						sender.sendMessage("��c�����Ҳ�����/������");
						return true;
					}
					Player player = Bukkit.getServer().getPlayer(args[2]);
					a=0;						ItemStack item = GetItemStack(Other.data.getString("CrateItem"));
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(meta.getDisplayName()+Other.data.getString("Info."+args[1]+".color")+args[1]);
					item.setItemMeta(meta);
					player.getInventory().addItem(item);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�齱��ɹ�"));
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("��c����������齱��");
								return true;
							}
						}
						if(Other.data.getString("CrateKey")==null) {
							sender.sendMessage("��cδ����Կ��");
							return true;
						}
						if(Bukkit.getServer().getPlayer(args[2]) == null) {
							sender.sendMessage("��c�����Ҳ�����/������");
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
						sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��aԿ�׳ɹ�"));
						return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
			}
			return true;
		}
		if(args.length==4) {
			if(args[0].equalsIgnoreCase("time")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("��c����������齱��");
								return true;
							}
						}
						int number = 1;
						try {
							number =Integer.parseInt(args[2]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c����ֻ��Ϊ����");
							return true;
						}
						double cd = 0.5;
						try {
							cd =Double.parseDouble(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c����������");
							return true;
						}
						if(number<=0) {
							sender.sendMessage("��c��������С�ڵ���0��");
							return true;
						}
						if(cd<=0) {
							sender.sendMessage("��cʱ�䲻��С�ڵ���0��");
							return true;
						}
						Other.data.set("Info."+args[1]+".number", number);
						Other.data.set("Info."+args[1]+".cd", cd);
				  		try {
				  			Other.data.save(file1);
				  		} catch (IOException e) {
				  			e.printStackTrace();
			        	}
				  		sender.sendMessage("��a�ɹ����ó齱��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�Ŀ���ʱ������Ϊ��2"+number+"��a��");
				  		sender.sendMessage("��a�ɹ����ó齱��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a��ÿ�α���������Ϊ��2"+cd+"��a��");
				  		return true;
					}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("9ninetime")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("��c����������齱��");
								return true;
							}
						}
						int number = 1;
						try {
							number =Integer.parseInt(args[2]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c����ֻ��Ϊ����");
							return true;
						}
						double cd = 0.5;
						try {
							cd =Double.parseDouble(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c����������");
							return true;
						}
						if(number<=0) {
							sender.sendMessage("��c��������С�ڵ���0��");
							return true;
						}
						if(cd<=0) {
							sender.sendMessage("��cʱ�䲻��С�ڵ���0��");
							return true;
						}
						Other.data.set("Info."+args[1]+".ninenumber", number);
						Other.data.set("Info."+args[1]+".ninecd", cd);
				  		try {
				  			Other.data.save(file1);
				  		} catch (IOException e) {
				  			e.printStackTrace();
			        	}
				  		sender.sendMessage("��a�ɹ����ó齱��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�ġ�c�������a����ʱ������Ϊ��2"+number+"��a��");
				  		sender.sendMessage("��a�ɹ����ó齱��"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�ġ�c�������aÿ�α���������Ϊ��2"+cd+"��a��");
				  		return true;
					}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			if(args[0].equalsIgnoreCase("key")) {
				if(sender.isOp()) {
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
							if(crate.equals(args[1])) {
								break;
							}
							a++;
							if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
								sender.sendMessage("��c����������齱��");
								return true;
							}
						}
						if(Other.data.getString("CrateKey")==null) {
							sender.sendMessage("��cδ����Կ��");
							return true;
						}
						if(Bukkit.getServer().getPlayer(args[2]) == null) {
							sender.sendMessage("��c�����Ҳ�����/������");
							return true;
						}
						int amount = 1;
						try {
							amount =Integer.parseInt(args[3]);
						}catch(NumberFormatException e) {
							sender.sendMessage("��c����������");
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
						sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��aԿ�׳ɹ�"));
						return true;
					}
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
			}
			if(args[0].equalsIgnoreCase("crate")) {
				if(sender.isOp()) {
					if(Other.data.getString("CrateItem")==null) {
						sender.sendMessage("��cδ��������");
						return true;
					}
					if(Other.data.getConfigurationSection("Info").getKeys(false).size()==0) {
						sender.sendMessage("��c��ǰû�д����κγ齱��");
						return true;
						}
						int a=0;
						for(String crate:Other.data.getConfigurationSection("Info").getKeys(false)) {
						if(crate.equals(args[1])) {
						break;
						}
						a++;
						if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
						sender.sendMessage("��c����������齱��");
						return true;
						}
					}
					if(Bukkit.getServer().getPlayer(args[2]) == null) {
						sender.sendMessage("��c�����Ҳ�����/������");
						return true;
					}
					int amount = 1;
					try {
						amount =Integer.parseInt(args[3]);
					}catch(NumberFormatException e) {
						sender.sendMessage("��c����������");
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
					sender.sendMessage(ChatColor.translateAlternateColorCodes('��', "��a�����b"+Other.data.getString("Info."+args[1]+".color")+args[1]+"��a�齱��ɹ�"));
					return true;
				}
				sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
				return true;
			}
			return true;
		}
		if(sender.isOp()) {
			sender.sendMessage("��a/cl help <ҳ��> ��2ʹ��ָ���������");
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
				sender.sendMessage("��cNull");
				return true;
			}
			sender.sendMessage("��cNull");
			return true;
		}
	}
//	ItemStackתString
	public String GetItemData(ItemStack item) {
		String a;
		try {
		    a = new StreamSerializer().serializeItemStack(item);
		} catch (Exception e) {
		    a = null;
		}
		return a;
	}
//	StringתItemStack
	public ItemStack GetItemStack(String data) {
		try {
			return new StreamSerializer().deserializeItemStack(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
