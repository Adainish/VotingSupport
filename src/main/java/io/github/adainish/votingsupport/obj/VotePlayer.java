package io.github.adainish.votingsupport.obj;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.util.PermissionUtil;
import io.github.adainish.votingsupport.util.ProfileFetcher;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VotePlayer {
    private UUID uuid;
    private String userName;
    private int voteCount = 0;
    private Streak streak;
    private int leaderBoardCount = 0;
    private long lastVoted;
    private long lastStreakIncrease;
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
        initialiseStreak();
    }

    public boolean outdatedUserName() {
        try {
            return ProfileFetcher.getName(uuid).equals(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUserName() {
        String newName = null;
        try {
            newName = ProfileFetcher.getName(uuid);
        } catch (IOException e) {
            VotingSupport.log.info("Something went wrong while attempting to update outdated player data, could not verify the account status with Mojang for Mojang UUID " + uuid);
        } catch (NullPointerException e) {
            VotingSupport.log.info("Something went wrong while attempting to update the outdated username for Mojang UUID :" + uuid);
            return;
        }
        if (newName == null || newName.isEmpty())
            return;

        setUserName(newName);
    }

    public boolean streakNeedsUpdating() {
        for (Streak s:VotingSupport.getStreakList()) {
            if (s.getIdentifier().equals(streak.getIdentifier())) {
                for (StreakDay d : streak.getStreakDayList()) {
                    for (VoteReward r:d.getVoteRewards()) {

                    }
                }
            }
        }
        return false;
    }

    public void updateStreakProgress() {
        if (streak.resetStreak()) {
            initialiseStreak();
            return;
        }

        streak.increaseStreakDay();
    }

    public void initialiseStreak() {

        if (VotingSupport.getStreakList().isEmpty())
            return;

        for (Streak s: VotingSupport.getStreakList()) {
            if (s.getPermissionNode().isEmpty()) {
                setStreak(s);
                break;
            }
            EntityPlayerMP p = Util.getPlayer(getUuid());
            if (!Util.isOnline(p.getUniqueID()))
                continue;

            if (PermissionUtil.canUse(s.getPermissionNode(), p)) {
                setStreak(s);
                break;
            }
        }
    }


    public void syncStreakWithConfig(Streak streak) {
        List <StreakDay> streakDayList = streak.getStreakDayList();
        for (int i = 0, streakDayListSize = streakDayList.size(); i < streakDayListSize; i++) {
            StreakDay d = streakDayList.get(i);
            List <StreakDay> dayList = getStreak().getStreakDayList();
            for (int j = 0, dayListSize = dayList.size(); j < dayListSize; j++) {
                StreakDay sd = dayList.get(j);
                if (!d.getIdentifier().equals(sd.getIdentifier()))
                    continue;
                d.setCompleted(sd.isCompleted());
                dayList.set(j, d);
            }
        }
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

    public void updateCache() {
        VotingSupport.getVotePlayers().remove(uuid);
        if (Util.isOnline(uuid)) {
            VotingSupport.getVotePlayers().put(uuid, this);
        }
    }


    public void markVote() {
        setLastVoted(System.currentTimeMillis());
        increaseVote();

        if (TimeUnit.HOURS.toMillis(24) <= getLastStreakIncrease()) {
            setLastStreakIncrease(System.currentTimeMillis());
            updateStreakProgress();
            return;
        }

        if (TimeUnit.HOURS.toMillis(48) <= getLastStreakIncrease()) {
            initialiseStreak();
            setLastStreakIncrease(System.currentTimeMillis());
            updateStreakProgress();
        }
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
        return ((getResetTimer() * 1000 - (System.currentTimeMillis() - getLastStreakIncrease()) / 1000));
    }
    public String timeLeftSeconds() {
        long timer = ((getResetTimer() * 1000 - (System.currentTimeMillis() - getLastStreakIncrease()) / 1000));
        return String.valueOf(TimeUnit.SECONDS.toSeconds(timer));
    }
    public String timeLeftMinutes() {
        return String.valueOf(TimeUnit.SECONDS.toMinutes(timer()));
    }

    public long getLastStreakIncrease() {
        return lastStreakIncrease;
    }

    public void setLastStreakIncrease(long lastStreakIncrease) {
        this.lastStreakIncrease = lastStreakIncrease;
    }

    public int getLeaderBoardCount() {
        return leaderBoardCount;
    }

    public void setLeaderBoardCount(int leaderBoardCount) {
        this.leaderBoardCount = leaderBoardCount;
    }

    public void increaseLeaderboardCount(int i) {
        this.leaderBoardCount += i;
    }
}
