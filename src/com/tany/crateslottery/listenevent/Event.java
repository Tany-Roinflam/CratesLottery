package com.tany.crateslottery.listenevent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Main;
import com.tany.crateslottery.Other;
import com.tany.crateslottery.conversation.Name;
import com.tany.crateslottery.gui.Gui;
import com.tany.crateslottery.task.NineWingTask;
import com.tany.crateslottery.task.NineWingTaskS;
import com.tany.crateslottery.task.WingTask;
import com.tany.crateslottery.task.WingTaskS;

public class Event implements Listener  {
	public static String crate = null;
	Set<String> Sneak = new HashSet<>();
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file1=new File(config.getDataFolder(),"data.yml");
    
    @EventHandler
	public void Toggle(PlayerToggleSneakEvent event) {
		if(event.isSneaking()) {
			Sneak.add(event.getPlayer().getName());
		}else if(Sneak.contains(event.getPlayer().getName())){
			Sneak.remove(event.getPlayer().getName());
		}
	}
    
    @EventHandler
	public void leave(PlayerQuitEvent event) {
    	if(Sneak.contains(event.getPlayer().getName())) {
    		Sneak.remove(event.getPlayer().getName());
    	}
	}

	@EventHandler
    public void Break(BlockBreakEvent event) {
        List<String> Location = Other.data.getStringList("Location");
        Location block = event.getBlock().getLocation();
        if(Location.size()!=0) {
        	for(String location:Location) {
				String world = location.split(":")[0];
				int x = Integer.parseInt(location.split(":")[1]);
				int y = Integer.parseInt(location.split(":")[2]);
				int z = Integer.parseInt(location.split(":")[3]);
				if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
					if(event.getPlayer().isOp()) {
						if(Sneak.contains(event.getPlayer().getName())) {
							event.getPlayer().sendMessage("§a成功破坏抽奖箱！");
							Location.remove(location);
							Other.data.set("Location", Location);
					  		try {
					  			Other.data.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
						}
					}else {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("BreakCrateMessage")));
					}
					return;
				}
        	}
        }
    }
    
    @EventHandler
	public void Place(BlockPlaceEvent event) {
		if(event.isCancelled())
			return;
	    if(event.getItemInHand()!=null&&event.getItemInHand().hasItemMeta()&&event.getItemInHand().getItemMeta().hasDisplayName()&&event.getItemInHand().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")))) {
	    	String title = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), ""));
	    	int a = 0;
	    	for(String name:Other.data.getConfigurationSection("Info").getKeys(false)) {
	    		if(name.equals(title)) {
	    			break;
	    		}
	    		a++;
	    		if(a==Other.data.getConfigurationSection("Info").getKeys(false).size()) {
	    			a=0;
	    			return;
	    		}
	    	}		
	    	a=0;
	    	List<String> Location = Other.data.getStringList("Location");
	    	if(!Location.contains(event.getBlock().getLocation().getWorld().getName()+":"+event.getBlock().getLocation().getBlockX()+":"+event.getBlock().getLocation().getBlockY()+":"+event.getBlock().getLocation().getBlockZ()+":"+ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")))) {
	    		Location.add(event.getBlock().getLocation().getWorld().getName()+":"+event.getBlock().getLocation().getBlockX()+":"+event.getBlock().getLocation().getBlockY()+":"+event.getBlock().getLocation().getBlockZ()+":"+ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")));
	    	}
	    	Other.data.set("Location", Location);
	  		try {
	  			Other.data.save(file1);
	  		} catch (IOException e) {
	  			e.printStackTrace();
	    	}
	  		return;
	    }
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.LEFT_CLICK_AIR)&&!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
	    	List<String> Location = Other.data.getStringList("Location");
	    	Location block = event.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String names = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	        			if(Other.data.getString("Info."+names+".color")==null) {
	        				event.getClickedBlock().setType(Material.AIR);
	        				event.setCancelled(true);
	        				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearCrateMessage")));
							Location.remove(location);
							Other.data.set("Location", Location);
					  		try {
					  			Other.data.save(file1);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
	        				return;
	        			}
	    			}
	    		}
	    	}
		}
		if(event.getItem()!=null&&event.getItem().getItemMeta().hasDisplayName()&&event.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")))) {
			event.setCancelled(true);
			String name = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), ""));
			if(Other.data.getString("Info."+name+".color")==null) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearKeyMessage")));
				event.getPlayer().getInventory().setItemInHand(null);
	    		return;
			}
			if(!Other.config.getBoolean("UnPackAnyTime")&&event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("UseKeyMessage")));
				return;
			}
			if(Other.config.getBoolean("UnPackAnyTime")) {
				if(event.getAction().equals(Action.LEFT_CLICK_AIR)) {
        			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    				Gui.showcrate(event.getPlayer(), name);
    				event.setCancelled(true);
    				return;
				}
				if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if(Sneak.contains(event.getPlayer().getName())) {
    					if(!event.getPlayer().hasPermission("cl.ninelottery")) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
    						return;
    					}
    					if(Other.data.getBoolean("Info."+name+".clear")) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
    						return;
    					}
    					if(!event.getPlayer().hasPermission("cl.allcrate")&&!event.getPlayer().hasPermission("cl.crate."+name)) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
						List<String> itemlist = Other.data.getStringList("Info."+name+".data");
						int a=1;
						if(itemlist.size()==0) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
						}
						for(String item:itemlist) {
						if(!item.split(":")[1].equals("null")) {
							break;
						}
						if(a==itemlist.size()) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
						}
						a++;
						}
						a=1;
    					if(event.getPlayer().getInventory().getItemInHand().getAmount()<9) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
    						return;
    					}
    					int amount = event.getPlayer().getInventory().getItemInHand().getAmount();
    					amount=amount-9;
    	    				if(amount!=0)
        	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
        	    			else
							event.getPlayer().getInventory().setItemInHand(null);
    	    			if(!Other.data.getString("Info."+name+".nine").equals("无"))
    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", event.getPlayer().getName())));
    	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    					if(Other.data.getBoolean("Info."+name+".nineanimation")) {
    						if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
    						new NineWingTask(event.getPlayer(), name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
    						else
    						new NineWingTask(event.getPlayer(), name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
    					} else {
    						new NineWingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
    					}
        				return;
					} else {
    					if(!event.getPlayer().hasPermission("cl.lottery")) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
    						return;
    					}
    					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
    					int a=1;
    					if(itemlist.size()==0) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
    					}
    					if(!event.getPlayer().hasPermission("cl.allcrate")&&!event.getPlayer().hasPermission("cl.crate."+name)) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
    					for(String item:itemlist) {
    						if(!item.split(":")[1].equals("null")) {
    							break;
    						}
    						if(a==itemlist.size()) {
    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
    						}
    						a++;
    					}
    					int amount = event.getPlayer().getInventory().getItemInHand().getAmount();
    					amount--;
    	    				if(amount!=0)
        	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
        	    			else
							event.getPlayer().getInventory().setItemInHand(null);
    	    			if(!Other.data.getString("Info."+name+".announcement").equals("无"))
    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", event.getPlayer().getName())));
    	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    					if(Other.data.getBoolean("Info."+name+".animation")) {
    						if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
    						new WingTask(event.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
    						else
    						new WingTask(event.getPlayer(), name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
    					}else {
    						new WingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
    					}
        				return;
					}
				}
			}
		}
		if(event.getClickedBlock()!=null&&event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    	List<String> Location = Other.data.getStringList("Location");
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
						if(event.getPlayer().getInventory().getItemInHand()==null||event.getPlayer().getInventory().getItemInHand().getType()==Material.AIR) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}else if(!event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}else if(!ChatColor.stripColor(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), "")).equals(name)) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}
    				if(Sneak.contains(event.getPlayer().getName())) {
    					if(!event.getPlayer().hasPermission("cl.ninelottery")) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
    						return;
    					}
    					if(Other.data.getBoolean("Info."+name+".clear")) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
    						return;
    					}
    					if(!event.getPlayer().hasPermission("cl.allcrate")&&!event.getPlayer().hasPermission("cl.crate."+name)) {
    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
        					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
        					int a=1;
        					if(itemlist.size()==0) {
    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
        					}
        					for(String item:itemlist) {
    						if(!item.split(":")[1].equals("null")) {
    							break;
    						}
    						if(a==itemlist.size()) {
    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
    						}
    						a++;
    						}
    						a=1;
        					if(event.getPlayer().getInventory().getItemInHand().getAmount()<9) {
        						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
        						return;
        					}
        					int amount = event.getPlayer().getInventory().getItemInHand().getAmount();
        					amount=amount-9;
        	    				if(amount!=0)
            	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
            	    			else
  								event.getPlayer().getInventory().setItemInHand(null);
        	    			if(!Other.data.getString("Info."+name+".nine").equals("无"))
        	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", event.getPlayer().getName())));
        	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
        					if(Other.data.getBoolean("Info."+name+".nineanimation")) {
        						if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
        						new NineWingTask(event.getPlayer(), name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
        						else
        						new NineWingTask(event.getPlayer(), name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
        					}else {
        						new NineWingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
        					}
            				return;
	    				}else {
	    					if(!event.getPlayer().hasPermission("cl.lottery")) {
	    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
	    						return;
	    					}
	    					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
	    					int a=1;
        					if(itemlist.size()==0) {
    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
        					}
	    					if(!event.getPlayer().hasPermission("cl.allcrate")&&!event.getPlayer().hasPermission("cl.crate."+name)) {
	    						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
	    						return;
	    					}
	    					for(String item:itemlist) {
	    						if(!item.split(":")[1].equals("null")) {
	    							break;
	    						}
	    						if(a==itemlist.size()) {
	    							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
	    							return;
	    						}
	    						a++;
	    					}
	    					int amount = event.getPlayer().getInventory().getItemInHand().getAmount();
	    					amount--;
	    	    				if(amount!=0)
	        	    			event.getPlayer().getInventory().getItemInHand().setAmount(amount);
	        	    			else
								event.getPlayer().getInventory().setItemInHand(null);
	    	    			if(!Other.data.getString("Info."+name+".announcement").equals("无"))
	    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", event.getPlayer().getName())));
	    	    			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
	    					if(Other.data.getBoolean("Info."+name+".animation")) {
	    						if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
	    						new WingTask(event.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
	    						else
	    						new WingTask(event.getPlayer(), name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
	    					}else {
	    						new WingTaskS(event.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
	    					}
	        				return;
	        			}
	    			}
	    		}
	    	}
		}
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)&&!Sneak.contains(event.getPlayer().getName())) {
	    	List<String> Location = Other.data.getStringList("Location");
	    	Location block = event.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String name = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	        			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
	    				Gui.showcrate(event.getPlayer(), name);
	    				event.setCancelled(true);
	    				return;
	    			}
	    		}
	    	}
		}
	}

	@EventHandler
	public void Click(InventoryClickEvent player) {
		Inventory inventory = player.getClickedInventory();
		if(inventory == null) {
			return;
		}
	    Player p = (Player) player.getWhoClicked();
	    if (!(player.getWhoClicked() instanceof Player)) {
	        return;
	    }
	    if(player.getClickedInventory().getTitle().equals(p.getInventory().getTitle())) {
	    	if(p.getOpenInventory().getTitle().startsWith("§c选§4择")||p.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")||p.getOpenInventory().getTitle().startsWith("§c抽奖箱：")) {
	    		player.setCancelled(true);
	    	}else if(p.getOpenInventory().getTitle().startsWith("§c抽奖箱：")||p.getOpenInventory().getTitle().startsWith("§3设置")||p.getOpenInventory().getTitle().startsWith("§3想对")) {
	    		player.setCancelled(true);
	    	}else if(p.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")||p.getOpenInventory().getTitle().startsWith("§a正在开箱")||p.getOpenInventory().getTitle().startsWith("§6抽奖箱设置§b：")||p.getOpenInventory().getTitle().startsWith("§d设置抽奖箱")) {
	    		player.setCancelled(true);
	    	}
	    }
	    if(player.getClickedInventory().getTitle().equals("§c选§4择")) {
	    	player.setCancelled(true);
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§5创建一个新的抽奖箱")) {
				p.closeInventory();
	            Conversation conversation = new Conversation(Main.plugin, p, new Name());
	            p.beginConversation(conversation);
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§a进入已有的抽奖箱设置")) {
				p.closeInventory();
				Gui.cratelist(p, 1);
			}
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			int type = Integer.parseInt(title.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if (player.getSlot() == 53&&player.getClickedInventory().getItem(53).getItemMeta().getDisplayName().equals("§a下一页")) {
				Gui.cratelist(p,++type);
			}
			if (player.getSlot() == 45&&player.getClickedInventory().getItem(45).getItemMeta().getDisplayName().equals("§a上一页")) {
				Gui.cratelist(p,--type);
			}
			if(player.getSlot() >44) {
				return;
			}
			String cratename = ChatColor.stripColor(player.getCurrentItem().getItemMeta().getDisplayName().replace("§6抽奖箱设置§b：", ""));
			Gui.choose(p, cratename);
			return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§c抽奖箱：")||player.getClickedInventory().getTitle().startsWith("§a正在开箱")||player.getClickedInventory().getTitle().startsWith("§c正在九连§a开箱")) {
	    	player.setCancelled(true);
	    	return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§6抽奖箱§c：")) {
	    	if(player.getCurrentItem()!=null&&player.getCurrentItem().hasItemMeta()&&player.getCurrentItem().getItemMeta().hasDisplayName()&&(player.getCurrentItem().getItemMeta().getDisplayName().contains("领取你的奖励吧！")||player.getCurrentItem().getItemMeta().getDisplayName().contains("§2仅展示")))
	        	player.setCancelled(true);
	    		return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§c抽奖§6箱：")) {
	    	if(player.getCurrentItem()!=null&&player.getCurrentItem().hasItemMeta()&&player.getCurrentItem().getItemMeta().hasDisplayName()&&(player.getCurrentItem().getItemMeta().getDisplayName().contains("领取你的战利品吧！")||player.getCurrentItem().getItemMeta().getDisplayName().contains("§2仅展示")))
	        	player.setCancelled(true);
	    		return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§3设置")) {
	    	player.setCancelled(true);
	    	String title = player.getClickedInventory().getTitle();
	    	String name = title.replace("§3设置", "").replace("§3显示的颜色", "");
	    	name = ChatColor.stripColor(name);
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§4红色")) {
				Other.data.set("Info."+name+".color", "§4");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§e黄色")) {
				Other.data.set("Info."+name+".color", "§e");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§3蓝色")) {
				Other.data.set("Info."+name+".color", "§3");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§1深蓝色")) {
				Other.data.set("Info."+name+".color", "§1");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§0黑色")) {
				Other.data.set("Info."+name+".color", "§0");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§5紫色")) {
				Other.data.set("Info."+name+".color", "§5");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
				Other.data.set("Info."+name+".color", "§6");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§f白色")) {
				Other.data.set("Info."+name+".color", "§f");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§a浅绿色")) {
				Other.data.set("Info."+name+".color", "§a");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§2绿色")) {
				Other.data.set("Info."+name+".color", "§2");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
				Other.data.set("Info."+name+".color", "§6");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§b天蓝色")) {
				Other.data.set("Info."+name+".color", "§b");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c粉红色")) {
				Other.data.set("Info."+name+".color", "§c");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§d亮紫色")) {
				Other.data.set("Info."+name+".color", "§d");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§7灰色")) {
				Other.data.set("Info."+name+".color", "§7");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§a返回设置列表")) {
				p.closeInventory();
				Gui.choose(p, name);
				return;
			}
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§d设置抽奖箱")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			String name = ChatColor.stripColor(title.replace("设置抽奖箱", "").replace("的开箱方式", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6点击对应的方块，设置对应的开箱动画")) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§a固定中间抽奖")) {
				Other.data.set("Info."+name+".type", "normal");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§a固定中间抽奖")) {
				Other.data.set("Info."+name+".ninetype", "normal");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§e随机位置抽奖")) {
				Other.data.set("Info."+name+".type", "random");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§e随机位置抽奖")) {
				Other.data.set("Info."+name+".ninetype", "random");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§b范围位置内抽奖")) {
				Other.data.set("Info."+name+".type", "order");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§b范围位置内抽奖")) {
				Other.data.set("Info."+name+".ninetype", "order");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§b有物品的随机抽奖")) {
				Other.data.set("Info."+name+".type", "embellishment");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§6快乐矩形抽奖")) {
				Other.data.set("Info."+name+".ninetype", "gradient");
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		p.sendMessage("§a成功设置");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§a返回设置列表")) {
				p.closeInventory();
				Gui.choose(p, name);
				return;
			}
	    	return;
	    }
	    if(player.getClickedInventory().getTitle().startsWith("§3想对")) {
	    	player.setCancelled(true);
			String title = player.getClickedInventory().getTitle();
			String name = ChatColor.stripColor(title.replace("§3想对", "").replace("§3进行什么操作？", ""));
			if(player.getCurrentItem() == null || player.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§5修改奖池奖励")) {
				Gui.setcrate(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§6设置颜色")) {
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a设置")) {
				Gui.way(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("§c删除这个箱子")) {
				Other.data.set("Info."+name, null);
		  		Other.data.set("backup."+name, null);
		  		try {
		  			Other.data.save(file1);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}else if(players.getOpenInventory().getTitle().startsWith("§2抽奖箱"+Other.data.getString("Info."+name+".color")+name)) {
		  					players.closeInventory();
		  					players.sendMessage("§a这个抽奖箱被§c"+p.getName()+"§a删除！");
		  			}else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					players.sendMessage("§a这个抽奖箱被§c"+p.getName()+"§a删除！");
		  			}else if(players.getOpenInventory().getTitle().startsWith(ChatColor.stripColor("设置"+Other.data.getString("Info."+name+".color")+name+"显示的颜色"))) {
	  					players.closeInventory();
	  					players.sendMessage("§a这个抽奖箱被§c"+p.getName()+"§a删除！");
		  			}else if(player.getClickedInventory().getTitle().startsWith(ChatColor.stripColor("§d设置抽奖箱"+Other.data.getString("Info."+name+".color")+name+"的开箱方式"))) {
	  					players.closeInventory();
	  					players.sendMessage("§a这个抽奖箱被§c"+p.getName()+"§a删除！");
		  			}
		  		}
				p.sendMessage("§c删除§a"+name+"§c成功");
				return;
			}
	    }
	}

	@EventHandler
public void Close(InventoryCloseEvent event) {
    if(event.getInventory().getTitle().startsWith("§2抽奖箱")) {
    	String title = ChatColor.stripColor(event.getInventory().getTitle().replace("§2抽奖箱", "").replace("§2设置", ""));
    	ArrayList<String> data = new ArrayList<String>();
  		int a=0;
  		while(a<54) {
  			String item = null;
  			if(event.getInventory().getItem(a)==null||event.getInventory().getItem(a).getType()==Material.AIR)
  			item = "null";
  			else
  			item = GetItemData(event.getInventory().getItem(a));
  			data.add(a+":"+item);
  			a++;
  		}
  		a=0;
  		List<String> exist = Other.data.getStringList("Info."+title+".data");
  		if(exist.size()==0) {
  			Other.data.set("Info."+title+".color", "§f");
  			Other.data.set("Info."+title+".animation", true);
  			Other.data.set("Info."+title+".nineanimation", true);
  			Other.data.set("Info."+title+".announcement", "无");
  			Other.data.set("Info."+title+".nine", "无");
  			Other.data.set("Info."+title+".number", -1);
  			Other.data.set("Info."+title+".cd", -1);
  			Other.data.set("Info."+title+".ninenumber", -1);
  			Other.data.set("Info."+title+".ninecd", -1);
  			Other.data.set("Info."+title+".type", "normal");
  			Other.data.set("Info."+title+".ninetype", "normal");
  			Other.data.set("Info."+title+".clear", false);
  			Other.data.set("Info."+title+".info", false);
  			Other.data.set("Info."+title+".nineinfo", false);
  			Other.data.set("Info."+title+".backup", false);
  			crate = null;
  		}
  		Other.data.set("backup."+title, data);
  		Other.data.set("Info."+title+".data", data);
  		try {
  			Other.data.save(file1);
  		} catch (IOException e) {
  			e.printStackTrace();
    	}
  		Player player = (Player) event.getPlayer();
  		player.sendMessage("§a保存成功");
  		for(Player players:Bukkit.getOnlinePlayers()) {
  			if(players.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
  				String titles = players.getOpenInventory().getTitle();
  				int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
  				players.closeInventory();
  				Gui.cratelist(players, type);
			}
		}
    	return;
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