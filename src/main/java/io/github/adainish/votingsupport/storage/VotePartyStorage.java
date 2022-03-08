package io.github.adainish.votingsupport.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.util.Adapters;

import java.io.*;

public class VotePartyStorage {

    public static void makeVoteParty() {
        File dir = VotingSupport.getStorageDir();
        dir.mkdirs();


         VoteParty voteParty = new VoteParty();

        File file = new File(dir, "votepartystorage.json");
        if (file.exists()) {
            VotingSupport.log.error("There was an issue generating the VoteParty, VoteParty already exists? Ending function");
            return;
        }

        Gson gson = Adapters.PRETTY_MAIN_GSON;
        String json = gson.toJson(voteParty);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveVoteParty(VoteParty party) {

        File dir = VotingSupport.getStorageDir();
        dir.mkdirs();

        File file = new File(dir, "votepartystorage.json");
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            VotingSupport.log.error("Something went wrong attempting to read the Vote Party Data");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(party));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update cache method
    }

    public static VoteParty getVoteParty() {
        File dir = VotingSupport.getStorageDir();
        dir.mkdirs();

        File storageFile = new File(dir, "votepartystorage.json");
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(storageFile));
        } catch (FileNotFoundException e) {
            VotingSupport.log.error("Something went wrong attempting to read the Vote Party, new Vote Party will be made");
            return null;
        }

        return gson.fromJson(reader, VoteParty.class);
    }

}
