package io.github.adainish.votingsupport.tasks;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.LeaderboardStorage;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import io.github.adainish.votingsupport.storage.VotePartyStorage;

import java.util.ArrayList;
import java.util.List;

public class UpdateStorageTask implements Runnable{

    @Override
    public void run() {

        if (!VotingSupport.getVotePlayers().values().isEmpty()) {
            List<VotePlayer> players = new ArrayList<>(VotingSupport.getVotePlayers().values());

            for (int i = 0; i < players.size(); i++) {
                VotePlayer p = players.get(i);

                if (p == null)
                    continue;

                PlayerStorage.savePlayer(p);
            }
        }

        LeaderboardStorage.saveLeaderBoard(VotingSupport.getLeaderboard());
        VotePartyStorage.saveVoteParty(VotePartyStorage.getVoteParty());
    }
}
