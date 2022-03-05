package io.github.adainish.votingsupport.obj;

import java.util.List;

public class VoteReward {
    private String itemDisplay;
    private String display;
    private List<String> lore;
    private List<String> commandList;
    private String permission;

    public VoteReward() {

    }

    public String getItemDisplay() {
        return itemDisplay;
    }

    public void setItemDisplay(String itemDisplay) {
        this.itemDisplay = itemDisplay;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List <String> getLore() {
        return lore;
    }

    public void setLore(List <String> lore) {
        this.lore = lore;
    }

    public List <String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List <String> commandList) {
        this.commandList = commandList;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
