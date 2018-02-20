package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.CommandObject;
import org.bukkit.command.CommandSender;

import java.util.List;

import static com.gb6.duels.utils.Constants.MSG_UTIL;

public class HelpCommand extends CommandObject {

    public HelpCommand() {
        super("help", "duels.help", Requirement.PLAYER_CONSOLE);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        MSG_UTIL.getMessageList("help").color().send(sender);
    }

}
