package com.gb6.duels.objects;

import com.gb6.duels.enums.DuelState;
import com.gb6.duels.enums.Setting;
import com.gb6.duels.threads.DuelStartThread;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.gb6.duels.enums.DuelState.*;
import static com.gb6.duels.utils.Constants.*;
import static com.gb6.duels.utils.GeneralUtilities.removeEffects;

public class Duel {
    //    @Getter private Player requester;
//    @Getter private Player opponent;
//    @Getter private Dueler duelerRequester;
//    @Getter private Dueler duelerOpponent;
    @Getter @Setter private Arena arena;
    @Getter @Setter private List<Setting> settings = new ArrayList<>();
    @Getter @Setter private DuelState state = PENDING;
    @Getter @Setter private Kit kit;
    //    @Getter @Setter private ArrayList<Integer> slotsRequester;
//    @Getter @Setter private ArrayList<Integer> slotsOpponent;
//    @Getter @Setter private int confirmSlotRequester;
//    @Getter @Setter private int confirmSlotOpponent;
//    @Getter @Setter private ItemStack[] stakesRequester;
//    @Getter @Setter private ItemStack[] stakesOpponent;
    @Getter @Setter private Person requester;
    @Getter @Setter private Person opponent;


    public Duel(Player requester, Player opponent) {
        this.requester = new Person(getDueler(requester), requester, new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(9);
            add(10);
            add(11);
            add(12);
            add(18);
            add(19);
            add(20);
            add(21);
        }}, 0);

        this.opponent = new Person(getDueler(opponent), opponent, new ArrayList<Integer>() {{
            add(5);
            add(6);
            add(7);
            add(14);
            add(15);
            add(16);
            add(17);
            add(23);
            add(24);
            add(25);
            add(26);
        }}, 8);

//        this.requester = requester;
//        this.opponent = opponent;
//        this.duelerRequester = getDueler(requester);
//        this.duelerOpponent = getDueler(opponent);

//        slotsRequester = new ArrayList<Integer>() {{
//            add(1);
//            add(2);
//            add(3);
//            add(9);
//            add(10);
//            add(11);
//            add(12);
//            add(18);
//            add(19);
//            add(20);
//            add(21);
//        }};
//        confirmSlotRequester = 0;
//
//        slotsOpponent = new ArrayList<Integer>() {{
//            add(5);
//            add(6);
//            add(7);
//            add(14);
//            add(15);
//            add(16);
//            add(17);
//            add(23);
//            add(24);
//            add(25);
//            add(26);
//        }};
//        confirmSlotOpponent = 8;
        this.requester.getDueler().getDuels().add(this);
        this.opponent.getDueler().getDuels().add(this);
//        duelerRequester.getDuels().add(this);
//        duelerOpponent.getDuels().add(this);
        this.kit = KIT_LIST.get(0);
    }

    public void start() {
        requester.getDueler().setInventory(requester.getPlayer().getInventory());
        opponent.getDueler().setInventory(opponent.getPlayer().getInventory());

        requester.getPlayer().teleport(arena.getLocation(0));
        opponent.getPlayer().teleport(arena.getLocation(1));

        kit.giveTo(requester.getPlayer());
        kit.giveTo(opponent.getPlayer());

        arena.setBeingPlayed(true);
        setState(COUNTDOWN);

        PLAYER_DATA.put(opponent.getPlayer().getUniqueId(), new PlayerData(opponent.getPlayer()));
        PLAYER_DATA.put(requester.getPlayer().getUniqueId(), new PlayerData(requester.getPlayer()));

        removeEffects(requester.getPlayer());
        removeEffects(opponent.getPlayer());

        //TODO: freeze players

        new DuelStartThread(this);
    }

    public void end(Player loser, boolean timeUp) {
        // Check if loser == null to make sure the winner actually killed him
    }

    public int getPlayerCount() {
        if (requester == null && opponent == null) {
            return 0;
        } else if (requester != null && opponent != null) {
            return 2;
        }
        return 1;

    }

    private Dueler getDueler(Player player) {
        if (!DUEL_MAP.containsKey(player.getUniqueId())) {
            DUEL_MAP.put(player.getUniqueId(), new Dueler(player));
        }
        return DUEL_MAP.get(player.getUniqueId());
    }

}



