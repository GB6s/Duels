package com.gb6.duels.objects;

import com.gb6.duels.enums.DuelState;
import com.gb6.duels.enums.Setting;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.gb6.duels.enums.DuelState.PENDING;

public class DuelObject {
    @Getter private Player requester;
    @Getter private Player opponent;
    @Getter @Setter private ArenaObject arena;
    @Getter @Setter private List<Setting> settings = new ArrayList<>();
    @Getter @Setter private DuelState state = PENDING;

    public DuelObject(Player requester, Player opponent) {
        this.requester = requester;
        this.opponent = opponent;
    }

    public void handle() {
        requester.teleport(arena.getLocation(0));
        opponent.teleport(arena.getLocation(1));
    }
}
