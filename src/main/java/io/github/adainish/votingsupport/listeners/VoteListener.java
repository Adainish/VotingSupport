package io.github.adainish.votingsupport.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.sponge.event.VotifierEvent;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.handlers.RewardHandler;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import io.github.adainish.votingsupport.util.ProfileFetcher;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.event.Listener;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VoteListener {

    @Listener
    public void handle(VotifierEvent event) throws Exception {
        Vote vote = event.getVote();

        UUID uuid = null;

        try {
            uuid = ProfileFetcher.getUUID(event.getVote().getUsername());
        } catch (IOException e) {
            VotingSupport.log.info("%n tried voting, it was received but there was an issue connecting to mojang!!"
                    .replace("%n", event.getVote().getUsername()));
            return;
        } catch (NullPointerException e) {
            VotingSupport.log.info("%n tried voting, it was received but the user doesn't exist according to mojang!"
                    .replace("%n", event.getVote().getUsername())
            );
            return;
        }
        try {
            if (!Util.isOnline(uuid)) {
                VotingSupport.log.info(vote.getUsername() + " wasn't online while voting and did potentially not receive rewards");
            }
            VotePlayer player = PlayerStorage.getPlayer(uuid);
            if (player == null) {
                EntityPlayerMP p = Util.getPlayer(uuid);
                PlayerStorage.makeVotePlayer(p);
                player = PlayerStorage.getPlayer(uuid);
            }
            VotingSupport.log.info("Vote received from " + vote.getUsername());

            if (player != null) {
                player.markVote();
                RewardHandler.handOutVoteReward(player);
                if (TimeUnit.MILLISECONDS.toHours(player.getStreak().getLastUpdated()) >= 24) {
                    player.getStreak().increaseStreakDay();
                }
                PlayerStorage.savePlayer(player);
                RewardHandler.updateLeaderBoard(player, 1);
            }
            if (VotingSupport.getParty() != null) {
                VotingSupport.getParty().increaseCurrentVotes();
            }


        } catch (Exception e) {

        }
    }
}
