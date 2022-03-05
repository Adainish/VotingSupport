package io.github.adainish.votingsupport.config;

public class StreakConfig extends Configurable{
    private static StreakConfig config;

    public static StreakConfig getConfig() {
        if (config == null) {
            config = new StreakConfig();
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
        return "Streaks.conf";
    }
}
