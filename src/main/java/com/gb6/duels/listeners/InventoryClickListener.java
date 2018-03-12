package com.gb6.duels.listeners;

import com.gb6.duels.events.GUIClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static com.gb6.duels.utils.Constants.GUI_MAP;

public class InventoryClickListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        GUI_MAP.forEach((p, g) -> {

            if (event.getInventory() == null || !(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null) {
                return;
            }

            if (g.getCheck() != null && g.getCheck().test(event)) {
                return;
            }

            if (!event.getInventory().getName().equals(g.getInventoryName())) {
                return;
            }

            if (g.isAlwaysCancel()) {
                event.setCancelled(true);
            }
            if (g.getClickEventConsumer() != null) {
                g.getClickEventConsumer().accept(new GUIClickEvent(event, g, null));
            }

//            System.out.println("slot: " + event.getSlot());
//            g.getIconMap().forEach((d, b) -> System.out.println("slot: " + d.toString() + " icon: " + b.toString()));

            if (g.getIconMap().containsKey(event.getSlot())) {
                if (g.getIconMap().get(event.getSlot()).isFiller()) {
                    event.setCancelled(true);
                    return;
                }

                g.getIconMap().get(event.getSlot()).onClick(new GUIClickEvent(event, g, g.getIconMap().get(event.getSlot())));
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        GUI_MAP.forEach((p, g) -> {
            if (event.getInventory() == null || !(event.getPlayer() instanceof Player)) {
                return;
            }

            if (!event.getInventory().getName().equals(g.getInventoryName())) {
                return;
            }

            if (g.getCloseEventConsumer() != null) {
                g.getCloseEventConsumer().accept(event);
            }
        });
    }

//    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
//    public void onInventoryClick(InventoryClickEvent event) {
//        if (event.getInventory() == null || !(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null) {
//            return;
//        }
//
//        if (!DUEL_MAP.containsKey(event.getWhoClicked().getUniqueId())) {
//            return;
//        }
//
//        String inventoryName = event.getInventory().getName();
//        Player player = (Player) event.getWhoClicked();
//        Inventory inventory = event.getClickedInventory();
//        ItemStack itemStack = event.getCurrentItem();
//        Dueler dueler = DUEL_MAP.get(player.getUniqueId());
//        Duel duel = dueler.getActiveDuel();
//        int slot = event.getSlot();
//        boolean top = event.getView().getTopInventory().equals(inventory);
//
//        if (inventoryName.equals("Arena selection") && top) {
//            event.setCancelled(true);
//            Arena arena = ARENA_LIST.stream().filter(a -> a.getSlot() == slot).findFirst().orElse(null);
//
//            if (arena == null || duel == null) {
//                return;
//            }
//
//            Player opponent = duel.getOpponent().getPlayer();
//
//            duel.setArena(arena);
//            duel.setState(DuelState.SENT);
//            new Message("duel-request-sent").format("player", opponent.getName()).send(player);
//            new Message("duel-request-received").format("player", player.getName()).send(opponent);
//            player.closeInventory();
//        } else if (inventoryName.equals("Duel settings") && top) {
//            event.setCancelled(true);
//
//            if (duel == null) {
//                return;
//            }
//
//            if (event.getSlot() == inventory.getSize() - 5) {
//                openArenaGui(player);
//                return;
//            }
//
//            if (slot == inventory.getSize() - 1 || slot == inventory.getSize() - 9) {
//                openKitSelectionGui(player);
//                return;
//            }
//
//            Setting setting = Arrays.stream(Setting.values()).filter(a -> a.getSlot() == slot).findFirst().orElse(null);
//
//            if (setting == null) {
//                return;
//            }
//
//            if (!duel.getSettings().contains(setting)) {
//                duel.getSettings().add(setting);
//            } else {
//                duel.getSettings().remove(setting);
//            }
//
//            inventory.setItem(setting.getSlot(), setting.getItem(duel.getSettings().contains(setting)));
//            inventory.setItem(inventory.getSize() - 5, getConfirmItem(player, false));
//        } else if (inventoryName.equals("Kit selection") && top) {
//            event.setCancelled(true);
//            Kit kit = Kit.fromSlot(slot).orElse(null);
//
//            if (kit != null) {
//                duel.setKit(kit);
//                openSettingGui(player);
//            }
//        } else if (inventoryName.contains("Edit kit:")) {
//            if (KIT_SLOTS.contains(slot)) {
//                event.setCancelled(true);
//            }
//        } else if (inventoryName.contains("'s Duel Settings") && top) {
//            event.setCancelled(true);
//            if (slot != 4) {
//                return;
//            }
//            Duel duel2 = DUEL_MAP.get(duel.getOpponent().getPlayer().getUniqueId()).getActiveDuel();
//            openStakesGui(duel2);
//        } else if (inventoryName.contains("Stakes: ")) {
//            event.setCancelled(true);
//
//        }
//    }
}
