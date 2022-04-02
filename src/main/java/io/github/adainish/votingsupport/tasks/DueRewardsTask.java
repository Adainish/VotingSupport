package io.github.adainish.votingsupport.tasks;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.handlers.RewardHandler;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VoterSpot;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

import static io.github.adainish.votingsupport.VotingSupport.getLeaderboard;

public class DueRewardsTask implements Runnable{

    @Override
    public void run() {

        if (getLeaderboard() == null)
            return;

        if (getLeaderboard().getCachedVoterSpots().isEmpty())
            return;

        List<VoterSpot> toRemove = new ArrayList <>();
        List<VoterSpot> cachedVoterSpots = getLeaderboard().getCachedVoterSpots();

        if (cachedVoterSpots.isEmpty())
            return;

        for (int i = 0, cachedVoterSpotsSize = cachedVoterSpots.size(); i < cachedVoterSpotsSize; i++) {
            VoterSpot s = cachedVoterSpots.get(i);

            if (s == null)
                continue;


            if (!Util.isOnline(s.getUuid()))
                continue;

            EntityPlayerMP playerMP = Util.getPlayer(s.getUuid());

            if (s.getMessage() != null && !s.getMessage().isEmpty()) {
                Util.send(playerMP, s.getMessage());
            }
            RewardHandler.handOutVoterTopRewards(s, playerMP);
            toRemove.add(s);
        }

        Leaderboard l = getLeaderboard();

        if (l == null)
            return;

        l.getCachedVoterSpots().removeAll(toRemove);
        VotingSupport.setLeaderboard(l);

    }
}
