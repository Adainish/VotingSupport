package io.github.adainish.votingsupport.obj;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.config.VotePartyConfig;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class VoteParty {
    private String display;
    private List <String> descriptionList = new ArrayList <>();
    private List <String> individualCommands = new ArrayList <>();
    private List <String> globalCommands = new ArrayList <>();
    private int requiredVotes;
    private int currentVotes;

    public VoteParty() {
        setDisplay(VotePartyConfig.getConfig().get().getNode("VoteParty", "Display").getString());
        try {
            setDescriptionList(VotePartyConfig.getConfig().get().getNode("VoteParty", "Description").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setDescriptionList(new ArrayList <>());
        }
        try {
            setIndividualCommands(VotePartyConfig.getConfig().get().getNode("VoteParty", "IndividualCommands").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setIndividualCommands(new ArrayList <>());
        }
        try {
            setGlobalCommands(VotePartyConfig.getConfig().get().getNode("VoteParty", "GlobalCommands").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setGlobalCommands(new ArrayList <>());
        }
        setRequiredVotes(VotePartyConfig.getConfig().get().getNode("VoteParty", "RequiredVotes").getInt());
    }

    public void executeVoteParty() {
        if (shouldExecuteVoteParty()) {
            int leftOver = currentVotes - requiredVotes;
            if (leftOver < 0)
                leftOver = 0;
            setCurrentVotes(leftOver);

            for (String s:globalCommands) {
                Util.runCommand(s);
            }

            for (EntityPlayerMP pl:Util.getInstance().getPlayerList().getPlayers()) {
                if (pl == null)
                    continue;

                if (individualCommands.isEmpty())
                    break;

                for (String s:individualCommands) {
                    if (s.isEmpty())
                        continue;
                    Util.runCommand(s.replace("@pl", pl.getName()));
                }
            }
            VotingSupport.regenVoteParty();
        } VotingSupport.regenCachedVoteParty(this);
    }

    public boolean shouldExecuteVoteParty() {
        return requiredVotes >= currentVotes;
    }

    public void increaseCurrentVotes() {
        currentVotes++;
        executeVoteParty();
    }

    public void increaseCurrentVotes(int i) {
        currentVotes += i;
        executeVoteParty();
    }

    public void increaseRequiredVotes() {
        requiredVotes++;
    }

    public void increaseRequiredVotes(int i) {
        requiredVotes += i;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List <String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List <String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public List <String> getIndividualCommands() {
        return individualCommands;
    }

    public void setIndividualCommands(List <String> individualCommands) {
        this.individualCommands = individualCommands;
    }

    public List <String> getGlobalCommands() {
        return globalCommands;
    }

    public void setGlobalCommands(List <String> globalCommands) {
        this.globalCommands = globalCommands;
    }

    public int getRequiredVotes() {
        return requiredVotes;
    }

    public void setRequiredVotes(int requiredVotes) {
        this.requiredVotes = requiredVotes;
    }

    public int getCurrentVotes() {
        return currentVotes;
    }

    public void setCurrentVotes(int currentVotes) {
        this.currentVotes = currentVotes;
    }
}
