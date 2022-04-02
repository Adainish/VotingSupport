package io.github.adainish.votingsupport.config;

import java.util.Arrays;

public class MainConfig extends Configurable{
    private static MainConfig config;

    public static MainConfig getConfig() {
        if (config == null) {
            config = new MainConfig();
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

        this.get().getNode("VoteMessage").setValue(Arrays.asList("This is a series of strings that are sent to a user wishing to vote", "&cThey also support Colour Codes", "cmd:</msg @pl hi> command execution and ", "url: mywebsite.com &cwebsite links!")).setComment("");

    }

    @Override
    public String getConfigName() {
        return "Main.conf";
    }
}
