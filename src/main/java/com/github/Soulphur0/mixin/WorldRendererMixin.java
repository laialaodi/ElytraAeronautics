package com.github.Soulphur0.mixin;

import com.github.Soulphur0.behaviour.EanCloudRenderBehaviour;
import com.github.Soulphur0.config.singletons.CloudConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @WrapOperation(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V"
        )
    )
    private void ean_renderClouds(
        WorldRenderer instance, 
        MatrixStack matrices, 
        Matrix4f positionMatrix, 
        Matrix4f lastPositionMatrix,
        float tickDelta, 
        double x, 
        double y, 
        double z, 
        Operation<Void> original
    ) {
        if (CloudConfig.getOrCreateInstance().isUseEanClouds()) {
            // 调用自定义云渲染行为
            EanCloudRenderBehaviour.ean_renderClouds(instance, matrices, positionMatrix, tickDelta, x, y, z);
        } else {
            // 使用默认的云渲染
            original.call(instance, matrices, positionMatrix, lastPositionMatrix, tickDelta, x, y, z);
        }
    }
}