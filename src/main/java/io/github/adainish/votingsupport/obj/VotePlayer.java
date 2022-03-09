package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VotePlayer {
    private UUID uuid;
    private String userName;
    private int voteCount;
    private Streak streak;
    private long lastVoted;
    private long resetTimer;

    public VotePlayer(UUID uuid) {
        setUuid(uuid);
        setVoteCount(0);
        try {
            EntityPlayerMP p = Util.getPlayer(uuid);
            setUserName(p.getName());
        } catch (NullPointerException e) {
            setUserName("");
        }
    }

    public boolean streakExpired() {

        return false;
    }

    public void updateStreakProgress() {

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

    public long getResetTimer() {
        return resetTimer;
    }

    public void setResetTimer(long resetTimer) {
        this.resetTimer = resetTimer;
    }

    public long timer() {
        return ((getResetTimer() *1000 - (System.currentTimeMillis() - getLastVoted())) / 1000);
    }
    public String timeLeftSeconds() {
        long timer = ((getResetTimer() * 1000 - (System.currentTimeMillis() - getLastVoted()) / 1000));
        return String.valueOf(TimeUnit.SECONDS.toSeconds(timer));
    }
    public String timeLeftMinutes() {
        return String.valueOf(TimeUnit.SECONDS.toMinutes(timer()));
    }
}
