package io.github.adainish.votingsupport;

import com.google.inject.Inject;
import io.github.adainish.votingsupport.config.*;
import io.github.adainish.votingsupport.listeners.VoteListener;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.obj.VoteParty;
import io.github.adainish.votingsupport.obj.VotePlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Plugin(

        id = "votingsupport",
        name = "VotingSupport",
        version = VotingSupport.VERSION,
        description = "Voting Support Plugin developed by Winglet, offering a multitude of extensive features for voting integration"
)

@Mod(
        modid = VotingSupport.MOD_ID,
        name = VotingSupport.MOD_NAME,
        version = VotingSupport.VERSION,
        acceptableRemoteVersions = "1.12.2",
        serverSideOnly = true
)
public class VotingSupport {

    public static final String MOD_ID = "votingsupport";
    public static final String MOD_NAME = "VotingSupport";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final String AUTHORS = "Winglet";
    public static Logger log = LogManager.getLogger(MOD_NAME);
    @Inject
    private Game game;
    private PluginContainer plugin;

    private static HashMap<UUID, VotePlayer> votePlayers = new HashMap <>();
    private static Leaderboard leaderboard;
    private static VoteParty party;
    private static File configDir;
    private static File dataDir;

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
    public void preinit(FMLPreInitializationEvent event) {
        setConfigDir(new File(event.getModConfigurationDirectory() + "/"));
        configDir.mkdirs();
        setDataDir(new File(configDir + "/VotingSupport/data/"));
        dataDir.mkdirs();
        setupConfigs();
        loadConfigs();
    }

    @Listener
    public void onAboutToStart(GameAboutToStartServerEvent event) {
        setInstance(this);
        this.game.getEventManager().registerListeners(this, new VoteListener());
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        this.plugin = Sponge.getPluginManager().getPlugin("votingsupport").get();
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

    }

    public static void initialiseVoteParty() {

    }
}
