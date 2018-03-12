package com.gb6.duels.threads;

import com.gb6.duels.objects.Duel;
import org.bukkit.scheduler.BukkitRunnable;

import static com.gb6.duels.utils.Constants.CFM;

public class DuelTimeThread extends BukkitRunnable {

    private Duel duel;
    private int duelTime;

    DuelTimeThread(Duel duel) {
        this.duelTime = CFM.getInteger("duel-settings.time-in-min") * 60;
        this.duel = duel;
    }

    @Override
    public void run() {
        int players = duel.getPlayerCount();

        if (players == 1) {
            this.cancel();// just need to cancel if arena size gets to 1, start duel thread will start the rewards.
        }

        if (duelTime > 0 && players == 2) {
            //TAB.sendActionbar(duel.getOpponent(), duel.getRequester(), CFM.getString("titles.actionbar").replace("%seconds%", "" + duelTime));
            duelTime--;
        } else {
            duel.end(null, true);
            this.cancel();
        }
    }
}
