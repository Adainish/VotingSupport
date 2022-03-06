package io.github.adainish.votingsupport.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Leaderboard {

    private List<VoterSpot> cachedVoterSpots = new ArrayList <>();
    private List <UUID> playerUUIDList = new ArrayList <>();
    private long lastExecutedVoterCommands;

    public Leaderboard() {

    }

    public List <UUID> getPlayerUUIDList() {
        return playerUUIDList;
    }

    public void setPlayerUUIDList(List <UUID> playerUUIDList) {
        this.playerUUIDList = playerUUIDList;
    }
}
