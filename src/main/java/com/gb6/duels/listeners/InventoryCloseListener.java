package com.gb6.duels.listeners;

import org.bukkit.event.Listener;

public class InventoryCloseListener implements Listener {

//    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
//    public void onInventoryClose(InventoryCloseEvent event) {
//        String inventoryName = event.getInventory().getName();
//        Player player = (Player) event.getPlayer();
//
//        if (inventoryName.contains("Edit kit:")) {
//            Kit kit = Kit.fromString(inventoryName.substring(inventoryName.lastIndexOf(" ") + 1)).orElseThrow(NullPointerException::new);
//            ItemStack[] inventoryContents = new ItemStack[36];
//            ItemStack[] armorContents = new ItemStack[4];
//
//            for (int i = 0; i < 36; i++) {
//                inventoryContents[i] = event.getInventory().getContents()[i];
//            }
//
//            armorContents[0] = isArmor(event.getInventory().getContents()[45], "boots") ? event.getInventory().getContents()[45] : null;
//            armorContents[1] = isArmor(event.getInventory().getContents()[46], "leggings") ? event.getInventory().getContents()[46] : null;
//            armorContents[2] = isArmor(event.getInventory().getContents()[47], "chestplate") ? event.getInventory().getContents()[47] : null;
//            armorContents[3] = isArmor(event.getInventory().getContents()[48], "helmet") ? event.getInventory().getContents()[48] : null;
//
//            kit.setInventoryContents(inventoryContents);
//            kit.setArmorContents(armorContents);
//
//            new Message("kit-saved").format("kit", kit.getName()).send(player);
//        } else if (inventoryName.contains("'s Duel Settings")) {
//            Player requester = Bukkit.getPlayerExact(inventoryName.split("'")[0]);
//            //DUEL_MAP.remove(requester.getUniqueId());
//            new Message("requester-denied").format("player", player.getName()).send(requester);
//            new Message("opponent-denied").format("player", requester.getName()).send(player);
//        }
//    }
}
