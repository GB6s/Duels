package com.gb6.duels.commands;

import com.gb6.duels.enums.Requirement;
import com.gb6.duels.objects.Command;
import com.gb6.duels.objects.Kit;
import com.gb6.duels.objects.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.gb6.duels.utils.Constants.*;

public class KitsCommand extends Command {

    public KitsCommand() {
        super("kits", "duels.admin", Requirement.PLAYER);
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

            if (Kit.fromString(name).orElse(null) != null) {
                new Message("kit-already-exists").format("kit", name).send(player);
                return;
            }

            Kit kit = new Kit(name);

            kit.setArmorContents(player.getInventory().getArmorContents());
            kit.setInventoryContents(player.getInventory().getContents());
            KIT_LIST.add(kit);
            new Message("kit-created").format("kit", name).send(player);
            GUI_UTIL.getKitEdit(player, kit).open(player);
            return;
        } else if (args.get(0).equalsIgnoreCase("list")) {
            new Message("&bKits:").send(player);
            KIT_LIST.forEach(a -> player.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "->" + ChatColor.RESET + ChatColor.WHITE + " " + a.getName()));
            return;
        } else if (args.size() == 2) {
            Kit kit = Kit.fromString(args.get(1)).orElse(null);

            if (kit == null) {
                new Message("unknown-kit").format("kit", args.get(1)).send(player);
                return;
            }

            switch (args.get(0).toLowerCase()) {
                case "edit":
                    GUI_UTIL.getKitEdit(player, kit).open(player);
                    return;
                case "get":
                    kit.giveTo(player);
                    new Message("kit-get").send(player);
                    return;
                case "set":
                    kit.setInventoryContents(player.getInventory().getContents());
                    kit.setArmorContents(player.getInventory().getArmorContents());
                    new Message("kit-set").send(player);
                    return;
                case "delete":
                case "remove":
                    KIT_LIST.remove(kit);
                    new Message("kit-removed").format("kit", kit.getName()).send(player);
                    return;
            }
        }

        new Message("invalid-arguments").send(player);
    }
}


