package io.github.adainish.votingsupport.obj;

import java.util.ArrayList;
import java.util.List;

public class Streak {
    private String display;
    private String Title;
    private long lastUpdated;
    private List <StreakDay> streakDayList = new ArrayList<>();

    public Streak() {

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
}
