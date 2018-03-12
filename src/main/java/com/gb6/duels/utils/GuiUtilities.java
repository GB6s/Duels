package com.gb6.duels.utils;

import com.gb6.duels.builders.inventory.GUI;
import com.gb6.duels.builders.inventory.Icon;
import com.gb6.duels.builders.inventory.ToggleableIcon;
import com.gb6.duels.builders.item.ItemBuilder;
import com.gb6.duels.enums.DuelState;
import com.gb6.duels.enums.Setting;
import com.gb6.duels.objects.*;
import com.gb6.duels.threads.StakesThread;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gb6.duels.utils.Constants.*;
import static com.gb6.duels.utils.GeneralUtilities.*;

public class GuiUtilities {

    public GUI getArena(Player player) {
        GUI gui = new GUI(player, "Arena selection", (int) Math.ceil(ARENA_LIST.size() / 9.0) * 9);

        ARENA_LIST.forEach(arena -> {
            Icon icon = new Icon(arena.getItem());

            icon.setCallBack((e) -> {
                Arena arenaOb = ARENA_LIST.stream().filter(a -> a.getSlot() == e.getSlot()).findFirst().orElse(null);

                if (arenaOb == null || e.getDuel() == null) {
                    return;
                }

                Player opponent = e.getDuel().getOpponent().getPlayer();

                e.getDuel().setArena(arenaOb);
                e.getDuel().setState(DuelState.SENT);
                new Message("duel-request-sent").format("player", opponent.getName()).send(e.getPlayer());
                new Message("duel-request-received").format("player", e.getPlayer().getName()).send(opponent);
                e.getPlayer().closeInventory();
            });

            gui.setIcon(arena.getSlot(), icon);
        });

        gui.fill(7);

        return gui;
    }

//    public static void openArenaGui(Player player) {
//        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(ARENA_LIST.size() / 9.0) * 9, "Arena selection");
//        for (int i = 0; i < inventory.getSize(); i++) {
//            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7));
//        }
//        ARENA_LIST.forEach(arenaObject -> inventory.setItem(arenaObject.getSlot(), arenaObject.getItem()));
//        player.closeInventory();
//        player.openInventory(inventory);
//    }

    public GUI getSettings(Player player) {
        Duel duel = DUEL_MAP.computeIfAbsent(player.getUniqueId(), key -> {
            throw new RuntimeException(key + " not found");
        }).getActiveDuel();
        int size = Arrays.stream(Setting.values()).mapToInt(Setting::getSlot).max().orElse(0);
        GUI gui = new GUI(player, "Duel settings", (int) Math.ceil(size / 9.0) + 1);

        Icon kitItem = new Icon(getKitItem(player)).setCallBack((e) -> getKitSelection(player).open(player));

        Icon confirmItem = new Icon(getConfirmItem(player, false)).setCallBack((e) -> getArena(player).open(player));

        gui.fill(7);
        Arrays.stream(Setting.values()).forEach(s -> gui.setIcon(s.getSlot(), new ToggleableIcon(s.getItem(duel.getSettings().contains(s))).setCallBack((e) -> {
            ((ToggleableIcon) e.getIcon()).toggle(e, (a -> {
                if (!duel.getSettings().contains(s)) {
                    duel.getSettings().add(s);
                } else {
                    duel.getSettings().remove(s);
                }

                e.getInventory().setItem(s.getSlot(), s.getItem(((ToggleableIcon) e.getIcon()).isToggled()));
                e.getInventory().setItem(e.getInventory().getSize() - 5, getConfirmItem(player, false));
            }));
        })));
        gui.setIcon(gui.getSize() - 5, confirmItem);
        gui.setIcon(gui.getSize() - 1, kitItem);
        gui.setIcon(gui.getSize() - 9, kitItem);

        return gui;
    }

//    public static void openSettingGui(Player player) {
//        Duel duel = DUEL_MAP.computeIfAbsent(player.getUniqueId(), key -> {
//            throw new RuntimeException(key + " not found");
//        }).getActiveDuel();
//        int size = Arrays.stream(Setting.values()).mapToInt(Setting::getSlot).max().orElse(0);
//        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(size / 9.0) * 9 + 9, "Duel settings");
//
//        for (int i = 0; i < inventory.getSize(); i++) {
//            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7));
//        }
//
//        Arrays.stream(Setting.values()).forEach(s -> inventory.setItem(s.getSlot(), s.getItem(duel.getSettings().contains(s))));
//        inventory.setItem(inventory.getSize() - 5, getConfirmItem(player, false));
//        inventory.setItem(inventory.getSize() - 1, getKitItem(player));
//        inventory.setItem(inventory.getSize() - 9, getKitItem(player));
//        player.closeInventory();
//        player.openInventory(inventory);
//    }

//    public static void openKitSelectionGui(Player player) {
//        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(KIT_LIST.size() / 9.0) * 9, "Kit selection");
//        for (int i = 0; i < inventory.getSize(); i++) {
//            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
//        }
//        KIT_LIST.forEach(s -> inventory.setItem(s.getSlot(), s.getItemStack()));
//        player.closeInventory();
//        player.openInventory(inventory);
//    }

    public GUI getKitSelection(Player player) {
        GUI gui = new GUI(player, "Kit section", (int) Math.ceil(KIT_LIST.size() / 9.0));

        gui.fill(7);
        KIT_LIST.forEach(s -> gui.setIcon(s.getSlot(), new Icon(s.getItemStack())));

        return gui;
    }

    public GUI getKitEdit(Player player, Kit kit) {
        GUI gui = new GUI(player, "Edit kit: " + kit.getName(), 6);

        gui.fill(7, KIT_SLOTS);
        gui.setAlwaysCancel(false);
        gui.setCheck(null);

        if (kit.getArmorContents() != null) {
            gui.setIcon(45, new Icon(kit.getArmorContents()[0]));
            gui.setIcon(46, new Icon(kit.getArmorContents()[1]));
            gui.setIcon(47, new Icon(kit.getArmorContents()[2]));
            gui.setIcon(48, new Icon(kit.getArmorContents()[3]));
        }

        if (kit.getInventoryContents() != null) {
            for (int i = 0; i < 36; i++) {
                gui.setIcon(i, new Icon(kit.getInventoryContents()[i]));
            }
        }

        gui.setIcon(53, new Icon(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 4).setDisplayName(ChatColor.YELLOW + "Save Changes")).setCallBack((e) -> e.getPlayer().closeInventory()));

        gui.setCloseEventConsumer((e) -> {
            String inventoryName = e.getInventory().getName();
            Kit kitCurrent = Kit.fromString(inventoryName.substring(inventoryName.lastIndexOf(" ") + 1)).orElseThrow(NullPointerException::new);
            ItemStack[] inventoryContents = new ItemStack[36];
            ItemStack[] armorContents = new ItemStack[4];

            for (int i = 0; i < 36; i++) {
                inventoryContents[i] = e.getInventory().getContents()[i];
            }

            armorContents[0] = isArmor(e.getInventory().getContents()[45], "boots") ? e.getInventory().getContents()[45] : null;
            armorContents[1] = isArmor(e.getInventory().getContents()[46], "leggings") ? e.getInventory().getContents()[46] : null;
            armorContents[2] = isArmor(e.getInventory().getContents()[47], "chestplate") ? e.getInventory().getContents()[47] : null;
            armorContents[3] = isArmor(e.getInventory().getContents()[48], "helmet") ? e.getInventory().getContents()[48] : null;

            kitCurrent.setInventoryContents(inventoryContents);
            kitCurrent.setArmorContents(armorContents);

            new Message("kit-saved").format("kit", kitCurrent.getName()).send(player);

        });

        return gui;
    }

//    public static void openKitEditGui(Player player, Kit kit) {
//        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Edit kit: " + kit.getName());
//        KIT_SLOTS.forEach(s -> inventory.setItem(s, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7)));
//
//        if (kit.getArmorContents() != null) {
//            inventory.setItem(45, kit.getArmorContents()[0]);
//            inventory.setItem(46, kit.getArmorContents()[1]);
//            inventory.setItem(47, kit.getArmorContents()[2]);
//            inventory.setItem(48, kit.getArmorContents()[3]);
//        }
//
//        if (kit.getInventoryContents() != null) {
//            for (int i = 0; i < 36; i++) {
//                inventory.setItem(i, kit.getInventoryContents()[i]);
//            }
//        }
//
//
//        player.closeInventory();
//        player.openInventory(inventory);
//    }

//    public static void openAcceptGui(Player opponent, Player requester) {
//        Inventory inventory = Bukkit.createInventory(null, 9, requester.getName() + "'s Duel Settings");
//        for (int i = 0; i < 9; i++) {
//            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
//        }
//
//        inventory.setItem(4, getConfirmItem(requester, true));
//        opponent.openInventory(inventory);
//

    public GUI getAccept(Player player) {
        GUI gui = new GUI(player, player.getName() + "'s Duel Settings", 1);
        Icon icon = new Icon(getConfirmItem(player, true));

        icon.setCallBack((event) -> {
            Duel duel = DUEL_MAP.get(DUEL_MAP.get(event.getPlayer().getUniqueId()).getOpponent().getPlayer().getUniqueId()).getActiveDuel();
            getStakes(duel).open(player);
        });
        gui.fill(7);
        gui.setIcon(4, icon);

        return gui;
    }

    public GUI getStakes(Duel duel) {
        GUI gui = new GUI(duel.getRequester().getPlayer(), "Stakes:" + duel.getOpponent().getPlayer().getName() + "/" + duel.getRequester().getPlayer().getName(), 3);
        ToggleableIcon icon = new ToggleableIcon(getStakeItem(false));

        icon.setCallBack((e) -> {
            icon.toggle(e, (event) -> e.getInventory().setItem(e.getSlot(), getStakeItem(icon.isToggled())));

            if (e.getGui().getToggles().stream().filter(ToggleableIcon::isToggled).count() == 2) {
                new StakesThread(INSTANCE, e.getDuel(), e.getInventory(), e.getPlayer()).runTaskTimer(INSTANCE, 0, 20L);
            }
        });

        gui.setClickEventConsumer((e) -> {
            List<Integer> slots = e.getDuel().getRequester().getPlayer() == e.getPlayer() ? e.getDuel().getRequester().getSlots() : e.getDuel().getOpponent().getSlots();

            if (e.isTop() && slots.contains(e.getSlot())) {
                e.getInventory().setItem(e.getSlot(), null);
                e.getPlayer().getInventory().addItem(e.getCurrentItem());
            }


            if (!e.isTop()) {
                Optional<Integer> emptySlot = getFirstEmpty(slots, e.getView().getTopInventory());
                if (!emptySlot.isPresent()) {
                    return;
                }
                e.getPlayer().getInventory().setItem(e.getSlot(), null);
                e.getView().getTopInventory().setItem(emptySlot.get(), e.getCurrentItem());
            }
        });

        gui.fill(7, 4, 13, 22);
        gui.setIcon(0, icon);
        gui.setIcon(8, icon);

        return gui;
    }

//    public static void openStakesGui(Duel duel) {
//        String title = "Stakes: " + duel.getOpponent().getPlayer().getName() + "/" + duel.getRequester().getPlayer().getName();
//        title = title.substring(0, Math.min(title.length(), 32));
//        Inventory stakesInventory = Bukkit.createInventory(null, 3 * 9, title);
//        ItemStack fill = getItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), "", Arrays.asList(""));
//
//        stakesInventory.setItem(0, getStakeItem(false));
//        stakesInventory.setItem(8, getStakeItem(false));
//        stakesInventory.setItem(4, fill);
//        stakesInventory.setItem(13, fill);
//        stakesInventory.setItem(22, fill);
//
//        duel.getOpponent().getPlayer().openInventory(stakesInventory);
//        duel.getRequester().getPlayer().openInventory(stakesInventory);
//    }

}
