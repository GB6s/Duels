package com.gb6.duels.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Dueler {

    @Getter @Setter private Inventory inventory;
    @Getter @Setter private boolean stakeAccepted;
    @Getter @Setter private Player player;
    @Getter @Setter private List<Duel> duels = new ArrayList<>();
    @Getter @Setter private Duel activeDuel;
    @Getter @Setter private boolean isSpectating;

    public Dueler(Player player) {
        this.player = player;
    }

    public Dueler(Player player, Duel duel) {
        this.player = player;
        this.duels.add(duel);
    }

    public Duel getByOpponent(Player player) {
        return duels.stream().filter(d -> d.getOpponent().getPlayer().equals(player)).findFirst().orElse(null);
    }

    public Duel getByRequester(Player player) {
        return duels.stream().filter(d -> d.getRequester().getPlayer().equals(player)).findFirst().orElse(null);
    }

    public Player getOpponent() {
        return activeDuel.getRequester().getPlayer() == player ? activeDuel.getOpponent().getPlayer() : activeDuel.getRequester().getPlayer();
    }

}
