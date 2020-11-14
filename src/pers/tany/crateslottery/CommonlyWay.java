package pers.tany.crateslottery;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CommonlyWay {
    //		命令判断是否为OP，不是则true
    public static boolean opUseCommand(CommandSender player) {
        return !player.isOp();
    }

    //		命令判断是否为控制台，是则true
    public static boolean consoleUse(CommandSender sender) {
        return !(sender instanceof Player);
    }

    //		序列化ItemStack
    public static String getItemData(ItemStack item) {
        String a;
        try {
            a = new StreamSerializer().serializeItemStack(item);
        } catch (Exception e) {
            a = null;
        }
        return a;
    }

    //		反序列化ItemStack
    public static ItemStack getItemStack(String data) {
        try {
            return new StreamSerializer().deserializeItemStack(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //		生成长度固定的随机字符串
    public static String createRandomString(int length) {
        String number = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) {
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                number += (char) (nextInt + random.nextInt(26));
            } else {
                number += String.valueOf(random.nextInt(10));
            }
        }
        return number;
    }

    //		获取玩家背包空格数
    public static int backpackEmptyNumber(Player player) {
        int number = 0;
        int empty = 0;
        while (number <= 35) {
            if (player.getInventory().getItem(number) == null) {
                empty++;
            }
            number++;
        }
        return empty;
    }

    //		增加/扣除玩家的经验（支持负数降级）
    public static void giveExp(Player player, int xp) {
        int level = player.getLevel();
        int toNextLevel = getUpgradeExp(level);
        int floatExp = (int) (player.getExp() * toNextLevel) + xp;
        while (floatExp >= toNextLevel) {
            floatExp -= toNextLevel;
            toNextLevel = getUpgradeExp(++level);
        }
        while (floatExp < 0) {
            floatExp += (toNextLevel = getUpgradeExp(--level));
            if (level < 0) {
                level = floatExp = 0;
            }
        }
        player.setLevel(level);
        player.setExp((float) floatExp / toNextLevel);
        player.setTotalExperience(getTotalExp(level) + floatExp);
    }

    public static int getUpgradeExp(int level) {
        return level < 16 ? level * 2 + 7 : level < 30 ? level * 5 - 38 : level * 9 - 158;
    }

    public static int getTotalExp(int level) {
        return (int) (level < 17 ? level * (level + 6) : level < 31 ? level * (level * 2.5 - 40.5) + 360 : level * (level * 4.5 - 162.5) + 2220);
    }

    //		给予玩家物品，如果背包满则在原地生成掉落物
    public static void giveItem(Player player, ItemStack item) {
        for (ItemStack i : player.getInventory().addItem(item).values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }

    //    判断玩家是否为空手，空手则true
    public static Boolean emptyItem(Player player) {
        return player.getInventory().getItemInHand() == null || player.getInventory().getItemInHand().getType() == Material.AIR;
    }


    //    返回这个物品的NBT
    public static String getItemNBT(ItemStack item) {
        Class<?> nbttagcompound;
        Class<?> itemstack;
        Method asNMSCopy;
        Method save;
        Class<?> saveitem;
        String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            saveitem = Class.forName("net.minecraft.server." + version + ".ItemStack");
            itemstack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            nbttagcompound = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
            asNMSCopy = itemstack.getMethod("asNMSCopy", ItemStack.class);
            save = saveitem.getMethod("save", nbttagcompound);

            Object tag = nbttagcompound.newInstance();
            Object nmsitem = asNMSCopy.invoke(null, item);
            Object saves = save.invoke(nmsitem, tag);

            return saves.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    //		遍历数组，替换数组字符串
    public static List<String> listReplace(List<String> list, String wasReplaced, String replace) {
        ArrayList<String> news = new ArrayList<>();
        for (String s : list) {
            news.add(s.replace(wasReplaced, replace));
        }
        return news;
    }

    //	  	判断交互手
    public static boolean getInteractHand(PlayerInteractEvent evt) {
        return evt.getHand() == null || !evt.getHand().equals(EquipmentSlot.HAND);
    }

    //	  	判断放置手
    public static boolean getPlaceHand(BlockPlaceEvent evt) {
        return evt.getHand() == null || !evt.getHand().equals(EquipmentSlot.HAND);
    }

    //		把几率%转化为真正的百分比
    public static double percentageNumber(String percentage) {
        try {
            return Double.parseDouble(percentage.replace("%", "")) / 100;
        } catch (Exception b) {
            return 9.99;
        }
    }

    //		把几率%转化为真正的百分比,并且进行乘法计算
    public static double percentage(double number, String percentage) {
        try {
            return number * (Double.parseDouble(percentage.replace("%", "")) / 100);
        } catch (Exception a) {
            return 9.99;
        }
    }

    //		把玩家传送到这个区块位置
    public static void telePortChunk(Player player, World world, Chunk chunk) {
        Location location = player.getLocation();
        location.setWorld(world);
        double x = chunk.getX() * 16;
        double z = chunk.getZ() * 16;
        location.setX(x);
        location.setZ(z);
        location.setY(world.getHighestBlockAt(location).getY());
        player.teleport(location);
    }

    //		返回这个世界的玩家数
    public static int playerNumber(World world) {
        int i = 0;
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                i++;
            }
        }
        return i;
    }

    //		返回服务器总MS
    public static double getMS() {
        String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class<?> minecraftserver = Class.forName("net.minecraft.server." + version + ".MinecraftServer");
            Method getserver = minecraftserver.getMethod("getServer");
            Class<?> c = getserver.invoke(minecraftserver).getClass();

            Field h = null;
            for (Field field : c.getFields()) {
                if (field.getType().getName().equalsIgnoreCase("[J")) {
                    h = field;
                    break;
                }
            }

            long average = 0;
            for (long b : (long[]) h.get(getserver.invoke(minecraftserver))) {
                average += b;
            }
            average /= ((long[]) h.get(getserver.invoke(minecraftserver))).length;

            return average * 1e-6;
        } catch (Exception a) {
            return 0;
        }
    }

    //		返回服务器总TPS
    public static double getTPS() {
        String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            Class<?> minecraftserver = Class.forName("net.minecraft.server." + version + ".MinecraftServer");
            Method getserver = minecraftserver.getMethod("getServer");

            Field h = null;
            for (Field field : minecraftserver.getFields()) {
                if (field.getType().getName().equalsIgnoreCase("[J")) {
                    h = field;
                    break;
                }
            }

            long average = 0;
            for (long b : (long[]) h.get(getserver.invoke(minecraftserver))) {
                average += b;
            }
            average /= ((long[]) h.get(getserver.invoke(minecraftserver))).length;

            return Math.min(1000 / (average * 1e-6), 20.0);
        } catch (Exception a) {
            return 0;
        }
    }

    //		随机一个范围的数字
    public static int randomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}