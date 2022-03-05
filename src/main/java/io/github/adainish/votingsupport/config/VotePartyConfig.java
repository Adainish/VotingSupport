package io.github.adainish.votingsupport.config;

public class VotePartyConfig extends Configurable{
    private static VotePartyConfig config;

    public static VotePartyConfig getConfig() {
        if (config == null) {
            config = new VotePartyConfig();
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
        return "VoteParty.conf";
    }
}
