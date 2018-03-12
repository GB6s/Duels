package com.gb6.duels.utils;

import com.gb6.duels.enums.Setting;
import com.gb6.duels.objects.Duel;
import com.gb6.duels.objects.Dueler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.Constants.DUEL_MAP;

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
        meta.setDisplayName(color(displayname));
        meta.setLore(lore.stream().map(GeneralUtilities::color).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getKitItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        Duel duel = DUEL_MAP.get(player.getUniqueId()).getActiveDuel();

        lore.add(duel.getKit().getName().equalsIgnoreCase("default") ? ChatColor.GREEN + ChatColor.BOLD.toString() + "None" : ChatColor.GREEN + ChatColor.BOLD.toString() + duel.getKit().getName());
        lore.add(ChatColor.GRAY + "Click to select a kit.");
        meta.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Kit");
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        addGlow(itemStack);
        return itemStack;
    }

    public static ItemStack getConfirmItem(Player player, boolean accept) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        Dueler dueler = DUEL_MAP.computeIfAbsent(player.getUniqueId(), key -> {
            throw new RuntimeException(key + " not found");
        });
        String add = dueler.getActiveDuel().getKit().getName().equalsIgnoreCase("default") ? ChatColor.RED + "NONE" : ChatColor.GREEN + dueler.getActiveDuel().getKit().getName();
        String displayName = accept ? ChatColor.YELLOW + ChatColor.BOLD.toString() + "ACCEPT DUEL" : ChatColor.YELLOW + ChatColor.BOLD.toString() + "Confirm Settings";
        String bottom = accept ? ChatColor.GRAY + "Click to accept & access the stakes menu" : ChatColor.GRAY + "Click to go to arena selection";

        Arrays.stream(Setting.values()).forEach(s -> {
            String addon = s.isEnabled(player, dueler.getOpponent()) ? ChatColor.GREEN + " ENABLED" : ChatColor.RED + " DISABLED";
            lore.add(ChatColor.YELLOW + s.getDisplayname() + addon);
        });
        lore.add(ChatColor.YELLOW + "Kit " + add);
        if (accept) {
            lore.add(ChatColor.YELLOW + "Arena " + ChatColor.GREEN + dueler.getActiveDuel().getArena().getName());
        }
        lore.add("");
        lore.add(bottom);

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static boolean isArmor(ItemStack itemStack, String type) {
        if (itemStack == null) {
            return false;
        }

        String[] split = itemStack.getType().toString().split("_");

        if (split.length != 2) {
            return false;
        }

        if (split[1].equalsIgnoreCase(type)) {
            return true;
        }

        return false;
    }

    public static void removeEffects(Player player) {
        player.getActivePotionEffects().forEach(s -> player.removePotionEffect(s.getType()));
    }

    public static ItemStack getStakeItem(boolean accepted) {
        return getItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) (accepted ? 5 : 14)), accepted ? ChatColor.YELLOW + "Click to ACCEPT Stake" : ChatColor.YELLOW + "Stake ACCEPTED", Arrays.asList(""));
    }

    public static Optional<Integer> getFirstEmpty(List<Integer> slots, Inventory inventory) {
        //slots.stream().forEach(System.out::println);
        return slots.stream().filter(s -> inventory.getItem(s) == null).findFirst();
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
