package io.github.adainish.votingsupport.obj;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.config.StreakConfig;
import io.github.adainish.votingsupport.registry.RewardRegistry;

import java.util.ArrayList;
import java.util.List;

public class StreakDay {

    private String identifier;
    private String itemDisplay;
    private String display;
    private List <String> loreList = new ArrayList <>();
    private boolean completed;
    private boolean claimed;
    private List<String> rewardIdentifiers = new ArrayList <>();
    private List<VoteReward> voteRewards = new ArrayList <>();

    public StreakDay(String identifier) {
        setIdentifier(identifier);
        setDisplay(StreakConfig.getConfig().get().getNode("Streak", identifier, "StreakDays", "Display").getString());
        setItemDisplay(StreakConfig.getConfig().get().getNode("Streak", identifier, "StreakDays", "ItemDisplay").getString());
        setClaimed(false);
        setCompleted(false);
        try {
            setLoreList(StreakConfig.getConfig().get().getNode("Streak", identifier, "StreakDays", "Lore").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setLoreList(new ArrayList <>());
        }
        try {
            setRewardIdentifiers(StreakConfig.getConfig().get().getNode("Streak", identifier, "StreakDays", "Rewards").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setRewardIdentifiers(new ArrayList <>());
        }
        loadStreakDayRewards();
    }

    public void loadStreakDayRewards() {
        voteRewards.clear();

        if (rewardIdentifiers.isEmpty())
            return;

        for (String s:getRewardIdentifiers()) {
            for (VoteReward r : RewardRegistry.streakRewardList) {
                if (r.getIdentifier().equals(s))
                    voteRewards.add(r);
            }
        }
    }

    public String getItemDisplay() {
        return itemDisplay;
    }

    public void setItemDisplay(String itemDisplay) {
        this.itemDisplay = itemDisplay;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List <String> getLoreList() {
        return loreList;
    }

    public void setLoreList(List <String> loreList) {
        this.loreList = loreList;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List <VoteReward> getVoteRewards() {
        return voteRewards;
    }

    public void setVoteRewards(List <VoteReward> voteRewards) {
        this.voteRewards = voteRewards;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List <String> getRewardIdentifiers() {
        return rewardIdentifiers;
    }

    public void setRewardIdentifiers(List <String> rewardIdentifiers) {
        this.rewardIdentifiers = rewardIdentifiers;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
}
