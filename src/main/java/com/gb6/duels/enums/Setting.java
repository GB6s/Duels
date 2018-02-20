package com.gb6.duels.enums;


import com.gb6.duels.objects.DuelObject;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import static com.gb6.duels.utils.Constants.DUEL_MAP;
import static com.gb6.duels.utils.GeneralUtilities.addGlow;

public enum Setting {
    GAPPLE(2, Material.GOLDEN_APPLE, "Golden Apples"),
    MCMMO(3, Material.DIAMOND_AXE, "MCMMO"),
    POTIONS(4, Material.BREWING_STAND_ITEM, "Potions"),
    BOWS(5, Material.BOW, "Bows"),
    HEALING(6, Material.GHAST_TEAR, "Healing"),
    FOOD(11, Material.COOKED_BEEF, "Food Loss"),
    DROP_ITEMS(12, Material.BONE, "Drop Items"),
    PEARL(13, Material.ENDER_PEARL, "Enderpearl");

    private Material material;
    @Getter private String displayname;
    @Getter private int slot;
    private List<String> loreDisabled = Arrays.asList(ChatColor.RED + ChatColor.BOLD.toString() + "DISABLED", "", ChatColor.GRAY + "Click to toggle this setting");
    private List<String> loreEnabled = Arrays.asList(ChatColor.GREEN + ChatColor.BOLD.toString() + "ENABLED", "", ChatColor.GRAY + "Click to toggle this setting");

    Setting(int slot, Material material, String displayname) {
        this.slot = slot;
        this.material = material;
        this.displayname = displayname;
    }

    public ItemStack getItem(boolean enabled) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + displayname);
        meta.setLore(enabled ? loreEnabled : loreDisabled);
        item.setItemMeta(meta);

        return enabled ? addGlow(item) : item;
    }

    public boolean isEnabled(Player player) {
        DuelObject duelObject = DUEL_MAP.entrySet().stream().filter(s -> s.getValue().getRequester().equals(player)).findFirst().orElse(null).getValue();
        if (duelObject == null) {
            return false;
        }
        return duelObject.getSettings().contains(this);
    }

}
