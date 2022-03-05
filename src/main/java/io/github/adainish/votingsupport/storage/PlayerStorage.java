package io.github.adainish.votingsupport.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.util.Adapters;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.*;
import java.util.UUID;

public class PlayerStorage {

    public static void makeVotePlayer(EntityPlayerMP player) {
        File dir = VotingSupport.getDataDir();
        dir.mkdirs();


        VotePlayer votePlayer = new VotePlayer(player.getUniqueID());

        File file = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUniqueID())));
        if (file.exists()) {
            VotingSupport.log.error("There was an issue generating the Player, Player already exists? Ending function");
            return;
        }

        Gson gson = Adapters.PRETTY_MAIN_GSON;
        String json = gson.toJson(votePlayer);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayer(VotePlayer player) {

        File dir = VotingSupport.getDataDir();
        dir.mkdirs();

        File file = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(player.getUuid())));
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (reader == null) {
            VotingSupport.log.error("Something went wrong attempting to read the Player Data");
            return;
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(player));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update cache method
    }

    public static VotePlayer getPlayer(UUID uuid) {
        File dir = VotingSupport.getDataDir();
        dir.mkdirs();

        if (VotingSupport.getVotePlayers().containsKey(uuid))
            return VotingSupport.getVotePlayers().get(uuid);

        File guildFile = new File(dir, "%uuid%.json".replaceAll("%uuid%", String.valueOf(uuid)));
        Gson gson = Adapters.PRETTY_MAIN_GSON;
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(guildFile));
        } catch (FileNotFoundException e) {
            VotingSupport.log.error("Something went wrong attempting to read the Player Data, new Player Perhaps?");
            return null;
        }

        return gson.fromJson(reader, VotePlayer.class);
    }

}
