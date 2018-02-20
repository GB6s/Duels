package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.CommandObject;
import org.bukkit.command.CommandSender;

import java.util.List;

public class KitsCommand extends CommandObject {

    public KitsCommand() {
        super("kits", "duels.admin", Requirement.PLAYER);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {

    }

}
