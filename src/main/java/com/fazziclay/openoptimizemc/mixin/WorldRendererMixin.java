package com.fazziclay.openoptimizemc.mixin;

import com.fazziclay.openoptimizemc.OpenOptimizeMc;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow @Final private MinecraftClient client;


    @Inject(at = @At("HEAD"), method = "renderEntity", cancellable = true)
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo info) {
        if (!OpenOptimizeMc.getConfig().isRenderEntities()) {
            info.cancel();
            return;
        }
        if (OpenOptimizeMc.getConfig().isAdvancedProfilerForGameRendererEntities()) {
            client.getProfiler().push(Registry.ENTITY_TYPE.getId(entity.getType()).toString());
        }
    }

    @Inject(at = @At("TAIL"), method = "renderEntity")
    private void renderEntity_profilerPop(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo info) {
        if (OpenOptimizeMc.getConfig().isAdvancedProfilerForGameRendererEntities()) {
            client.getProfiler().pop();
        }
    }

    @Inject(at = @At("HEAD"), method = "updateChunks", cancellable = true)
    private void updateChunks(Camera camera, CallbackInfo info) {
        if (!OpenOptimizeMc.getConfig().isUpdateChunks()) info.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderLayer", cancellable = true)
    private void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, CallbackInfo info) {
        if (!OpenOptimizeMc.getConfig().isRenderLevel()) info.cancel();
    }
}
