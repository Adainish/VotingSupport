package io.github.adainish.votingsupport.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.gui.LeaderBoardGUI;
import io.github.adainish.votingsupport.handlers.RewardHandler;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FakeVoteCommand extends CommandBase {
    @Override
    public String getName() {
        return "fakevote";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/fakevote <add/set> <vote/streak> <amount>";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.fakevote.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("add")) {
                    if (PermissionUtil.canUse("votingsupport.command.fakevote.add", sender)) {
                        Util.send(sender, "&cPlease provide a valid streak/vote count option, as well as a player!");
                    } else sendNoPermMessage(sender);
                }
                break;
            }
            case 2: {
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("set")) {
                    if (args[1].equalsIgnoreCase("streak") || args[1].equalsIgnoreCase("vote")) {
                        Util.send(sender, "&cPlease provide a player to adjust!");
                    }
                }
                break;
            }
            case 3: {
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("set")) {
                    if (args[1].equalsIgnoreCase("streak") || args[1].equalsIgnoreCase("vote")) {
                        String userName = "";
                        UUID uuid;
                        userName = args[2];
                        try {
                            uuid = ProfileFetcher.getUUID(userName);
                        } catch (IOException e) {
                            Util.send(sender, "&cCould not verify the target with mojangs services");
                            break;
                        } catch (NullPointerException e) {
                            Util.send(sender, "&cCould not find the players data, are you sure they've logged in before, or if the player even exists?");
                            break;
                        }
                        if (uuid == null) {
                            Util.send(sender, "&cThe provided player doesn't exist");
                            Util.send(sender, "&cWe also need a valid amount to work with!");
                            break;
                        }
                        if (Util.isOnline(uuid)) {
                            Util.send(sender, "&cPlease provide an amount to work with!");
                            break;
                        }
                    }
                }
                break;
            }
            case 4: {
                String userName = "";
                UUID uuid;
                int amount = 0;
                String secArg = args[3];
                if (args[0].equalsIgnoreCase("add")) {
                    userName = args[2];
                    try {
                        uuid = ProfileFetcher.getUUID(userName);
                    } catch (IOException e) {
                        Util.send(sender, "&cCould not verify the target with mojangs services");
                        break;
                    } catch (NullPointerException e) {
                        Util.send(sender, "&cCould not find the players data, are you sure they've logged in before, or if the player even exists?");
                        break;
                    }
                    VotePlayer p = null;
                    if (Util.isOnline(uuid)) {
                        p = VotingSupport.getVotePlayers().get(uuid);
                    } else p = PlayerStorage.getPlayer(uuid);

                    if (p == null) {
                        Util.send(sender, "&cPlayer doesn't exist in storage, are you sure they've logged in before?");
                        break;
                    }
                    try {
                        amount = Integer.parseInt(secArg);
                    } catch (NumberFormatException e) {
                        Util.send(sender, "&cThat's not a valid number!");
                        return;
                    }
                    if (args[1].equalsIgnoreCase("vote")) {
                        p.increaseVote(amount);
                        p.increaseLeaderboardCount(amount);
                        p.markVote();
                        for (int i = 0; i < amount; i++) {
                            RewardHandler.handOutVoteReward(p);
                        }
                        VotingSupport.log.info("Forcefully increased the vote count for " + p.getUserName() + " " + p.getUuid() + " by :" + amount);
                        VotingSupport.log.info("They've been handed out new rewards");
                    }

                    if (args[1].equalsIgnoreCase("streak")) {
                        p.getStreak().increaseStreakDay(amount);
                        VotingSupport.log.info("Forcefully increased the streak count for " + p.getUserName() + " " + p.getUuid() + " by :" + amount);
                        VotingSupport.log.info("They now have new rewards available");
                    }
                    p.updateCache();

                }
                if (args[0].equalsIgnoreCase("set")) {
                    userName = args[1];
                    try {
                        uuid = ProfileFetcher.getUUID(userName);
                    } catch (IOException e) {
                        Util.send(sender, "&cCould not verify the target with mojangs services");
                        break;
                    } catch (NullPointerException e) {
                        Util.send(sender, "&cCould not find the players data, are you sure they've logged in before, or if the player even exists?");
                        break;
                    }
                    VotePlayer p = null;
                    if (Util.isOnline(uuid)) {
                        p = VotingSupport.getVotePlayers().get(uuid);
                    } else p = PlayerStorage.getPlayer(uuid);

                    if (p == null) {
                        Util.send(sender, "&cPlayer doesn't exist in storage, are you sure they've logged in before?");
                        break;
                    }
                    try {
                        amount = Integer.parseInt(secArg);
                    } catch (NumberFormatException e) {
                        Util.send(sender, "&cThat's not a valid number!");
                        return;
                    }
                    if (args[1].equalsIgnoreCase("vote")) {
                        p.setVoteCount(amount);
                        p.setLeaderBoardCount(amount);
                        VotingSupport.log.info("Forcefully set the vote count for " + p.getUserName() + " " + p.getUuid() + " by :" + amount);
                    }

                    if (args[1].equalsIgnoreCase("streak")) {
                        p.getStreak().setStreakDay(amount);
                        VotingSupport.log.info("Forcefully set the Streak Day for " + p.getUserName() + " " + p.getUuid() + " by :" + amount);
                    }
                    p.updateCache();
                }

            }
            default: {
                if (sender instanceof EntityPlayer) {
                    Util.send(sender, getUsage(sender));
                } else {
                    Util.send(sender, "&cOnly a player may use this command");
                    return;
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
        return Arrays.asList("vtp", "top");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList <>();

        if (args.length == 1) {
            if (PermissionUtil.canUse("votingsupport.command.votetop.reset", sender))
                possibleArgs.add("reset");
        }

        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}
