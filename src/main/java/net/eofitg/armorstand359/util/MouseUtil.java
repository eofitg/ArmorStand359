package net.eofitg.armorstand359.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ArmorStandEntity;

public class MouseUtil {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static ArmorStandEntity getMouseOverAS() {
        if (client.player == null || client.world == null || client.targetedEntity == null) {
            return null;
        }
        if (client.targetedEntity instanceof ArmorStandEntity) {
            return (ArmorStandEntity) client.targetedEntity;
        }
        return null;
    }
}
