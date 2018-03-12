package com.gb6.duels.threads;

import com.gb6.duels.objects.Duel;
import com.gb6.duels.objects.Message;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.gb6.duels.enums.DuelState.ACTIVE;
import static com.gb6.duels.utils.Constants.*;

public class DuelStartThread extends BukkitRunnable {

    private Player sender;
    private Player target;
    private Duel duel;
    private int countDown;

    public DuelStartThread(Duel duel) {
        this.sender = duel.getRequester().getPlayer();
        this.target = duel.getOpponent().getPlayer();
        this.countDown = CFM.getInteger("duel-settings.countdown-in-sec");
        this.duel = duel;
        this.runTaskTimer(INSTANCE, 20L, 20L);
    }

    @Override
    public void run() {
        int duelSize = duel.getPlayerCount();

        if (duelSize == 0) {
            duel.end(null, false);
            this.cancel();
        }

        if (countDown > 0 && duelSize == 2) {
            new Message("duel-countdown").format("%seconds%", "" + countDown).send(sender);
            new Message("duel-countdown").format("%seconds%", "" + countDown).send(target);
            countDown--;
        } else {
            if (duelSize == 2) {
                new Message("duel-start").format("%opponent%", "" + target.getName()).send(sender);
                new Message("duel-start").format("%opponent%", "" + sender.getName()).send(target);
                duel.setState(ACTIVE);
                // TODO: Unfreeze players
                new DuelTimeThread(duel).runTaskTimer(INSTANCE, 20L, 20L);
            }
            this.cancel();
        }
    }
}
