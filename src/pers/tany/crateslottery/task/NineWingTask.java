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
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Preset;

public class NineWingTask extends BukkitRunnable {
    int a = 0;
    int b = 0;
    int c = 0;
    public Player player;
    public String Crate;

    public NineWingTask(Player p, String s, int n) {
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
            switch (Other.data.getString("Info." + Crate + ".ninetype")) {
                case "normal":
                    Preset.ninewing(player, Crate);
                    break;
                case "random":
                    Preset.randomninewing(player, Crate);
                    break;
                case "order":
                    Preset.orderninewing(player, Crate);
                    break;
                case "gradient":
                    Preset.gradientwing(player, Crate);
                    break;
                case "repeatedly":
                    Preset.repeatedlyninewing(player, Crate);
                    break;
                case "show":
                    Preset.showninewing(player, Crate);
                    break;
                default:

            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WingMessage")));
            try {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            } catch (Exception a) {
                player.playSound(player.getLocation(), Sound.valueOf("ORB_PICKUP"), Other.config.getInt("SoundSize"), Other.config.getInt("SoundTimbre"));
            }
            a = 0;
            b = 0;
            cancel();
            if (Other.data.getBoolean("Info." + Crate + ".nineinfo")) {
                int size = player.getOpenInventory().getBottomInventory().getSize() - 1;
                int location = 0;
                String info = "";
                while (location < size) {
                    ItemStack item = player.getOpenInventory().getItem(location);
                    if (item == null || item.getType() == Material.AIR) {
                        location++;
                        continue;
                    }
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("领取你的战利品吧！")) {
                            location++;
                            continue;
                        }
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("§2仅展示")) {
                            location++;
                            continue;
                        }
                        if (item.getAmount() == 1) {
                            info += item.getItemMeta().getDisplayName() + " ";
                        } else {
                            info += item.getItemMeta().getDisplayName() + "*" + item.getAmount() + " ";
                        }
                    } else {
                        if (item.getAmount() == 1) {
                            info += item.getType().name() + " ";
                        } else {
                            info += item.getType().name() + "*" + item.getAmount() + " ";
                        }
                    }
                    location++;
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineInfoMessage").replace("[player]", player.getName()).replace("[crate]", Other.data.getString("Info." + Crate + ".color") + Crate).replace("[item]", info)));
            }
            if (Other.config.getBoolean("Log")) {
                int size = player.getOpenInventory().getBottomInventory().getSize() - 1;
                int location = 0;
                String info = "";
                while (location < size) {
                    ItemStack item = player.getOpenInventory().getItem(location);
                    if (item == null || item.getType() == Material.AIR) {
                        location++;
                        continue;
                    }
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("领取你的战利品吧！")) {
                            location++;
                            continue;
                        }
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("§2仅展示")) {
                            location++;
                            continue;
                        }
                        if (item.getAmount() == 1) {
                            info += item.getItemMeta().getDisplayName() + " ";
                        } else {
                            info += item.getItemMeta().getDisplayName() + "*" + item.getAmount() + " ";
                        }
                    } else {
                        if (item.getAmount() == 1) {
                            info += item.getType().name() + " ";
                        } else {
                            info += item.getType().name() + "*" + item.getAmount() + " ";
                        }
                    }
                    location++;
                }
                SimpleDateFormat time = new SimpleDateFormat("MM月dd日 HH时mm分ss秒");
                String timegroup = time.format(new Date());
                List<String> log = new ArrayList<>(Other.log.getStringList("Log"));
                log.add("玩家" + player.getName() + "于" + timegroup + "九连抽开启了" + Crate + "抽奖箱，获得了" + info);
                Other.log.set("Log", log);
                File file = new File(Main.plugin.getDataFolder(), "log.yml");
                try {
                    Other.log.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        a++;
        if (Other.data.getString("Info." + Crate + ".ninetype").equals("repeatedly") || Other.data.getString("Info." + Crate + ".ninetype").equals("show")) {
            Random random = new Random();
            int randoms = random.nextInt(3) + 1;
            if (randoms == 2) {
                a++;
            } else if (randoms == 1) {
                a--;
            }
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
        switch (Other.data.getString("Info." + Crate + ".ninetype")) {
            case "normal":
                Preset.ninewinging(player, Crate);
                break;
            case "random":
                Preset.randomninewinging(player, Crate);
                break;
            case "order":
                Preset.orderninewinging(player, Crate);
                break;
            case "gradient":
                Preset.gradientwinging(player, Crate);
                break;
            case "repeatedly":
                Preset.repeatedlyninewinging(player, Crate);
                break;
            case "show":
                Preset.showninewinging(player, Crate);
                break;
            default:

        }
    }
}
