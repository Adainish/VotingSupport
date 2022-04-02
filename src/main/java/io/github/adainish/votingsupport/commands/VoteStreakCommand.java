package io.github.adainish.votingsupport.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.config.MainConfig;
import io.github.adainish.votingsupport.gui.StreakGUI;
import io.github.adainish.votingsupport.obj.Message;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.util.PermissionUtil;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VoteStreakCommand extends CommandBase {
    @Override
    public String getName() {
        return "streak";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/streak";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.votestreak.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        if (args.length >= 2) {
            Util.send(sender, getUsage(sender));
            return;
        }
        if (sender instanceof EntityPlayer) {
            VotePlayer p = VotingSupport.getVotePlayers().get(((EntityPlayer) sender).getUniqueID());
            if (p == null) {
                Util.send(sender, "&cSomething went wrong loading your playerdata, please contact an Administrator!");
                return;
            }
            UIManager.openUIPassively((EntityPlayerMP) sender, StreakGUI.StreakDisplay(p), 20, TimeUnit.SECONDS);
        } else {
            Util.send(sender, "&cOnly a player may use this command");
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public @NotNull List <String> getAliases() {
        return Arrays.asList("vs", "votestreak");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
