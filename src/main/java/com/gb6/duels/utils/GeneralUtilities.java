package com.gb6.duels.utils;

import com.gb6.duels.enums.Setting;
import com.gb6.duels.objects.DuelObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.Constants.MSG_UTIL;

public class GeneralUtilities {

    public static ItemStack addGlow(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        GlowUtil glow = new GlowUtil(10);
        itemMeta.addEnchant(glow, 1, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getItem(ItemStack itemStack, String displayname, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MSG_UTIL.color(displayname));
        meta.setLore(lore.stream().map(MSG_UTIL::color).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getConfirmItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Confirm Settings");
        List<String> lore = new ArrayList<>();
        Arrays.stream(Setting.values()).forEach(s -> {
                String add = s.isEnabled(player) ? ChatColor.GREEN + " ENABLED" : ChatColor.RED + " DISABLED";
                lore.add(ChatColor.YELLOW + s.getDisplayname() + add);
        });

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


}
