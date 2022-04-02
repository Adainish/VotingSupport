package io.github.adainish.votingsupport.tasks;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.Leaderboard;

public class CheckLeaderBoardTask implements Runnable{

    @Override
    public void run() {
        Leaderboard l = VotingSupport.getLeaderboard();

        if (l == null)
            return;

        if (l.shouldReset()) {
            l.cacheVoteSpots();
        }
    }
}
