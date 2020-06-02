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
		Inventory gui = Bukkit.createInventory(null, 9, "��cѡ��4��");
		ItemStack Already = new ItemStack(Material.ENDER_CHEST);
		ItemStack Crate = new ItemStack(Material.CHEST);
		ItemMeta data = Already.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        data.setDisplayName("��a�������еĳ齱������");
        lore.add("��2����������֮ǰ���õĳ齱���б�");
        lore.add("��2���������Ը�������Ҫ�Ľ��и������ݡ��Ƴ��齱��");
        data.setLore(lore);
        Already.setItemMeta(data);
        lore.clear();
        data.setDisplayName("��5����һ���µĳ齱��");
        lore.add("��d���������Ҫ����齱�����ơ�6����֧����ɫ�����b&��6��");
        lore.add("��d�������ͻᴴ��һ��gui�����gui�������һ�����ó齱�������");
        lore.add("��c�����������汾����1.9�����鲻Ҫ����̫��������");
        lore.add("��c�ʾ��Ǽ���ԭ��");
        data.setLore(lore);
        Crate.setItemMeta(data);
        lore.clear();
        gui.setItem(2, Already);
        gui.setItem(6, Crate);
        player.openInventory(gui);
	}
	
	public static void createcrate(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('��', "��2�齱��"+name+"��2����"));
	    player.openInventory(gui);
	}

	public static void cratelist(Player player,Integer type) {
		Inventory gui = Bukkit.createInventory(null, 54, "��a�齱���2�б��5����d�ڡ�e"+type+"��dҳ");
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
	    	player.sendMessage("��cû�д������κ�����");
	    	return;
	    	}
	    }
	    
	    ArrayList<String> crate = new ArrayList<String>();
	    for(String string:Other.data.getConfigurationSection("Info").getKeys(false)) {
	    	crate.add(string);
	    }
	    ItemMeta abc = jieshao.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    
	    abc.setDisplayName("��a��һҳ");
	    lore.add("��c�鿴��һҳ");
	    xiaye.setDurability((short) 14);
	    abc.setLore(lore);
	    xiaye.setItemMeta(abc);
	    lore.clear();
	    
	    abc.setDisplayName("��a��һҳ");
	    lore.add("��c������һҳ");
	    shangye.setDurability((short) 11);
	    abc.setLore(lore);
	    shangye.setItemMeta(abc);
	    lore.clear();
	    
	    abc.setDisplayName("��a�����ǡ�b�ڡ�2"+type+"��bҳ��");
	    lore.add("��a�����Ҫ�༭������");
	    jieshao.setDurability((short) 1);
	    abc.setLore(lore);
	    jieshao.setItemMeta(abc);
	    lore.clear();
	    
	    int i=(type-1)*45;
	    int a=0;
	    int size = crate.size()-1;
	    while(i<=size&&i<=44+(type-1)*45) {
	    	abc.setDisplayName(ChatColor.translateAlternateColorCodes('��', "��6�齱�����á�b��"+Other.data.getString("Info."+crate.get(i)+".color")+crate.get(i)));
	    	
			lore.add("��a�齱������d������b״̬��f��"+Other.data.getString("Info."+crate.get(i)+".animation").replace("true", "��a����").replace("false", "��cδ����"));
			lore.add("��c�������a������d������b״̬��f��"+Other.data.getString("Info."+crate.get(i)+".nineanimation").replace("true", "��a����").replace("false", "��cδ����"));
			
			lore.add("");
			
			if(Other.data.getString("Info."+crate.get(i)+".announcement").equals("��"))
			lore.add("��a�齱��������״̬����cδ����");
			else
			lore.add(ChatColor.translateAlternateColorCodes('&', "��a�齱���������ı���d����f"+Other.data.getString("Info."+crate.get(i)+".announcement")));
			
			if(Other.data.getString("Info."+crate.get(i)+".nine").equals("��"))
			lore.add("��c�������a��������״̬��d����cδ����");
			else
			lore.add(ChatColor.translateAlternateColorCodes('&', "��c�������a���������ı���d����f"+Other.data.getString("Info."+crate.get(i)+".nine")));
			
			lore.add("");
			
			if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
			lore.add("��a�Զ�����ͨ�齱ʱ�䣺��cδ����");
			else {
				lore.add("��a��ͨ�齱��ô�����e����6"+Other.data.getDouble("Info."+crate.get(i)+".number"));
				lore.add("��a��ͨ�齱��ü��ʱ���e����6"+Other.data.getDouble("Info."+crate.get(i)+".cd"));
				lore.add("");
			}
			if(Other.data.getDouble("Info."+crate.get(i)+".ninecd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".ninenumber")<=0)
			lore.add("��a�Զ����c�������a�齱ʱ�䣺��cδ����");
			else {
				if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
				lore.add("");
				lore.add("��c�������a��ô�����e����6"+Other.data.getDouble("Info."+crate.get(i)+".ninenumber"));
				lore.add("��c�������a��ü��ʱ���e����6"+Other.data.getDouble("Info."+crate.get(i)+".ninecd"));
			}	

			lore.add("");
			if(Other.data.getBoolean("Info."+crate.get(i)+".info")) {
				lore.add("��a���浥�鵽����Ʒ�� ��a����");
			}else {
				lore.add("��a���浥�鵽����Ʒ�� ��cδ����");
			}
			if(Other.data.getBoolean("Info."+crate.get(i)+".nineinfo")) {
				lore.add("��a�����c�������a�鵽����Ʒ�� ��a����");
			}else {
				lore.add("��a�����c�������a�鵽����Ʒ����cδ����");
			}
			lore.add("");
			if(Other.data.getBoolean("Info."+crate.get(i)+".clear")) {
				lore.add("��dһ���ԡ�a������ģʽ�� ��a����");
				if(Other.data.getBoolean("Info."+crate.get(i)+".backup")) {
					lore.add("��a����ճ齱�䵽��c�ա�aʱ����������b�� ��a����");
				}else {
					lore.add("��a����ճ齱�䵽��c�ա�aʱ����������b�� ��cδ����");
				}
			}else {
				lore.add("��dһ���ԡ�a������ģʽ�� ��cδ����");
			}
			lore.add("");	
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("normal"))
				lore.add("��a�齱����״̬��d�� ��a��l�̶��м�齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("random"))
				lore.add("��a�齱����״̬��d�� ��e��l���λ�ó齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".type").equals("order"))
				lore.add("��a�齱����״̬��d�� ��b��l��Χλ���ڳ齱");
			else
				if(Other.data.getString("Info."+crate.get(i)+".type").equals("embellishment"))
				lore.add("��a�齱����״̬��d�� ��b��l����Ʒ������齱");
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("normal"))
				lore.add("��c�������a�齱����״̬��d�� ��a��l�̶��м�齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("random"))
				lore.add("��c�������a�齱����״̬��d�� ��e��l���λ�ó齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("order"))
				lore.add("��c�������a�齱����״̬��d�� ��b��l��Χλ���ڳ齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("gradient"))
				lore.add("��c�������a�齱����״̬��d�� ��6��l���־��γ齱");
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
	    
	    
	    
		Inventory gui = Bukkit.createInventory(null, 9, "��3���"+Other.data.getString("Info."+name+".color")+name+"��3����ʲô������");
		ItemStack set = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemStack color = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack remove = new ItemStack(Material.TRAPPED_CHEST);
		ItemStack way = new ItemStack(Material.BOOK);
		
		ItemMeta data = color.getItemMeta();
		ItemMeta datas = way.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
            
        data.setDisplayName("��cɾ���������");
        lore.add("��4ֱ��ɾ���������");
        data.setLore(lore);
        remove.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��5�޸Ľ��ؽ���");
        lore.add("��2����������֮ǰ���������õ���Ʒ");
        data.setLore(lore);
        set.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��6������ɫ");
        lore.add("��e�������ӵ���ʾ��ɫ");
        color.setDurability((short) 1);
        data.setLore(lore);
        color.setItemMeta(data);
        lore.clear();
        
        datas.setDisplayName("��a����"+Other.data.getString("Info."+name+".color")+name+"��a���ӵĿ��䷽ʽ");
        lore.add("��2��ͨ�齱�;����鶼��������");
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
		Inventory gui = Bukkit.createInventory(null, 27, "��3����"+Other.data.getString("Info."+name+".color")+name+"��3��ʾ����ɫ");	
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
            
        data.setDisplayName("��4��ɫ");
        lore.add("��a����������ɫ");
        red.setDurability((short) 14);
        data.setLore(lore);
        red.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��e��ɫ");
        lore.add("��a����������ɫ");
        yellow.setDurability((short) 4);
        data.setLore(lore);
        yellow.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��6��ɫ");
        lore.add("��a����������ɫ");
        orange.setDurability((short) 1);
        data.setLore(lore);
        orange.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��3��ɫ");
        lore.add("��a����������ɫ");
        blue.setDurability((short) 9);
        data.setLore(lore);
        blue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��2��ɫ");
        lore.add("��a����������ɫ");
        green.setDurability((short) 5);
        data.setLore(lore);
        green.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��5��ɫ");
        lore.add("��a����������ɫ");
        purple.setDurability((short) 10);
        data.setLore(lore);
        purple.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��f��ɫ");
        lore.add("��a����������ɫ");
        white.setDurability((short) 0);
        data.setLore(lore);
        white.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��aǳ��ɫ");
        lore.add("��a����������ɫ");
        lightgreen.setDurability((short) 13);
        data.setLore(lore);
        lightgreen.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��7��ɫ");
        lore.add("��a����������ɫ");
        gray.setDurability((short) 7);
        data.setLore(lore);
        gray.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��c�ۺ�ɫ");
        lore.add("��a����������ɫ");
        lightred.setDurability((short) 6);
        data.setLore(lore);
        lightred.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��d����ɫ");
        lore.add("��a����������ɫ");
        lightpurple.setDurability((short) 2);
        data.setLore(lore);
        lightpurple.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��b����ɫ");
        lore.add("��a����������ɫ");
        lightblue.setDurability((short) 3);
        data.setLore(lore);
        lightblue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��1����ɫ");
        lore.add("��a����������ɫ");
        drakblue.setDurability((short) 11);
        data.setLore(lore);
        drakblue.setItemMeta(data);
        lore.clear();
        
        data.setDisplayName("��0��ɫ");
        lore.add("��a����������ɫ");
        black.setDurability((short) 15);
        data.setLore(lore);
        black.setItemMeta(data);
        lore.clear();
        
        datas.setDisplayName("��a���������б�");
        lore.add("��2���ٷ��������б�������������");
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
		Inventory gui = Bukkit.createInventory(null, 45, "��d���ó齱��"+Other.data.getString("Info."+name+".color")+name+"��d�Ŀ��䷽ʽ");
		ItemStack normal = new ItemStack(Material.CHEST);
		ItemStack random = new ItemStack(Material.FURNACE);
		ItemStack order = new ItemStack(Material.ENDER_CHEST);
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack embellishment = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemStack gradient = new ItemStack(Material.COMPASS);
		ItemStack back= new ItemStack(Material.EYE_OF_ENDER);
		
		ItemMeta glassdata = glass.getItemMeta();
		glassdata.setDisplayName("��6�����Ӧ�ķ��飬���ö�Ӧ�Ŀ��䶯��");
		lore.add("��a����Ϊ���飬����Ϊ��c������");
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
		normaldata.setDisplayName("��a�̶��м�齱");
		lore.add("��2�˶���ΪĬ������");
		lore.add("��a���ڹ̶�һ�������ڳ齱");
		normaldata.setLore(lore);
		lore.clear();
		normal.setItemMeta(normaldata);
		gui.setItem(10, normal);
		
		normaldata.setDisplayName("��c�������d����a�̶��м�齱");
		lore.add("��2�ˡ�c�������a����ΪĬ������");
		lore.add("��a���ڹ̶��Ÿ������ڳ齱");
		normaldata.setLore(lore);
		lore.clear();
		normal.setItemMeta(normaldata);
		gui.setItem(28, normal);
		
		ItemMeta randomdata = random.getItemMeta();
		randomdata.setDisplayName("��e���λ�ó齱");
		lore.add("��a�ڳ齱���������һ�������ڳ齱");
		randomdata.setLore(lore);
		lore.clear();
		random.setItemMeta(randomdata);
		gui.setItem(12, random);
		
		randomdata.setDisplayName("��c�������d����e���λ�ó齱");
		lore.add("��a�ڳ齱���������һ�������ڳ齱");
		randomdata.setLore(lore);
		lore.clear();
		random.setItemMeta(randomdata);
		gui.setItem(30, random);
		
		
		ItemMeta orderdata = order.getItemMeta();
		orderdata.setDisplayName("��b��Χλ���ڳ齱");
		lore.add("��a�ڳ齱�����м�λ��������·�Χ�齱");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(14, order);
		
		orderdata.setDisplayName("��c�������d����b��Χλ���ڳ齱");
		lore.add("��a�ڳ齱�������������·�Χ�齱");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(32, order);
		
		ItemMeta embellishmentdata = embellishment.getItemMeta();
		embellishmentdata.setDisplayName("��b����Ʒ������齱");
		lore.add("��a�˶������Զ����ݽ��ش�С�䶯�齱�����С");
		lore.add("��a������ڶ�Ӧ����λ�����������齱");
		embellishmentdata.setLore(lore);
		lore.clear();
		embellishment.setItemMeta(embellishmentdata);
		gui.setItem(16, embellishment);
		
		ItemMeta gradientdata = gradient.getItemMeta();
		gradientdata.setDisplayName("��c�������d����6���־��γ齱");
		lore.add("��6���a�š�2�ġ�1�š�3����4���5����6����a����dҲ��b�ǡ�e���f���3���1���a�ѡ�2д��4��");
		gradientdata.setLore(lore);
		lore.clear();
		gradient.setItemMeta(gradientdata);
		gui.setItem(34, gradient);
		
		ItemMeta backdata = back.getItemMeta();
        backdata.setDisplayName("��a���������б�");
        lore.add("��2���ٷ��������б�������������");
        backdata.setLore(lore);
        back.setItemMeta(backdata);
        lore.clear();
        gui.setItem(22, back);
		
		
		player.openInventory(gui);
	}
	
	
	public static void setcrate(Player player,String name) {
	    
	    
	    
	    
		for(Player players:Bukkit.getOnlinePlayers()) {
			if(players.getOpenInventory().getTitle().contains("��2�齱��"+Other.data.getString("Info."+name+".color")+name+"��2����")) {
				player.sendMessage("��c��������Ѿ������ڱ༭��");
				return;
			}
		}
		
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('��', "��2�齱��"+Other.data.getString("Info."+name+".color")+name+"��2����"));
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
	    Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
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
			gui = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
			if(d==9) {
				gui = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
				if(e==9) {
					gui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
					if(f==9) {
						gui = Bukkit.createInventory(null, 18, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
						if(g==9) {
							gui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('��', "��c�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��b�ɳ鵽����Ʒ"));
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

//		ItemStackתString
		public String GetItemData(ItemStack item) {
			String a;
			try {
			    a = new StreamSerializer().serializeItemStack(item);
			} catch (Exception e) {
			    a = null;
			}
			return a;
		}
//		StringתItemStack
		public static ItemStack GetItemStack(String data) {
			try {
				return new StreamSerializer().deserializeItemStack(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
