package io.github.adainish.votingsupport.util;

import io.github.adainish.votingsupport.VotingSupport;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Util {

    public static MinecraftServer getInstance() {
        return server;
    }

    private static MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static EntityPlayerMP getPlayer(UUID uuid) {
        return server.getPlayerList().getPlayerByUUID(uuid);
    }

    public static void runCommands(List <String> cmds) {
        for (String s:cmds) {
            runCommand(s);
        }
    }

    public static void runCommands(List <String> cmds, EntityPlayerMP target) {
        if (target == null) {
            VotingSupport.log.info("Someone voted while offline and didn't get their rewards");
            return;
        }
        if (isOnline(target.getUniqueID())) {
            for (String s : cmds) {
                runCommand(s.replace("@pl", target.getName()));
            }
        } else {
            VotingSupport.log.info("Someone voted while offline and didn't get their rewards");
        }
    }

    public static boolean isOnline(UUID uuid) {
        return getInstance().getPlayerList().getPlayers().stream().anyMatch(p -> p.getUniqueID().equals(uuid));
    }

    public static void send(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }

    public static void runCommand(String cmd) {
        server.getCommandManager().executeCommand(server, cmd);
    }

    public static String formattedString(String s) {
        return s.replaceAll("&", "§");
    }

    public static List<String> formattedArrayList(List<String> list) {

        List<String> formattedList = new ArrayList <>();
        for (String s:list) {
            formattedList.add(formattedString(s));
        }

        return formattedList;
    }
}
