package pers.tany.crateslottery.listenevent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pers.tany.crateslottery.CommonlyWay;
import pers.tany.crateslottery.Main;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.conversation.Name;
import pers.tany.crateslottery.gui.Gui;
import pers.tany.crateslottery.task.NineWingTask;
import pers.tany.crateslottery.task.NineWingTaskS;
import pers.tany.crateslottery.task.WingTask;
import pers.tany.crateslottery.task.WingTaskS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Event implements Listener {
    public static String crate = null;
    Set<String> Sneak = new HashSet<>();
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file = new File(config.getDataFolder(), "data.yml");

    @EventHandler
    public void Toggle(PlayerToggleSneakEvent evt) {
        if (evt.isSneaking()) {
            Sneak.add(evt.getPlayer().getName());
        } else {
            Sneak.remove(evt.getPlayer().getName());
        }
    }

    @EventHandler
    public void leave(PlayerQuitEvent evt) {
        Sneak.remove(evt.getPlayer().getName());
    }

    @EventHandler
    public void Break(BlockBreakEvent evt) {
        if (evt.isCancelled()) {
            return;
        }
        List<String> Location = Other.data.getStringList("Location");
        Location block = evt.getBlock().getLocation();
        if (Location.size() != 0) {
            for (String location : Location) {
                String world = location.split(":")[0];
                int x = Integer.parseInt(location.split(":")[1]);
                int y = Integer.parseInt(location.split(":")[2]);
                int z = Integer.parseInt(location.split(":")[3]);
                if (block.getBlockX() == x && block.getBlockY() == y && block.getBlockZ() == z && block.getWorld().equals(Bukkit.getWorld(world))) {
                    if (evt.getPlayer().hasPermission("cl.break")) {
                        if (Sneak.contains(evt.getPlayer().getName())) {
                            evt.getPlayer().sendMessage("§a成功破坏抽奖箱！");
                            Location.remove(location);
                            Other.data.set("Location", Location);
                            try {
                                Other.data.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        evt.setCancelled(true);
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("BreakCrateMessage")));
                    }
                    return;
                }
            }
        }
    }

    @EventHandler
    public void Place(BlockPlaceEvent evt) {
        if (evt.isCancelled()) {
            return;
        }
        if (!(Bukkit.getVersion().contains("1.7.") || Bukkit.getVersion().contains("1.8."))) {
            if (CommonlyWay.getPlaceHand(evt)) {
                return;
            }
        }
        if (evt.getItemInHand() != null && evt.getItemInHand().hasItemMeta() && evt.getItemInHand().getItemMeta().hasDisplayName() && evt.getItemInHand().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")))) {
            String title = ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), ""));
            int a = 0;
            for (String name : Other.data.getConfigurationSection("Info").getKeys(false)) {
                if (name.equals(title)) {
                    break;
                }
                a++;
                if (a == Other.data.getConfigurationSection("Info").getKeys(false).size()) {
                    a = 0;
                    return;
                }
            }
            a = 0;
            List<String> Location = Other.data.getStringList("Location");
            if (!Location.contains(evt.getBlock().getLocation().getWorld().getName() + ":" + evt.getBlock().getLocation().getBlockX() + ":" + evt.getBlock().getLocation().getBlockY() + ":" + evt.getBlock().getLocation().getBlockZ() + ":" + ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")))) {
                Location.add(evt.getBlock().getLocation().getWorld().getName() + ":" + evt.getBlock().getLocation().getBlockX() + ":" + evt.getBlock().getLocation().getBlockY() + ":" + evt.getBlock().getLocation().getBlockZ() + ":" + ChatColor.stripColor(evt.getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLottery")), "")));
            }
            Other.data.set("Location", Location);
            try {
                Other.data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent evt) {
        if (!(Bukkit.getVersion().contains("1.7.") || Bukkit.getVersion().contains("1.8."))) {
            if (CommonlyWay.getInteractHand(evt)) {
                return;
            }
        }
        if (!evt.getAction().equals(Action.LEFT_CLICK_AIR) && !evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            List<String> Location = Other.data.getStringList("Location");
            Location block = evt.getClickedBlock().getLocation();
            if (Location.size() != 0) {
                for (String location : Location) {
                    String world = location.split(":")[0];
                    int x = Integer.parseInt(location.split(":")[1]);
                    int y = Integer.parseInt(location.split(":")[2]);
                    int z = Integer.parseInt(location.split(":")[3]);
                    String names = location.split(":")[4];
                    if (block.getBlockX() == x && block.getBlockY() == y && block.getBlockZ() == z && block.getWorld().equals(Bukkit.getWorld(world))) {
                        if (Other.data.getString("Info." + names + ".color") == null) {
                            evt.getClickedBlock().setType(Material.AIR);
                            evt.setCancelled(true);
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearCrateMessage")));
                            Location.remove(location);
                            Other.data.set("Location", Location);
                            try {
                                Other.data.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                }
            }
        }
        if (evt.getItem() != null && evt.getItem().hasItemMeta() && evt.getItem().getItemMeta().hasDisplayName() && evt.getItem().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")))) {
            evt.setCancelled(true);
            String name = ChatColor.stripColor(evt.getItem().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), ""));
            if (!evt.getItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', Other.config.getString("Lock") + name))) {
                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WarnMessage")));
                return;
            }
            if (Other.data.getString("Info." + name + ".color") == null) {
                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearKeyMessage")));
                evt.getPlayer().getInventory().setItemInHand(null);
                return;
            }
            if (!Other.data.getBoolean("Info." + name + ".unpackanytime") && evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("UseKeyMessage")));
                return;
            }
            if (Other.data.getBoolean("Info." + name + ".unpackanytime")) {
                if (evt.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    if (!(evt.getPlayer().hasPermission("cl.checkall") || evt.getPlayer().hasPermission("cl.check." + name))) {
                        evt.setCancelled(true);
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoCheckMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                        return;
                    }
                    if (!Other.data.getBoolean("Info." + name + ".check")) {
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoShowCrates")));
                        evt.setCancelled(true);
                        return;
                    }
                    evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                    Gui.showcrate(evt.getPlayer(), name);
                    evt.setCancelled(true);
                    return;
                }
                if (evt.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (Sneak.contains(evt.getPlayer().getName())) {
                        if (!evt.getPlayer().hasPermission("cl.ninelottery")) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
                            return;
                        }
                        if (Other.data.getBoolean("Info." + name + ".clear")) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
                            return;
                        }
                        if (!evt.getPlayer().hasPermission("cl.allcrate") && !evt.getPlayer().hasPermission("cl.crate." + name)) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info." + name + ".color") + name))));
                            return;
                        }
                        List<String> itemlist = Other.data.getStringList("Info." + name + ".data");
                        int a = 1;
                        if (itemlist.size() == 0) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                            return;
                        }
                        for (String item : itemlist) {
                            if (!item.split(":")[1].equals("null")) {
                                break;
                            }
                            if (a == itemlist.size()) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                return;
                            }
                            a++;
                        }
                        a = 1;
                        if (evt.getPlayer().getInventory().getItemInHand().getAmount() < 9) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
                            return;
                        }
                        int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
                        amount = amount - 9;
                        if (amount != 0) {
                            evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
                        } else {
                            evt.getPlayer().getInventory().setItemInHand(null);
                        }
                        if (!Other.data.getString("Info." + name + ".nine").equals("无")) {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info." + name + ".nine").replace("[evt]", evt.getPlayer().getName())));
                        }
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                        if (Other.data.getBoolean("Info." + name + ".nineanimation")) {
                            if (Other.data.getDouble("Info." + name + ".ninecd") <= 0 && Other.data.getDouble("Info." + name + ".ninenumber") <= 0) {
                                new NineWingTask(evt.getPlayer(), name, Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime") * 20));
                            } else {
                                new NineWingTask(evt.getPlayer(), name, Other.data.getInt("Info." + name + ".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info." + name + ".ninecd") * 20));
                            }
                        } else {
                            new NineWingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
                        }
                    } else {
                        if (!evt.getPlayer().hasPermission("cl.lottery")) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
                            return;
                        }
                        List<String> itemlist = Other.data.getStringList("Info." + name + ".data");
                        int a = 1;
                        if (itemlist.size() == 0) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                            return;
                        }
                        if (!evt.getPlayer().hasPermission("cl.allcrate") && !evt.getPlayer().hasPermission("cl.crate." + name)) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info." + name + ".color") + name))));
                            return;
                        }
                        for (String item : itemlist) {
                            if (!item.split(":")[1].equals("null")) {
                                break;
                            }
                            if (a == itemlist.size()) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                return;
                            }
                            a++;
                        }
                        int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
                        amount--;
                        if (amount != 0) {
                            evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
                        } else {
                            evt.getPlayer().getInventory().setItemInHand(null);
                        }
                        if (!Other.data.getString("Info." + name + ".announcement").equals("无")) {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info." + name + ".announcement").replace("[evt]", evt.getPlayer().getName())));
                        }
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                        if (Other.data.getBoolean("Info." + name + ".animation")) {
                            if (Other.data.getDouble("Info." + name + ".cd") <= 0 && Other.data.getDouble("Info." + name + ".number") <= 0) {
                                new WingTask(evt.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime") * 20));
                            } else {
                                new WingTask(evt.getPlayer(), name, Other.data.getInt("Info." + name + ".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info." + name + ".cd") * 20));
                            }
                        } else {
                            new WingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
                        }
                    }
                    return;
                }
            }
        }
        if (evt.getClickedBlock() != null && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            List<String> Location = Other.data.getStringList("Location");
            Location block = evt.getClickedBlock().getLocation();
            if (Location.size() != 0) {
                for (String location : Location) {
                    String world = location.split(":")[0];
                    int x = Integer.parseInt(location.split(":")[1]);
                    int y = Integer.parseInt(location.split(":")[2]);
                    int z = Integer.parseInt(location.split(":")[3]);
                    String name = location.split(":")[4];
                    if (block.getBlockX() == x && block.getBlockY() == y && block.getBlockZ() == z && block.getWorld().equals(Bukkit.getWorld(world))) {
                        evt.setCancelled(true);
                        if (evt.getPlayer().getInventory().getItemInHand() == null || evt.getPlayer().getInventory().getItemInHand().getType() == Material.AIR) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info." + name + ".color") + name)));
                            return;
                        } else if (!evt.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info." + name + ".color") + name)));
                            return;
                        } else if (!ChatColor.stripColor(evt.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', Other.message.getString("CrateLotteryKey")), "")).equals(name)) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrateMessage").replace("[key]", Other.data.getString("Info." + name + ".color") + name)));
                            return;
                        } else if (!evt.getItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', Other.config.getString("Lock") + name))) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("WarnMessage")));
                            return;
                        }
                        if (Sneak.contains(evt.getPlayer().getName())) {
                            if (!evt.getPlayer().hasPermission("cl.ninelottery")) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineLotteryMessage")));
                                return;
                            }
                            if (Other.data.getBoolean("Info." + name + ".clear")) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ClearMessage")));
                                return;
                            }
                            if (!evt.getPlayer().hasPermission("cl.allcrate") && !evt.getPlayer().hasPermission("cl.crate." + name)) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info." + name + ".color") + name))));
                                return;
                            }
                            List<String> itemlist = Other.data.getStringList("Info." + name + ".data");
                            int a = 1;
                            if (itemlist.size() == 0) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                return;
                            }
                            for (String item : itemlist) {
                                if (!item.split(":")[1].equals("null")) {
                                    break;
                                }
                                if (a == itemlist.size()) {
                                    evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                    return;
                                }
                                a++;
                            }
                            a = 1;
                            if (evt.getPlayer().getInventory().getItemInHand().getAmount() < 9) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoNineKeyMessage")));
                                return;
                            }
                            int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
                            amount = amount - 9;
                            if (amount != 0) {
                                evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
                            } else {
                                evt.getPlayer().getInventory().setItemInHand(null);
                            }
                            if (!Other.data.getString("Info." + name + ".nine").equals("无")) {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info." + name + ".nine").replace("[evt]", evt.getPlayer().getName())));
                            }
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NineOpenCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                            if (Other.data.getBoolean("Info." + name + ".nineanimation")) {
                                if (Other.data.getDouble("Info." + name + ".ninecd") <= 0 && Other.data.getDouble("Info." + name + ".ninenumber") <= 0) {
                                    new NineWingTask(evt.getPlayer(), name, Other.config.getInt("NineWingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("NineWingSpaceTime") * 20));
                                } else {
                                    new NineWingTask(evt.getPlayer(), name, Other.data.getInt("Info." + name + ".ninenumber")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info." + name + ".ninecd") * 20));
                                }
                            } else {
                                new NineWingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
                            }
                        } else {
                            if (!evt.getPlayer().hasPermission("cl.lottery")) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoLotteryMessage")));
                                return;
                            }
                            List<String> itemlist = Other.data.getStringList("Info." + name + ".data");
                            int a = 1;
                            if (itemlist.size() == 0) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                return;
                            }
                            if (!evt.getPlayer().hasPermission("cl.allcrate") && !evt.getPlayer().hasPermission("cl.crate." + name)) {
                                evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoOpenCrate".replace("[crate]", Other.data.getString("Info." + name + ".color") + name))));
                                return;
                            }
                            for (String item : itemlist) {
                                if (!item.split(":")[1].equals("null")) {
                                    break;
                                }
                                if (a == itemlist.size()) {
                                    evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoItemMessage")));
                                    return;
                                }
                                a++;
                            }
                            int amount = evt.getPlayer().getInventory().getItemInHand().getAmount();
                            amount--;
                            if (amount != 0) {
                                evt.getPlayer().getInventory().getItemInHand().setAmount(amount);
                            } else {
                                evt.getPlayer().getInventory().setItemInHand(null);
                            }
                            if (!Other.data.getString("Info." + name + ".announcement").equals("无")) {
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Other.data.getString("Info." + name + ".announcement").replace("[evt]", evt.getPlayer().getName())));
                            }
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("OpenCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                            if (Other.data.getBoolean("Info." + name + ".animation")) {
                                if (Other.data.getDouble("Info." + name + ".cd") <= 0 && Other.data.getDouble("Info." + name + ".number") <= 0) {
                                    new WingTask(evt.getPlayer(), name, Other.config.getInt("WingLongTime")).runTaskTimer(Main.plugin, 0, (int) (Other.config.getDouble("WingSpaceTime") * 20));
                                } else {
                                    new WingTask(evt.getPlayer(), name, Other.data.getInt("Info." + name + ".number")).runTaskTimer(Main.plugin, 0, (int) (Other.data.getDouble("Info." + name + ".cd") * 20));
                                }
                            } else {
                                new WingTaskS(evt.getPlayer(), name).runTaskTimer(Main.plugin, 0, 0);
                            }
                        }
                        return;
                    }
                }
            }
        }
        if (evt.getAction().equals(Action.LEFT_CLICK_BLOCK) && !Sneak.contains(evt.getPlayer().getName())) {
            List<String> Location = Other.data.getStringList("Location");
            Location block = evt.getClickedBlock().getLocation();
            if (Location.size() != 0) {
                for (String location : Location) {
                    String world = location.split(":")[0];
                    int x = Integer.parseInt(location.split(":")[1]);
                    int y = Integer.parseInt(location.split(":")[2]);
                    int z = Integer.parseInt(location.split(":")[3]);
                    String name = location.split(":")[4];
                    if (block.getBlockX() == x && block.getBlockY() == y && block.getBlockZ() == z && block.getWorld().equals(Bukkit.getWorld(world))) {
                        if (!(evt.getPlayer().hasPermission("cl.checkall") || evt.getPlayer().hasPermission("cl.check." + name))) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoCheckMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                            evt.setCancelled(true);
                            return;
                        }
                        if (!Other.data.getBoolean("Info." + name + ".check")) {
                            evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoShowCrates")));
                            evt.setCancelled(true);
                            return;
                        }
                        evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ShowCrateMessage").replace("[crate]", Other.data.getString("Info." + name + ".color") + name)));
                        Gui.showcrate(evt.getPlayer(), name);
                        evt.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void Click(InventoryClickEvent evt) {
        if (!(evt.getWhoClicked() instanceof Player)) {
            return;
        }
        Inventory inventory = evt.getClickedInventory();
        if (inventory == null) {
            return;
        }
        Player p = (Player) evt.getWhoClicked();
        if (!p.getOpenInventory().getTitle().equals(evt.getView().getTitle()) && evt.getInventory().getType() == InventoryType.PLAYER) {
            if (p.getOpenInventory().getTitle().startsWith("§c选§4择") || p.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e") || p.getOpenInventory().getTitle().startsWith("§a抽奖箱：")) {
                evt.setCancelled(true);
            } else if (p.getOpenInventory().getTitle().startsWith("§3设置") || p.getOpenInventory().getTitle().startsWith("§3想对")) {
                evt.setCancelled(true);
            } else if (p.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e") || p.getOpenInventory().getTitle().startsWith("§a正在开箱") || p.getOpenInventory().getTitle().startsWith("§6抽奖箱设置§b：") || p.getOpenInventory().getTitle().startsWith("§d设置抽奖箱")) {
                evt.setCancelled(true);
            }
        }
        if (evt.getView().getTitle().equals("§c选§4择")) {
            evt.setCancelled(true);
            if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || !evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5创建一个新的抽奖箱")) {
                p.closeInventory();
                Conversation conversation = new Conversation(Main.plugin, p, new Name());
                p.beginConversation(conversation);
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a进入已有的抽奖箱设置")) {
                p.closeInventory();
                Gui.cratelist(p, 1);
            }
        }
        if (evt.getView().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
            evt.setCancelled(true);
            String title = evt.getView().getTitle();
            int type = Integer.parseInt(title.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
            if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || !evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (evt.getSlot() == 53 && evt.getView().getItem(53).getItemMeta().getDisplayName().equals("§a下一页")) {
                Gui.cratelist(p, ++type);
            }
            if (evt.getSlot() == 45 && evt.getView().getItem(45).getItemMeta().getDisplayName().equals("§a上一页")) {
                Gui.cratelist(p, --type);
            }
            if (evt.getSlot() > 44) {
                return;
            }
            String cratename = ChatColor.stripColor(evt.getCurrentItem().getItemMeta().getDisplayName().replace("§6抽奖箱设置§b：", ""));
            Gui.choose(p, cratename);
            return;
        }
        if (evt.getView().getTitle().startsWith("§a抽奖箱：") || evt.getView().getTitle().startsWith("§a正在开箱") || evt.getView().getTitle().startsWith("§c正在九连§a开箱")) {
            evt.setCancelled(true);
            return;
        }
        if (evt.getView().getTitle().startsWith("§6抽奖箱§c：")) {
            if (evt.getCurrentItem() != null && evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName() && (evt.getCurrentItem().getItemMeta().getDisplayName().contains("领取你的奖励吧！") || evt.getCurrentItem().getItemMeta().getDisplayName().contains("§2仅展示"))) {
                evt.setCancelled(true);
            }
            return;
        }
        if (evt.getView().getTitle().startsWith("§c抽奖§6箱：")) {
            if (evt.getCurrentItem() != null && evt.getCurrentItem().hasItemMeta() && evt.getCurrentItem().getItemMeta().hasDisplayName() && (evt.getCurrentItem().getItemMeta().getDisplayName().contains("领取你的战利品吧！") || evt.getCurrentItem().getItemMeta().getDisplayName().contains("§2仅展示"))) {
                evt.setCancelled(true);
            }
            return;
        }
        if (evt.getView().getTitle().startsWith("§3设置")) {
            evt.setCancelled(true);
            String title = evt.getView().getTitle();
            String name = title.replace("§3设置", "").replace("§3显示的颜色", "");
            name = ChatColor.stripColor(name);
            if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || !evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§4红色")) {
                Other.data.set("Info." + name + ".color", "§4");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§e黄色")) {
                Other.data.set("Info." + name + ".color", "§e");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§3蓝色")) {
                Other.data.set("Info." + name + ".color", "§3");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§1深蓝色")) {
                Other.data.set("Info." + name + ".color", "§1");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§0黑色")) {
                Other.data.set("Info." + name + ".color", "§0");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5紫色")) {
                Other.data.set("Info." + name + ".color", "§5");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
                Other.data.set("Info." + name + ".color", "§6");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§f白色")) {
                Other.data.set("Info." + name + ".color", "§f");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a浅绿色")) {
                Other.data.set("Info." + name + ".color", "§a");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§2绿色")) {
                Other.data.set("Info." + name + ".color", "§2");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6橙色")) {
                Other.data.set("Info." + name + ".color", "§6");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§b天蓝色")) {
                Other.data.set("Info." + name + ".color", "§b");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c粉红色")) {
                Other.data.set("Info." + name + ".color", "§c");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§d亮紫色")) {
                Other.data.set("Info." + name + ".color", "§d");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§7灰色")) {
                Other.data.set("Info." + name + ".color", "§7");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a返回设置列表")) {
                p.closeInventory();
                Gui.choose(p, name);
                return;
            }
        }
        if (evt.getView().getTitle().startsWith("§d设置抽奖箱")) {
            evt.setCancelled(true);
            String title = evt.getView().getTitle();
            String name = ChatColor.stripColor(title.replace("设置抽奖箱", "").replace("的开箱方式", ""));
            if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || !evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6点击对应的方块，设置对应的开箱动画")) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a固定中间抽奖")) {
                Other.data.set("Info." + name + ".type", "normal");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§a固定中间抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "normal");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§e随机位置抽奖")) {
                Other.data.set("Info." + name + ".type", "random");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§e随机位置抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "random");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§b范围位置内抽奖")) {
                Other.data.set("Info." + name + ".type", "order");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§b范围位置内抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "order");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§b有物品的随机抽奖")) {
                Other.data.set("Info." + name + ".type", "embellishment");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c反复横跳抽奖")) {
                Other.data.set("Info." + name + ".type", "repeatedly");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§c反复横跳抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "repeatedly");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§6快乐矩形抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "gradient");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§3跑马灯抽奖")) {
                Other.data.set("Info." + name + ".type", "show");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c九连抽§d：§3跑马灯抽奖")) {
                Other.data.set("Info." + name + ".ninetype", "show");
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    }
                }
                p.sendMessage("§a成功设置");
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a返回设置列表")) {
                p.closeInventory();
                Gui.choose(p, name);
                return;
            }
            return;
        }
        if (evt.getView().getTitle().startsWith("§3想对")) {
            evt.setCancelled(true);
            String title = evt.getView().getTitle();
            String name = ChatColor.stripColor(title.replace("§3想对", "").replace("§3进行什么操作？", ""));
            if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || !evt.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a单独设置箱子属性")) {
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§5修改奖池奖励")) {
                Gui.setcrate(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§6设置颜色")) {
                Gui.color(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a设置")) {
                Gui.way(p, name);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a返回选项列表")) {
                p.closeInventory();
                Gui.cratelist(p, 1);
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用单抽动画")) {
                if (Other.data.getBoolean("Info." + name + ".animation")) {
                    Other.data.set("Info." + name + ".animation", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".animation", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用§c九连抽§a动画")) {
                if (Other.data.getBoolean("Info." + name + ".nineanimation")) {
                    Other.data.set("Info." + name + ".nineanimation", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".nineanimation", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用公告单抽到的物品")) {
                if (Other.data.getBoolean("Info." + name + ".info")) {
                    Other.data.set("Info." + name + ".info", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".info", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用公告§c九连抽§a到的物品")) {
                if (Other.data.getBoolean("Info." + name + ".nineinfo")) {
                    Other.data.set("Info." + name + ".nineinfo", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".nineinfo", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否单抽箱子清理抽到的物品功能")) {
                if (Other.data.getBoolean("Info." + name + ".clear")) {
                    Other.data.set("Info." + name + ".backup", false);
                    Other.data.set("Info." + name + ".clear", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".clear", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用填充因clear清空完内容的箱子内容")) {
                if (!Other.data.getBoolean("Info." + name + ".clear")) {
                    p.sendMessage("§c这个箱子没有开启一次性抽奖功能！");
                    return;
                }
                if (Other.data.getBoolean("Info." + name + ".backup")) {
                    Other.data.set("Info." + name + ".backup", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".backup", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改是否启用这个抽奖箱无需对箱即可抽奖")) {
                if (Other.data.getBoolean("Info." + name + ".unpackanytime")) {
                    Other.data.set("Info." + name + ".unpackanytime", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".unpackanytime", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a修改这个抽奖箱是否可以查看奖池内容")) {
                if (Other.data.getBoolean("Info." + name + ".check")) {
                    Other.data.set("Info." + name + ".check", false);
                    p.sendMessage("§2成功修改为：§c未启用");
                } else {
                    Other.data.set("Info." + name + ".check", true);
                    p.sendMessage("§2成功修改为：§a启用");
                }
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        Gui.choose(evts, name);
                    }
                }
                return;
            }
            if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§c删除这个箱子")) {
                Other.data.set("Info." + name, null);
                Other.data.set("backup." + name, null);
                try {
                    Other.data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.closeInventory();
                for (Player evts : Bukkit.getOnlinePlayers()) {
                    if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                        String titles = evts.getOpenInventory().getTitle();
                        int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                        evts.closeInventory();
                        Gui.cratelist(evts, type);
                    } else if (evts.getOpenInventory().getTitle().startsWith("§2抽奖箱" + Other.data.getString("Info." + name + ".color") + name)) {
                        evts.closeInventory();
                        evts.sendMessage("§a这个抽奖箱被§c" + p.getName() + "§a删除！");
                    } else if (evts.getOpenInventory().getTitle().equals(title)) {
                        evts.closeInventory();
                        evts.sendMessage("§a这个抽奖箱被§c" + p.getName() + "§a删除！");
                    } else if (evts.getOpenInventory().getTitle().startsWith(ChatColor.stripColor("设置" + Other.data.getString("Info." + name + ".color") + name + "显示的颜色"))) {
                        evts.closeInventory();
                        evts.sendMessage("§a这个抽奖箱被§c" + p.getName() + "§a删除！");
                    } else if (evt.getView().getTitle().startsWith(ChatColor.stripColor("§d设置抽奖箱" + Other.data.getString("Info." + name + ".color") + name + "的开箱方式"))) {
                        evts.closeInventory();
                        evts.sendMessage("§a这个抽奖箱被§c" + p.getName() + "§a删除！");
                    }
                }
                p.sendMessage("§c删除§a" + name + "§c成功");
            }
        }
    }

    @EventHandler
    public void Close(InventoryCloseEvent evt) {
        if (evt.getView().getTitle().startsWith("§2抽奖箱")) {
            String title = ChatColor.stripColor(evt.getView().getTitle().replace("§2抽奖箱", "").replace("§2设置", ""));
            ArrayList<String> data = new ArrayList<String>();
            int a = 0;
            while (a < 54) {
                String item = null;
                if (evt.getInventory().getItem(a) == null || evt.getInventory().getItem(a).getType() == Material.AIR) {
                    item = "null";
                } else {
                    item = CommonlyWay.getItemData(evt.getInventory().getItem(a));
                }
                data.add(a + ":" + item);
                a++;
            }
            a = 0;
            List<String> exist = Other.data.getStringList("Info." + title + ".data");
            if (exist.size() == 0) {
                Other.data.set("Info." + title + ".color", "§f");
                Other.data.set("Info." + title + ".animation", true);
                Other.data.set("Info." + title + ".nineanimation", true);
                Other.data.set("Info." + title + ".announcement", "无");
                Other.data.set("Info." + title + ".nine", "无");
                Other.data.set("Info." + title + ".number", -1);
                Other.data.set("Info." + title + ".cd", -1);
                Other.data.set("Info." + title + ".ninenumber", -1);
                Other.data.set("Info." + title + ".ninecd", -1);
                Other.data.set("Info." + title + ".type", "normal");
                Other.data.set("Info." + title + ".ninetype", "normal");
                Other.data.set("Info." + title + ".clear", false);
                Other.data.set("Info." + title + ".info", false);
                Other.data.set("Info." + title + ".nineinfo", false);
                Other.data.set("Info." + title + ".backup", false);
                Other.data.set("Info." + title + ".unpackanytime", false);
                Other.data.set("Info." + title + ".check", true);
                crate = null;
            }
            Other.data.set("backup." + title, data);
            Other.data.set("Info." + title + ".data", data);
            try {
                Other.data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Player player = (Player) evt.getPlayer();
            player.sendMessage("§a保存成功");
            for (Player evts : Bukkit.getOnlinePlayers()) {
                if (evts.getOpenInventory().getTitle().startsWith("§a抽奖箱§2列表§5：§d第§e")) {
                    String titles = evts.getOpenInventory().getTitle();
                    int type = Integer.parseInt(titles.replace("§a抽奖箱§2列表§5：§d第§e", "").replace("§d页", ""));
                    evts.closeInventory();
                    Gui.cratelist(evts, type);
                }
            }
            return;
        }
        if (evt.getView().getTitle().contains("§c九连开箱结果") || evt.getView().getTitle().contains("§a开箱结果")) {
            for (ItemStack item : evt.getInventory().getContents()) {
                if (item == null || item.getType() == Material.AIR) {
                    continue;
                }
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && (item.getItemMeta().getDisplayName().contains("领取你的奖励吧！") || item.getItemMeta().getDisplayName().equals("§2仅展示") || item.getItemMeta().getDisplayName().contains("领取你的战利品吧！"))) {
                    continue;
                }
                CommonlyWay.giveItem((Player) evt.getPlayer(), item);
            }
        }
    }
}