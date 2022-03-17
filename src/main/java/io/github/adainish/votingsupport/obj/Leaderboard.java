package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.config.LeaderBoardConfig;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import io.github.adainish.votingsupport.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Leaderboard {

    private List<VoterSpot> cachedVoterSpots = new ArrayList <>();
    private List <UUID> playerUUIDList = new ArrayList <>();
    private long lastExecutedVoterCommands;
    private long initialisedTime;
    private long validDays;
    private boolean automatic;

    public Leaderboard() {
        setInitialisedTime(System.currentTimeMillis());
        setValidDays(LeaderBoardConfig.getConfig().get().getNode("LeaderBoard", "ValidDays").getInt());
        setAutomatic(LeaderBoardConfig.getConfig().get().getNode("LeaderBoard", "Automatic").getBoolean());
    }

    public boolean shouldReset() {
        long timePassed = System.currentTimeMillis() - initialisedTime;
        long timeNeeded = TimeUnit.DAYS.toMillis(getValidDays());
        return timePassed > timeNeeded;
    }

    public void cacheVoteSpots() {
        List<VotePlayer> votePlayers = new ArrayList <>();
        for (UUID uuid:playerUUIDList) {
            VotePlayer p = null;
            if (Util.isOnline(uuid))
                p = VotingSupport.getVotePlayers().get(uuid);
            else p = PlayerStorage.getPlayer(uuid);

            if (p == null)
                continue;

            votePlayers.add(p);

        }
        votePlayers.sort(Comparator.comparing(VotePlayer::getLeaderBoardCount));
        for (int i = 0; i < votePlayers.size(); i++) {
            if (i == 3)
                break;

            VotePlayer p = votePlayers.get(i);
            VoterSpot voterSpot = new VoterSpot(p.getUuid(), i +1, p.getLeaderBoardCount());
            cachedVoterSpots.add(voterSpot);
        }
        VotingSupport.setLeaderboard(this);
        VotingSupport.resetLeaderBoard();
    }

    public List <UUID> getPlayerUUIDList() {
        return playerUUIDList;
    }

    public void setPlayerUUIDList(List <UUID> playerUUIDList) {
        this.playerUUIDList = playerUUIDList;
    }

    public List<VoterSpot> getCachedVoterSpots() {
        return cachedVoterSpots;
    }

    public void setCachedVoterSpots(List<VoterSpot> cachedVoterSpots) {
        this.cachedVoterSpots = cachedVoterSpots;
    }

    public long getLastExecutedVoterCommands() {
        return lastExecutedVoterCommands;
    }

    public void setLastExecutedVoterCommands(long lastExecutedVoterCommands) {
        this.lastExecutedVoterCommands = lastExecutedVoterCommands;
    }

    public long getInitialisedTime() {
        return initialisedTime;
    }

    public void setInitialisedTime(long initialisedTime) {
        this.initialisedTime = initialisedTime;
    }

    public long getValidDays() {
        return validDays;
    }

    public void setValidDays(long validDays) {
        this.validDays = validDays;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}
