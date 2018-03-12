package com.gb6.duels.builders.item;

import com.gb6.duels.utils.GeneralUtilities;
import com.gb6.duels.utils.GlowUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.GeneralUtilities.color;

public class ItemBuilder extends ItemStack {

    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemBuilder(Material material, int amount, int bit) {
        super(material, amount, (byte) bit);
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(color(displayName));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLores(List<String> lores) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(lores.stream().map(GeneralUtilities::color).collect(Collectors.toList()));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLores(String... lores) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(Arrays.stream(lores).map(GeneralUtilities::color).collect(Collectors.toList()));
        setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addGlow() {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(new GlowUtil(10), 1, true);
        setItemMeta(itemMeta);
        return this;
    }


}
