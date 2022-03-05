package io.github.adainish.votingsupport.config;

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

        this.get().getNode("").setValue("").setComment("");

    }

    @Override
    public String getConfigName() {
        return "Rewards.conf";
    }
}
