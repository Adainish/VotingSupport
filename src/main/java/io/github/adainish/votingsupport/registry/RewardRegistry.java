package io.github.adainish.votingsupport.registry;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.config.RewardsConfig;
import io.github.adainish.votingsupport.obj.VoteReward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardRegistry {

    public static List<VoteReward> voteRewardList = new ArrayList<>();
    public static List<VoteReward> streakRewardList = new ArrayList<>();
    public static List<VoteReward> voterSpotRewardList = new ArrayList<>();

    public static void loadVoteRewardsToCache() {
        voteRewardList.clear();
        streakRewardList.clear();
        voterSpotRewardList.clear();

        CommentedConfigurationNode rootNode = RewardsConfig.getConfig().get().getNode("Rewards");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject != null) {
                String node = nodeObject.toString();
                if (node != null) {
                    VoteReward reward = new VoteReward(node);
                    switch (reward.getType().toLowerCase()) {
                        case "top_voter": {
                            voterSpotRewardList.add(reward);
                            break;
                        }
                        case "streak": {
                            streakRewardList.add(reward);
                            break;
                        }
                        case "vote": {
                            voteRewardList.add(reward);
                            break;
                        }
                    }
                } else VotingSupport.log.info("%node% returned null, check your config!");
            } VotingSupport.log.info("%node% returned null, check your config!");
        }


    }
}
