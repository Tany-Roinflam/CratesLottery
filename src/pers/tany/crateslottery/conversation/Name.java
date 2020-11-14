package pers.tany.crateslottery.conversation;


import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import pers.tany.crateslottery.Other;
import pers.tany.crateslottery.gui.Gui;
import pers.tany.crateslottery.listenevent.Event;

public class Name extends StringPrompt {
    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        Player player = (Player) context.getForWhom();
        if (input.equals(Event.crate)) {
            return this;
        }
        if (input.contains("&")) {
            return this;
        }
        if (input.contains("§")) {
            return this;
        }
        if (Other.data.getConfigurationSection("Info").getKeys(false).size() != 0) {
            for (String cratesname : Other.data.getConfigurationSection("Info").getKeys(false)) {
                if (cratesname.equals(input)) {
                    return this;
                }
            }
        }
        Event.crate = input;
        Gui.createcrate(player, input);
        return END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "§a请输入一个不与先前抽奖箱冲突的名称！";
    }
}
