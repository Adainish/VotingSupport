package io.github.adainish.votingsupport.obj;

import java.util.ArrayList;
import java.util.List;

public class VoteParty {
    private String display;
    private List <String> descriptionList = new ArrayList <>();
    private List<String> individualCommands = new ArrayList <>();
    private List<String> globalCommands = new ArrayList <>();
    private int requiredVotes;
    private int currentVotes;

    public VoteParty() {

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
