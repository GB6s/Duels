package com.gb6.duels.builders.inventory;

import com.gb6.duels.events.GUIClickEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.Constants.*;

public class GUI implements Listener {
    @Getter private Map<Integer, Icon> iconMap = new HashMap<>();
    @Getter @Setter private String inventoryName;
    @Getter @Setter private int size;
    @Setter @Getter private Consumer<GUIClickEvent> clickEventConsumer;
    @Setter @Getter private Consumer<InventoryCloseEvent> closeEventConsumer;
    @Setter @Getter private boolean alwaysCancel = true;
    @Getter @Setter Predicate<InventoryClickEvent> check = event -> !DUEL_MAP.containsKey(event.getWhoClicked().getUniqueId());

    public GUI() {

    }

    public GUI(Player player, String inventoryName, int rows) {
        this.inventoryName = inventoryName;
        this.size = rows * 9;
        GUI_MAP.put(player, this);
    }

    public void setIcon(int slot, Icon icon) {
        iconMap.put(slot, icon);
    }

    public void fill(int color, int... slots) {
        Arrays.stream(slots).forEach(s -> iconMap.putIfAbsent(s, new Icon(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color))));
    }

    public void fill(int color) {
        for (int i = 0; i < size; i++) {
            iconMap.putIfAbsent(i, new Icon().setFiller());
        }
    }

    public void fill(int color, List<Integer> slots) {
        slots.forEach(s -> iconMap.putIfAbsent(s, new Icon(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color))));
    }

    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, size, inventoryName.substring(0, Math.min(inventoryName.length(), 32)));
        iconMap.forEach((k, v) -> inv.setItem(k, v.getItemStack()));
        return inv;
    }

    public List<ToggleableIcon> getToggles() {
        return iconMap.entrySet().stream()
                .filter(t -> t.getValue() instanceof ToggleableIcon)
                .map(Map.Entry::getValue)
                .map(t -> (ToggleableIcon) t)
                .collect(Collectors.toList());
    }

    public void open(Player player) {
        player.openInventory(this.getInventory());
    }

}
