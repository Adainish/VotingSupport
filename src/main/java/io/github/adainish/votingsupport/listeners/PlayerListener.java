package io.github.adainish.votingsupport.listeners;

import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerListener {

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.player == null)
            return;

        EntityPlayer p = event.player;
        VotePlayer vp = PlayerStorage.getPlayer(p.getUniqueID());

        if (vp == null) {
            PlayerStorage.makeVotePlayer((EntityPlayerMP) p);
            vp = PlayerStorage.getPlayer(p.getUniqueID());
        }

        VotingSupport.getVotePlayers().put(p.getUniqueID(), vp);
    }


    @SubscribeEvent
    public void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {

        PlayerStorage.savePlayer(VotingSupport.getVotePlayers().get(event.player.getUniqueID()));
        VotingSupport.getVotePlayers().remove(event.player.getUniqueID());

    }
}