package io.github.adainish.votingsupport.handlers;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.Streak;
import io.github.adainish.votingsupport.obj.StreakDay;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.obj.VoterSpot;
import io.github.adainish.votingsupport.obj.rewards.VoteReward;
import io.github.adainish.votingsupport.registry.RewardRegistry;
import io.github.adainish.votingsupport.util.PermissionUtil;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
import java.util.Random;

public class RewardHandler {

    public static void handOutVoteReward(EntityPlayerMP playerMP) {
        VoteReward r = pickReward(RewardRegistry.voteRewardList);
        if (r == null) {
            VotingSupport.log.info("%p || %u did not get their vote reward"
                    .replace("%p", playerMP.getName())
                    .replace("%u", String.valueOf(playerMP.getUniqueID()))
            );
            Util.send(playerMP, "&cSomething went wrong loading your vote reward and you were unable to receive it, please contact a staff member!");
            return;
        }
        handOutReward(playerMP, r);
    }

    public static void handOutVoteReward(VotePlayer player)
    {
        EntityPlayerMP playerMP = Util.getPlayer(player.getUuid());
        VoteReward r = pickReward(RewardRegistry.voteRewardList);
        if (r == null) {
            VotingSupport.log.info("%p || %u did not get their vote reward"
                    .replace("%p", player.getUserName())
                    .replace("%u", String.valueOf(player.getUuid()))
            );
            Util.send(playerMP, "&cSomething went wrong loading your vote reward and you were unable to receive it, please contact a staff member!");
            return;
        }
        handOutReward(playerMP, r);
    }

    public static VoteReward pickReward(List <VoteReward> rewardList) {
        int weightSum = 0;

        for (VoteReward reward: rewardList) {
            weightSum += reward.getFrequency();
        }
        Random random = new Random();
        int selectedReward = random.nextInt(weightSum);
        int count = 0;

        for (VoteReward reward: rewardList) {
            count += reward.getFrequency();
            if (count >= selectedReward) {
                return reward;
            }
        }
        return null;
    }

    public static void handOutReward(EntityPlayerMP player, VoteReward reward) {
        if (reward.getPermission().isEmpty()) {
            Util.send(player, reward.getMessage());
            Util.runCommands(reward.getCommandList());
        } else if (PermissionUtil.canUse(reward.getPermission(), player)) {
            Util.runCommands(reward.getCommandList());
            Util.send(player, reward.getMessage());
        } else Util.send(player, reward.getMessage());
    }

    public static void handOutVoterTopRewards(VoterSpot spot, EntityPlayerMP player) {
        for (VoteReward r:spot.getRewardList()) {
            handOutReward(player, r);
        }
    }

    public static void handOutStreakDayRewards(StreakDay day, EntityPlayerMP player) {
        for (VoteReward r:day.getVoteRewards()) {
            handOutReward(player, r);
        }
    }

}
