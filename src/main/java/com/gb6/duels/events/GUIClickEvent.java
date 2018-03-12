package com.gb6.duels.events;

import com.gb6.duels.builders.inventory.GUI;
import com.gb6.duels.builders.inventory.Icon;
import com.gb6.duels.objects.Duel;
import com.gb6.duels.objects.Dueler;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import static com.gb6.duels.utils.Constants.DUEL_MAP;

public class GUIClickEvent extends InventoryClickEvent {

    @Getter private String inventoryName;
    @Getter private Player player;
    @Getter private Dueler dueler;
    @Getter private Duel duel;
    @Getter private boolean top;
    @Getter private GUI gui;
    @Getter private Icon icon;

    public GUIClickEvent(InventoryClickEvent event, GUI gui, Icon icon) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
        this.inventoryName = event.getInventory().getName();
        this.player = (Player) event.getWhoClicked();
        this.dueler = DUEL_MAP.get(player.getUniqueId());
        this.duel = dueler != null ? dueler.getActiveDuel() : null;
        this.top = event.getView().getTopInventory().equals(event.getClickedInventory());
        this.gui = gui;
        this.icon = icon;
    }
}
