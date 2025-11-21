package net.eofitg.armorstand359.mixin;

import net.eofitg.armorstand359.ArmorStand359;
import net.eofitg.armorstand359.renderer.OverlayRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.state.ArmorStandEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public class ArmorStandRendererMixin {
    @Inject(
            method = "render", at = @At("HEAD")
    )
    private void onRenderArmorStandBefore(
            ArmorStandEntityRenderState armorStandEntityRenderState,
            MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            CameraRenderState cameraRenderState,
            CallbackInfo ci
    ) {
        OverlayRenderer.renderArmorStandOverlay();
    }
}
