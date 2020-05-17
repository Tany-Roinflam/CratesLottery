package com.tany.crateslottery.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.crateslottery.Main;

public class Preset {
	public static int location = 0;
	public static void winging(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"�齱��"+colors+"��");
        lore.add("��"+color+"�齱һʱˬ��a����"+colors+"һֱ�齱һֱˬ");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(i, glass);
        i++;
        }
        i=0;
        
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		gui.setItem(22, GetItemStack(item));
		
        player.openInventory(gui);
	}
	
	public static void wing(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��ȡ��Ľ����ɣ�");
        lore.add("��"+color+"�����"+colors+"���");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(i, glass);
        i++;
        }
        i=0;
        
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		Preset.location=get;
		gui.setItem(22, GetItemStack(item));
		
        player.openInventory(gui);
	}
	
	public static void ninewinging(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);

		Inventory gui = Bukkit.createInventory(null, 45, "��c���ھ�����a����"+config2.getString("Info."+name+".color")+name+"��d��");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��c������4�齱��"+colors+"��");
        lore.add("��"+color+"��һ����������Ħ��");
        lore.add("��"+colors+"�齱ing");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(i, glass);
        i++;
        }
        i=0;
        
        int number = 1;
        int location = 1;
        while(number<=9) {
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		if(location==1)
		gui.setItem(12, GetItemStack(item));
		else
		if(location==2)
		gui.setItem(13, GetItemStack(item));
		else
		if(location==3)
		gui.setItem(14, GetItemStack(item));
		else
		if(location==4)
		gui.setItem(21, GetItemStack(item));
		else
		if(location==5)
		gui.setItem(22, GetItemStack(item));
		else
		if(location==6)
		gui.setItem(23, GetItemStack(item));
		else
		if(location==7)
		gui.setItem(30, GetItemStack(item));
		else
		if(location==8)
		gui.setItem(31, GetItemStack(item));
		else
		if(location==9)
		gui.setItem(32, GetItemStack(item));
		number++;
		location++;
        }
        player.openInventory(gui);
	}
	
	public static void ninewing(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��c�齱��6�䣺"+config2.getString("Info."+name+".color")+name+"��c����������");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��ȡ���ս��Ʒ�ɣ�");
        lore.add("��"+color+"���������"+colors+"���");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(i, glass);
        i++;
        }
        i=0;
        
        int number = 1;
        int location = 1;
        while(number<=9) {
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		if(location==1)
		gui.setItem(12, GetItemStack(item));
		else
		if(location==2)
		gui.setItem(13, GetItemStack(item));
		else
		if(location==3)
		gui.setItem(14, GetItemStack(item));
		else
		if(location==4)
		gui.setItem(21, GetItemStack(item));
		else
		if(location==5)
		gui.setItem(22, GetItemStack(item));
		else
		if(location==6)
		gui.setItem(23, GetItemStack(item));
		else
		if(location==7)
		gui.setItem(30, GetItemStack(item));
		else
		if(location==8)
		gui.setItem(31, GetItemStack(item));
		else
		if(location==9)
		gui.setItem(32, GetItemStack(item));
		number++;
		location++;
        }
		
        player.openInventory(gui);
	}
	
	public static void randomwinging(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = config2.getStringList("Info."+name+".data");
	    
	    int i=0;
	    int a=0;
	    while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
	    data.setDisplayName("��"+color+"�齱��"+colors+"��");
	    lore.add("��"+color+"�齱һʱˬ��a����"+colors+"һֱ�齱һֱˬ");
	    glass.setDurability((short) durability);
	    data.setLore(lore);
	    glass.setItemMeta(data);
	    lore.clear();
	    gui.setItem(a, glass);
	    a++;
	    i++;
	    }
	    i=0;
	    a=0;
	    
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		get = random.nextInt(45);
		gui.setItem(get, GetItemStack(item));
		
	    player.openInventory(gui);
	}

	public static void randomwing(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        int a=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��ȡ��Ľ����ɣ�");
        lore.add("��"+color+"�����"+colors+"���");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(a, glass);
        a++;
        i++;
        }
        i=0;
        a=0;
        
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		Preset.location=get;
		get = random.nextInt(45);
		gui.setItem(get, GetItemStack(item));
		
        player.openInventory(gui);
	}
	
	public static void randomninewinging(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");

	    File file1=new File(config.getDataFolder(),"data.yml");


	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);

	    
		Inventory gui = Bukkit.createInventory(null, 45, "��c���ھ�����a����"+config2.getString("Info."+name+".color")+name+"��d��");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��c������4�齱��"+colors+"��");
        lore.add("��"+color+"��һ����������Ħ��");
        lore.add("��"+colors+"�齱ing");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(i, glass);
        i++;
        }
        i=0;
        
        ArrayList<Integer> location = new ArrayList<Integer>();
        Random random = new Random();
        int number = 1;
        while(number<=9) {
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		get = random.nextInt(45);
		while(location.size()!=0&&location.contains(get)) {
			get = random.nextInt(45);
		}
		gui.setItem(get, GetItemStack(item));
		location.add(get);
		number++;
        }
		location.clear();
        player.openInventory(gui);
	}
	
	public static void randomninewing(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");

	    File file1=new File(config.getDataFolder(),"data.yml");


	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);

	    
		Inventory gui = Bukkit.createInventory(null, 45, "��c�齱��6�䣺"+config2.getString("Info."+name+".color")+name+"��c����������");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = config2.getStringList("Info."+name+".data");
        
        int i=0;
        int a=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("��"+color+"��ȡ���ս��Ʒ�ɣ�");
        lore.add("��"+color+"���������"+colors+"���");
        glass.setDurability((short) durability);
        data.setLore(lore);
        glass.setItemMeta(data);
        lore.clear();
        gui.setItem(a, glass);
        a++;
        i++;
        }
        i=0;
        a=0;
        
        ArrayList<Integer> location = new ArrayList<Integer>();
        int number = 1;
        while(number<=9) {
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		get = random.nextInt(45);
		while(location.size()!=0&&location.contains(get)) {
			get = random.nextInt(45);
		}
		gui.setItem(get, GetItemStack(item));
		location.add(get);
		number++;
        }
		location.clear();
        player.openInventory(gui);
	}
	
	public static void orderwinging(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	    
		Inventory gui = Bukkit.createInventory(null, 45, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = config2.getStringList("Info."+name+".data");
	    
	    int i=0;
	    while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
	    data.setDisplayName("��"+color+"�齱��"+colors+"��");
	    lore.add("��"+color+"�齱һʱˬ��a����"+colors+"һֱ�齱һֱˬ");
	    glass.setDurability((short) durability);
	    data.setLore(lore);
	    glass.setItemMeta(data);
	    lore.clear();
	    gui.setItem(i, glass);
	    i++;
	    }
	    i=0;
	    
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
		}
		get = random.nextInt(5);
		gui.setItem(4+get*9, GetItemStack(item));
		
	    player.openInventory(gui);
	}

		public static void orderwing(Player player,String name) {
	    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
	    File file1=new File(config.getDataFolder(),"data.yml");
	    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);

	    
		Inventory gui = Bukkit.createInventory(null, 45, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = config2.getStringList("Info."+name+".data");
	    
	    int i=0;
	    while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
	    data.setDisplayName("��"+color+"��ȡ��Ľ����ɣ�");
	    lore.add("��"+color+"�����"+colors+"���");
	    glass.setDurability((short) durability);
	    data.setLore(lore);
	    glass.setItemMeta(data);
	    lore.clear();
	    gui.setItem(i, glass);
	    i++;
	    }
	    i=0;
	    
		Random random = new Random();
		int get = random.nextInt(54);
		String item = list.get(get).split(":")[1];
		while(item.equals("null")) {
			get = random.nextInt(54);
			item = list.get(get).split(":")[1];
			Preset.location=get;
			Preset.location=get;
		}
		get = random.nextInt(5);
		gui.setItem(4+get*9, GetItemStack(item));
		
	    player.openInventory(gui);
	}

		public static void orderninewinging(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
		    
			Inventory gui = Bukkit.createInventory(null, 45, "��c���ھ�����a����"+config2.getString("Info."+name+".color")+name+"��d��");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = config2.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
		    data.setDisplayName("��"+color+"��c������4�齱��"+colors+"��");
		    lore.add("��"+color+"��һ����������Ħ��");
		    lore.add("��"+colors+"�齱ing");
		    glass.setDurability((short) durability);
		    data.setLore(lore);
		    glass.setItemMeta(data);
		    lore.clear();
		    gui.setItem(i, glass);
		    i++;
		    }
		    i=0;
		    
			Random randoms = new Random();
			int location = randoms.nextInt(5);
		    int number = 1;
		    while(number<=9) {
			Random random = new Random();
			int get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			Preset.location=get;
			gui.setItem(number-1+9*location, GetItemStack(item));
			number++;
		    }
		    player.openInventory(gui);
		}

		public static void orderninewing(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
		    
			Inventory gui = Bukkit.createInventory(null, 45, "��c�齱��6�䣺"+config2.getString("Info."+name+".color")+name+"��c����������");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = config2.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
		    data.setDisplayName("��"+color+"��ȡ���ս��Ʒ�ɣ�");
		    lore.add("��"+color+"���������"+colors+"���");
		    glass.setDurability((short) durability);
		    data.setLore(lore);
		    glass.setItemMeta(data);
		    lore.clear();
		    gui.setItem(i, glass);
		    i++;
		    }
		    i=0;
		    
			Random randoms = new Random();
			int location = randoms.nextInt(5);
		    int number = 1;
		    while(number<=9) {
			Random random = new Random();
			int get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			gui.setItem(number-1+9*location, GetItemStack(item));
			number++;
		    }
			
		    player.openInventory(gui);
		}

		public static void embellishmentwinging(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	
		    Inventory gui = Bukkit.createInventory(null, 54, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
			List<String> list = config2.getStringList("Info."+name+".data");
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
			int size=54;
			if(c==9) {
				size=45;
				gui = Bukkit.createInventory(null, 45, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
				if(d==9) {
					size=36;
					gui = Bukkit.createInventory(null, 36, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
					if(e==9) {
						size=27;
						gui = Bukkit.createInventory(null, 27, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
						if(f==9) {
							size=18;
							gui = Bukkit.createInventory(null, 18, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
							if(g==9) {
								size=9;
								gui = Bukkit.createInventory(null, 9, "��a���ڿ���"+config2.getString("Info."+name+".color")+name+"��d��");
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
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack glasss = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
			ItemMeta datas = glasss.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> lists = config2.getStringList("Info."+name+".data");
	        int i=0;
	        while(i<size) {
	        data.setDisplayName("��f�齱��d��");
	        lore.add("��f�齱һʱˬ��a����2һֱ�齱һֱˬ");
	        data.setLore(lore);
	        glass.setDurability((short) 8);
	        glass.setItemMeta(data);
	        lore.clear();
	        gui.setItem(i, glass);
	        i++;
	        }
	        i=0;
	        
			while(a<=list.size()-1) {
				int b = Integer.parseInt(list.get(a).split(":")[0]);
				String item = list.get(a).split(":")[1];
				ItemStack stack= null;
				if(item.equals("null")) {
					a++;
					continue;
				}
				stack = GetItemStack(item);
				ItemMeta stackdata = stack.getItemMeta();
				lore.add("��a��û�����ң����Ҹ�ʲô");
				stackdata.setLore(lore);
				lore.clear();
				stackdata.setDisplayName("��2��չʾ");
				stack.setItemMeta(stackdata);
				gui.setItem(b, stack);
				a++;
			}
		    
	        datas.setDisplayName("��7�齱��d��");
	        glasss.setDurability((short) 14);
	        lore.add("��8�齱һʱˬ��a����2һֱ�齱һֱˬ");
	        datas.setLore(lore);
			datas.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
	        glasss.setItemMeta(datas);
	        lore.clear();
	        
			Random random = new Random();
			int get = random.nextInt(54);
			String item = lists.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(size);
				item = list.get(get).split(":")[1];
			}
			ItemStack items = GetItemStack(item);
			
			gui.setItem(Integer.parseInt(lists.get(get).split(":")[0]),items);
			
			if(Integer.parseInt(lists.get(get).split(":")[0])!=8&&Integer.parseInt(lists.get(get).split(":")[0])!=17&&Integer.parseInt(lists.get(get).split(":")[0])!=26&&Integer.parseInt(lists.get(get).split(":")[0])!=35&&Integer.parseInt(lists.get(get).split(":")[0])!=44&&Integer.parseInt(lists.get(get).split(":")[0])!=53) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])+1, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])!=0&&Integer.parseInt(lists.get(get).split(":")[0])%9!=0) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])-1, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])>8) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])-9, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])<size-9) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])+9, glasss);
			}
		    player.openInventory(gui);
		}

		public static void embellishmentwing(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	
		    Inventory gui = Bukkit.createInventory(null, 54, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
			List<String> list = config2.getStringList("Info."+name+".data");
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
			int size=54;
			if(c==9) {
				size=45;
				gui = Bukkit.createInventory(null, 45, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
				if(d==9) {
					size=36;
					gui = Bukkit.createInventory(null, 36, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
					if(e==9) {
						size=27;
						gui = Bukkit.createInventory(null, 27, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
						if(f==9) {
							size=18;
							gui = Bukkit.createInventory(null, 18, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
							if(g==9) {
								size=9;
								gui = Bukkit.createInventory(null, 9, "��6�齱���c��"+config2.getString("Info."+name+".color")+name+"��a������");
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
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemStack glasss = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
			ItemMeta datas = glasss.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> lists = config2.getStringList("Info."+name+".data");
		    
	        int i=0;
	        while(i<size) {
	        data.setDisplayName("��f��ȡ��Ľ����ɣ�");
	        lore.add("��f�����a���");
	        data.setLore(lore);
	        glass.setDurability((short) 8);
	        glass.setItemMeta(data);
	        lore.clear();
	        gui.setItem(i, glass);
	        i++;
	        }
	        i=0;
	        
			while(a<=list.size()-1) {
				int b = Integer.parseInt(list.get(a).split(":")[0]);
				String item = list.get(a).split(":")[1];
				ItemStack stack= null;
				if(item.equals("null")) {
					a++;
					continue;
				}
				stack = GetItemStack(item);
				ItemMeta stackdata = stack.getItemMeta();
				lore.add("��a��û�����ң����Ҹ�ʲô");
				stackdata.setLore(lore);
				lore.clear();
				stackdata.setDisplayName("��2��չʾ");
				stack.setItemMeta(stackdata);
				gui.setItem(b, stack);
				a++;
			}
			
	        datas.setDisplayName("��7��ȡ��Ľ����ɣ�");
	        lore.add("��8�����2���");
	        datas.setLore(lore);
	        datas.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
	        glasss.setDurability((short) 14);
	        glasss.setItemMeta(datas);
	        lore.clear();
		    
			Random random = new Random();
			int get = random.nextInt(54);
			String item = lists.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(size);
				item = list.get(get).split(":")[1];
			}
			Preset.location=get;
			gui.setItem(Integer.parseInt(lists.get(get).split(":")[0]), GetItemStack(item));
			
			if(Integer.parseInt(lists.get(get).split(":")[0])!=8&&Integer.parseInt(lists.get(get).split(":")[0])!=17&&Integer.parseInt(lists.get(get).split(":")[0])!=26&&Integer.parseInt(lists.get(get).split(":")[0])!=35&&Integer.parseInt(lists.get(get).split(":")[0])!=44&&Integer.parseInt(lists.get(get).split(":")[0])!=53) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])+1, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])!=0&&Integer.parseInt(lists.get(get).split(":")[0])%9!=0) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])-1, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])>8) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])-9, glasss);
			}
			if(Integer.parseInt(lists.get(get).split(":")[0])<size-9) {
				gui.setItem(Integer.parseInt(lists.get(get).split(":")[0])+9, glasss);
			}
			
		    player.openInventory(gui);
		}

		public static void gradientwinging(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
	
		    
			Inventory gui = Bukkit.createInventory(null, 45, "��c���ھ�����a����"+config2.getString("Info."+name+".color")+name+"��d��");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
	        ArrayList<String> lore = new ArrayList<String>();
	        List<String> list = config2.getStringList("Info."+name+".data");
	        
	        int i=0;
	        while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
	        data.setDisplayName("��"+color+"��c������4�齱��"+colors+"��");
	        lore.add("��"+color+"��һ����������Ħ��");
	        lore.add("��"+colors+"�齱ing");
	        glass.setDurability((short) durability);
	        data.setLore(lore);
	        glass.setItemMeta(data);
	        lore.clear();
	        gui.setItem(i, glass);
	        i++;
	        }
	        i=0;

			Random random = new Random();
			int get = random.nextInt(10);
			Boolean abc = false;
		    if(!Main.number.containsKey(player.getName())) {
		    	Main.number.put(player.getName(), get);
		    }
		    int start = Main.number.get(player.getName());
		    
	        int a=1;
	        while(a<=9) {
	        get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			
			if(a==1) {
			if(0+start==9) {
				gui.setItem(0, GetItemStack(item));
			}else
			gui.setItem(0+start, GetItemStack(item));
			}
			else
			if(a==2) {
			if(9+start==18) {
				gui.setItem(9, GetItemStack(item));
			}else
			gui.setItem(9+start, GetItemStack(item));
			}
			else
			if(a==3) {
			if(10+start==18) {
				gui.setItem(9, GetItemStack(item));
			}else if(10+start==19) {
				gui.setItem(10, GetItemStack(item));
			}else
			gui.setItem(10+start, GetItemStack(item));
			}
			else
			if(a==4) {
			if(18+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else
			gui.setItem(18+start, GetItemStack(item));
			}
			else
			if(a==5) {
			if(19+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else if(19+start==28) {
				gui.setItem(19, GetItemStack(item));
			}else
			gui.setItem(19+start, GetItemStack(item));
			}
			else
			if(a==6) {
			if(20+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else if(20+start==28) {
				gui.setItem(19, GetItemStack(item));
			}else if(20+start==29) {
				gui.setItem(20, GetItemStack(item));
				abc=true;
			}else 
			gui.setItem(20+start, GetItemStack(item));
			}
			else
			if(a==7) {
			if(27+start==36) {
				gui.setItem(27, GetItemStack(item));
			}else
			gui.setItem(27+start, GetItemStack(item));
			}
			else
			if(a==8) {
			if(28+start==36) {
				gui.setItem(27, GetItemStack(item));
			}else if(28+start==37) {
				gui.setItem(28, GetItemStack(item));
			}else
			gui.setItem(28+start, GetItemStack(item));
			}
			else
			if(a==9) {
			if(36+start==45) {
				gui.setItem(36, GetItemStack(item));
			}else
			gui.setItem(36+start, GetItemStack(item));
			}
			a++;
	        }
	        a=0;
	        if(abc) {
	        	Main.number.put(player.getName(), 1);
	        }else {
		    	Main.number.put(player.getName(), ++start);
	        }
		    player.openInventory(gui);
		}

		public static void gradientwing(Player player,String name) {
		    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
		    File file1=new File(config.getDataFolder(),"data.yml");
		    FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
		
			Inventory gui = Bukkit.createInventory(null, 45, "��c�齱��6�䣺"+config2.getString("Info."+name+".color")+name+"��c����������");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
	        ArrayList<String> lore = new ArrayList<String>();
	        List<String> list = config2.getStringList("Info."+name+".data");
	        
	        int i=0;
	        while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
	        data.setDisplayName("��"+color+"��ȡ���ս��Ʒ�ɣ�");
	        lore.add("��"+color+"���������"+colors+"���");
	        glass.setDurability((short) durability);
	        data.setLore(lore);
	        glass.setItemMeta(data);
	        lore.clear();
	        gui.setItem(i, glass);
	        i++;
	        }
	        i=0;
			Random random = new Random();
			int get = random.nextInt(10);
		    if(!Main.number.containsKey(player.getName())) {
		    	Main.number.put(player.getName(), get);
		    }
		    int start = Main.number.get(player.getName());
		    
		    int a=1;
	        while(a<=9) {
	        get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			
			if(a==1) {
			if(0+start==9) {
				gui.setItem(0, GetItemStack(item));
			}else
			gui.setItem(0+start, GetItemStack(item));
			}
			else
			if(a==2) {
			if(9+start==18) {
				gui.setItem(9, GetItemStack(item));
			}else
			gui.setItem(9+start, GetItemStack(item));
			}
			else
			if(a==3) {
			if(10+start==18) {
				gui.setItem(9, GetItemStack(item));
			}else if(10+start==19) {
				gui.setItem(10, GetItemStack(item));
			}else
			gui.setItem(10+start, GetItemStack(item));
			}
			else
			if(a==4) {
			if(18+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else
			gui.setItem(18+start, GetItemStack(item));
			}
			else
			if(a==5) {
			if(19+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else if(19+start==28) {
				gui.setItem(19, GetItemStack(item));
			}else
			gui.setItem(19+start, GetItemStack(item));
			}
			else
			if(a==6) {
			if(20+start==27) {
				gui.setItem(18, GetItemStack(item));
			}else if(20+start==28) {
				gui.setItem(19, GetItemStack(item));
			}else if(20+start==29) {
				gui.setItem(20, GetItemStack(item));
			}else 
			gui.setItem(20+start, GetItemStack(item));
			}
			else
			if(a==7) {
			if(27+start==36) {
				gui.setItem(27, GetItemStack(item));
			}else
			gui.setItem(27+start, GetItemStack(item));
			}
			else
			if(a==8) {
			if(28+start==36) {
				gui.setItem(27, GetItemStack(item));
			}else if(28+start==37) {
				gui.setItem(28, GetItemStack(item));
			}else
			gui.setItem(28+start, GetItemStack(item));
			}
			else
			if(a==9) {
			if(36+start==45) {
				gui.setItem(36, GetItemStack(item));
			}else
			gui.setItem(36+start, GetItemStack(item));
			}
			a++;
	        }
	        a=0;
		    Main.number.remove(player.getName());
		    player.openInventory(gui);
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
