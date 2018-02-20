package com.gb6.duels.commands;

import com.gb6.duels.enums.DuelState;
import com.gb6.duels.objects.DuelObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.gb6.duels.utils.Constants.DUEL_MAP;
import static com.gb6.duels.utils.Constants.MSG_UTIL;
import static com.gb6.duels.utils.GuiUtilities.openSettingGui;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MSG_UTIL.getMessage("player-only").color().send(sender);
            return true;
        }

        if (!sender.hasPermission("duels.play")) {
            MSG_UTIL.getMessage("no-permission").color().send(sender);
            return true;
        }

        if (args.length == 0) {
            MSG_UTIL.getMessage("invalid-arguments").color().send(sender);
            return true;
        }

        Player requester = (Player) sender;
        Player opponent = Bukkit.getPlayer(args[0]);

        if (opponent == null) {
            MSG_UTIL.getMessage("invalid-player").color().format("%player%", args[0]).send(requester);
            return true;
        }

        if (DUEL_MAP.containsKey(opponent.getUniqueId()) && DUEL_MAP.get(opponent.getUniqueId()).getRequester().equals(opponent) && DUEL_MAP.get(opponent.getUniqueId()).getState() == DuelState.SENT) {
            DUEL_MAP.get(opponent.getUniqueId()).setState(DuelState.ACCEPTED);
            DUEL_MAP.get(opponent.getUniqueId()).handle();
            return true;
            // TODO: Accept duel
        }

        DUEL_MAP.put(requester.getUniqueId(), new DuelObject(requester, opponent));
        openSettingGui(requester);
        return false;
    }

}
