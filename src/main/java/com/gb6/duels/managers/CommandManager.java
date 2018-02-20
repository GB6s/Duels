package com.gb6.duels.managers;

import com.gb6.duels.commands.ArenaCommand;
import com.gb6.duels.commands.HelpCommand;
import com.gb6.duels.objects.CommandObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gb6.duels.enums.Requirement.CONSOLE;
import static com.gb6.duels.enums.Requirement.PLAYER;
import static com.gb6.duels.utils.Constants.COMMAND_LIST;
import static com.gb6.duels.utils.Constants.MSG_UTIL;


public class CommandManager implements CommandExecutor {

    public CommandManager() {
        COMMAND_LIST.add(new HelpCommand());
        COMMAND_LIST.add(new ArenaCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (CommandObject cmd : COMMAND_LIST) {
            List<String> output = new LinkedList<>(Arrays.asList(args));

            if (output.size() > 0) {
                output.remove(0);
            }
            if (args.length == 0 && cmd.getClass() == HelpCommand.class) {
                cmd.execute(sender, output);
                return true;
            }

            if (!args[0].equalsIgnoreCase(cmd.getSyntax())) {
                continue;
            }

            Boolean isPlayer = sender instanceof Player;
            Boolean isConsole = sender instanceof ConsoleCommandSender;

            if (!isConsole && cmd.getRequirement() == CONSOLE) {
                MSG_UTIL.getMessage("console-only").color().send(sender);
                return true;
            }

            if (!isPlayer && cmd.getRequirement() == PLAYER) {
                MSG_UTIL.getMessage("player-only").color().send(sender);
                return true;
            }

            if (!sender.isOp() && !sender.hasPermission(cmd.getPermission())) {
                MSG_UTIL.getMessage("no-permission").color().send(sender);
                return true;
            }
            cmd.execute(sender, output);
            return true;
        }
        MSG_UTIL.getMessage("unknown-command").color().send(sender);
        return true;
    }
}

