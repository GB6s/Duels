package com.gb6.duels.objects;

import com.gb6.duels.utils.GeneralUtilities;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gb6.duels.utils.Constants.ARENA_LIST;

public class Arena {
    private static final AtomicInteger count = new AtomicInteger(-1);
    @Getter @Setter private String name;
    @Getter @Setter private boolean beingPlayed;
    @Getter @Setter private ItemStack item;
    @Getter @Setter private int slot;
    private Location spawnPointOne;
    private Location spawnPointTwo;

    public Arena(String name) {
        this.name = name;
        this.item = GeneralUtilities.getItem(new ItemStack(Material.DIRT), ChatColor.YELLOW + name, Arrays.asList(ChatColor.GRAY + "Click to select this arena."));
        slot = count.incrementAndGet();
    }

    public static Optional<Arena> fromString(String text) {
        return ARENA_LIST.stream().filter(a -> a.getName().equals(text)).findFirst();
    }

    public void setSpawnPoint(int number, Location location) {
        switch (number) {
            case 1:
                this.spawnPointOne = location;
                return;
            case 2:
                this.spawnPointTwo = location;
        }
    }

    public Location getLocation(int number) {
        return number == 1 ? spawnPointOne : spawnPointTwo;
    }


}
