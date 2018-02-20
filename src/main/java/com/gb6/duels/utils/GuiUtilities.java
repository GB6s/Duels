package com.gb6.duels.utils;

import com.gb6.duels.enums.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static com.gb6.duels.utils.Constants.ARENA_LIST;
import static com.gb6.duels.utils.GeneralUtilities.getConfirmItem;

public class GuiUtilities {
    public static void openArenaGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(ARENA_LIST.size() / 9.0) * 9, "Arena selection");
        ARENA_LIST.forEach(arenaObject -> inventory.setItem(arenaObject.getSlot(), arenaObject.getItem()));
        player.openInventory(inventory);
    }

    public static void openSettingGui(Player player) {
        int size = Arrays.stream(Setting.values()).mapToInt(Setting::getSlot).max().orElse(0);
        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(size / 9.0) * 9 + 9, "Duel settings");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
        }
        Arrays.stream(Setting.values()).forEach(s -> inventory.setItem(s.getSlot(), s.getItem(false)));
        inventory.setItem(inventory.getSize() - 5, getConfirmItem(player));
        player.openInventory(inventory);
    }

    public static void openKitGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil(ARENA_LIST.size() / 9.0) * 9, "Kit selection");
    }
}
