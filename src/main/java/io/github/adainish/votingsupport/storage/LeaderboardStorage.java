package io.github.adainish.votingsupport.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.util.Adapters;

import java.io.*;

public class LeaderboardStorage {

    public static void makeLeaderBoard() {
        File dir = VotingSupport.getLeaderboardDir();
        dir.mkdirs();


        Leaderboard leaderboard = new Leaderboard();

        File file = new File(dir, "leaderboardstorage.json");
        if (file.exists()) {
            VotingSupport.log.error("There was an issue generating the LeaderBoard, Leaderboard already exists? Ending function");
            return;
        }

        Gson gson = Adapters.PRETTY_MAIN_GSON;
        String json = gson.toJson(leaderboard);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLeaderBoard(Leaderboard leaderboard) {

        File dir = VotingSupport.getLeaderboardDir();
        dir.mkdirs();

        File file = new File(dir, "leaderboardstorage.json");
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            VotingSupport.log.error("Something went wrong attempting to read the Leader Board Data");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(leaderboard));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update cache method
    }

    public static Leaderboard getLeaderboard() {
        File dir = VotingSupport.getLeaderboardDir();
        dir.mkdirs();

        File storageFile = new File(dir, "leaderboardstorage.json");
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(storageFile));
        } catch (FileNotFoundException e) {
            VotingSupport.log.error("Something went wrong attempting to read the Leader Board, new Leader Board will be made");
            return null;
        }

        return gson.fromJson(reader, Leaderboard.class);
    }


}
