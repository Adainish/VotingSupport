package io.github.adainish.votingsupport.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import com.cable.library.tasks.Task;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.gui.VotePartyGUI;
import io.github.adainish.votingsupport.util.PermissionUtil;
import io.github.adainish.votingsupport.util.Util;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VotePartyCommand extends CommandBase {
    @Override
    public String getName() {
        return "voteparty";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/voteparty";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.voteparty.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        if (args.length >= 3) {
            Util.send(sender, getUsage(sender));
            return;
        }

        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("force")) {
                    if (PermissionUtil.canUse("votingsupport.command.voteparty.force", sender)) {
                        Util.send(sender, "&cMissing a second argument! /voteparty force <seconds>");
                    } else sendNoPermMessage(sender);
                }
                break;
            }
            case 2: {
                if (args[0].equalsIgnoreCase("force")) {
                    if (PermissionUtil.canUse("votingsupport.command.voteparty.force", sender)) {
                        int seconds = 0;
                        String secArg = args[1];
                        try {
                            seconds = Integer.parseInt(secArg);
                        } catch (NumberFormatException e) {
                            Util.send(sender, "&cThat's not a valid number!");
                            return;
                        }
                        Task.builder()
                                .delay(20L * seconds)
                                .iterations(0)
                                .execute(() -> {
                                    VotingSupport.getParty().executeVoteParty();
                                    VotingSupport.log.info("&cForcibly executed a Vote Party...");
                        }).build();
                        Util.send(sender, "&aOkay! &7We've scheduled a &bVote Party &7to occur in &a%s Seconds"
                                .replace("%s", secArg));
                    } else sendNoPermMessage(sender);
                }
                break;
            }
            default: {
                if (sender instanceof EntityPlayer) {
                    GooeyPage p = VotePartyGUI.VotePartyGUI((EntityPlayer) sender);
                    UIManager.openUIPassively((EntityPlayerMP) sender, p, 20, TimeUnit.SECONDS);
                } else {
                    Util.send(sender, "&cOnly a player may use this command");
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
        return Arrays.asList("vp", "votep");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

}
