package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.Arena;
import com.gb6.duels.objects.Command;
import com.gb6.duels.objects.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.gb6.duels.utils.Constants.ARENA_LIST;

public class ArenaCommand extends Command {

    public ArenaCommand() {
        super("arena", "duels.admin", Requirement.PLAYER);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        if (args.size() == 0) {
            new Message("invalid-arguments").send(player);
            return;
        }
        if (args.get(0).equalsIgnoreCase("create")) {
            if (args.size() != 2) {
                new Message("invalid-arguments").send(player);
                return;
            }

            String name = args.get(1);

            if (Arena.fromString(name).orElse(null) != null) {
                new Message("arena-already-exists").format("arena", name).send(player);
                return;
            }

            ARENA_LIST.add(new Arena(name));
            new Message("arena-created").format("arena", name).send(player);
            return;
        } else if (args.get(0).equalsIgnoreCase("list")) {
            new Message("arena-header").send(player);
            ARENA_LIST.forEach(a -> player.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "->" + ChatColor.RESET + ChatColor.WHITE + " " + a.getName()));
            return;
        } else if (args.get(0).equalsIgnoreCase("remove") && ARENA_LIST.stream().map(Arena::getName).anyMatch(name -> name.equalsIgnoreCase(args.get(1)))) {
            Arena arena = Arena.fromString(args.get(1)).orElse(null);
            ARENA_LIST.remove(arena);
            new Message("arena-removed").send(sender);
            return;
        } else {
            Arena arena = Arena.fromString(args.get(0)).orElse(null);

            if (arena == null) {
                new Message("unknown-arena").format("arena", args.get(1)).send(player);
                return;
            }

            switch (args.get(0).toLowerCase()) {
                case "set-item":
                    if (player.getItemInHand() == null) {
                        new Message("invalid-item").send(player);
                        return;
                    }
                    new Message("item-set").format("arena", arena.getName()).send(player);
                    arena.setItem(player.getItemInHand());
                    return;
                case "spawnpoint":
                    if (args.size() != 4 || (!args.get(2).equalsIgnoreCase("tp") && !args.get(2).equalsIgnoreCase("set")) || !StringUtils.isNumeric(args.get(3)) || (Integer.parseInt(args.get(3)) != 1 && Integer.parseInt(args.get(3)) != 2)) {
                        new Message("invalid-arguments").send(player);
                        return;
                    }
                    switch (args.get(2)) {
                        case "set":
                            new Message("spawnpoint-set").format("index", args.get(3)).format("arena", arena.getName()).send(player);
                            ARENA_LIST.get(ARENA_LIST.indexOf(arena)).setSpawnPoint(Integer.parseInt(args.get(3)), player.getLocation());
                            return;
                        case "tp":
                            player.teleport(arena.getLocation(Integer.parseInt(args.get(3))));
                    }
                    return;
            }
            new Message("no-such-arena").format("arena", args.get(0)).send(player);
        }
        new Message("no-such-arena").format("arena", args.get(0)).send(player);
    }

}
