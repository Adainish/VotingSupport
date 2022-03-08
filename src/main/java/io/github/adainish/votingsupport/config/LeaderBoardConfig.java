package io.github.adainish.votingsupport.config;

public class LeaderBoardConfig extends Configurable{
    private static LeaderBoardConfig config;

    public static LeaderBoardConfig getConfig() {
        if (config == null) {
            config = new LeaderBoardConfig();
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

        this.get().getNode("LeaderBoard", "Automatic").setValue(true).setComment("Whether the Leaderboard should automatically hand out rewards and reset the top voters");
        this.get().getNode("LeaderBoard", "ValidDays").setValue(30).setComment("For how many days a Leaderboard is valid, assuming this is handled automatically");
    }

    @Override
    public String getConfigName() {
        return "LeaderBoard.conf";
    }

}
