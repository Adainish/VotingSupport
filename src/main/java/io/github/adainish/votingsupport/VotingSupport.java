package io.github.adainish.votingsupport;

import io.github.adainish.votingsupport.config.*;
import io.github.adainish.votingsupport.listeners.PlayerListener;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.LeaderboardStorage;
import io.github.adainish.votingsupport.storage.VotePartyStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Mod(
        modid = VotingSupport.MOD_ID,
        name = VotingSupport.MOD_NAME,
        version = VotingSupport.VERSION,
        acceptedMinecraftVersions = "1.12.2",
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class VotingSupport {

    public static final String MOD_ID = "votingsupport";
    public static final String MOD_NAME = "VotingSupport";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final String AUTHORS = "Winglet";
    public static Logger log = LogManager.getLogger(MOD_NAME);

    private static HashMap<UUID, VotePlayer> votePlayers = new HashMap <>();
    private static Leaderboard leaderboard;
    private static VoteParty party;
    private static File leaderboardDir;
    private static File configDir;
    private static File dataDir;
    private static File storageDir;

    private static VotingSupport instance;
    @Mod.Instance(MOD_ID)
    public static VotingSupport INSTANCE;

    public static VotingSupport getInstance() {
        return instance;
    }

    public static void setInstance(VotingSupport instance) {
        VotingSupport.instance = instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setConfigDir(new File(event.getModConfigurationDirectory() + "/"));
        configDir.mkdirs();
        setDataDir(new File(configDir + "/VotingSupport/data/"));
        dataDir.mkdirs();
        setStorageDir(new File(configDir + "/VotingSupport/storage/"));
        storageDir.mkdirs();
        setLeaderboardDir(new File(storageDir + "/leaderboard/"));
        leaderboardDir.mkdirs();
        setupConfigs();
        loadConfigs();

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        loadObjects();
    }


    public static HashMap <UUID, VotePlayer> getVotePlayers() {
        return votePlayers;
    }

    public static void setVotePlayers(HashMap <UUID, VotePlayer> votePlayers) {
        VotingSupport.votePlayers = votePlayers;
    }


    public static Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public static void setLeaderboard(Leaderboard leaderboard) {
        VotingSupport.leaderboard = leaderboard;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        VotingSupport.configDir = configDir;
    }

    public static File getDataDir() {
        return dataDir;
    }

    public static File getLeaderboardDir() {
        return leaderboardDir;
    }

    public static void setLeaderboardDir(File leaderboardDir) {
        VotingSupport.leaderboardDir = leaderboardDir;
    }

    public static File getStorageDir() {
        return storageDir;
    }

    public static void setStorageDir(File storageDir) {
        VotingSupport.storageDir = storageDir;
    }

    public static void setDataDir(File dataDir) {
        VotingSupport.dataDir = dataDir;
    }

    public static VoteParty getParty() {
        return party;
    }

    public static void setParty(VoteParty party) {
        VotingSupport.party = party;
    }

    public static void setupConfigs() {
        MainConfig.getConfig().setup();
        RewardsConfig.getConfig().setup();
        GUIConfig.getConfig().setup();
        StreakConfig.getConfig().setup();
        VotePartyConfig.getConfig().setup();
        TopVoterConfig.getConfig().setup();
    }
    public static void loadConfigs() {
        MainConfig.getConfig().load();
        RewardsConfig.getConfig().load();
        GUIConfig.getConfig().load();
        StreakConfig.getConfig().load();
        VotePartyConfig.getConfig().load();
        TopVoterConfig.getConfig().load();
    }
    public static void loadObjects() {
        initialiseLeaderBoard();
        initialiseVoteParty();
    }

    public static void initialiseLeaderBoard() {
        Leaderboard l = LeaderboardStorage.getLeaderboard();

        if (l == null) {
            LeaderboardStorage.makeLeaderBoard();
            l = LeaderboardStorage.getLeaderboard();
        }

        setLeaderboard(l);
        LeaderboardStorage.saveLeaderBoard(getLeaderboard());
    }

    public static void initialiseVoteParty() {
        VoteParty p = VotePartyStorage.getVoteParty();

        if (p == null) {
            VotePartyStorage.makeVoteParty();
            p = VotePartyStorage.getVoteParty();
        }

        setParty(p);
        VotePartyStorage.saveVoteParty(getParty());
    }
}
