package io.github.adainish.votingsupport.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.gui.VotePartyGUI;
import io.github.adainish.votingsupport.obj.VotePlayer;
import io.github.adainish.votingsupport.storage.PlayerStorage;
import io.github.adainish.votingsupport.util.PermissionUtil;
import io.github.adainish.votingsupport.util.ProfileFetcher;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class VoteCheckCommand extends CommandBase {
    @Override
    public String getName() {
        return "votecheck";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/votecheck";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.votecheck", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        switch (args.length) {
            case 1: {
                if (PermissionUtil.canUse("votingsupport.command.votecheck", sender)) {
                    UUID uuid;
                    String arg = args[0];
                    VotePlayer p;
                        try {
                            uuid = ProfileFetcher.getUUID(arg);
                            if (Util.isOnline(uuid))
                                p = VotingSupport.getVotePlayers().get(uuid);
                            else p = PlayerStorage.getPlayer(uuid);

                            if (p == null) {
                                Util.send(sender, "&4The players data doesn't exist, are you sure they've logged in before?");
                                break;
                            }

                            Util.send(sender, "&7The Vote Count for &e%target% &7is &b" + p.getVoteCount());
                            Util.send(sender, "&7Their &bLeaderboard &7count is &b" + p.getLeaderBoardCount());

                        } catch (IOException e) {
                            Util.send(sender, "&cSomething went wrong connecting and/or Verifying the profided username with Mojang");
                        } catch (NullPointerException e) {
                            Util.send(sender, "&4The provided user does not exist according to Mojang");
                        }
                    break;
                } else sendNoPermMessage(sender);
                break;
            }
            case 2: {
                if (PermissionUtil.canUse("votingsupport.command.votecheck", sender)) {
                    Util.send(sender, getUsage(sender));
                    break;
                } else sendNoPermMessage(sender);
                break;
            }
            default: {
                if (sender instanceof EntityPlayer) {
                    VotePlayer p = VotingSupport.getVotePlayers().get(((EntityPlayer) sender).getUniqueID());
                    Util.send(sender, "&7Your current vote count: &b" + p.getVoteCount());
                    Util.send(sender, "&7Your current leader board count: &b" + p.getLeaderBoardCount());
                } else {
                    Util.send(sender, "&cOnly a player may use this command");
                    break;
                }
                break;
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public @NotNull List <String> getAliases() {
        return Arrays.asList("vcheck", "checkvote");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List <String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List <String> possibleArgs = new ArrayList <>();

        if (args.length == 1) {
            if (PermissionUtil.canUse("votingsupport.command.votecheck", sender))
                Collections.addAll(possibleArgs, Util.getInstance().getPlayerList().getOnlinePlayerNames());
        }

        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}
