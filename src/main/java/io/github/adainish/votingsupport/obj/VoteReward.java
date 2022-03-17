package io.github.adainish.votingsupport.obj;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.config.RewardsConfig;

import java.util.ArrayList;
import java.util.List;

public class VoteReward {
    private String identifier;
    private String itemDisplay;
    private String display;
    private String message;
    private String couldHaveMessage;
    private List<String> lore;
    private List<String> commandList;
    private String permission;
    private String type;
    private int frequency;

    public VoteReward(String identifier) {
        setIdentifier(identifier);
        setItemDisplay(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Item").getString());
        setDisplay(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Display").getString());
        try {
            setLore(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Lore").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setLore(new ArrayList<>());
        }
        try {
            setCommandList(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "CommandList").getList(TypeToken.of(String.class)));
        } catch (ObjectMappingException e) {
            setCommandList(new ArrayList<>());
        }
        setMessage(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Message").getString());
        setCouldHaveMessage(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "CouldHave").getString());
        setPermission(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Permission").getString());
        setFrequency(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Frequency").getInt());
        setType(RewardsConfig.getConfig().get().getNode("Rewards", identifier, "Type").getString());
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getCouldHaveMessage() {
        return couldHaveMessage;
    }

    public void setCouldHaveMessage(String couldHaveMessage) {
        this.couldHaveMessage = couldHaveMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
