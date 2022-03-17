package io.github.adainish.votingsupport.obj;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.config.TopVoterConfig;
import io.github.adainish.votingsupport.registry.RewardRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoterSpot {
    private String rewardIdentifier;
    private UUID uuid;
    private String userName;
    private int position;
    private int totalVotes;
    private String message;
    private List<String> rewardIdentifiers = new ArrayList <>();
    private List <VoteReward> rewardList = new ArrayList <>();
    private boolean received;

    public VoterSpot(UUID uuid, int position, int totalVotes) {
        setUuid(uuid);
        setPosition(position);
        setTotalVotes(totalVotes);
        setMessage(TopVoterConfig.getConfig().get().getNode("TopVoter", String.valueOf(position), "Message").getString());
        setReceived(false);
        try {
            setRewardIdentifiers(TopVoterConfig.getConfig().get().getNode("TopVoter", String.valueOf(position), "Rewards").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException | NullPointerException e) {
            setRewardIdentifiers(new ArrayList <>());
        }
        loadRewards();
    }

    public void loadRewards() {
        if (getRewardIdentifiers().isEmpty())
            return;

        for (String s:getRewardIdentifiers()) {
            for (VoteReward r: RewardRegistry.voterSpotRewardList) {
                if (r.getIdentifier().equals(s))
                    rewardList.add(r);
            }
        }

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

    public String getRewardIdentifier() {
        return rewardIdentifier;
    }

    public void setRewardIdentifier(String rewardIdentifier) {
        this.rewardIdentifier = rewardIdentifier;
    }

    public List <String> getRewardIdentifiers() {
        return rewardIdentifiers;
    }

    public void setRewardIdentifiers(List <String> rewardIdentifiers) {
        this.rewardIdentifiers = rewardIdentifiers;
    }
}
