package pers.tany.crateslottery.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pers.tany.crateslottery.CommonlyWay;
import pers.tany.crateslottery.Other;

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
			
			if(Other.data.getBoolean("Info."+crate.get(i)+".animation")) {
				if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
					lore.add("��a�Զ�����ͨ�齱ʱ�䣺��cδ����");
				else {
					lore.add("��a��ͨ�齱��ô�����e����6"+Other.data.getDouble("Info."+crate.get(i)+".number"));
					lore.add("��a��ͨ�齱��ü��ʱ���e����6"+Other.data.getDouble("Info."+crate.get(i)+".cd"));
					lore.add("");
				}
			}
			
			if(Other.data.getBoolean("Info."+crate.get(i)+".nineanimation")) {
				if(Other.data.getDouble("Info."+crate.get(i)+".ninecd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".ninenumber")<=0)
					lore.add("��a�Զ����c�������a�齱ʱ�䣺��cδ����");
				else {
					if(Other.data.getDouble("Info."+crate.get(i)+".cd")<=0&&Other.data.getDouble("Info."+crate.get(i)+".number")<=0)
					lore.add("");
					lore.add("��c�������a��ô�����e����6"+Other.data.getDouble("Info."+crate.get(i)+".ninenumber"));
					lore.add("��c�������a��ü��ʱ���e����6"+Other.data.getDouble("Info."+crate.get(i)+".ninecd"));
				}	
			}
			
			if(Other.data.getBoolean("Info."+crate.get(i)+".nineanimation")||Other.data.getBoolean("Info."+crate.get(i)+".animation"))
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
			else
				if(Other.data.getString("Info."+crate.get(i)+".type").equals("repeatedly"))
				lore.add("��a�齱����״̬��d�� ��c��l���������齱");
			else
				if(Other.data.getString("Info."+crate.get(i)+".type").equals("show"))
				lore.add("��a�齱����״̬��d�� ��3��l����Ƴ齱");
			
			
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
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("repeatedly"))
				lore.add("��c�������a�齱����״̬��d��  ��c��l���������齱");
			else
			if(Other.data.getString("Info."+crate.get(i)+".ninetype").equals("show"))
				lore.add("��c�������d����3��l����Ƴ齱");
			
			lore.add("");
			if(Other.data.getBoolean("Info."+crate.get(i)+".unpackanytime"))
				lore.add("��a��ʱ�齱״̬������");
			else
				lore.add("��a��ʱ�齱״̬����cδ����");
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
	    
	    
	    
		Inventory gui = Bukkit.createInventory(null, 36, "��3���"+Other.data.getString("Info."+name+".color")+name+"��3����ʲô������");
		ItemStack set = new ItemStack(Material.ENCHANTMENT_TABLE);
		ItemStack color = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack remove = new ItemStack(Material.TRAPPED_CHEST);
		ItemStack way = new ItemStack(Material.BOOK);
		ItemStack frame = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack back= new ItemStack(Material.EYE_OF_ENDER);
		
		ItemMeta setdata = set.getItemMeta();
		ItemMeta colordata = color.getItemMeta();
		ItemMeta removedata = remove.getItemMeta();
		ItemMeta waydata = way.getItemMeta();
		ItemMeta framedata = frame.getItemMeta();
		ItemMeta backdata = back.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
            
        removedata.setDisplayName("��cɾ���������");
        lore.add("��4ֱ��ɾ���������");
        removedata.setLore(lore);
        remove.setItemMeta(removedata);
        lore.clear();
        
        setdata.setDisplayName("��5�޸Ľ��ؽ���");
        lore.add("��2����������֮ǰ���������õ���Ʒ");
        setdata.setLore(lore);
        set.setItemMeta(setdata);
        lore.clear();
        
        colordata.setDisplayName("��6������ɫ");
        lore.add("��e�������ӵ���ʾ��ɫ");
        color.setDurability((short) 1);
        colordata.setLore(lore);
        color.setItemMeta(colordata);
        lore.clear();
        
        waydata.setDisplayName("��a����"+Other.data.getString("Info."+name+".color")+name+"��a���ӵĿ��䷽ʽ");
        lore.add("��2��ͨ�齱�;����鶼��������");
        waydata.setLore(lore);
        way.setItemMeta(waydata);
        lore.clear();
        
        framedata.setDisplayName("��a����������������");
        lore.add("��dѡ���Ӧ����Ŀ����������޸İɣ�");
        color.setDurability((short) 3);
        framedata.setLore(lore);
        frame.setItemMeta(framedata);
        lore.clear();
        
        backdata.setDisplayName("��a����ѡ���б�");
        lore.add("��2���ص����б�");
        backdata.setLore(lore);
        back.setItemMeta(backdata);
        lore.clear();
        
		ItemStack setcrate = new ItemStack(Material.CHEST);
		ItemStack ninesetcrate = new ItemStack(Material.ENDER_CHEST);
		ItemStack info = new ItemStack(Material.PAPER);
		ItemStack nineinfo = new ItemStack(Material.BOOK);
		ItemStack clear = new ItemStack(Material.LAVA_BUCKET);
		ItemStack backup = new ItemStack(Material.WATER_BUCKET);
		ItemStack check = new ItemStack(Material.QUARTZ);
		ItemStack unpackanytime = new ItemStack(Material.TRIPWIRE_HOOK);
		
		ItemMeta setcratedata = setcrate.getItemMeta();
		ItemMeta ninesetcratedata = ninesetcrate.getItemMeta();
		ItemMeta infodata = info.getItemMeta();
		ItemMeta nineinfodata = nineinfo.getItemMeta();
		ItemMeta checkdata = check.getItemMeta();
		ItemMeta cleardata = clear.getItemMeta();
		ItemMeta backupdata = clear.getItemMeta();
		ItemMeta unpackanytimedata = unpackanytime.getItemMeta();
		
		
		setcratedata.setDisplayName("��a�޸��Ƿ����õ��鶯��");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".animation").replace("true", "��a����").replace("false", "��cδ����"));
        setcratedata.setLore(lore);
        setcrate.setItemMeta(setcratedata);
        lore.clear();
        
        ninesetcratedata.setDisplayName("��a�޸��Ƿ����á�c�������a����");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".nineanimation").replace("true", "��a����").replace("false", "��cδ����"));
        ninesetcratedata.setLore(lore);
        ninesetcrate.setItemMeta(ninesetcratedata);
        lore.clear();
        
        infodata.setDisplayName("��a�޸��Ƿ����ù��浥�鵽����Ʒ");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".info").replace("true", "��a����").replace("false", "��cδ����"));
        infodata.setLore(lore);
        info.setItemMeta(infodata);
        lore.clear();
        
        nineinfodata.setDisplayName("��a�޸��Ƿ����ù����c�������a������Ʒ");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".nineinfo").replace("true", "��a����").replace("false", "��cδ����"));
        nineinfodata.setLore(lore);
        nineinfo.setItemMeta(nineinfodata);
        lore.clear();
        
        cleardata.setDisplayName("��a�޸��Ƿ񵥳���������鵽����Ʒ����");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".clear").replace("true", "��a����").replace("false", "��cδ����"));
        cleardata.setLore(lore);
        clear.setItemMeta(cleardata);
        lore.clear();
        
        backupdata.setDisplayName("��a�޸��Ƿ����������clear��������ݵ���������");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".backup").replace("true", "��a����").replace("false", "��cδ����"));
        backupdata.setLore(lore);
        backup.setItemMeta(backupdata);
        lore.clear();
        
        unpackanytimedata.setDisplayName("��a�޸��Ƿ���������齱��������伴�ɳ齱");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".unpackanytime").replace("true", "��a����").replace("false", "��cδ����"));
        lore.add("��2��Ը��֮Ϊ����d��ճ齱");
        unpackanytimedata.setLore(lore);
        unpackanytime.setItemMeta(unpackanytimedata);
        lore.clear();
        
        checkdata.setDisplayName("��a�޸�����齱���Ƿ���Բ鿴��������");
        lore.add("��2��ǰ״̬��"+Other.data.getString("Info."+name+".check").replace("true", "��a����").replace("false", "��cδ����"));
        checkdata.setLore(lore);
        check.setItemMeta(checkdata);
        lore.clear();
        
        gui.setItem(0, color);
        gui.setItem(2, way);
        gui.setItem(4, set);
        gui.setItem(6, remove);
        gui.setItem(8, back);
        gui.setItem(18, setcrate);
        gui.setItem(19, ninesetcrate);
        gui.setItem(20, info);
        gui.setItem(21, nineinfo);
        gui.setItem(23, clear);
        gui.setItem(24, backup);
        gui.setItem(26, unpackanytime);
        gui.setItem(27, check);
        
        for(int a=9;a<=17;a++) {
        	gui.setItem(a, frame);
        }
        gui.setItem(22, frame);
        gui.setItem(31, frame);
        
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
		ItemStack repeatedly= new ItemStack(Material.ENDER_PEARL);
		ItemStack horse= new ItemStack(Material.GOLD_BARDING);
		ItemStack ninehorse= new ItemStack(Material.DIAMOND_BARDING);
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
		gui.setItem(11, random);
		
		randomdata.setDisplayName("��c�������d����e���λ�ó齱");
		lore.add("��a�ڳ齱���������һ�������ڳ齱");
		randomdata.setLore(lore);
		lore.clear();
		random.setItemMeta(randomdata);
		gui.setItem(29, random);
		
		
		ItemMeta orderdata = order.getItemMeta();
		orderdata.setDisplayName("��b��Χλ���ڳ齱");
		lore.add("��a�ڳ齱�����м�λ��������·�Χ�齱");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(12, order);
		
		orderdata.setDisplayName("��c�������d����b��Χλ���ڳ齱");
		lore.add("��a�ڳ齱�������������·�Χ�齱");
		orderdata.setLore(lore);
		lore.clear();
		order.setItemMeta(orderdata);
		gui.setItem(30, order);
		
		ItemMeta repeatedlydata = repeatedly.getItemMeta();
		repeatedlydata.setDisplayName("��c���������齱");
		lore.add("��a�˶�����һ�λ������һ��λ������");
		lore.add("��aȻ����˳����������������/��ʱ����ת");
		repeatedlydata.setLore(lore);
		lore.clear();
		repeatedly.setItemMeta(repeatedlydata);
		gui.setItem(14, repeatedly);
		
		ItemMeta ninerepeatedlydata = repeatedly.getItemMeta();
		ninerepeatedlydata.setDisplayName("��c�������d����c���������齱");
		lore.add("��a�˶�����һ�λ�����ھŸ�λ������");
		lore.add("��aȻ����˳����������������/��ʱ����ת");
		ninerepeatedlydata.setLore(lore);
		lore.clear();
		repeatedly.setItemMeta(ninerepeatedlydata);
		gui.setItem(32, repeatedly);
		
		ItemMeta embellishmentdata = embellishment.getItemMeta();
		embellishmentdata.setDisplayName("��b����Ʒ������齱");
		lore.add("��a�˶������Զ����ݽ��ش�С�䶯�齱�����С");
		lore.add("��a������ڶ�Ӧ����λ�����������齱");
		embellishmentdata.setLore(lore);
		lore.clear();
		embellishment.setItemMeta(embellishmentdata);
		gui.setItem(16, embellishment);
		
		ItemMeta horsedata = horse.getItemMeta();
		horsedata.setDisplayName("��3����Ƴ齱");
		lore.add("��aÿ�γ鶼���ڽ�����ʾ�Ÿ���Ʒ");
		lore.add("��a���ݲ�����������ʾ��λ�û�ȡ��Ӧλ�õ���Ʒ");
		horsedata.setLore(lore);
		lore.clear();
		horse.setItemMeta(horsedata);
		gui.setItem(15, horse);
		
		ItemMeta ninehorsedata = ninehorse.getItemMeta();
		ninehorsedata.setDisplayName("��c�������d����3����Ƴ齱");
		lore.add("��aÿ�γ鶼���ڽ�����ʾ�Ÿ���Ʒ");
		lore.add("��a���ݲ�����������ʾ���б��ö�Ӧ��ʾ��������Ʒ");
		ninehorsedata.setLore(lore);
		lore.clear();
		ninehorse.setItemMeta(ninehorsedata);
		gui.setItem(33, ninehorse);
		
		
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
			stack = CommonlyWay.GetItemStack(item);
			gui.setItem(b, stack);
			a++;
		}
	    player.openInventory(gui);
	}

		public static void showcrate(Player player,String name) {
	    Inventory gui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
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
			gui = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
			if(d==9) {
				gui = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
				if(e==9) {
					gui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
					if(f==9) {
						gui = Bukkit.createInventory(null, 18, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
						if(g==9) {
							gui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('��', "��a�齱�䣺"+Other.data.getString("Info."+name+".color")+name+"��a�ɳ鵽����Ʒ"));
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
			stack = CommonlyWay.GetItemStack(item);
			gui.setItem(b, stack);
			a++;
		}
		a=0;
	    player.openInventory(gui);
	}
}
