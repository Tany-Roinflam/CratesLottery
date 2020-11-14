package pers.tany.crateslottery.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Preset;

public class NineWingTaskS extends BukkitRunnable {
    public Player player;
    public String Crate;

    public NineWingTaskS(Player p, String s) {
        player = p;
        Crate = s;
    }

    @Override
    public void run() {
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
            player.playSound(player.getLocation(), Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 2f, 2f);
        } catch (Exception a) {
            player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 2f, 2f);
        }
        cancel();
        if (Other.data.getBoolean("Info." + Crate + ".nineinfo")) {
            int size = player.getOpenInventory().getBottomInventory().getSize() - 1;
            int location = 0;
            String info = "";
            while (location < size) {
                ItemStack item = player.getOpenInventory().getItem(location);
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
    }
}
