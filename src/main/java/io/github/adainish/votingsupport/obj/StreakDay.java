package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.obj.rewards.VoteReward;

import java.util.ArrayList;
import java.util.List;

public class StreakDay {

    private String itemDisplay;
    private String display;
    private List <String> loreList = new ArrayList <>();
    private boolean completed;
    private List<VoteReward> voteRewards = new ArrayList <>();

    public StreakDay(String identifier) {

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
}
