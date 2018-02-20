package com.gb6.duels.listeners;

import com.gb6.duels.enums.DuelState;
import com.gb6.duels.enums.Setting;
import com.gb6.duels.objects.ArenaObject;
import com.gb6.duels.objects.DuelObject;
import com.gb6.duels.utils.GuiUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

import static com.gb6.duels.utils.Constants.*;
import static com.gb6.duels.utils.GeneralUtilities.getConfirmItem;

public class InventoryClickListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null || !(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null) {
            return;
        }

        int slot = event.getSlot();
        String inventoryName = event.getInventory().getName();
        Player requester = (Player) event.getWhoClicked();

        if (inventoryName.equals("Arena selection")) {

            event.setCancelled(true);
            ArenaObject arenaObject = ARENA_LIST.stream().filter(a -> a.getSlot() == slot).findFirst().orElse(null);

            if (arenaObject == null) {
                //System.out.println("Please report this to GB6: ERROR_CODE(1)");
                return;
            }

            if (!DUEL_MAP.containsKey(event.getWhoClicked().getUniqueId())) {
                System.out.println("Please report this to GB6: ERROR_CODE(2)");
                return;
            }

            Player opponent = DUEL_MAP.get(requester.getUniqueId()).getOpponent();

            DUEL_MAP.get(requester.getUniqueId()).setArena(arenaObject);
            DUEL_MAP.get(requester.getUniqueId()).setState(DuelState.SENT);
            MSG_UTIL.getMessage("duel-request-sent").color().format("%player%", opponent.getName()).send(requester);
            MSG_UTIL.getMessage("duel-request-received").color().format("%player%", requester.getName()).send(opponent);
            requester.closeInventory();
        } else if (inventoryName.equals("Duel settings")) {
            event.setCancelled(true);

            if(event.getSlot() == event.getInventory().getSize() - 5) {
                GuiUtilities.openArenaGui(requester);
                return;
            }

            Setting setting = Arrays.stream(Setting.values()).filter(a -> a.getSlot() == slot).findFirst().orElse(null);
            DuelObject duelObject = DUEL_MAP.get(event.getWhoClicked().getUniqueId());

            if (setting == null) {
                System.out.println("Please report this to GB6: ERROR_CODE(1)");
                return;
            }

            if (!duelObject.getSettings().contains(setting)) {
                duelObject.getSettings().add(setting);
            } else {
                duelObject.getSettings().remove(setting);
            }

            event.getInventory().setItem(setting.getSlot(), setting.getItem(duelObject.getSettings().contains(setting)));
            event.getInventory().setItem(event.getInventory().getSize() - 5, getConfirmItem(requester));
        }
    }
}
