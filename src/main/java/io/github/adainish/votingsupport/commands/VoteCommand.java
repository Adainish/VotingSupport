package io.github.adainish.votingsupport.commands;

import com.google.common.reflect.TypeToken;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import io.github.adainish.votingsupport.config.MainConfig;
import io.github.adainish.votingsupport.obj.Message;
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

public class VoteCommand extends CommandBase {
    @Override
    public String getName() {
        return "vote";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/vote";
    }

    public void sendNoPermMessage(ICommandSender sender) {
        Util.send(sender, "&c(&4!&c) &eYou're not allowed to do this");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!PermissionUtil.canUse("votingsupport.command.vote.base", sender)) {
            sendNoPermMessage(sender);
            return;
        }

        if (args.length >= 2) {
            Util.send(sender, getUsage(sender));
            return;
        }

        List<String> messageStrings = null;
        try {
            messageStrings = MainConfig.getConfig().get().getNode("VoteMessage").getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            messageStrings = new ArrayList <>();
            return;
        }

        if (messageStrings == null) {
            Util.send(sender, "&4&l(&e&l!&4&l) &cSomething went wrong loading the vote command, please report this to an Administrator!");
            return;
        }
        if (sender instanceof EntityPlayer) {
            Message message = new Message(messageStrings, (EntityPlayerMP) sender);
            message.sendMessage();
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
        return Arrays.asList("vo");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
