package io.github.adainish.votingsupport.config;

import java.util.Arrays;

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

        this.get().getNode("VoteParty", "Display").setValue("&bVote Party").setComment("");
        this.get().getNode("VoteParty", "Description").setValue(Arrays.asList("&bA Party that will execute when the required votes has been obtained")).setComment("");
        this.get().getNode("VoteParty", "IndividualCommands").setValue(Arrays.asList("")).setComment("The commands that will be executed for each player online");
        this.get().getNode("VoteParty", "GlobalCommands").setValue(Arrays.asList("")).setComment("The commands that will be executed once, and not for each individual");
        this.get().getNode("VoteParty", "RequiredVotes").setValue(100).setComment("The votes needed for a vote party to be reached");
    }

    @Override
    public String getConfigName() {
        return "VoteParty.conf";
    }
}
