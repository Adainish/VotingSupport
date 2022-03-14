package io.github.adainish.votingsupport.config;

import java.util.Arrays;

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

        this.get().getNode("TopVoter", "1", "Rewards").setValue(Arrays.asList("")).setComment("The reward ids for this voter spot");
        this.get().getNode("TopVoter", "1", "Message").setValue("&7Congrats on being the #1 voter").setComment("The message sent to the player once obtained");
        this.get().getNode("TopVoter", "2", "Rewards").setValue(Arrays.asList("")).setComment("The reward ids for this voter spot");
        this.get().getNode("TopVoter", "2", "Message").setValue("&7Congrats on being the #2 voter").setComment("The message sent to the player once obtained");
        this.get().getNode("TopVoter", "3", "Rewards").setValue(Arrays.asList("")).setComment("The reward ids for this voter spot");
        this.get().getNode("TopVoter", "3", "Message").setValue("&7Congrats on being the #3 voter").setComment("The message sent to the player once obtained");
    }

    @Override
    public String getConfigName() {
        return "TopVoter.conf";
    }
}
