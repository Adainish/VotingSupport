package io.github.adainish.votingsupport.config;

import java.util.Arrays;

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

        this.get().getNode("Streak", "Example", "Permission").setValue("votingsupport.streak.example").setComment("The permission node needed for a player to be able to claim rewards from this streak");
        this.get().getNode("Streak", "Example", "Expiry").setValue(1000000).setComment("How many seconds a player has before the streak expires");
        this.get().getNode("Streak", "Example", "Display").setValue("").setComment("The Display when displayed in GUI");
        this.get().getNode("Streak", "Example", "Title").setValue("").setComment("The Title of the Streak when the GUI opens");
        this.get().getNode("Streak", "Example", "StreakDays").setComment("Configure the streak days for this streak type here");
        this.get().getNode("Streak", "Example", "StreakDays", "ItemDisplay").setValue("minecraft:paper").setComment("The item used to display the day in the GUI");
        this.get().getNode("Streak", "Example", "StreakDays", "Display").setValue("&bExample Day").setComment("The GUI title");
        this.get().getNode("Streak", "Example", "StreakDays", "Lore").setValue(Arrays.asList("&7Example day")).setComment("The GUI Lore");
        this.get().getNode("Streak", "Example", "StreakDays", "Rewards").setValue(Arrays.asList("")).setComment("a list of rewards that will attempt to be given upon reaching the day");
    }

    @Override
    public String getConfigName() {
        return "Streaks.conf";
    }
}
