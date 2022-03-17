package io.github.adainish.votingsupport;

import com.cable.library.tasks.Task;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import io.github.adainish.votingsupport.config.*;
import io.github.adainish.votingsupport.listeners.PlayerListener;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.Streak;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.registry.RewardRegistry;
import io.github.adainish.votingsupport.storage.LeaderboardStorage;
import io.github.adainish.votingsupport.storage.VotePartyStorage;
import io.github.adainish.votingsupport.tasks.CheckLeaderBoardTask;
import io.github.adainish.votingsupport.tasks.DueRewardsTask;
import io.github.adainish.votingsupport.tasks.UpdateStorageTask;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static final String YEAR = "2022";
    public static Logger log = LogManager.getLogger(MOD_NAME);

    private static HashMap<UUID, VotePlayer> votePlayers = new HashMap <>();
    private static Leaderboard leaderboard;
    private static VoteParty party;
    private static File leaderboardDir;
    private static File configDir;
    private static File dataDir;
    private static File storageDir;
    private static List <Streak> streakList = new ArrayList<>();

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
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
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

        Task.builder().infiniteIterations().interval( (20 * 60) * 5).execute(new UpdateStorageTask()).build();
        Task.builder().infiniteIterations().interval( (20 * 60) * 2 ).execute(new DueRewardsTask()).build();
        Task.builder().infiniteIterations().interval( (20 * 60) * 5).execute(new CheckLeaderBoardTask()).build();

        //Register Commands
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


    public static List <Streak> getStreakList() {
        return streakList;
    }

    public static void setStreakList(List <Streak> streakList) {
        VotingSupport.streakList = streakList;
    }


    public static void setupConfigs() {
        MainConfig.getConfig().setup();
        RewardsConfig.getConfig().setup();
        GUIConfig.getConfig().setup();
        StreakConfig.getConfig().setup();
        VotePartyConfig.getConfig().setup();
        TopVoterConfig.getConfig().setup();
        LeaderBoardConfig.getConfig().setup();
    }
    public static void loadConfigs() {
        MainConfig.getConfig().load();
        RewardsConfig.getConfig().load();
        GUIConfig.getConfig().load();
        StreakConfig.getConfig().load();
        VotePartyConfig.getConfig().load();
        TopVoterConfig.getConfig().load();
        LeaderBoardConfig.getConfig().load();
    }
    public static void loadObjects() {
        RewardRegistry.loadVoteRewardsToCache();
        initialiseLeaderBoard();
        initialiseVoteParty();
    }

    public static void resetLeaderBoard() {
        Leaderboard l = getLeaderboard();
        l.setInitialisedTime(System.currentTimeMillis());
        l.setValidDays(LeaderBoardConfig.getConfig().get().getNode("LeaderBoard", "ValidDays").getInt());
        l.setAutomatic(LeaderBoardConfig.getConfig().get().getNode("LeaderBoard", "Automatic").getBoolean());
        setLeaderboard(l);
        LeaderboardStorage.saveLeaderBoard(l);
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

    public static void regenCachedVoteParty(VoteParty p) {
        setParty(p);
        VotePartyStorage.saveVoteParty(p);
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

    public static void regenVoteParty() {
        VoteParty p = new VoteParty();
        setParty(p);
        VotePartyStorage.saveVoteParty(p);
        log.info("Refreshed the Vote Party Data as one just occured");
    }

    public static void regenStreaks() {
        getStreakList().clear();


    }
}
