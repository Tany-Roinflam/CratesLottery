package com.tany.crateslottery.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Other;

public class Gui {
	public static void gui(Player player) {
		Inventory gui = Bukkit.createInventory(null, 9, "§c选§4择");
		ItemStack Already = new ItemStack(Material.ENDER_CHEST);
		ItemStack Crate = new ItemStack(Material.CHEST);
		ItemMeta data = Already.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        data.setDisplayName("§a进入已有的抽奖箱设置");
        lore.add("§2这里存放了你之前设置的抽奖箱列表");
        lore.add("§2点击后你可以根据你需要的进行更改内容、移除抽奖箱");
        data.setLore(lore);
        Already.setItemMeta(data);
        lore.clear();
        data.setDisplayName("§5创建一个新的抽奖箱");
        lore.add("§d点击后，你需要输入抽奖箱名称§6（不支持颜色代码§b&§6）");
        lore.add("§d输入完后就会创建一个gui，这个gui就是你第一次设置抽奖箱的内容");
        lore.add("§c如果你服务器版本低于1.9，建议不要输入太长的名称");
        lore.add("§c问就是兼容原因");
        data.setLore(lore);
        Crate.setItemMeta(data);
        lore.clear();
        gui.setItem(2, Already);
        gui.setItem(6, Crate);
        player.openInventory(gui);
	}
	
	public static void createcrate(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('§', "§2抽奖箱"+name+"§2设置"));
	    player.openInventory(gui);
	}

	public static void cratelist(Player player,Integer type) {
		Inventory gui = Bukkit.createInventory(null, 54, "§a抽奖箱§2列表§5：§d第§e"+type+"§d页");
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemStack xiaye = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack shangye = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack jieshao = new ItemStack(Material.STAINED_GLASS_PANE);
		
	    if(Other.data.getConfigurationSection("Info").getKeys(false).size()<(type-1)*45+1) {
	    	if(type>1) {
	    		player.closeInventory();
	    		Gui.cratelist(player, --type);
	    		return;
	    	}else {
	    	Gui.gui(player);
	    	player.sendMessage("§c没有创建过任何箱子");
	    	return;
	    	}
	    }
	    
	    ArrayList<String> crate = new ArrayList<String>();
	    for(String string:Other.data.getConfigurationSection("Info").getKeys(false)) {
	    	crate.add(string);
	    }
	    ItemMeta abc = jieshao.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    
	    abc.setDisplayName("§a下一页");
	    lore.add("§c查看下一页");
	    xiaye.setDurability((short) 14);
	    abc.setLore(lore);
	    xiaye.setItemMeta(abc);
	    lore.clear();
	    
	    abc.setDisplayName("§a下一页");
	    lore.add("§c返回下一页");
	    shangye.setDurability((short) 11);
	    abc.setLore(lore);
	    shangye.setItemMeta(abc);
	    lore.clear();
	    
	    abc.setDisplayName("§a这里是§b第§2"+type+"§b页！");
	    lore.add("§a点击你要编辑的箱子");
	    jieshao.setDurability((short) 1);
	    abc.setLore(lore);
	    jieshao.setItemMeta(abc);
	    lore.clear();
	    
	    int i=(type-1)*45;
	    int a=0;
	    int size = crate.size()-1;
	    while(i<=size&&i<=44+(type-1)*45) {
	    	abc.setDisplayName(ChatColor.translateAlternateColorCodes('§', "§6抽奖箱设置§b："+Other.data.getString("Info."+crate.get(i)+".color")+crate.get(i)));
	    	
			lore.add("§a抽奖启动§d动画§b状态§f："+Other.data.getString("Info."+crate.get(i)+".animation").replace("true", "§a启用").replace("false", "§c未启用"));
			lore.add("§c九连抽§a启动§d动画§b状态§f："+Other.data.getString("Info."+crate.get(i)+".nineanimation").replace("true", "§a启用").replace("false", "§c未启用"));
			
			lore.add("");
			
			if(Other.data.getString("Info."+crate.get(i)+".announcement").equals("无"))
			lore.add("§a抽奖公告设置状态：§c未启用");
			else
			lore.add(ChatColor.translateAlternateColorCodes('&', "§a抽奖公告设置文本§d：§f"+Other.data.getString("Info."+crate.get(i)+".announcement")));
			
			if(Other.data.getString("Info."+crate.get(i)+".nine").equals("无"))
			lore.add("§c九连抽§a公告设置状态§d：§c未启用");
			else
			lore.add(ChatColor.translateAlternateColorCodes('&', "§c九连抽§a公告设置文本§d：§f"+Other.data.getString("Info."+crate.get(i)+".nine")));
			
			lore.add("");
			
			if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
			lore.add("§a自定义普通抽奖时间：§c未启用");
			else {
				lore.add("§a普通抽奖变幻次数§e：§6"+Other.data.getDouble("Info."+crate.get(i)+".number"));
				lore.add("§a普通抽奖变幻间隔时间§e：§6"+Other.data.getDouble("Info."+crate.get(i)+".cd"));
				lore.add("");
			}
			if(Other.data.getDouble("Info."+crate.get(i)+".ninecd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".ninenumber")<=0)
			lore.add("§a自定义§c九连抽§a抽奖时间：§c未启用");
			else {
				if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
				lore.add("");
				lore.add("§c九连抽§a变幻次数§e：§6"+Other.data.getDouble("Info."+crate.get(i)+".ninenumber"));
				lore.add("§c九连抽§a变幻间隔时间§e：§6"+Other.data.getDouble("Info."+crate.get(i)+".ninecd"));
			}	

			lore.add("");
			if(Other.data.getBoolean("Info."+crate.get(i)+".info")) {
				lore.add("§a公告单抽到的物品： §a启用");
			}else {
				lore.add("§a公告单抽到的物品： §c未启用");
			}
			if(Other.data.getBoolean("Info."+crate.get(i)+".nineinfo")) {
				lore.add("§a公告§c九连抽§a抽到的物品： §a启用");
			}else {
				lore.add("§a公告§c九连抽§a抽到的物品：§c未启用");
			}
			lore.add("");
			if(Other.data.getBoolean("Info."+crate.get(i)+".clear")) {
				lore.add("§d一次性§a单抽箱模式： §a启用");
				if(Other.data.getBoolean("Info."+crate.get(i)+".backup")) {
					lore.add("§a当清空抽奖箱到§c空§a时，备份填充§b： §a启用");
				}else {
					lore.add("§a当清空抽奖箱到§c空§a时，备份填充§b： §c未启用");
				}
			}else {
				lore.add("§d一次性§a单抽箱模式： §c未启用");
			}
			lore.add("");	
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("normal"))
				lore.add("§a抽奖动画状态§d： §a§l固定中间抽奖");
			else
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("random"))
				lore.add("§a抽奖动画状态§d： §e§l随机位置抽奖");
			else
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("order"))
				lore.add("§a抽奖动画状态§d： §b§l范围位置内抽奖");
			else
				if(Other.data.getString("Info."+crate.get(i)+".type").equals("embellishment"))
				lore.add("§a抽奖动画状态§d： §b§l有物品的随机抽奖");
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("normal"))
				lore.add("§c九连抽§a抽奖动画状态§d： §a§l固定中间抽奖");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("random"))
				lore.add("§c九连抽§a抽奖动画状态§d： §e§l随机位置抽奖");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("order"))
				lore.add("§c九连抽§a抽奖动画状态§d： §b§l范围位置内抽奖");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("gradient"))
				lore.add("§c九连抽§a抽奖动画状态§d： §6§l快乐矩形抽奖");
			abc.setLore(lore);
			chest.setItemMeta(abc);
			lore.clear();
			gui.setItem(a, chest);
	    	a++;
	    	i++;
	    }
	
	    if(type>1)
	    gui.setItem(45, shangye);
	    else
	    gui.setItem(45, jieshao);
	    gui.setItem(46, jieshao);
	    gui.setItem(47, jieshao);
	    gui.setItem(48, jieshao);
	    gui.setItem(49, jieshao);
	    gui.setItem(50, jieshao);
	    gui.setItem(51, jieshao);
	    gui.setItem(52, jieshao);
	    if (crate.size() < 46+(type-1)*45)
	    gui.setItem(53, jieshao);
	    else
	    gui.setItem(53, xiaye);
	    
	    player.openInventory(gui);
	}

	public static void choose(Player player,String name) {
	    
	    
	    
		Inventory gui = Bukkit.createInventory(null, 9, "§3想对"+Other.data.getString("Info."+name+".color")+name+"§3进行什么操作？");
		ItemStack set = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemStack color = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack remove = new ItemStack(Material.TRAPPED_CHEST);
		ItemStack way = new ItemStack(Material.BOOK);
		
		ItemMeta data = color.getItemMeta();
		ItemMeta datas = way.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
            
        data.setDisplayName("§c删除这个箱子");
        lore.add("§4直接删除这个箱子");
        data.setLore(lore);
        remove.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§5修改奖池奖励");
        lore.add("§2这里存放了你之前设置已设置的物品");
        data.setLore(lore);
        set.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§6设置颜色");
        lore.add("§e设置箱子的显示颜色");
        color.setDurability((short) 1);
        data.setLore(lore);
        color.setItemMeta(data);
        lore.clear();
        
        datas.setDisplayName("§a设置"+Other.data.getString("Info."+name+".color")+name+"§a箱子的开箱方式");
        lore.add("§2普通抽奖和九连抽都在这设置");
        datas.setLore(lore);
        way.setItemMeta(datas);
        lore.clear();
        
        gui.setItem(1, color);
        gui.setItem(3, set);
        gui.setItem(5, way);
        gui.setItem(7, remove);
        
        player.openInventory(gui);
	}
	
	public static void color(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 27, "§3设置"+Other.data.getString("Info."+name+".color")+name+"§3显示的颜色");	
		ItemStack red = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack blue = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack green = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack purple = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack orange = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack white = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack lightgreen = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack lightpurple = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack lightred = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack gray = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack lightblue = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack black = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack drakblue = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack back= new ItemStack(Material.EYE_OF_ENDER);
		
		ItemMeta data = red.getItemMeta();
		ItemMeta datas = back.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
            
        data.setDisplayName("§4红色");
        lore.add("§a设置名字颜色");
        red.setDurability((short) 14);
        data.setLore(lore);
        red.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§e黄色");
        lore.add("§a设置名字颜色");
        yellow.setDurability((short) 4);
        data.setLore(lore);
        yellow.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§6橙色");
        lore.add("§a设置名字颜色");
        orange.setDurability((short) 1);
        data.setLore(lore);
        orange.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§3蓝色");
        lore.add("§a设置名字颜色");
        blue.setDurability((short) 9);
        data.setLore(lore);
        blue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§2绿色");
        lore.add("§a设置名字颜色");
        green.setDurability((short) 5);
        data.setLore(lore);
        green.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§5紫色");
        lore.add("§a设置名字颜色");
        purple.setDurability((short) 10);
        data.setLore(lore);
        purple.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§f白色");
        lore.add("§a设置名字颜色");
        white.setDurability((short) 0);
        data.setLore(lore);
        white.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§a浅绿色");
        lore.add("§a设置名字颜色");
        lightgreen.setDurability((short) 13);
        data.setLore(lore);
        lightgreen.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§7灰色");
        lore.add("§a设置名字颜色");
        gray.setDurability((short) 7);
        data.setLore(lore);
        gray.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§c粉红色");
        lore.add("§a设置名字颜色");
        lightred.setDurability((short) 6);
        data.setLore(lore);
        lightred.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§d亮紫色");
        lore.add("§a设置名字颜色");
        lightpurple.setDurability((short) 2);
        data.setLore(lore);
        lightpurple.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§b天蓝色");
        lore.add("§a设置名字颜色");
        lightblue.setDurability((short) 3);
        data.setLore(lore);
        lightblue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§1深蓝色");
        lore.add("§a设置名字颜色");
        drakblue.setDurability((short) 11);
        data.setLore(lore);
        drakblue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("§0黑色");
        lore.add("§a设置名字颜色");
        black.setDurability((short) 15);
        data.setLore(lore);
        black.setItemMeta(data);
        lore.clear();
        
        datas.setDisplayName("§a返回设置列表");
        lore.add("§2快速返回设置列表以设置其他项");
        datas.setLore(lore);
        back.setItemMeta(datas);
        lore.clear();
        
        gui.setItem(1, blue);
        gui.setItem(3, green);
        gui.setItem(5, purple);
        gui.setItem(7, orange);
        gui.setItem(10, red);
        gui.setItem(11, black);
        gui.setItem(12, yellow);
        gui.setItem(13, back);
        gui.setItem(14, lightgreen);
        gui.setItem(15, drakblue);
        gui.setItem(16, white);
        gui.setItem(19, lightpurple);
        gui.setItem(21, lightblue);
        gui.setItem(23, lightred);
        gui.setItem(25, gray);
        
        
        player.openInventory(gui);
	}
	public static void way(Player player,String name) {
	    
	    
	    
	    
	    ArrayList<String> lore = new ArrayList<String>();
		Inventory gui = Bukkit.createInventory(null, 45, "§d设置抽奖箱"+Other.data.getString("Info."+name+".color")+name+"§d的开箱方式");
		ItemStack normal = new ItemStack(Material.CHEST);
		ItemStack random = new ItemStack(Material.FURNACE);
		ItemStack order = new ItemStack(Material.ENDER_CHEST);
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack embellishment = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemStack gradient = new ItemStack(Material.COMPASS);
		ItemStack back= new ItemStack(Material.EYE_OF_ENDER);
		
		ItemMeta glassdata = glass.getItemMeta();
		glassdata.setDisplayName("§6点击对应的方块，设置对应的开箱动画");
		lore.add("§a上面为单抽，下面为§c九连抽");
		glass.setDurability((short) 1);
		glassdata.setLore(lore);
		lore.clear();
		glass.setItemMeta(glassdata);
		for(int i=0;i<=9;i++) {
			gui.setItem(i, glass);
		}
		for(int i=36;i<=44;i++) {
			gui.setItem(i, glass);
		}
		for(int i=18;i<=26;i++) {
			if(i==22) {
				continue;
			}
			gui.setItem(i, glass);
		}
		gui.setItem(9, glass);
		gui.setItem(17, glass);
		gui.setItem(27, glass);
		gui.setItem(35, glass);
		
		ItemMeta normaldata = normal.getItemMeta();
		normaldata.setDisplayName("§a固定中间抽奖");
		lore.add("§2此动画为默认设置");
		lore.add("§a会在固定一个格子内抽奖");
		normaldata.setLore(lore);
		lore.clear();
		normal.setItemMeta(normaldata);
		gui.setItem(10, normal);
		
		normaldata.setDisplayName("§c九连抽§d：§a固定中间抽奖");
		lore.add("§2此§c九连抽§a动画为默认设置");
		lore.add("§a会在固定九个格子内抽奖");
		normaldata.setLore(lore);
		lore.clear();
		normal.setItemMeta(normaldata);
		gui.setItem(28, normal);
		
		ItemMeta randomdata = random.getItemMeta();
		randomdata.setDisplayName("§e随机位置抽奖");
		lore.add("§a在抽奖界面里随机一个格子内抽奖");
		randomdata.setLore(lore);
		lore.clear();
		random.setItemMeta(randomdata);
		gui.setItem(12, random);
		
		randomdata.setDisplayName("§c九连抽§d：§e随机位置抽奖");
		lore.add("§a在抽奖界面里随机一个格子内抽奖");
		randomdata.setLore(lore);
		lore.clear();
		random.setItemMeta(randomdata);
		gui.setItem(30, random);
		
		
		ItemMeta orderdata = order.getItemMeta();
		orderdata.setDisplayName("§b范围位置内抽奖");
		lore.add("§a在抽奖界面中间位置随机上下范围抽奖");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(14, order);
		
		orderdata.setDisplayName("§c九连抽§d：§b范围位置内抽奖");
		lore.add("§a在抽奖界面横排随机上下范围抽奖");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(32, order);
		
		ItemMeta embellishmentdata = embellishment.getItemMeta();
		embellishmentdata.setDisplayName("§b有物品的随机抽奖");
		lore.add("§a此动画会自动根据奖池大小变动抽奖界面大小");
		lore.add("§a会随机在对应奖池位置亮出奖励抽奖");
		embellishmentdata.setLore(lore);
		lore.clear();
		embellishment.setItemMeta(embellishmentdata);
		gui.setItem(16, embellishment);
		
		ItemMeta gradientdata = gradient.getItemMeta();
		gradientdata.setDisplayName("§c九连抽§d：§6快乐矩形抽奖");
		lore.add("§6最§a炫§2的§1九§3连§4抽§5动§6画§a，§d也§b是§e这§f里§3面§1最§a难§2写§4的");
		gradientdata.setLore(lore);
		lore.clear();
		gradient.setItemMeta(gradientdata);
		gui.setItem(34, gradient);
		
		ItemMeta backdata = back.getItemMeta();
        backdata.setDisplayName("§a返回设置列表");
        lore.add("§2快速返回设置列表以设置其他项");
        backdata.setLore(lore);
        back.setItemMeta(backdata);
        lore.clear();
        gui.setItem(22, back);
		
		
		player.openInventory(gui);
	}
	
	
	public static void setcrate(Player player,String name) {
	    
	    
	    
	    
		for(Player players:Bukkit.getOnlinePlayers()) {
			if(players.getOpenInventory().getTitle().contains("§2抽奖箱"+Other.data.getString("Info."+name+".color")+name+"§2设置")) {
				player.sendMessage("§c这个箱子已经有人在编辑了");
				return;
			}
		}
		
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('§', "§2抽奖箱"+Other.data.getString("Info."+name+".color")+name+"§2设置"));
		List<String> list = Other.data.getStringList("Info."+name+".data");
		
		
		int a=0;
		while(a<=list.size()-1) {
			int b = Integer.parseInt(list.get(a).split(":")[0]);
			String item = list.get(a).split(":")[1];
			ItemStack stack= null;
			if(item.equals("null")) {
				a++;
				continue;
			}
			stack = GetItemStack(item);
			gui.setItem(b, stack);
			a++;
		}
	    player.openInventory(gui);
	}

		public static void showcrate(Player player,String name) {
	    Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
		List<String> list = Other.data.getStringList("Info."+name+".data");
		int a=0;
		int c=0;
		int d=0;
		int e=0;
		int f=0;
		int g=0;
		while(a<=list.size()-1) {
			int b = Integer.parseInt(list.get(a).split(":")[0]);
			String item = list.get(a).split(":")[1];
			if(item.equals("null")) {
				if(b>=45) 
					c++;
				if(b>=36&&b<=44) 
					d++;
				if(b>=27&&b<=35) 
					e++;
				if(b>=18&&b<=26) 
					f++;
				if(b>=9&&b<=17) 
					g++;
				a++;
				continue;
			}
			a++;
		}
		if(c==9) {
			gui = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
			if(d==9) {
				gui = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
				if(e==9) {
					gui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
					if(f==9) {
						gui = Bukkit.createInventory(null, 18, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
						if(g==9) {
							gui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('§', "§c抽奖箱："+Other.data.getString("Info."+name+".color")+name+"§b可抽到的物品"));
						}
					}
				}
			}
		}
		a=0;
		c=0;
		d=0;
		e=0;
		f=0;
		g=0;
		
		while(a<=list.size()-1) {
			int b = Integer.parseInt(list.get(a).split(":")[0]);
			String item = list.get(a).split(":")[1];
			ItemStack stack= null;
			if(item.equals("null")) {
				a++;
				continue;
			}
			stack = GetItemStack(item);
			gui.setItem(b, stack);
			a++;
		}
		a=0;
	    player.openInventory(gui);
	}

//		ItemStack转String
		public String GetItemData(ItemStack item) {
			String a;
			try {
			    a = new StreamSerializer().serializeItemStack(item);
			} catch (Exception e) {
			    a = null;
			}
			return a;
		}
//		String转ItemStack
		public static ItemStack GetItemStack(String data) {
			try {
				return new StreamSerializer().deserializeItemStack(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
