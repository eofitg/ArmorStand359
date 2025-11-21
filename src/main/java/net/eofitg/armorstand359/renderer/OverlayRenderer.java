package net.eofitg.armorstand359.renderer;

import net.eofitg.armorstand359.ArmorStand359;
import net.eofitg.armorstand359.util.ArmorStandUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class OverlayRenderer {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Text aimText = Text.literal("§c§l||||||||||");
    private static final Text reguText = Text.literal("§a§l||||||||||");

    public static void renderArmorStandOverlay() {
        if (client.world == null || client.getBufferBuilders() == null) return;

        if (client.player == null) return;
        Vec3d playerPos = client.player.getCameraPosVec(1.0f);
        double range = client.options.getClampedViewDistance() * 16;
        Box area = new Box(playerPos.add(-range, -range, -range), playerPos.add(range, range, range));

        for (ArmorStandEntity entity : client.world.getEntitiesByType(net.minecraft.entity.EntityType.ARMOR_STAND, area, entity -> true)) {
            if (entity.getCustomName() != null && !Objects.equals(entity.getCustomName(), aimText) && !Objects.equals(entity.getCustomName(), reguText)) continue;
            entity.setCustomNameVisible(true);
            if (!ArmorStand359.enabled) {
                entity.setCustomNameVisible(false);
                continue;
            }
            if (!Objects.equals(ArmorStand359.target, "")) {
                if (!Objects.equals(ArmorStand359.target, ArmorStandUtil.getArmorStandId(entity))) {
                    entity.setCustomNameVisible(false);
                    continue;
                }
            }
            if (client.targetedEntity != null && client.targetedEntity == entity) {
                entity.setCustomName(aimText);
            } else {
                entity.setCustomName(reguText);
            }
        }
    }
}
