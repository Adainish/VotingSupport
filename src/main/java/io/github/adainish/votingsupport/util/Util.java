package io.github.adainish.votingsupport.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class Util {

    public static MinecraftServer getInstance() {
        return server;
    }

    private static MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static EntityPlayerMP getPlayer(UUID uuid) {
        return server.getPlayerList().getPlayerByUUID(uuid);
    }

    public static void runCommand(String cmd) {
        server.getCommandManager().executeCommand(server, cmd);
    }
}
