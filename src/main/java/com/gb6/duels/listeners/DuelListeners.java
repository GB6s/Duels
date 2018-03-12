package com.gb6.duels.listeners;

import com.gb6.duels.enums.DuelState;
import com.gb6.duels.objects.Dueler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.gb6.duels.utils.Constants.DUEL_MAP;

public class DuelListeners implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        if (!DUEL_MAP.containsKey(event.getDamager().getUniqueId()) || !DUEL_MAP.containsKey(event.getEntity().getUniqueId())) {
            return;
        }

        Dueler damager = DUEL_MAP.get(event.getDamager().getUniqueId());
        Dueler receiver = DUEL_MAP.get(event.getEntity().getUniqueId());

        if (damager.isSpectating() || receiver.isSpectating()) {
            event.setCancelled(true);
            return;
        }

        if (damager.getActiveDuel() == null || receiver.getActiveDuel() == null) {
            return;
        }

        if (!damager.getActiveDuel().equals(receiver.getActiveDuel())) {
            return;
        }

        if (damager.getActiveDuel().getState() != DuelState.ACTIVE) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(false);

    }
}
