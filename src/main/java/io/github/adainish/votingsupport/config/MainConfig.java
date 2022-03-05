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

        this.get().getNode("").setValue("").setComment("");

    }

    @Override
    public String getConfigName() {
        return "Main.conf";
    }
}
