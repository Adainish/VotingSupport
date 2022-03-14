package io.github.adainish.votingsupport.config;

import io.github.adainish.votingsupport.enums.RewardTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RewardsConfig extends Configurable{
    private static RewardsConfig config;

    public static RewardsConfig getConfig() {
        if (config == null) {
            config = new RewardsConfig();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }
    @Override
    public void populate() {

        List<String> rewardTypes = new ArrayList<>();
        for (RewardTypes t: RewardTypes.values()) {
            rewardTypes.add(t.name());
        }
        this.get().getNode("RewardTypes").setValue(rewardTypes).setComment("The types of Rewards that can be worked with");
        this.get().getNode("Rewards",  "Reward", "Display").setValue("&bA Reward").setComment("The display");
        this.get().getNode("Rewards",  "Reward", "Item").setValue("minecraft:paper").setComment("what item represents this reward");
        this.get().getNode("Rewards",  "Reward", "Lore").setValue(Arrays.asList("")).setComment("The item lore when displayed");
        this.get().getNode("Rewards",  "Reward", "Frequency").setValue(1).setComment("The rarity of this item when calculating the chance for it to be given when relevant");
        this.get().getNode("Rewards",  "Reward", "CommandList").setValue(Arrays.asList("")).setComment("The commands to be executed when this reward is received");
        this.get().getNode("Rewards",  "Reward", "Permission").setValue("reward.example").setComment("The permission needed to be able to obtain this reward");
        this.get().getNode("Rewards",  "Reward", "Type").setValue("").setComment("The type of reward this reward is");
        this.get().getNode("Rewards",  "Reward", "Message").setValue("&7You received the default reward for voting").setComment("The message sent if a player DOES have the permission node");
        this.get().getNode("Rewards",  "Reward", "CouldHave").setValue("&7If you had Trainer rank you would have been able to get this reward...").setComment("The message sent if a player LACKS a permission node, informing them of what could have been");
    }

    @Override
    public String getConfigName() {
        return "Rewards.conf";
    }
}
