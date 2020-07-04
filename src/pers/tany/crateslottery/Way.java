package pers.tany.crateslottery;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import com.comphenix.protocol.utility.StreamSerializer;

public class Way {
//		命令判断是否为OP
		public static boolean OpUseCommand(CommandSender player) {
			if(player.isOp())
			return false;
			else
			return true;
		}
		
//		判断玩家是否为OP
		public static boolean OpUse(Player player) {
			if(player.isOp())
			return false;
			else
			return true;
		}
		
//		判断玩家是否为OP
		public static boolean ConsoleUse(CommandSender sender) {
			if(sender instanceof Player)
			return false;
			else
			return true;
		}
		
//		ItemStack转String
		public static String GetItemData(ItemStack item) {
			String a;
			int amount = item.getAmount();
			item.setAmount(1);
			try {
			    a = new StreamSerializer().serializeItemStack(item);
			} catch (Exception e) {
			    a = null;
			}
			item.setAmount(amount);
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
		
//		获取玩家背包空格数
		public static int Backpack(Player player) {
			int a=0;
			int b=0;
			while(a<=35) {
				if(player.getInventory().getItem(a)==null) {
					b++;
				}
				a++;
			}
			return b;
		}
		
//		增加/扣除玩家的经验
		public static  void giveExp(Player player, int exp) {
			int level = player.getLevel();
			int toNextLevel = getUpgradeExp(level);
			int floatExp = (int) (player.getExp() * toNextLevel) + exp;
          	while(floatExp >= toNextLevel) {
        	  	floatExp -= toNextLevel;
        	  	toNextLevel = getUpgradeExp(++level);
          	}
          	while(floatExp < 0) {
          		floatExp += (toNextLevel = getUpgradeExp(--level));
                  	if(level < 0) 
                  		level = floatExp = 0;
          	}
          	player.setLevel(level);
          	player.setExp( (float) floatExp / toNextLevel);
          	player.setTotalExperience(getTotalExp(level) + floatExp);
	  	}
  
	  	public static int getUpgradeExp(int level) {
	  		return level < 16  ? level * 2 + 7 : level < 30 ? level * 5 - 38 : level * 9 - 158;
  		}
  
  		public static int getTotalExp(int level) {
  			return (int) (level < 17 ? level * (level + 6) : level < 31 ? level * (level * 2.5 - 40.5) + 360 : level * (level * 4.5 - 162.5) + 2220);
  		}
  		
//		给予玩家物品，如果背包满则在原地生成掉落物
		public static void GiveItem(Player player,ItemStack item) {
			if(player.getInventory().firstEmpty()==-1) {
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			} else {
				player.getInventory().addItem(item);
			}
		}
		
//  	判断交互手
  		public static boolean getInteractHand(PlayerInteractEvent evt)
  		{
  			if(!evt.getHand().equals(EquipmentSlot.HAND))
			return true;
  			else
  			return false;
  		}
  		
//  	判断放置手
  		public static boolean getPlaceHand(BlockPlaceEvent evt)
  		{
  			if(!evt.getHand().equals(EquipmentSlot.HAND))
			return true;
  			else
  			return false;
  		}
}
