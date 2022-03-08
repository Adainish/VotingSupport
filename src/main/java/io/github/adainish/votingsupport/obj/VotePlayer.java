package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.util.ProfileFetcher;

import java.io.IOException;
import java.util.UUID;

public class VotePlayer {
    private UUID uuid;
    private String userName;
    private int voteCount;
    private Streak streak;
    private long lastVoted;

    public VotePlayer(UUID uuid) {
        setUuid(uuid);
        setVoteCount(0);
        try {
            setUserName(ProfileFetcher.getName(uuid));
        } catch (IOException | NullPointerException e) {
            setUserName("");
        }
    }

    public void initialiseStreak() {
        streak = new Streak("");

    }

    public void resetStreak() {

    }

    public void syncStreakWithConfig() {

    }

    public void increaseVote(int i) {
        this.voteCount += i;
    }

    public void increaseVote() {
        this.voteCount++;
    }

    public void decreaseVote() {
        this.voteCount--;
    }

    public void decreaseVote(int i) {
        this.voteCount -= i;
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Streak getStreak() {
        return streak;
    }

    public void setStreak(Streak streak) {
        this.streak = streak;
    }

    public long getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(long lastVoted) {
        this.lastVoted = lastVoted;
    }
}
