package io.github.adainish.votingsupport.commands;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import com.cable.library.tasks.Task;
import io.github.adainish.votingsupport.VotingSupport;
import io.github.adainish.votingsupport.gui.LeaderBoardGUI;
import io.github.adainish.votingsupport.obj.Leaderboard;
import io.github.adainish.votingsupport.util.PermissionUtil;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VoteTopCommand extends CommandBase {
    @Override
    public String getName() {
        return "votetop";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/scheduler";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.votetop.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("reset")) {
                    if (PermissionUtil.canUse("votingsupport.command.votetop.reset", sender)) {
                        Util.send(sender, "&4The Leader Board has been Reset, all top positions have been cached!");
                        VotingSupport.getLeaderboard().cacheVoteSpots();
                    } else sendNoPermMessage(sender);
                }
                break;
            }
            default: {
                if (sender instanceof EntityPlayer) {
                    LinkedPage p = LeaderBoardGUI.LeaderBoardDisplay();
                    UIManager.openUIPassively((EntityPlayerMP) sender, p, 20, TimeUnit.SECONDS);
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
