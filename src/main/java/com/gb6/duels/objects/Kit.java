package com.gb6.duels.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gb6.duels.utils.Constants.KIT_LIST;
import static com.gb6.duels.utils.GeneralUtilities.getItem;

public class Kit {
    private static final AtomicInteger count = new AtomicInteger(-1);
    @Getter @Setter private int slot;
    @Getter @Setter private ItemStack[] inventoryContents;
    @Getter @Setter private ItemStack[] armorContents;
    @Getter @Setter private String name;
    @Getter @Setter private ItemStack itemStack;

    public Kit(String name) {
        this.name = name;
        this.slot = count.incrementAndGet();
        this.itemStack = getItem(new ItemStack(Material.DIRT), ChatColor.YELLOW + name, Arrays.asList(ChatColor.GRAY + "Click to select this kit"));
    }

    public void giveTo(Player player) {
        if (armorContents != null) {
            player.getInventory().setArmorContents(armorContents);
        }

        if (inventoryContents != null) {
            player.getInventory().setContents(inventoryContents);
        }
    }


    public static Optional<Kit> fromString(String text) {
        return KIT_LIST.stream().filter(a -> a.getName().equals(text)).findFirst();
    }

    public static Optional<Kit> fromSlot(int slot) {
        return KIT_LIST.stream().filter(a -> a.getSlot() == slot).findFirst();
    }

}
