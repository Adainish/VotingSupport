package io.github.adainish.votingsupport.tasks;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.handlers.RewardHandler;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VoterSpot;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class DueRewardsTask implements Runnable{

    @Override
    public void run() {

        if (VotingSupport.getLeaderboard().getCachedVoterSpots().isEmpty())
            return;

        List<VoterSpot> toRemove = new ArrayList <>();
        List <VoterSpot> cachedVoterSpots = VotingSupport.getLeaderboard().getCachedVoterSpots();
        for (VoterSpot s : cachedVoterSpots) {

            EntityPlayerMP playerMP = Util.getPlayer(s.getUuid());
            if (!Util.isOnline(playerMP.getUniqueID()))
                continue;

            Util.send(playerMP, s.getMessage());
            RewardHandler.handOutVoterTopRewards(s, playerMP);
            toRemove.add(s);
        }

        Leaderboard l = VotingSupport.getLeaderboard();

        l.getCachedVoterSpots().removeAll(toRemove);
        VotingSupport.setLeaderboard(l);

    }
}
