package pers.tany.crateslottery.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pers.tany.crateslottery.CommonlyWay;
import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Preset;

public class WingTask extends BukkitRunnable {
    int a = 0;
    int b = 0;
    int c = 0;
    public Player player;
    public String Crate;

    public WingTask(Player p, String s, int n) {
        player = p;
        Crate = s;
        b = n - 1;
    }

    @Override
    public void run() {
        if (c == 5) {
            c = 0;
        }
        c++;
        if (a >= b) {
            try {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            } catch (Exception a) {
                player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            }
            switch (Other.data.getString("Info." + Crate + ".type")) {
                case "normal":
                    Preset.wing(player, Crate);
                    break;
                case "random":
                    Preset.randomwing(player, Crate);
                    break;
                case "order":
                    Preset.orderwing(player, Crate);
                    break;
                case "embellishment":
                    Preset.embellishmentwing(player, Crate);
                    break;
                case "repeatedly":
                    Preset.repeatedlywing(player, Crate);
                    break;
                case "show":
                    Preset.showwing(player, Crate);
                    break;
                default:

            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WingMessage")));
            a = 0;
            b = 0;
            cancel();
            List<String> list = Other.data.getStringList("Info." + Crate + ".data");
            if (Other.data.getBoolean("Info." + Crate + ".info")) {
                if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).hasItemMeta() && CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().hasDisplayName()) {
                    if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount() == 1) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info." + Crate + ".color") + Crate).replace("[item]", CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName())));
                    } else {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info." + Crate + ".color") + Crate).replace("[item]", CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName() + "*" + CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
                    }
                } else {
                    if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount() == 1) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info." + Crate + ".color") + Crate).replace("[item]", CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getType().name())));
                    } else {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("InfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info." + Crate + ".color") + Crate).replace("[item]", CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getType().name() + "*" + CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount())));
                    }
                }
            }
            if (Other.config.getBoolean("Log")) {
                String info;
                if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).hasItemMeta() && CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().hasDisplayName()) {
                    if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount() == 1) {
                        info = CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName();
                    } else {
                        info = CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getItemMeta().getDisplayName() + "*" + CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount();
                    }
                } else {
                    if (CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount() == 1) {
                        info = CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getType().name();
                    } else {
                        info = CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getType().name() + "*" + CommonlyWay.getItemStack(list.get(Preset.location).split(":")[1]).getAmount();
                    }
                }
                SimpleDateFormat time = new SimpleDateFormat("MM月dd日 HH时mm分ss秒");
                String timegroup = time.format(new Date());
                List<String> log = new ArrayList<>(Other.log.getStringList("Log"));
                log.add("玩家" + player.getName() + "于" + timegroup + "单抽开启了" + Crate + "抽奖箱，获得了" + info);
                Other.log.set("Log", log);
                File file = new File(Main.plugin.getDataFolder(), "log.yml");
                try {
                    Other.log.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Other.data.getBoolean("Info." + Crate + ".clear")) {
                int size = player.getOpenInventory().getBottomInventory().getSize() - 1;
                int location = 0;
                while (location < size) {
                    ItemStack item = player.getOpenInventory().getItem(location);
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("领取你的奖励吧！")) {
                            location++;
                            continue;
                        }
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("§2仅展示")) {
                            location++;
                            continue;
                        }
                    }
                    list.remove(list.get(Preset.location));
                    list.add(Preset.location + ":" + "null");
                    Other.data.set("Info." + Crate + ".data", list);
                    if (Other.data.getBoolean("Info." + Crate + ".backup")) {
                        int a = 0;
                        for (String backup : list) {
                            if (!backup.split(":")[1].equals("null")) {
                                break;
                            }
                            a++;
                            if (a == list.size()) {
                                List<String> back = Other.data.getStringList("backup." + Crate);
                                Other.data.set("Info." + Crate + ".data", back);
                                break;
                            }
                        }
                        a = 0;
                    }
                    File file = new File(Main.plugin.getDataFolder(), "data.yml");
                    try {
                        Other.data.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Preset.location = 0;
                    break;
                }
            }
            return;
        }
        if (Other.config.getBoolean("ChangeSoundTimbre")) {
            try {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), c);
            } catch (Exception a) {
                player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), c);
            }
        } else {
            try {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            } catch (Exception a) {
                player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            }
        }
        a++;
        if (Other.data.getString("Info." + Crate + ".type").equals("repeatedly") || Other.data.getString("Info." + Crate + ".type").equals("show")) {
            Random random = new Random();
            int randoms = random.nextInt(3) + 1;
            if (randoms == 2) {
                a++;
            } else if (randoms == 1) {
                a--;
            }
        }
        switch (Other.data.getString("Info." + Crate + ".type")) {
            case "normal":
                Preset.winging(player, Crate);
                break;
            case "random":
                Preset.randomwinging(player, Crate);
                break;
            case "order":
                Preset.orderwinging(player, Crate);
                break;
            case "embellishment":
                Preset.embellishmentwinging(player, Crate);
                break;
            case "repeatedly":
                Preset.repeatedlywinging(player, Crate);
                break;
            case "show":
                Preset.showwinging(player, Crate);
                break;
            default:

        }
    }
}
