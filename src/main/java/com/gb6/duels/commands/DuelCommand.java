package com.gb6.duels.commands;

import com.gb6.duels.objects.Duel;
import com.gb6.duels.objects.Dueler;
import com.gb6.duels.objects.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.gb6.duels.enums.DuelState.SENT;
import static com.gb6.duels.utils.Constants.*;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            new Message("player-only").send(sender);
            return true;
        }

        if (!sender.hasPermission("duels.play")) {
            new Message("no-permission").send(sender);
            return true;
        }

        if (args.length == 0) {
            new Message("invalid-arguments").send(sender);
            return true;
        }

        Player player = (Player) sender;
        Player opponent = Bukkit.getPlayer(args[0]);

        if (opponent == null || player == opponent) {
            new Message("invalid-player").format("player", args[0]).send(player);
            return true;
        }

        DUEL_MAP.putIfAbsent(player.getUniqueId(), new Dueler(player));
        DUEL_MAP.putIfAbsent(opponent.getUniqueId(), new Dueler(opponent));

        if (DUEL_MAP.get(opponent.getUniqueId()).getByOpponent(player) != null && DUEL_MAP.get(opponent.getUniqueId()).getByOpponent(player).getState() == SENT) {
            DUEL_MAP.get(player.getUniqueId()).setActiveDuel(DUEL_MAP.get(opponent.getUniqueId()).getByOpponent(player));
            GUI_UTIL.getAccept(player).open(player);
            return true;
            // TODO: Accept duel
        }

        Duel duelPlayer = new Duel(player, opponent);

        //DUEL_MAP.get(opponent.getUniqueId()).getDuels().add(new Duel(opponent, player));
        DUEL_MAP.get(player.getUniqueId()).setActiveDuel(duelPlayer);
        DUEL_MAP.get(player.getUniqueId()).getDuels().add(duelPlayer);

        GUI_UTIL.getSettings(player).open(player);
        return false;
    }

}
