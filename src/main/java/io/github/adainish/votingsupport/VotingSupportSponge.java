package io.github.adainish.votingsupport;

import com.google.inject.Inject;
import io.github.adainish.votingsupport.listeners.VoteListener;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(

        id = "votingsupportsponge",
        name = "VotingSupport",
        version = VotingSupport.VERSION,
        description = "Voting Support Plugin developed by Winglet, offering a multitude of extensive features for voting integration"
)

public class VotingSupportSponge {
    private static VotingSupportSponge instance;
    @Inject private Logger logger;
    @Inject private Game game;
    private PluginContainer plugin;

    public static void setInstance(VotingSupportSponge instance) {
        VotingSupportSponge.instance = instance;
    }

    @Listener
    public void onAboutToStart(GameAboutToStartServerEvent event) {
        VotingSupport.log.info("Initialising Sponge and Voting integration for Voting Support");
        setInstance(this);
        game = instance.game;
        this.game.getEventManager().registerListeners(this, new VoteListener());
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        setPlugin(Sponge.getPluginManager().getPlugin("votingsupport").get());

        VotingSupport.log.info("Sponge system and vote support initialised");
    }

    public PluginContainer getPlugin() {
        return plugin;
    }

    public void setPlugin(PluginContainer plugin) {
        this.plugin = plugin;
    }
}
