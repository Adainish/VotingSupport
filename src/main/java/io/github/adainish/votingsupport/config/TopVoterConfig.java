package io.github.adainish.votingsupport.config;

public class TopVoterConfig extends Configurable{
    private static TopVoterConfig config;

    public static TopVoterConfig getConfig() {
        if (config == null) {
            config = new TopVoterConfig();
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
        return "TopVoter.conf";
    }
}
