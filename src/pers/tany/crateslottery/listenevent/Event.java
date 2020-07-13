package pers.tany.crateslottery.listenevent;

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

import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.Way;
import pers.tany.crateslottery.conversation.Name;
import pers.tany.crateslottery.gui.Gui;
import pers.tany.crateslottery.task.NineWingTask;
import pers.tany.crateslottery.task.NineWingTaskS;
import pers.tany.crateslottery.task.WingTask;
import pers.tany.crateslottery.task.WingTaskS;

public class Event implements Listener  {
	public static String crate = null;
	Set<String> Sneak = new HashSet<>();
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file=new File(config.getDataFolder(),"data.yml");
    
    @EventHandler
	public void Toggle(PlayerToggleSneakEvent evt) {
		if(evt.isSneaking()) {
			Sneak.add(evt.getPlayer().getName());
		}else if(Sneak.contains(evt.getPlayer().getName())){
			Sneak.remove(evt.getPlayer().getName());
		}
	}
    
    @EventHandler
	public void leave(PlayerQuitEvent evt) {
    	if(Sneak.contains(evt.getPlayer().getName())) {
    		Sneak.remove(evt.getPlayer().getName());
    	}
	}

	@EventHandler
    public void Break(BlockBreakEvent evt) {
		if(evt.isCancelled())
			return;
        List<String> Location = Other.data.getStringList("Location");
        Location block = evt.getBlock().getLocation();
        if(Location.size()!=0) {
        	for(String location:Location) {
				String world = location.split(":")[0];
				int x = Integer.parseInt(location.split(":")[1]);
				int y = Integer.parseInt(location.split(":")[2]);
				int z = Integer.parseInt(location.split(":")[3]);
				if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
					if(evt.getPlayer().hasPermission("cl.break")) {
						if(Sneak.contains(evt.getPlayer().getName())) {
							evt.getPlayer().sendMessage("��a�ɹ��ƻ��齱�䣡");
							Location.remove(location);
							Other.data.set("Location", Location);
					  		try {
					  			Other.data.save(file);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
						}
					}else {
						evt.setCancelled(true);
						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("BreakCrateMessage")));
					}
					return;
				}
        	}
        }
    }
    
    @EventHandler
	public void Place(BlockPlaceEvent evt) {
		if(evt.isCancelled())
			return;
		if(!(Bukkit.getVersion().contains("1.7.")||Bukkit.getVersion().contains("1.8."))) {
			if(Way.getPlaceHand(evt)) {
				return;
			}
		}
	    if(evt.getItemInHand()!=null&&evt.getItemInHand().hasItemMeta()&&evt.getItemInHand().getItemMeta().hasDisplayName()&&evt.getItemInHand().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")))) {
	    	String title = ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), ""));
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
	    	if(!Location.contains(evt.getBlock().getLocation().getWorld().getName()+":"+evt.getBlock().getLocation().getBlockX()+":"+evt.getBlock().getLocation().getBlockY()+":"+evt.getBlock().getLocation().getBlockZ()+":"+ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")))) {
	    		Location.add(evt.getBlock().getLocation().getWorld().getName()+":"+evt.getBlock().getLocation().getBlockX()+":"+evt.getBlock().getLocation().getBlockY()+":"+evt.getBlock().getLocation().getBlockZ()+":"+ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")));
	    	}
	    	Other.data.set("Location", Location);
	  		try {
	  			Other.data.save(file);
	  		} catch (IOException e) {
	  			e.printStackTrace();
	    	}
	  		return;
	    }
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		if(!(Bukkit.getVersion().contains("1.7.")||Bukkit.getVersion().contains("1.8."))) {
			if(Way.getInteractHand(evt)) {
				return;
			}
		}
		if(!evt.getAction().equals(Action.LEFT_CLICK_AIR)&&!evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
	    	List<String> Location = Other.data.getStringList("Location");
	    	Location block = evt.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String names = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	        			if(Other.data.getString("Info."+names+".color")==null) {
	        				evt.getClickedBlock().setType(Material.AIR);
	        				evt.setCancelled(true);
	        				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearCrateMessage")));
							Location.remove(location);
							Other.data.set("Location", Location);
					  		try {
					  			Other.data.save(file);
					  		} catch (IOException e) {
					  			e.printStackTrace();
				        	}
	        				return;
	        			}
	    			}
	    		}
	    	}
		}
		if(evt.getItem()!=null&&evt.getItem().hasItemMeta()&&evt.getItem().getItemMeta().hasDisplayName()&&evt.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")))) {
			evt.setCancelled(true);
			String name = ChatColor.stripColor(evt.getItem().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), ""));
			if(Other.data.getString("Info."+name+".color")==null) {
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearKeyMessage")));
				evt.getPlayer().getInventory().setItemInHand(null);
	    		return;
			}
			if(!Other.data.getBoolean("Info."+name+".unpackanytime")&&evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("UseKeyMessage")));
				return;
			}
			if(Other.data.getBoolean("Info."+name+".unpackanytime")) {
				if(evt.getAction().equals(Action.LEFT_CLICK_AIR)) {
					if(!(evt.getPlayer().hasPermission("cl.checkall")||evt.getPlayer().hasPermission("cl.check."+name))) {
						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoCheckMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
						return;
					}
        			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    				Gui.showcrate(evt.getPlayer(), name);
    				evt.setCancelled(true);
    				return;
				}
				if(evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					if(Sneak.contains(evt.getPlayer().getName())) {
    					if(!evt.getPlayer().hasPermission("cl.ninelottery")) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
    						return;
    					}
    					if(Other.data.getBoolean("Info."+name+".clear")) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
    						return;
    					}
    					if(!evt.getPlayer().hasPermission("cl.allcrate")&&!evt.getPlayer().hasPermission("cl.crate."+name)) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
						List<String> itemlist = Other.data.getStringList("Info."+name+".data");
						int a=1;
						if(itemlist.size()==0) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
						}
						for(String item:itemlist) {
						if(!item.split(":")[1].equals("null")) {
							break;
						}
						if(a==itemlist.size()) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
						}
						a++;
						}
						a=1;
    					if(evt.getPlayer().getInventory().getItemInHand().getAmount()<9) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
    						return;
    					}
    					int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
    					amount=amount-9;
    	    				if(amount!=0)
        	    			evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
        	    			else
							evt.getPlayer().getInventory().setItemInHand(null);
    	    			if(!Other.data.getString("Info."+name+".nine").equals("��"))
    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", evt.getPlayer().getName())));
    	    			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    					if(Other.data.getBoolean("Info."+name+".nineanimation")) {
    						if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
    						new NineWingTask(evt.getPlayer(), name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
    						else
    						new NineWingTask(evt.getPlayer(), name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
    					} else {
    						new NineWingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
    					}
        				return;
					} else {
    					if(!evt.getPlayer().hasPermission("cl.lottery")) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
    						return;
    					}
    					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
    					int a=1;
    					if(itemlist.size()==0) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
							return;
    					}
    					if(!evt.getPlayer().hasPermission("cl.allcrate")&&!evt.getPlayer().hasPermission("cl.crate."+name)) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
    					for(String item:itemlist) {
    						if(!item.split(":")[1].equals("null")) {
    							break;
    						}
    						if(a==itemlist.size()) {
    							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
    						}
    						a++;
    					}
    					int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
    					amount--;
    	    				if(amount!=0)
        	    			evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
        	    			else
							evt.getPlayer().getInventory().setItemInHand(null);
    	    			if(!Other.data.getString("Info."+name+".announcement").equals("��"))
    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", evt.getPlayer().getName())));
    	    			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
    					if(Other.data.getBoolean("Info."+name+".animation")) {
    						if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
    						new WingTask(evt.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
    						else
    						new WingTask(evt.getPlayer(), name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
    					}else {
    						new WingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
    					}
        				return;
					}
				}
			}
		}
		if(evt.getClickedBlock()!=null&&evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    	List<String> Location = Other.data.getStringList("Location");
	    	Location block = evt.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String name = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
	    				evt.setCancelled(true);
						if(evt.getPlayer().getInventory().getItemInHand()==null||evt.getPlayer().getInventory().getItemInHand().getType()==Material.AIR) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}else if(!evt.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}else if(!ChatColor.stripColor(evt.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), "")).equals(name)) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}
    				if(Sneak.contains(evt.getPlayer().getName())) {
    					if(!evt.getPlayer().hasPermission("cl.ninelottery")) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
    						return;
    					}
    					if(Other.data.getBoolean("Info."+name+".clear")) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
    						return;
    					}
    					if(!evt.getPlayer().hasPermission("cl.allcrate")&&!evt.getPlayer().hasPermission("cl.crate."+name)) {
    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
    						return;
    					}
        					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
        					int a=1;
        					if(itemlist.size()==0) {
    							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
        					}
        					for(String item:itemlist) {
    						if(!item.split(":")[1].equals("null")) {
    							break;
    						}
    						if(a==itemlist.size()) {
    							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
    						}
    						a++;
    						}
    						a=1;
        					if(evt.getPlayer().getInventory().getItemInHand().getAmount()<9) {
        						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
        						return;
        					}
        					int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
        					amount=amount-9;
        	    				if(amount!=0)
            	    			evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
            	    			else
  								evt.getPlayer().getInventory().setItemInHand(null);
        	    			if(!Other.data.getString("Info."+name+".nine").equals("��"))
        	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".nine").replace("[player]", evt.getPlayer().getName())));
        	    			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
        					if(Other.data.getBoolean("Info."+name+".nineanimation")) {
        						if(Other.data.getDouble("Info."+name+".ninecd")<=0&&Other.data.getDouble("Info."+name+".ninenumber")<=0)
        						new NineWingTask(evt.getPlayer(), name,Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime")*20));
        						else
        						new NineWingTask(evt.getPlayer(), name,Other.data.getInt("Info."+name+".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".ninecd")*20));
        					}else {
        						new NineWingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
        					}
            				return;
	    				}else {
	    					if(!evt.getPlayer().hasPermission("cl.lottery")) {
	    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
	    						return;
	    					}
	    					List<String> itemlist = Other.data.getStringList("Info."+name+".data");
	    					int a=1;
        					if(itemlist.size()==0) {
    							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
    							return;
        					}
	    					if(!evt.getPlayer().hasPermission("cl.allcrate")&&!evt.getPlayer().hasPermission("cl.crate."+name)) {
	    						evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info."+name+".color")+name))));
	    						return;
	    					}
	    					for(String item:itemlist) {
	    						if(!item.split(":")[1].equals("null")) {
	    							break;
	    						}
	    						if(a==itemlist.size()) {
	    							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
	    							return;
	    						}
	    						a++;
	    					}
	    					int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
	    					amount--;
	    	    				if(amount!=0)
	        	    			evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
	        	    			else
								evt.getPlayer().getInventory().setItemInHand(null);
	    	    			if(!Other.data.getString("Info."+name+".announcement").equals("��"))
	    	    				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info."+name+".announcement").replace("[player]", evt.getPlayer().getName())));
	    	    			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
	    					if(Other.data.getBoolean("Info."+name+".animation")) {
	    						if(Other.data.getDouble("Info."+name+".cd")<=0&&Other.data.getDouble("Info."+name+".number")<=0)
	    						new WingTask(evt.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime")*20));
	    						else
	    						new WingTask(evt.getPlayer(), name, Other.data.getInt("Info."+name+".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info."+name+".cd")*20));
	    					}else {
	    						new WingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
	    					}
	        				return;
	        			}
	    			}
	    		}
	    	}
		}
		if(evt.getAction().equals(Action.LEFT_CLICK_BLOCK)&&!Sneak.contains(evt.getPlayer().getName())) {
	    	List<String> Location = Other.data.getStringList("Location");
	    	Location block = evt.getClickedBlock().getLocation();
	    	if(Location.size()!=0) {
	    		for(String location:Location) {
	    			String world = location.split(":")[0];
	    			int x = Integer.parseInt(location.split(":")[1]);
	    			int y = Integer.parseInt(location.split(":")[2]);
	    			int z = Integer.parseInt(location.split(":")[3]);
	    			String name = location.split(":")[4];
	    			if(block.getBlockX()==x&&block.getBlockY()==y&&block.getBlockZ()==z&&block.getWorld().equals(Bukkit.getWorld(world))) {
						if(!(evt.getPlayer().hasPermission("cl.checkall")||evt.getPlayer().hasPermission("cl.check."+name))) {
							evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoCheckMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
							return;
						}
	        			evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info."+name+".color")+name)));
	    				Gui.showcrate(evt.getPlayer(), name);
	    				evt.setCancelled(true);
	    				return;
	    			}
	    		}
	    	}
		}
	}

	@EventHandler
	public void Click(InventoryClickEvent player) {
	    if (!(player.getWhoClicked() instanceof Player)) {
	        return;
	    }
		Inventory inventory = player.getClickedInventory();
		if(inventory == null) {
			return;
		}
	    Player p = (Player) player.getWhoClicked();
	    if(!p.getOpenInventory().getTitle().equals(player.getInventory().getTitle())&&player.getClickedInventory().getTitle().equals(p.getInventory().getTitle())) {
	    	if(p.getOpenInventory().getTitle().startsWith("��cѡ��4��")||p.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")||p.getOpenInventory().getTitle().startsWith("��a�齱�䣺")) {
	    		player.setCancelled(true);
	    	}else if(p.getOpenInventory().getTitle().startsWith("��3����")||p.getOpenInventory().getTitle().startsWith("��3���")) {
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
	            Conversation conversation = new Conversation(Main.plugin, p, new Name());
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
	    if(player.getClickedInventory().getTitle().startsWith("��a�齱�䣺")||player.getClickedInventory().getTitle().startsWith("��a���ڿ���")||player.getClickedInventory().getTitle().startsWith("��c���ھ�����a����")) {
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
				Other.data.set("Info."+name+".color", "��4");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��e��ɫ")) {
				Other.data.set("Info."+name+".color", "��e");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��3��ɫ")) {
				Other.data.set("Info."+name+".color", "��3");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��1����ɫ")) {
				Other.data.set("Info."+name+".color", "��1");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��0��ɫ")) {
				Other.data.set("Info."+name+".color", "��0");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��5��ɫ")) {
				Other.data.set("Info."+name+".color", "��5");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��6��ɫ")) {
				Other.data.set("Info."+name+".color", "��6");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��f��ɫ")) {
				Other.data.set("Info."+name+".color", "��f");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��aǳ��ɫ")) {
				Other.data.set("Info."+name+".color", "��a");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��2��ɫ")) {
				Other.data.set("Info."+name+".color", "��2");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��6��ɫ")) {
				Other.data.set("Info."+name+".color", "��6");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b����ɫ")) {
				Other.data.set("Info."+name+".color", "��b");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�ۺ�ɫ")) {
				Other.data.set("Info."+name+".color", "��c");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��d����ɫ")) {
				Other.data.set("Info."+name+".color", "��d");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
				p.closeInventory();
				Gui.color(p, name);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��7��ɫ")) {
				Other.data.set("Info."+name+".color", "��7");
		  		try {
		  			Other.data.save(file);
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
				Other.data.set("Info."+name+".type", "normal");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����a�̶��м�齱")) {
				Other.data.set("Info."+name+".ninetype", "normal");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��e���λ�ó齱")) {
				Other.data.set("Info."+name+".type", "random");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����e���λ�ó齱")) {
				Other.data.set("Info."+name+".ninetype", "random");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b��Χλ���ڳ齱")) {
				Other.data.set("Info."+name+".type", "order");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����b��Χλ���ڳ齱")) {
				Other.data.set("Info."+name+".ninetype", "order");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��b����Ʒ������齱")) {
				Other.data.set("Info."+name+".type", "embellishment");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c���������齱")) {
				Other.data.set("Info."+name+".type", "repeatedly");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����c���������齱")) {
				Other.data.set("Info."+name+".ninetype", "repeatedly");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����6���־��γ齱")) {
				Other.data.set("Info."+name+".ninetype", "gradient");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��3����Ƴ齱")) {
				Other.data.set("Info."+name+".type", "show");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
		  		}
		  		p.sendMessage("��a�ɹ�����");
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��c�������d����3����Ƴ齱")) {
				Other.data.set("Info."+name+".ninetype", "show");
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			}
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
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��a����������������")) {
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
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a����ѡ���б�")) {
				p.closeInventory();
				Gui.cratelist(p, 1);
				return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ����õ��鶯��")) {
				if(Other.data.getBoolean("Info."+name+".animation")) {
					Other.data.set("Info."+name+".animation", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".animation", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ����á�c�������a����")) {
				if(Other.data.getBoolean("Info."+name+".nineanimation")) {
					Other.data.set("Info."+name+".nineanimation", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".nineanimation", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ����ù��浥�鵽����Ʒ")) {
				if(Other.data.getBoolean("Info."+name+".info")) {
					Other.data.set("Info."+name+".info", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".info", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ����ù����c�������a������Ʒ")) {
				if(Other.data.getBoolean("Info."+name+".nineinfo")) {
					Other.data.set("Info."+name+".nineinfo", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".nineinfo", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ񵥳���������鵽����Ʒ����")) {
				if(Other.data.getBoolean("Info."+name+".clear")) {
					Other.data.set("Info."+name+".backup", false);
					Other.data.set("Info."+name+".clear", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".clear", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ����������clear��������ݵ���������")) {
				if(!Other.data.getBoolean("Info."+name+".clear")) {
					p.sendMessage("��c�������û�п���һ���Գ齱���ܣ�");
					return;
				}
				if(Other.data.getBoolean("Info."+name+".backup")) {
					Other.data.set("Info."+name+".backup", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".backup", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().startsWith("��a�޸��Ƿ���������齱��������伴�ɳ齱")) {
				if(Other.data.getBoolean("Info."+name+".unpackanytime")) {
					Other.data.set("Info."+name+".unpackanytime", false);
					p.sendMessage("��2�ɹ��޸�Ϊ����cδ����");
				}
				else {
					Other.data.set("Info."+name+".unpackanytime", true);
					p.sendMessage("��2�ɹ��޸�Ϊ����a����");
				}
		  		try {
		  			Other.data.save(file);
		  		} catch (IOException e) {
		  			e.printStackTrace();
	        	}
		  		for(Player players:Bukkit.getOnlinePlayers()) {
		  			if(players.getOpenInventory().getTitle().startsWith("��a�齱���2�б��5����d�ڡ�e")) {
		  				String titles = players.getOpenInventory().getTitle();
		  				int type = Integer.parseInt(titles.replace("��a�齱���2�б��5����d�ڡ�e", "").replace("��dҳ", ""));
		  				players.closeInventory();
		  				Gui.cratelist(players, type);
		  			} else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					Gui.choose(players, name);
		  			}
		  		}
		  		return;
			}
			if(player.getCurrentItem().getItemMeta().getDisplayName().equals("��cɾ���������")) {
				Other.data.set("Info."+name, null);
		  		Other.data.set("backup."+name, null);
		  		try {
		  			Other.data.save(file);
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
		  			}else if(players.getOpenInventory().getTitle().startsWith("��2�齱��"+Other.data.getString("Info."+name+".color")+name)) {
		  					players.closeInventory();
		  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(players.getOpenInventory().getTitle().equals(title)) {
	  					players.closeInventory();
	  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(players.getOpenInventory().getTitle().startsWith(ChatColor.stripColor("����"+Other.data.getString("Info."+name+".color")+name+"��ʾ����ɫ"))) {
	  					players.closeInventory();
	  					players.sendMessage("��a����齱�䱻��c"+p.getName()+"��aɾ����");
		  			}else if(player.getClickedInventory().getTitle().startsWith(ChatColor.stripColor("��d���ó齱��"+Other.data.getString("Info."+name+".color")+name+"�Ŀ��䷽ʽ"))) {
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
public void Close(InventoryCloseEvent evt) {
    if(evt.getInventory().getTitle().startsWith("��2�齱��")) {
    	String title = ChatColor.stripColor(evt.getInventory().getTitle().replace("��2�齱��", "").replace("��2����", ""));
    	ArrayList<String> data = new ArrayList<String>();
  		int a=0;
  		while(a<54) {
  			String item = null;
  			if(evt.getInventory().getItem(a)==null||evt.getInventory().getItem(a).getType()==Material.AIR)
  			item = "null";
  			else
  			item = GetItemData(evt.getInventory().getItem(a));
  			data.add(a+":"+item);
  			a++;
  		}
  		a=0;
  		List<String> exist = Other.data.getStringList("Info."+title+".data");
  		if(exist.size()==0) {
  			Other.data.set("Info."+title+".color", "��f");
  			Other.data.set("Info."+title+".animation", true);
  			Other.data.set("Info."+title+".nineanimation", true);
  			Other.data.set("Info."+title+".announcement", "��");
  			Other.data.set("Info."+title+".nine", "��");
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
  			Other.data.set("Info."+title+".unpackanytime", false);
  			crate = null;
  		}
  		Other.data.set("backup."+title, data);
  		Other.data.set("Info."+title+".data", data);
  		try {
  			Other.data.save(file);
  		} catch (IOException e) {
  			e.printStackTrace();
    	}
  		Player player = (Player) evt.getPlayer();
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
    if(evt.getInventory().getTitle().contains("��c����������")||evt.getInventory().getTitle().contains("��a������")) {
    	for(ItemStack item:evt.getInventory().getContents()) {
    		if(item==null||item.getType() == Material.AIR) {
    			continue;
    		}
    		if(item.hasItemMeta()&&item.getItemMeta().hasDisplayName()&&(item.getItemMeta().getDisplayName().contains("��ȡ��Ľ����ɣ�")||item.getItemMeta().getDisplayName().equals("��2��չʾ")||item.getItemMeta().getDisplayName().contains("��ȡ���ս��Ʒ�ɣ�"))) {
    			continue;
    		}
    		Way.GiveItem((Player) evt.getPlayer(), item);
    	}
    	return;
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