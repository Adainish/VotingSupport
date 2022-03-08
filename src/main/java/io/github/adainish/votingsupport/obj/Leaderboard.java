package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.config.LeaderBoardConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Leaderboard {

    private List<VoterSpot> cachedVoterSpots = new ArrayList <>();
    private List <UUID> playerUUIDList = new ArrayList <>();
    private long lastExecutedVoterCommands;
    private long initialisedTime;
    private int validDays;

    public Leaderboard() {
        setInitialisedTime(System.currentTimeMillis());
        setValidDays(LeaderBoardConfig.getConfig().get().getNode("LeaderBoard", "ValidDays").getInt());
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

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }
}
