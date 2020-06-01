package com.tany.crateslottery.conversation;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.tany.crateslottery.gui.Gui;
import com.tany.crateslottery.listenevent.Event;

public class Name extends StringPrompt {
    Plugin config = Bukkit.getPluginManager().getPlugin("CratesLottery");
    File file1=new File(config.getDataFolder(),"data.yml");
	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
        FileConfiguration config2=YamlConfiguration.loadConfiguration(file1);
		Player player = (Player) context.getForWhom();
		if(input.equals(Event.crate)) {
			return this;
		}
		if(input.contains("&")) {
			return this;
		}
		if(input.contains("§")) {
			return this;
		}
		for(String cratesname:config2.getConfigurationSection("Info").getKeys(false)) {
			if(cratesname.equals(input)) {
				return this;
			}
		}
		
		Event.crate=input;
		Gui.createcrate(player, input);
		return END_OF_CONVERSATION;
	}

	@Override
	public String getPromptText(ConversationContext context) {
		return "§a请输入一个不与先前抽奖箱冲突的名称！";
	}



}
