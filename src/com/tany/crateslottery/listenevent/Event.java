package com.tany.crateslottery.listenevent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Main;
import com.tany.crateslottery.conversation.Name;
import com.tany.crateslottery.gui.Gui;
import com.tany.crateslottery.task.NineWingTask;
import com.tany.crateslottery.task.NineWingTaskS;
import com.tany.crateslottery.task.WingTask;
import com.tany.crateslottery.task.WingTaskS;

import net.minecraft.server.v1_12_R1.MobEffects;

public class Event implements Listener  {
	public static String crate = null;
    HashMap<Player, Boolean> Sneak = new HashMap<Player, Boolean>();
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
    
    @EventHandler
	public void Toggle(PlayerToggleSneakEvent event) {
	    FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
		if(event.isSneaking()) {
			Sneak.put(event.getPlayer(), true);
		}else {
			Sneak.put(event.getPlayer(), false);
		}
	}

	@EventHandler
    public void Break(BlockBreakEvent event) {
        FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
        FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
        FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
        List<String> Location = config2.getStringList("Location");
        Location block = event.getBlock().getLocation();
        if(Location.size()!=0) {
        	for(String location:Location) {
				String world = location.split(":")[0];
				int x = Integer.parseInt(location.split(":")[1]);
				int y = Integer.parseInt(location.split(":")[2]);
				int z = Integer.parseInt(location.split(":")[3]);
				if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
					if(event.getPlayer().isOp()) {
						if(Sneak.containsKey(event.getPlayer())&&Sneak.get(event.getPlayer())) {
							event.getPlayer().sendMessage("��a�ɹ��ƻ��齱�䣡");
							Location.remove(location);
							config2.set("Location", Location);
					  		try {
					  			config2.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
						}
					}else {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("BreakCrateMessage")));
					}
					return;
				}
        	}
        }
    }
    
    @EventHandler
	public void onInteract(PlayerInteractEvent event) {
	    FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
		Action action=event.getAction();
		if((event.getItem()!=null&&event.getItem().getItemMeta().hasDisplayName()&&event.getItem().getItemMeta().getDisplayName().startsWith("��a�����6Կ�ס�3��"))) {
			event.setCancelled(true);
			String name = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName().replace("��a�����6Կ�ס�3��", ""));
			if(config2.getString("Info."+name+".color")==null) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ClearKeyMessage")));
				event.getPlayer().getInventory().setItemInHand(null);
	    		return;
			}
			if(action.equals(action.RIGHT_CLICK_AIR)) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("UseKeyMessage")));
				return;
			}
		}
		if(event.getItem()!=null&&event.getItem().getItemMeta().hasDisplayName()&&!event.getItem().getItemMeta().getDisplayName().equals("��a���2�ӡ�5��")&&event.getItem().getItemMeta().getDisplayName().startsWith("��a���2�ӡ�5��")) {
			String name = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName().replace("��a���2�ӡ�5��", ""));
			if(config2.getString("Info."+name+".color")==null) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ClearCrateMessage")));
				event.getPlayer().getInventory().setItemInHand(null);
			}
			return;
		}
		if(event.getClickedBlock()!=null&&action.equals(action.RIGHT_CLICK_BLOCK)) {
	    	List<String> Location = config2.getStringList("Location");
	    	Location block = event.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String name = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	    				event.setCancelled(true);
	        			if(config2.getString("Info."+name+".color")==null) {
	        				event.getClickedBlock().setType(Material.AIR);
	        				event.setCancelled(true);
	        				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ClearCrateMessage")));
							Location.remove(location);
							config2.set("Location", Location);
					  		try {
					  			config2.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
	        				return;
	        			}
						String version = Bukkit.getServer().getVersion();
						if(version.contains("1.9")||version.contains("1.10")||version.contains("1.11")||version.contains("1.12")||version.contains("1.13")||version.contains("1.14")||version.contains("1.15")) {
							if(event.getPlayer().getInventory().getItemInHand()==null||event.getPlayer().getInventory().getItemInHand().getType()==Material.AIR) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}else if(!event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}else if(!ChatColor.stripColor(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().replace("��a�����6Կ�ס�3��", "")).equals(name)) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}
						}else {
							if(event.getPlayer().getInventory().getItemInHand()==null||event.getPlayer().getInventory().getItemInHand().getType()==Material.AIR) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}else if(!event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}else if(!ChatColor.stripColor(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().replace("��a�����6Կ�ס�3��", "")).equals(name)) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoOpenCrateMessage").replace("[key]", config2.getString("Info."+name+".color")+name)));
								return;
							}
						}
	    				if(Sneak.containsKey(event.getPlayer())&&Sneak.get(event.getPlayer())) {
	    					if(!event.getPlayer().hasPermission("cl.ninelottery")) {
	    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoNineLotteryMessage")));
	    						return;
	    					}
	    					if(config2.getBoolean("Info."+name+".clear")) {
	    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ClearMessage")));
	    						return;
	    					}
	        					List<String> itemlist = config2.getStringList("Info."+name+".data");
	        					int a=1;
	        					for(String item:itemlist) {
	        						if(!item.split(":")[1].equals("null")) {
	        							break;
	        						}
	        						if(a==itemlist.size()) {
	        							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoItemMessage")));
	        							return;
	        						}
	        						a++;
	        					}
	            					if(event.getItem().getAmount()<9) {
	            						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoNineKeyMessage")));
	            						return;
	            					}
	            					int amount = event.getItem().getAmount();
	            					amount=amount-9;
	            	    			if(version.contains("1.9")||version.contains("1.10")||version.contains("1.11")||version.contains("1.12")||version.contains("1.13")||version.contains("1.14")||version.contains("1.15")) {
	                	    			if(amount!=0)
	            	    				event.getPlayer().getInventory().getItemInHand().setAmount(amount);
	                	    			else
	                	    			event.getPlayer().getInventory().setItemInHand(null);
	            	    			}else {
	            	    				if(amount!=0)
	                	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
	                	    			else
	      								event.getPlayer().getInventory().setItemInHand(null);
	            	    			}
	            	    			if(!config2.getString("Info."+name+".nine").equals("��"))
	            	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config2.getString("Info."+name+".nine").replace("[player]", event.getPlayer().getName())));
	            	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NineOpenCrateMessage").replace("[crate]", config2.getString("Info."+name+".color")+name)));
	            					if(config2.getBoolean("Info."+name+".nineanimation")) {
	            						if(config2.getDouble("Info."+name+".ninecd")<=0&&config2.getDouble("Info."+name+".ninenumber")<=0)
	            						new NineWingTask(event.getPlayer(), name,config1.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (config1.getDouble("NineWingSpaceTime")*20));
	            						else
	            						new NineWingTask(event.getPlayer(), name,config2.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (config2.getDouble("Info."+name+".ninecd")*20));
	            					}else {
	            						new NineWingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
	            					}
	                				return;
	    				}else {
	    					if(!event.getPlayer().hasPermission("cl.lottery")) {
	    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoLotteryMessage")));
	    						return;
	    					}
	    					List<String> itemlist = config2.getStringList("Info."+name+".data");
	    					int a=1;
	    					for(String item:itemlist) {
	    						if(!item.split(":")[1].equals("null")) {
	    							break;
	    						}
	    						if(a==itemlist.size()) {
	    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("NoItemMessage")));
	    							return;
	    						}
	    						a++;
	    					}
	    					int amount = event.getItem().getAmount();
	    					amount--;
	    	    			if(version.contains("1.9")||version.contains("1.10")||version.contains("1.11")||version.contains("1.12")||version.contains("1.13")||version.contains("1.14")||version.contains("1.15")) {
	        	    			if(amount!=0)
	    	    				event.getPlayer().getInventory().getItemInHand().setAmount(amount);
	        	    			else
	        	    			event.getPlayer().getInventory().setItemInHand(null);
	    	    			}else {
	    	    				if(amount!=0)
	        	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
	        	    			else
								event.getPlayer().getInventory().setItemInHand(null);
	    	    			}
	    	    			if(!config2.getString("Info."+name+".announcement").equals("��"))
	    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config2.getString("Info."+name+".announcement").replace("[player]", event.getPlayer().getName())));
	    	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("OpenCrateMessage").replace("[crate]", config2.getString("Info."+name+".color")+name)));
	    					if(config2.getBoolean("Info."+name+".animation")) {
	    						if(config2.getDouble("Info."+name+".cd")<=0&&config2.getDouble("Info."+name+".number")<=0)
	    						new WingTask(event.getPlayer(), name, config1.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (config1.getDouble("WingSpaceTime")*20));
	    						else
	    						new WingTask(event.getPlayer(), name, config2.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (config2.getDouble("Info."+name+".cd")*20));
	    					}else {
	    						new WingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
	    					}
	        				return;
	        			}
	    			}
	    		}
	    	}
		}
		if(action.equals(Action.LEFT_CLICK_BLOCK)&&!(Sneak.containsKey(event.getPlayer())&&Sneak.get(event.getPlayer()))) {
	    	List<String> Location = config2.getStringList("Location");
	    	Location block = event.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String name = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	        			if(config2.getString("Info."+name+".color")==null) {
	        				event.getClickedBlock().setType(Material.AIR);
	        				event.setCancelled(true);
	        				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ClearCrateMessage")));
							Location.remove(location);
							config2.set("Location", Location);
					  		try {
					  			config2.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
	        				return;
	        			}
	        			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config3.getString("ShowCrateMessage").replace("[crate]", config2.getString("Info."+name+".color")+name)));
	    				Gui.showcrate(event.getPlayer(), name);
	    				event.setCancelled(true);
	    				return;
	    			}
	    		}
	    	}
		}
	}

	@EventHandler
	public void Place(BlockPlaceEvent event) {
	    FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
	    if(event.getItemInHand()!=null&&event.getItemInHand().hasItemMeta()&&event.getItemInHand().getItemMeta().hasDisplayName()&&event.getItemInHand().getItemMeta().getDisplayName().startsWith("��a���2�ӡ�5��")) {
	    	String title = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().replace("��a���2�ӡ�5��", ""));
	    	int a = 0;
	    	for(String name:config2.getConfigurationSection("Info").getKeys(false)) {
	    		if(name.equals(title)) {
	    			break;
	    		}
	    		a++;
	    		if(a==config2.getConfigurationSection("Info").getKeys(false).size()) {
	    			a=0;
	    			return;
	    		}
	    	}
	    	a=0;
	    	List<String> Location = config2.getStringList("Location");
	    	Location.add(event.getBlock().getLocation().getWorld().getName()+":"+event.getBlock().getLocation().getBlockX()+":"+event.getBlock().getLocation().getBlockY()+":"+event.getBlock().getLocation().getBlockZ()+":"+ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().replace("��a���2�ӡ�5��", "")));
	    	config2.set("Location", Location);
	  		try {
	  			config2.save(file1);
	  		} catch (IOException e) {
	  			e.printStackTrace();
	    	}
	    }
	}

	@EventHandler
	public void Click(InventoryClickEvent player) {
	    FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
		Inventory inventory = player.getClickedInventory();
		if(inventory == null) {
			return;
		}
	    Player p = (Player) player.getWhoClicked();
	    if (!(player.getWhoClicked() instanceof Player)) {
	        return;
	    }
	    if(player.getClickedInventory().getTitle().equals(p.getInventory().getTitle())) {
	    	if(p.getOpenInventory().getTitle().startsWith("��cѡ��4��")||p.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")||p.getOpenInventory().getTitle().startsWith("��c�齱�䣺")) {
	    		player.setCancelled(true);
	    	}else if(p.getOpenInventory().getTitle().startsWith("��c�齱�䣺")||p.getOpenInventory().getTitle().startsWith("��3����")||p.getOpenInventory().getTitle().startsWith("��3���")) {
	    		player.setCancelled(true);
	    	}else if(p.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")||p.getOpenInventory().getTitle().startsWith("��a���ڿ���")||p.getOpenInventory().getTitle().startsWith("��6�齱�����á�b��")||p.getOpenInventory().getTitle().startsWith("��d���ó齱��")) {
	    		player.setCancelled(true);
	    	}
	    }
	    if(player.getClickedInventory().getTitle().equals("��cѡ��4��")) {
	    	player.setCancelled(true);
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��5����һ���µĳ齱��")) {
				p.closeInventory();
	            Conversation conversation = new Conversation(Main.getInstance(), p, new Name());
	            p.beginConversation(conversation);
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��a�������еĳ齱������")) {
				p.closeInventory();
				Gui.cratelist(p, 1);
			}
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			int type = Integer.parseInt(title.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if (player.getSlot() == 53&&player.getClickedInventory().getItem(53).getItemMeta().getDisplayName().equals("��a��һҳ")) {
				Gui.cratelist(p,++type);
			}
			if (player.getSlot() == 45&&player.getClickedInventory().getItem(45).getItemMeta().getDisplayName().equals("��a��һҳ")) {
				Gui.cratelist(p,--type);
			}
			if(player.getSlot() >44) {
				return;
			}
			String cratename = ChatColor.stripColor(player.getCurrentItem().getItemMeta().getDisplayName().replace("��6�齱�����á�b��", ""));
			Gui.choose(p, cratename);
			return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��c�齱�䣺")||player.getClickedInventory().getTitle().startsWith("��a���ڿ���")||player.getClickedInventory().getTitle().startsWith("��c���ھ�����a����")) {
	    	player.setCancelled(true);
	    	return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��6�齱���c��")) {
	    	if(player.getCurrentItem()!=null&&player.getCurrentItem().hasItemMeta()&&player.getCurrentItem().getItemMeta().hasDisplayName()&&(player.getCurrentItem().getItemMeta().getDisplayName().contains("��ȡ��Ľ����ɣ�")||player.getCurrentItem().getItemMeta().getDisplayName().contains("��2��չʾ")))
	        	player.setCancelled(true);
	    		return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��c�齱��6�䣺")) {
	    	if(player.getCurrentItem()!=null&&player.getCurrentItem().hasItemMeta()&&player.getCurrentItem().getItemMeta().hasDisplayName()&&(player.getCurrentItem().getItemMeta().getDisplayName().contains("��ȡ���ս��Ʒ�ɣ�")||player.getCurrentItem().getItemMeta().getDisplayName().contains("��2��չʾ")))
	        	player.setCancelled(true);
	    		return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��3����")) {
	    	player.setCancelled(true);
	    	String title = player.getClickedInventory().getTitle();
	    	String name = title.replace("��3����", "").replace("��3��ʾ����ɫ", "");
	    	name = ChatColor.stripColor(name);
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��4��ɫ")) {
				config2.set("Info."+name+".color", "��4");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��e��ɫ")) {
				config2.set("Info."+name+".color", "��e");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��3��ɫ")) {
				config2.set("Info."+name+".color", "��3");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��1����ɫ")) {
				config2.set("Info."+name+".color", "��1");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��0��ɫ")) {
				config2.set("Info."+name+".color", "��0");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��5��ɫ")) {
				config2.set("Info."+name+".color", "��5");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��6��ɫ")) {
				config2.set("Info."+name+".color", "��6");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��f��ɫ")) {
				config2.set("Info."+name+".color", "��f");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��aǳ��ɫ")) {
				config2.set("Info."+name+".color", "��a");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��2��ɫ")) {
				config2.set("Info."+name+".color", "��2");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��6��ɫ")) {
				config2.set("Info."+name+".color", "��6");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b����ɫ")) {
				config2.set("Info."+name+".color", "��b");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�ۺ�ɫ")) {
				config2.set("Info."+name+".color", "��c");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��d����ɫ")) {
				config2.set("Info."+name+".color", "��d");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��7��ɫ")) {
				config2.set("Info."+name+".color", "��7");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��a���������б�")) {
				p.closeInventory();
				Gui.choose(p, name);
				return;
			}
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��d���ó齱��")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			String name = ChatColor.stripColor(title.replace("���ó齱��", "").replace("�Ŀ��䷽ʽ", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("��6�����Ӧ�ķ��飬���ö�Ӧ�Ŀ��䶯��")) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��a�̶��м�齱")) {
				config2.set("Info."+name+".type", "normal");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����a�̶��м�齱")) {
				config2.set("Info."+name+".ninetype", "normal");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��e���λ�ó齱")) {
				config2.set("Info."+name+".type", "random");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����e���λ�ó齱")) {
				config2.set("Info."+name+".ninetype", "random");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b��Χλ���ڳ齱")) {
				config2.set("Info."+name+".type", "order");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����b��Χλ���ڳ齱")) {
				config2.set("Info."+name+".ninetype", "order");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b����Ʒ������齱")) {
				config2.set("Info."+name+".type", "embellishment");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����6���־��γ齱")) {
				config2.set("Info."+name+".ninetype", "gradient");
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��a���������б�")) {
				p.closeInventory();
				Gui.choose(p, name);
				return;
			}
	    	return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("��3���")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			String name = ChatColor.stripColor(title.replace("��3���", "").replace("��3����ʲô������", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��5�޸Ľ��ؽ���")) {
				Gui.setcrate(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��6������ɫ")) {
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a����")) {
				Gui.way(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��cɾ���������")) {
				config2.set("Info."+name, null);
		  		try {
		  			config2.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}else if(players.getOpenInventory().getTitle().startsWith("��2�齱��"+config2.getString("Info."+name+".color")+name)) {
		  					players.closeInventory();
		  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(players.getOpenInventory().getTitle().startsWith(ChatColor.stripColor("����"+config2.getString("Info."+name+".color")+name+"��ʾ����ɫ"))) {
	  					players.closeInventory();
	  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(player.getClickedInventory().getTitle().startsWith(ChatColor.stripColor("��d���ó齱��"+config2.getString("Info."+name+".color")+name+"�Ŀ��䷽ʽ"))) {
	  					players.closeInventory();
	  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}
		  		}
				p.sendMessage("��cɾ����a"+name+"��c�ɹ�");
				return;
			}
	    }
	}

	@EventHandler
public void Close(InventoryCloseEvent event) {
    FileConfiguration config1=YamlConfiguration.loadConfiguration(file);
    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
    FileConfiguration config3=YamlConfiguration.loadConfiguration(file2);
    if(event.getInventory().getTitle().startsWith("��2�齱��")) {
    	String title = ChatColor.stripColor(event.getInventory().getTitle().replace("��2�齱��", "").replace("��2����", ""));
    	ArrayList<String> data = new ArrayList<String>();
  		int a=0;
  		while(a<54) {
  			String item = null;
  			if(event.getInventory().getItem(a)==null||event.getInventory().getItem(a).getType()==Material.AIR)
  			item = "null";
  			else
  			item = ItemData(event.getInventory().getItem(a));
  			data.add(a+":"+item);
  			a++;
  		}
  		a=0;
  		List<String> exist = config2.getStringList("Info."+title+".data");
  		if(exist.size()==0) {
  			config2.set("Info."+title+".color", "��f");
  			config2.set("Info."+title+".animation", true);
  			config2.set("Info."+title+".nineanimation", true);
  			config2.set("Info."+title+".announcement", "��");
  			config2.set("Info."+title+".nine", "��");
  			config2.set("Info."+title+".number", -1);
  			config2.set("Info."+title+".cd", -1);
  			config2.set("Info."+title+".ninenumber", -1);
  			config2.set("Info."+title+".ninecd", -1);
  			config2.set("Info."+title+".type", "normal");
  			config2.set("Info."+title+".ninetype", "normal");
  			config2.set("Info."+title+".clear", false);
  			config2.set("Info."+title+".info", false);
  			config2.set("Info."+title+".nineinfo", false);
  			crate = null;
  		}
  		config2.set("Info."+title+".data", data);
  		try {
  			config2.save(file1);
  		} catch (IOException e) {
  			e.printStackTrace();
    	}
  		Player player = (Player) event.getPlayer();
  		player.sendMessage("��a����ɹ�");
  		for(Player players:Bukkit.getOnlinePlayers()) {
  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
  				String titles = players.getOpenInventory().getTitle();
  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
  				players.closeInventory();
  				Gui.cratelist(players, type);
			}
		}	
    	return;
    }
    
}
    @EventHandler
    public void Death(PlayerDeathEvent a) {
    	Player killer = a.getEntity().getKiller();
    	a.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
    }

	//	ItemStackתString
	public static String ItemData(ItemStack item) {
		StreamSerializer data = new StreamSerializer();
		String s;
		try {
		    s = data.serializeItemStack(item);
		} catch (Exception e) {
		    s = null;
		}
		return s;
	}
//	StringתItemStack
	public static ItemStack GetItemStack(String data) {
		StreamSerializer item = new StreamSerializer();
		try {
			return item.deserializeItemStack(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
