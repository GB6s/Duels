package com.gb6.duels.threads;

import com.gb6.duels.Duels;
import com.gb6.duels.objects.Duel;
import com.gb6.duels.utils.GeneralUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

import static com.gb6.duels.enums.DuelState.ACCEPTED;
import static com.gb6.duels.utils.Constants.DUEL_MAP;
import static com.gb6.duels.utils.GeneralUtilities.getStakeItem;

public class StakesThread extends BukkitRunnable implements Listener {

    private Duel duel;
    private Inventory inventory;
    private Player player;
    private static int counter = 5;

    public StakesThread(Duels duels, Duel duel, Inventory inventory, Player player) {
        this.duel = duel;
        this.inventory = inventory;
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, duels);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null || !(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getInventory().getName().contains("Stakes: ")) {
            return;
        }

        event.getInventory().setItem(duel.getRequester().getConfirmSlot(), getStakeItem(false));
        event.getInventory().setItem(duel.getOpponent().getConfirmSlot(), getStakeItem(false));

        ItemStack filler = inventory.getItem(4);

        filler.setAmount(1);
        inventory.setItem(4, filler);
        inventory.setItem(13, filler);
        inventory.setItem(22, filler);

        this.cancel();
    }

    @Override
    public void run() {
        if (counter != 0) {
            inventory.setItem(4, getItem(counter));
            inventory.setItem(13, getItem(counter));
            inventory.setItem(22, getItem(counter));

            counter--;
        } else {
            duel.start();
            DUEL_MAP.get(player.getUniqueId()).getActiveDuel().setState(ACCEPTED);
            this.cancel();
        }
    }

    private ItemStack getItem(int time) {
        Byte bite = 0;
        switch (time) {
            case 5:
                bite = 7;
                break;
            case 4:
            case 3:
                bite = 1;
                break;
            case 2:
            case 1:
                bite = 14;
                break;
        }
        return GeneralUtilities.getItem(new ItemStack(Material.STAINED_GLASS_PANE, time, bite), ChatColor.YELLOW + ChatColor.BOLD.toString() + time, Arrays.asList(""));
    }
}
