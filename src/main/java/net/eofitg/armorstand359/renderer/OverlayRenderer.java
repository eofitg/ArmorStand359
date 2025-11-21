package net.eofitg.armorstand359.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class OverlayRenderer {
    public static void renderArmorStandOverlay() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.getBufferBuilders() == null) return;

        if (client.player == null) return;
        Vec3d playerPos = client.player.getCameraPosVec(1.0f);
        double range = client.options.getClampedViewDistance() * 16;
        Box area = new Box(playerPos.add(-range, -range, -range), playerPos.add(range, range, range));

        for (ArmorStandEntity entity : client.world.getEntitiesByType(net.minecraft.entity.EntityType.ARMOR_STAND, area, entity -> true)) {
            entity.setCustomNameVisible(true);
            entity.setCustomName(Text.literal("§a|||||||||||"));
        }
    }
}
