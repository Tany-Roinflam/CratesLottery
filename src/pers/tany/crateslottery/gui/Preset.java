package pers.tany.crateslottery.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pers.tany.crateslottery.CommonlyWay;
import pers.tany.crateslottery.Other;

public class Preset {
	public static int location = 0;
	static HashMap<String, Integer> number = new HashMap<String, Integer>();
	static HashMap<String, int[]> item = new HashMap<String, int[]>();
	static HashMap<String, Boolean> law = new HashMap<String, Boolean>();
	public static void winging(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("§"+color+"抽奖§"+colors+"中");
        lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
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
		gui.setItem(22, CommonlyWay.GetItemStack(item));
		
        player.openInventory(gui);
	}
	
	public static void wing(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("§"+color+"领取你的奖励吧！");
        lore.add("§"+color+"开箱§"+colors+"结果");
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
		gui.setItem(22, CommonlyWay.GetItemStack(item));
        player.openInventory(gui);
	}
	
	public static void ninewinging(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
        lore.add("§"+color+"搏一搏，单车变摩托");
        lore.add("§"+colors+"抽奖ing");
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
		gui.setItem(12, CommonlyWay.GetItemStack(item));
		else
		if(location==2)
		gui.setItem(13, CommonlyWay.GetItemStack(item));
		else
		if(location==3)
		gui.setItem(14, CommonlyWay.GetItemStack(item));
		else
		if(location==4)
		gui.setItem(21, CommonlyWay.GetItemStack(item));
		else
		if(location==5)
		gui.setItem(22, CommonlyWay.GetItemStack(item));
		else
		if(location==6)
		gui.setItem(23, CommonlyWay.GetItemStack(item));
		else
		if(location==7)
		gui.setItem(30, CommonlyWay.GetItemStack(item));
		else
		if(location==8)
		gui.setItem(31, CommonlyWay.GetItemStack(item));
		else
		if(location==9)
		gui.setItem(32, CommonlyWay.GetItemStack(item));
		number++;
		location++;
        }
        player.openInventory(gui);
	}
	
	public static void ninewing(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("§"+color+"领取你的战利品吧！");
        lore.add("§"+color+"九连开箱§"+colors+"结果");
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
		gui.setItem(12, CommonlyWay.GetItemStack(item));
		else
		if(location==2)
		gui.setItem(13, CommonlyWay.GetItemStack(item));
		else
		if(location==3)
		gui.setItem(14, CommonlyWay.GetItemStack(item));
		else
		if(location==4)
		gui.setItem(21, CommonlyWay.GetItemStack(item));
		else
		if(location==5)
		gui.setItem(22, CommonlyWay.GetItemStack(item));
		else
		if(location==6)
		gui.setItem(23, CommonlyWay.GetItemStack(item));
		else
		if(location==7)
		gui.setItem(30, CommonlyWay.GetItemStack(item));
		else
		if(location==8)
		gui.setItem(31, CommonlyWay.GetItemStack(item));
		else
		if(location==9)
		gui.setItem(32, CommonlyWay.GetItemStack(item));
		number++;
		location++;
        }
		
        player.openInventory(gui);
	}
	
	public static void randomwinging(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = Other.data.getStringList("Info."+name+".data");
	    
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
	    data.setDisplayName("§"+color+"抽奖§"+colors+"中");
	    lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
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
		gui.setItem(get, CommonlyWay.GetItemStack(item));
		
	    player.openInventory(gui);
	}

	public static void randomwing(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
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
        data.setDisplayName("§"+color+"领取你的奖励吧！");
        lore.add("§"+color+"开箱§"+colors+"结果");
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
		gui.setItem(get, CommonlyWay.GetItemStack(item));
		
        player.openInventory(gui);
	}
	
	public static void randomninewinging(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
        int i=0;
        while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
        data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
        lore.add("§"+color+"搏一搏，单车变摩托");
        lore.add("§"+colors+"抽奖ing");
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
		gui.setItem(get, CommonlyWay.GetItemStack(item));
		location.add(get);
		number++;
        }
		location.clear();
        player.openInventory(gui);
	}
	
	public static void randomninewing(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        List<String> list = Other.data.getStringList("Info."+name+".data");
        
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
        data.setDisplayName("§"+color+"领取你的战利品吧！");
        lore.add("§"+color+"九连开箱§"+colors+"结果");
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
		gui.setItem(get, CommonlyWay.GetItemStack(item));
		location.add(get);
		number++;
        }
		location.clear();
        player.openInventory(gui);
	}
	
	public static void orderwinging(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = Other.data.getStringList("Info."+name+".data");
	    
	    int i=0;
	    while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
	    data.setDisplayName("§"+color+"抽奖§"+colors+"中");
	    lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
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
		gui.setItem(4+get*9, CommonlyWay.GetItemStack(item));
		
	    player.openInventory(gui);
	}

		public static void orderwing(Player player,String name) {
		Inventory gui = Bukkit.createInventory(null, 45, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta data = glass.getItemMeta();
	    ArrayList<String> lore = new ArrayList<String>();
	    List<String> list = Other.data.getStringList("Info."+name+".data");
	    
	    int i=0;
	    while(i<=44) {
		Random random = new Random();
		short durability = (short) random.nextInt(16);
		while(durability==8) {
			durability = (short) random.nextInt(16);
		}
		int color = (short) random.nextInt(9)+1;
		int colors = (short) random.nextInt(9)+1;
	    data.setDisplayName("§"+color+"领取你的奖励吧！");
	    lore.add("§"+color+"开箱§"+colors+"结果");
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
		gui.setItem(4+get*9, CommonlyWay.GetItemStack(item));
		
	    player.openInventory(gui);
	}

		public static void orderninewinging(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 45, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
		    data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		    lore.add("§"+color+"搏一搏，单车变摩托");
		    lore.add("§"+colors+"抽奖ing");
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
			gui.setItem(number-1+9*location, CommonlyWay.GetItemStack(item));
			number++;
		    }
		    player.openInventory(gui);
		}

		public static void orderninewing(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 45, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
			
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
		    data.setDisplayName("§"+color+"领取你的战利品吧！");
		    lore.add("§"+color+"九连开箱§"+colors+"结果");
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
			gui.setItem(number-1+9*location, CommonlyWay.GetItemStack(item));
			number++;
		    }
			
		    player.openInventory(gui);
		}

		public static void embellishmentwinging(Player player,String name) {
		    Inventory gui = Bukkit.createInventory(null, 54, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
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
			int size=54;
			if(c==9) {
				size=45;
				gui = Bukkit.createInventory(null, 45, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
				if(d==9) {
					size=36;
					gui = Bukkit.createInventory(null, 36, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
					if(e==9) {
						size=27;
						gui = Bukkit.createInventory(null, 27, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
						if(f==9) {
							size=18;
							gui = Bukkit.createInventory(null, 18, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
							if(g==9) {
								size=9;
								gui = Bukkit.createInventory(null, 9, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
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
		    List<String> lists = Other.data.getStringList("Info."+name+".data");
	        int i=0;
	        while(i<size) {
	        data.setDisplayName("§f抽奖§d中");
	        lore.add("§f抽奖一时爽§a，§2一直抽奖一直爽");
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
				stack = CommonlyWay.GetItemStack(item);
				ItemMeta stackdata = stack.getItemMeta();
				lore.add("§a又没抽中我，看我干什么");
				stackdata.setLore(lore);
				lore.clear();
				stackdata.setDisplayName("§2仅展示");
				stack.setItemMeta(stackdata);
				gui.setItem(b, stack);
				a++;
			}
		    
	        datas.setDisplayName("§7抽奖§d中");
	        glasss.setDurability((short) 14);
	        lore.add("§8抽奖一时爽§a，§2一直抽奖一直爽");
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
			ItemStack items = CommonlyWay.GetItemStack(item);
			
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
		    Inventory gui = Bukkit.createInventory(null, 54, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
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
			int size=54;
			if(c==9) {
				size=45;
				gui = Bukkit.createInventory(null, 45, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
				if(d==9) {
					size=36;
					gui = Bukkit.createInventory(null, 36, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
					if(e==9) {
						size=27;
						gui = Bukkit.createInventory(null, 27, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
						if(f==9) {
							size=18;
							gui = Bukkit.createInventory(null, 18, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
							if(g==9) {
								size=9;
								gui = Bukkit.createInventory(null, 9, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
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
		    List<String> lists = Other.data.getStringList("Info."+name+".data");
		    
	        int i=0;
	        while(i<size) {
	        data.setDisplayName("§f领取你的奖励吧！");
	        lore.add("§f开箱§a结果");
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
				stack = CommonlyWay.GetItemStack(item);
				ItemMeta stackdata = stack.getItemMeta();
				lore.add("§a又没抽中我，看我干什么");
				stackdata.setLore(lore);
				lore.clear();
				stackdata.setDisplayName("§2仅展示");
				stack.setItemMeta(stackdata);
				gui.setItem(b, stack);
				a++;
			}
			
	        datas.setDisplayName("§7领取你的奖励吧！");
	        lore.add("§8开箱§2结果");
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
			gui.setItem(Integer.parseInt(lists.get(get).split(":")[0]), CommonlyWay.GetItemStack(item));
			
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
			Inventory gui = Bukkit.createInventory(null, 45, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
	        ArrayList<String> lore = new ArrayList<String>();
	        List<String> list = Other.data.getStringList("Info."+name+".data");
	        
	        int i=0;
	        while(i<=44) {
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		        lore.add("§"+color+"搏一搏，单车变摩托");
		        lore.add("§"+colors+"抽奖ing");
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
		    if(!number.containsKey(player.getName())) {
		    	number.put(player.getName(), get);
		    }
		    int start = number.get(player.getName());
		    
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
				gui.setItem(0, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(0+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==2) {
			if(9+start==18) {
				gui.setItem(9, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(9+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==3) {
			if(10+start==18) {
				gui.setItem(9, CommonlyWay.GetItemStack(item));
			}else if(10+start==19) {
				gui.setItem(10, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(10+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==4) {
			if(18+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(18+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==5) {
			if(19+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else if(19+start==28) {
				gui.setItem(19, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(19+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==6) {
			if(20+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else if(20+start==28) {
				gui.setItem(19, CommonlyWay.GetItemStack(item));
			}else if(20+start==29) {
				gui.setItem(20, CommonlyWay.GetItemStack(item));
				abc=true;
			}else 
			gui.setItem(20+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==7) {
			if(27+start==36) {
				gui.setItem(27, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(27+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==8) {
			if(28+start==36) {
				gui.setItem(27, CommonlyWay.GetItemStack(item));
			}else if(28+start==37) {
				gui.setItem(28, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(28+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==9) {
			if(36+start==45) {
				gui.setItem(36, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(36+start, CommonlyWay.GetItemStack(item));
			}
			a++;
	        }
	        a=0;
	        if(abc) {
	        	number.put(player.getName(), 1);
	        }else {
		    	number.put(player.getName(), ++start);
	        }
		    player.openInventory(gui);
		}

		public static void gradientwing(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 45, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
	        ArrayList<String> lore = new ArrayList<String>();
	        List<String> list = Other.data.getStringList("Info."+name+".data");
	        
	        int i=0;
	        while(i<=44) {
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
	        data.setDisplayName("§"+color+"领取你的战利品吧！");
	        lore.add("§"+color+"九连开箱§"+colors+"结果");
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
		    if(!number.containsKey(player.getName())) {
		    	number.put(player.getName(), get);
		    }
		    int start = number.get(player.getName());
		    
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
				gui.setItem(0, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(0+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==2) {
			if(9+start==18) {
				gui.setItem(9, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(9+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==3) {
			if(10+start==18) {
				gui.setItem(9, CommonlyWay.GetItemStack(item));
			}else if(10+start==19) {
				gui.setItem(10, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(10+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==4) {
			if(18+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(18+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==5) {
			if(19+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else if(19+start==28) {
				gui.setItem(19, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(19+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==6) {
			if(20+start==27) {
				gui.setItem(18, CommonlyWay.GetItemStack(item));
			}else if(20+start==28) {
				gui.setItem(19, CommonlyWay.GetItemStack(item));
			}else if(20+start==29) {
				gui.setItem(20, CommonlyWay.GetItemStack(item));
			}else 
			gui.setItem(20+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==7) {
			if(27+start==36) {
				gui.setItem(27, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(27+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==8) {
			if(28+start==36) {
				gui.setItem(27, CommonlyWay.GetItemStack(item));
			}else if(28+start==37) {
				gui.setItem(28, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(28+start, CommonlyWay.GetItemStack(item));
			}
			else
			if(a==9) {
			if(36+start==45) {
				gui.setItem(36, CommonlyWay.GetItemStack(item));
			}else
			gui.setItem(36+start, CommonlyWay.GetItemStack(item));
			}
			a++;
	        }
	        a=0;
		    number.remove(player.getName());
		    player.openInventory(gui);
		}
		
		public static void repeatedlywinging(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 54, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=53) {
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
			    data.setDisplayName("§"+color+"抽奖§"+colors+"中");
			    lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
			    glass.setDurability((short) durability);
			    data.setLore(lore);
			    glass.setItemMeta(data);
			    lore.clear();
			    gui.setItem(i, glass);
			    i++;
		    }
		    i=0;
		    if(!number.containsKey(player.getName())&&!law.containsKey(player.getName())) {
				Random random = new Random();
			    number.put(player.getName(), random.nextInt(54));
			    if(random.nextInt(2)==0) {
			    	if(number.get(player.getName())==53)
			    		number.put(player.getName(), 52);
			    	law.put(player.getName(), true);
			    } else {
			    	if(number.get(player.getName())==0)
			    		number.put(player.getName(), 1);
				    law.put(player.getName(), false);
			    }
		    }

		    int location = number.get(player.getName());
			Random random = new Random();
			int get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			gui.setItem(location, CommonlyWay.GetItemStack(item));
			
			if(law.get(player.getName())) {
				location++;
				if(location==53) {
					law.put(player.getName(), false);
				}
				number.put(player.getName(), location);
			} else {
				location--;
				if(location==0) {
					law.put(player.getName(), true);
				}
				number.put(player.getName(), location);
			}
		    player.openInventory(gui);
		}
		
		public static void repeatedlywing(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 54, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=53) {
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"领取你的奖励吧！");
		        lore.add("§"+color+"开箱§"+colors+"结果");
		        glass.setDurability((short) durability);
		        data.setLore(lore);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
			    i++;
		    }
		    i=0;
		    if(!number.containsKey(player.getName())&&!law.containsKey(player.getName())) {
				Random random = new Random();
			    number.put(player.getName(), random.nextInt(54));
			    law.put(player.getName(), true);
		    }

		    int location = number.get(player.getName());
			Random random = new Random();
			int get = random.nextInt(54);
			String item = list.get(get).split(":")[1];
			while(item.equals("null")) {
				get = random.nextInt(54);
				item = list.get(get).split(":")[1];
			}
			Preset.location=get;
			gui.setItem(location, CommonlyWay.GetItemStack(item));
			
			law.remove(player.getName());
			number.remove(player.getName());
			
		    player.openInventory(gui);
		}
		
		public static void repeatedlyninewinging(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 54, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
	        int i=0;
	        while(i<=53) {
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		        lore.add("§"+color+"搏一搏，单车变摩托");
		        lore.add("§"+colors+"抽奖ing");
		        glass.setDurability((short) durability);
		        data.setLore(lore);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
		        i++;
	        }
	        i=0;
		    if(!number.containsKey(player.getName())&&!law.containsKey(player.getName())) {
				Random random = new Random();
			    number.put(player.getName(), random.nextInt(46));
			    if(random.nextInt(2)==0) {
			    	if(number.get(player.getName())>=45)
			    		number.put(player.getName(), 44);
			    	law.put(player.getName(), true);
			    } else {
			    	if(number.get(player.getName())<=0) {
			    		number.put(player.getName(), 1);
			    	}
				    law.put(player.getName(), false);
			    }
		    }
			int location = number.get(player.getName());
		    for(int a=0;a<9;a++) {
				Random random = new Random();
				int get = random.nextInt(54);
				String item = list.get(get).split(":")[1];
				while(item.equals("null")) {
					get = random.nextInt(54);
					item = list.get(get).split(":")[1];
				}
//			    Bukkit.broadcastMessage(number.get(player.getName())+"+"+a+"="+(location+a));
				gui.setItem(location+a, CommonlyWay.GetItemStack(item));
		    }
		    
			
			if(law.get(player.getName())) {
				location++;
				if(location==45) {
					law.put(player.getName(), false);
				}
				number.put(player.getName(), location);
			} else {
				location--;
				if(location==0) {
				law.put(player.getName(), true);
				}
				number.put(player.getName(), location);
			}
		    player.openInventory(gui);
		}
		
		public static void repeatedlyninewing(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 54, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
	        int i=0;
	        while(i<=53) {
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"领取你的战利品吧！");
		        lore.add("§"+color+"九连开箱§"+colors+"结果");
		        glass.setDurability((short) durability);
		        data.setLore(lore);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
		        i++;
	        }
	        i=0;
		    if(!number.containsKey(player.getName())&&!law.containsKey(player.getName())) {
				Random random = new Random();
			    number.put(player.getName(), random.nextInt(46));
			    if(random.nextInt(2)==0) {
			    	if(number.get(player.getName())==45)
			    		number.put(player.getName(), 44);
			    	law.put(player.getName(), true);
			    } else {
			    	if(number.get(player.getName())==0)
			    		number.put(player.getName(), 1);
				    law.put(player.getName(), false);
			    }
		    }
		    
		    int location = number.get(player.getName());
		    for(int a=0;a<9;a++) {
				Random random = new Random();
				int get = random.nextInt(54);
				String item = list.get(get).split(":")[1];
				while(item.equals("null")) {
					get = random.nextInt(54);
					item = list.get(get).split(":")[1];
				}
				gui.setItem(location+a, CommonlyWay.GetItemStack(item));
		    }

			
			law.remove(player.getName());
			number.remove(player.getName());
			
		    player.openInventory(gui);
		}
		
		public static void showninewinging(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 27, "§c正在九连§a开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=26) {
			    if(i>8&&i<18) {
			    	i++;
			    	continue;
			    }
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		        lore.add("§"+color+"搏一搏，单车变摩托");
		        lore.add("§"+colors+"抽奖ing");
		        glass.setDurability((short) durability);
		        data.setLore(lore);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
		        i++;
		    }
		    i=0;
		    if(!item.containsKey(player.getName())&&!number.containsKey(player.getName())) {
				int[] numbers = new int[9];
				Random random = new Random();
		    	for(i=0;i<=8;i++) {
					int get = random.nextInt(54);
					while(list.get(get).split(":")[1].equals("null")) {
						get = random.nextInt(54);
					}
					numbers[i] = get;
		    	}
		    	if(!law.containsKey(player.getName())){
			    	number.put(player.getName(), random.nextInt(9));
		    	} else {
		    		number.put(player.getName(), 0);
		    	}
		    	law.put(player.getName(), true);
		    	item.put(player.getName(), numbers);
		    }
		    int location = number.get(player.getName());
		    int b = 0;
		    for(int a:item.get(player.getName())) {
		    	ItemStack item = CommonlyWay.GetItemStack(list.get(a).split(":")[1]);
		    	ItemMeta itemdata = item.getItemMeta();
				Random random = new Random();
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        itemdata.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		        lore.add("§"+color+"搏一搏，单车变摩托");
		        lore.add("§"+colors+"抽奖ing");
		        itemdata.setLore(lore);
		        item.setItemMeta(itemdata);
		        lore.clear();
		    	gui.setItem(9+b, item);
		    	b++;
		    }
		    ItemStack chooseglass = new ItemStack(Material.THIN_GLASS);
		    ItemMeta chooseglassmeta = chooseglass.getItemMeta();
			Random random = new Random();
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
			chooseglassmeta.setDisplayName("§"+color+"§c九连§4抽奖§"+colors+"中");
		    lore.add("§"+color+"搏一搏，单车变摩托");
		    lore.add("§"+colors+"抽奖ing");
		    chooseglassmeta.setLore(lore);
		    chooseglass.setItemMeta(chooseglassmeta);
		    gui.setItem(0+location, chooseglass);
		    gui.setItem(18+location, chooseglass);
		    
		    location++;
		    if(location>=9) {
		    	number.remove(player.getName());
		    	item.remove(player.getName());
		    } else {
		    	number.put(player.getName(), location);
		    }
		    player.openInventory(gui);
		}

		public static void showninewing(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 27, "§c抽奖§6箱："+Other.data.getString("Info."+name+".color")+name+"§c九连开箱结果");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=26) {
		    if(i>8&&i<18) {
		    	i++;
		    	continue;
		    }
			Random random = new Random();
			short durability = (short) random.nextInt(16);
			while(durability==8) {
				durability = (short) random.nextInt(16);
			}
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
		    data.setDisplayName("§"+color+"领取你的战利品吧！");
		    lore.add("§"+color+"九连开箱§"+colors+"结果");
		    glass.setDurability((short) durability);
		    data.setLore(lore);
		    glass.setItemMeta(data);
		    lore.clear();
		    gui.setItem(i, glass);
		    i++;
		    }
		    i=0;
		    if(!item.containsKey(player.getName())&&!number.containsKey(player.getName())) {
				int[] numbers = new int[9];
				Random random = new Random();
		    	for(i=0;i<=8;i++) {
					int get = random.nextInt(54);
					while(list.get(get).split(":")[1].equals("null")) {
						get = random.nextInt(54);
					}
					numbers[i] = get;
		    	}
		    	if(!law.containsKey(player.getName())){
			    	number.put(player.getName(), random.nextInt(9));
		    	} else {
		    		number.put(player.getName(), 0);
		    	}
		    	law.put(player.getName(), true);
		    	item.put(player.getName(), numbers);
		    }
		    int b = 0;
		    int location = number.get(player.getName());
		    for(int a:item.get(player.getName())) {
		    	ItemStack item = CommonlyWay.GetItemStack(list.get(a).split(":")[1]);
		    	gui.setItem(9+b, item);
		    	b++;
		    }
		    ItemStack chooseglass = new ItemStack(Material.THIN_GLASS);
		    ItemMeta chooseglassmeta = chooseglass.getItemMeta();
			Random random = new Random();
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
			chooseglassmeta.setDisplayName("§"+color+"领取你的战利品吧！");
		    lore.add("§"+color+"九连开箱§"+colors+"结果");
		    chooseglassmeta.setLore(lore);
		    chooseglass.setItemMeta(chooseglassmeta);
		    gui.setItem(0+location, chooseglass);
		    gui.setItem(18+location, chooseglass);
		    
		    law.remove(player.getName());
		    item.remove(player.getName());
		    number.remove(player.getName());
		    player.openInventory(gui);
		}

		public static void showwinging(Player player,String name) {
			Inventory gui = Bukkit.createInventory(null, 27, "§a正在开箱"+Other.data.getString("Info."+name+".color")+name+"§d中");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=26) {
			    if(i>8&&i<18) {
			    	i++;
			    	continue;
			    }
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"抽奖§"+colors+"中");
		        lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
		        data.setLore(lore);
		        glass.setDurability((short) durability);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
		        i++;
		    }
		    i=0;
		    if(!item.containsKey(player.getName())&&!number.containsKey(player.getName())) {
				int[] numbers = new int[9];
				Random random = new Random();
		    	for(i=0;i<=8;i++) {
					int get = random.nextInt(54);
					while(list.get(get).split(":")[1].equals("null")) {
						get = random.nextInt(54);
					}
					numbers[i] = get;
		    	}
		    	if(!law.containsKey(player.getName())){
			    	number.put(player.getName(), random.nextInt(9));
		    	} else {
		    		number.put(player.getName(), 0);
		    	}
		    	law.put(player.getName(), true);
		    	item.put(player.getName(), numbers);
		    }
		    int location = number.get(player.getName());
		    int b = 0;
		    for(int a:item.get(player.getName())) {
		    	ItemStack item = CommonlyWay.GetItemStack(list.get(a).split(":")[1]);
		    	ItemMeta itemdata = item.getItemMeta();
		        data.setDisplayName("§f抽奖§d中");
		        lore.add("§f抽奖一时爽§a，§2一直抽奖一直爽");
		        itemdata.setLore(lore);
		        item.setItemMeta(itemdata);
		        lore.clear();
		    	gui.setItem(9+b, item);
		    	b++;
		    }
		    ItemStack chooseglass = new ItemStack(Material.THIN_GLASS);
		    ItemMeta chooseglassmeta = chooseglass.getItemMeta();
			Random random = new Random();
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
			chooseglassmeta.setDisplayName("§"+color+"抽奖§"+colors+"中");
	        lore.add("§"+color+"抽奖一时爽§a，§"+colors+"一直抽奖一直爽");
	        chooseglassmeta.setLore(lore);
	        chooseglass.setItemMeta(chooseglassmeta);
	        lore.clear();
	        gui.setItem(0+location, chooseglass);
	        gui.setItem(18+location, chooseglass);
	        
	        location++;
	        if(location>=9) {
	        	number.remove(player.getName());
	        	item.remove(player.getName());
	        } else {
	        	number.put(player.getName(), location);
	        }
		    player.openInventory(gui);
		}

		public static void showwing(Player player,String name) {
			 Inventory gui = Bukkit.createInventory(null, 27, "§6抽奖箱§c："+Other.data.getString("Info."+name+".color")+name+"§a开箱结果");
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta data = glass.getItemMeta();
		    ArrayList<String> lore = new ArrayList<String>();
		    List<String> list = Other.data.getStringList("Info."+name+".data");
		    
		    int i=0;
		    while(i<=26) {
			    if(i>8&&i<18) {
			    	i++;
			    	continue;
			    }
				Random random = new Random();
				short durability = (short) random.nextInt(16);
				while(durability==8) {
					durability = (short) random.nextInt(16);
				}
				int color = (short) random.nextInt(9)+1;
				int colors = (short) random.nextInt(9)+1;
		        data.setDisplayName("§"+color+"领取你的奖励吧！");
		        lore.add("§"+color+"开箱§"+colors+"结果");
		        data.setLore(lore);
		        glass.setDurability((short) durability);
		        glass.setItemMeta(data);
		        lore.clear();
		        gui.setItem(i, glass);
		        i++;
		    }
		    i=0;
		    if(!item.containsKey(player.getName())&&!number.containsKey(player.getName())) {
				int[] numbers = new int[9];
				Random random = new Random();
		    	for(i=0;i<=8;i++) {
					int get = random.nextInt(54);
					while(list.get(get).split(":")[1].equals("null")) {
						get = random.nextInt(54);
					}
					numbers[i] = get;
		    	}
		    	if(!law.containsKey(player.getName())){
			    	number.put(player.getName(), random.nextInt(9));
		    	} else {
		    		number.put(player.getName(), 0);
		    	}
		    	law.put(player.getName(), true);
		    	item.put(player.getName(), numbers);
		    }
		    int b = 0;
		    int location = number.get(player.getName());
		    for(int a:item.get(player.getName())) {
		    	ItemStack item = CommonlyWay.GetItemStack(list.get(a).split(":")[1]);
		    	if(b!=location) {
					ItemMeta stackdata = item.getItemMeta();
					lore.add("§a又没抽中我，看我干什么");
					stackdata.setLore(lore);
					lore.clear();
					stackdata.setDisplayName("§2仅展示");
					item.setItemMeta(stackdata);
		    	} else {
		    		Preset.location=location;
		    	}
		    	gui.setItem(9+b, item);
		    	b++;
		    }
		    ItemStack chooseglass = new ItemStack(Material.THIN_GLASS);
		    ItemMeta chooseglassmeta = chooseglass.getItemMeta();
			Random random = new Random();
			int color = (short) random.nextInt(9)+1;
			int colors = (short) random.nextInt(9)+1;
			chooseglassmeta.setDisplayName("§"+color+"领取你的奖励吧！");
	        lore.add("§"+color+"开箱§"+colors+"结果");
	        chooseglassmeta.setLore(lore);
	        chooseglass.setItemMeta(chooseglassmeta);
	        lore.clear();
	        gui.setItem(0+location, chooseglass);
	        gui.setItem(18+location, chooseglass);
	        
	        law.remove(player.getName());
	        item.remove(player.getName());
	        number.remove(player.getName());
		    player.openInventory(gui);
		}
}
