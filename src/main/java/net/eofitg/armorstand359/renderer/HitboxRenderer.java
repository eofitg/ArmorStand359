package net.eofitg.armorstand359.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class HitboxRenderer {

    public static void renderArmorStandHitbox(MatrixStack matrices, Vec3d cameraPos) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.getBufferBuilders() == null) return;

        VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());

        if (client.player == null) return;
        Vec3d playerPos = client.player.getCameraPosVec(1.0f);
        double range = client.options.getClampedViewDistance() * 16;
        Box area = new Box(playerPos.add(-range, -range, -range), playerPos.add(range, range, range));

        for (ArmorStandEntity entity : client.world.getEntitiesByType(net.minecraft.entity.EntityType.ARMOR_STAND, area, entity -> true)) {
            renderHitbox(matrices, vertexConsumer, cameraPos, entity);
        }

        vertexConsumers.draw();
    }

    private static void renderHitbox(MatrixStack matrices, VertexConsumer vertexConsumer, Vec3d cameraPos, ArmorStandEntity armorStand) {
        Box hitbox = armorStand.getBoundingBox();
        armorStand.setCustomNameVisible(true);

        double x = hitbox.minX - cameraPos.x;
        double y = hitbox.minY - cameraPos.y;
        double z = hitbox.minZ - cameraPos.z;
        double maxX = hitbox.maxX - cameraPos.x;
        double maxY = hitbox.maxY - cameraPos.y;
        double maxZ = hitbox.maxZ - cameraPos.z;

        MatrixStack.Entry entry = matrices.peek();
        float r = 1f, g = 1f, b = 1f, a = 1f;

        drawLine(entry, vertexConsumer, x, y, z, maxX, y, z, r, g, b, a);
        drawLine(entry, vertexConsumer, x, maxY, z, maxX, maxY, z, r, g, b, a);
        drawLine(entry, vertexConsumer, x, y, maxZ, maxX, y, maxZ, r, g, b, a);
        drawLine(entry, vertexConsumer, x, maxY, maxZ, maxX, maxY, maxZ, r, g, b, a);

        drawLine(entry, vertexConsumer, x, y, z, x, maxY, z, r, g, b, a);
        drawLine(entry, vertexConsumer, maxX, y, z, maxX, maxY, z, r, g, b, a);
        drawLine(entry, vertexConsumer, x, y, maxZ, x, maxY, maxZ, r, g, b, a);
        drawLine(entry, vertexConsumer, maxX, y, maxZ, maxX, maxY, maxZ, r, g, b, a);

        drawLine(entry, vertexConsumer, x, y, z, x, y, maxZ, r, g, b, a);
        drawLine(entry, vertexConsumer, maxX, y, z, maxX, y, maxZ, r, g, b, a);
        drawLine(entry, vertexConsumer, x, maxY, z, x, maxY, maxZ, r, g, b, a);
        drawLine(entry, vertexConsumer, maxX, maxY, z, maxX, maxY, maxZ, r, g, b, a);
    }

    private static void drawLine(MatrixStack.Entry entry, VertexConsumer vertexConsumer,
                                 double x1, double y1, double z1,
                                 double x2, double y2, double z2,
                                 float r, float g, float b, float a) {
        vertexConsumer.vertex(entry.getPositionMatrix(), (float)x1, (float)y1, (float)z1)
                .color(r, g, b, a)
                .normal(0.0f, 1.0f, 0.0f);
        vertexConsumer.vertex(entry.getPositionMatrix(), (float)x2, (float)y2, (float)z2)
                .color(r, g, b, a)
                .normal(0.0f, 1.0f, 0.0f);
    }

}
