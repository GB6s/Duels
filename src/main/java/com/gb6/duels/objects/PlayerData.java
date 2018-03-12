package com.gb6.duels.objects;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerData {
    private ItemStack[] armor;
    private ItemStack[] inventory;
    private Location location;
    private Float saturation;
    private Integer expLevel;
    private Integer foodLevel;
    private double health;
    private GameMode gameMode;
    private boolean allowedFlight;
    private Player player;

    public PlayerData(Player player) {
        this.armor = player.getInventory().getArmorContents();
        this.inventory = player.getInventory().getContents();
        this.location = player.getLocation();
        this.saturation = player.getSaturation();
        this.foodLevel = player.getFoodLevel();
        this.expLevel = player.getLevel();
        this.health = player.getHealth();
        this.gameMode = player.getGameMode();
        this.allowedFlight = player.getAllowFlight();
        this.player = player;
    }

    public void restore() {
        player.getInventory().setArmorContents(armor);
        player.getInventory().setContents(inventory);
        player.teleport(location);
        player.setSaturation(saturation);
        player.setLevel(expLevel);
        player.setFoodLevel(foodLevel);
        player.setHealth(health);
        player.setGameMode(gameMode);
        player.setAllowFlight(allowedFlight);
    }
}
