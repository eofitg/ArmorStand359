package net.eofitg.armorstand359;

import net.eofitg.armorstand359.command.ArmorStandCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class ArmorStand359 implements ClientModInitializer {
    public static boolean enabled = true;
    public static String target = "";

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> ArmorStandCommand.register(dispatcher));
    }
}
