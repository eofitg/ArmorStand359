package net.eofitg.armorstand359.mixin;

import net.eofitg.armorstand359.renderer.HitboxRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(
            net.minecraft.client.util.ObjectAllocator allocator,
            net.minecraft.client.render.RenderTickCounter tickCounter,
            boolean renderBlockOutline,
            net.minecraft.client.render.Camera camera,
            org.joml.Matrix4f positionMatrix,
            org.joml.Matrix4f matrix4f,
            org.joml.Matrix4f projectionMatrix,
            com.mojang.blaze3d.buffers.GpuBufferSlice fogBuffer,
            org.joml.Vector4f fogColor,
            boolean renderSky,
            CallbackInfo ci
    ) {
        MatrixStack matrices = new MatrixStack();
        matrices.multiplyPositionMatrix(positionMatrix);
        HitboxRenderer.renderArmorStandHitbox(matrices, camera.getPos());
    }
}