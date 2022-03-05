package io.github.adainish.votingsupport.config;

public class GUIConfig extends Configurable{
    private static GUIConfig config;

    public static GUIConfig getConfig() {
        if (config == null) {
            config = new GUIConfig();
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
        return "GUI.conf";
    }
}
