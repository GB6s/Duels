package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.ArenaObject;
import com.gb6.duels.objects.CommandObject;
import com.gb6.duels.objects.MessageObject;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.plugin2.message.Message;

import java.util.List;

import static com.gb6.duels.utils.Constants.ARENA_LIST;
import static com.gb6.duels.utils.Constants.MSG_UTIL;

public class ArenaCommand extends CommandObject {

    public ArenaCommand() {
        super("arena", "duels.admin", Requirement.PLAYER);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        if (args.size() == 0) {
            MSG_UTIL.getMessage("invalid-arguments").color().send(player);
            return;
        }
        if (args.get(0).equalsIgnoreCase("create")) {
            if (args.size() != 2) {
                MSG_UTIL.getMessage("invalid-arguments").color().send(player);
                return;
            }
            String name = args.get(1);
            ARENA_LIST.add(new ArenaObject(name));
            MSG_UTIL.getMessage("arena-created").color().send(player);
            return;
        } else if (args.get(0).equalsIgnoreCase("list")) {
            new MessageObject("&bArenas:").color().send(player);
            ARENA_LIST.forEach(a -> player.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "->" + ChatColor.RESET + ChatColor.WHITE + " " + a.getName()));
            return;
        } else if(args.get(0).equals("remove") && ARENA_LIST.stream().map(ArenaObject::getName).anyMatch(name -> name.equals(args.get(1)))) {
            ArenaObject arenaObject = ArenaObject.fromString(args.get(1)).orElse(null);
            ARENA_LIST.remove(arenaObject);
            MSG_UTIL.getMessage("arena-removed").color().send(sender);
            return;
        } else if (ARENA_LIST.stream().map(ArenaObject::getName).anyMatch(name -> name.equals(args.get(0)))) {
            if (args.size() != 4 || !args.get(1).equalsIgnoreCase("spawnpoint") || (!args.get(2).equalsIgnoreCase("tp") && !args.get(2).equalsIgnoreCase("set")) || !StringUtils.isNumeric(args.get(3)) || (Integer.parseInt(args.get(3)) != 1 && Integer.parseInt(args.get(3)) != 2)) {
                MSG_UTIL.getMessage("invalid-arguments").color().send(player);
                return;
            }

            ArenaObject arenaObject = ArenaObject.fromString(args.get(0)).orElse(null);

            if (args.get(2).equalsIgnoreCase("set")) {
                MSG_UTIL.getMessage("spawnpoint-set").color().format("%index%", args.get(3)).format("%arena%", arenaObject.getName()).send(player);
                ARENA_LIST.get(ARENA_LIST.indexOf(arenaObject)).setSpawnPoint(Integer.parseInt(args.get(3)), player.getLocation());
            } else {
                player.teleport(arenaObject.getLocation(Integer.parseInt(args.get(3))));
            }
            return;
        }
        MSG_UTIL.getMessage("no-such-arena").color().format("%arena%", args.get(0)).send(player);
    }

}
