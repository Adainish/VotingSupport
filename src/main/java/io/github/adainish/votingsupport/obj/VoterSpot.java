package io.github.adainish.votingsupport.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoterSpot {
    private UUID uuid;
    private String userName;
    private int position;
    private int totalVotes;
    private String message;
    private List <VoteReward> rewardList = new ArrayList <>();
    private boolean received;

    public VoterSpot(UUID uuid, int position, int totalVotes, String identifier) {
        setUuid(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List <VoteReward> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List <VoteReward> rewardList) {
        this.rewardList = rewardList;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
