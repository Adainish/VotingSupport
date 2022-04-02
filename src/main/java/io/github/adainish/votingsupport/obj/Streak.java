package io.github.adainish.votingsupport.obj;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.config.StreakConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Streak {
    private String permissionNode;
    private String identifier;
    private String display;
    private String Title;
    private long lastUpdated;
    private int streakDay;
    private List <StreakDay> streakDayList = new ArrayList<>();
    private long expirySeconds;

    public Streak(String identifier) {
        setIdentifier(identifier);
        setLastUpdated(System.currentTimeMillis());
        setExpirySeconds(StreakConfig.getConfig().get().getNode("Streak", identifier, "Expiry").getInt());
        setDisplay(StreakConfig.getConfig().get().getNode("Streak", identifier, "Display").getString());
        setTitle(StreakConfig.getConfig().get().getNode("Streak", identifier, "Title").getString());
        setPermissionNode(StreakConfig.getConfig().get().getNode("Streak", identifier, "Permission").getString());
        setStreakDay(0);
        loadStreakDays();
    }

    public void loadStreakDays() {
        streakDayList.clear();

        CommentedConfigurationNode rootNode = StreakConfig.getConfig().get().getNode("Streak", identifier, "StreakDays");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) ;
            else {
                String node = nodeObject.toString();
                if (node == null) ;
                else {
                    StreakDay day = new StreakDay(node);
                    streakDayList.add(day);
                }
            }
        }
    }

    public boolean resetStreak() {
        return (this.streakDay >= streakDayList.size() +1);
    }

    public void increaseStreakDay() {
        this.streakDay++;
    }

    public void increaseStreakDay(int amount) {
        this.streakDay += amount;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List <StreakDay> getStreakDayList() {
        return streakDayList;
    }

    public void setStreakDayList(List <StreakDay> streakDayList) {
        this.streakDayList = streakDayList;
    }

    public long getExpirySeconds() {
        return expirySeconds;
    }

    public void setExpirySeconds(long expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

    public void setPermissionNode(String permissionNode) {
        this.permissionNode = permissionNode;
    }

    public int getStreakDay() {
        return streakDay;
    }

    public void setStreakDay(int streakDay) {
        this.streakDay = streakDay;
    }
}
