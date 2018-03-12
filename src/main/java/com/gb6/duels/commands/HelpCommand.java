package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.Command;
import com.gb6.duels.objects.Message;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "duels.help", Requirement.PLAYER_CONSOLE);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        new Message("help", true).send(sender);
    }

}
