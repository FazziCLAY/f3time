package com.fazziclay.openoptimizemc.mixin;

import com.fazziclay.openoptimizemc.OpenOptimizeMc;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<M> extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Shadow
    private void setModelPose(AbstractClientPlayerEntity a) {}

    /**
     * @author FazziCLAY ( <a href="https://fazziclay.github.io">My site</a> )
     * @reason Add ONLY_HEADS, IS_RENDER_PlAYERS, IS_PLAYER_MODEL_POSE
     */
    @Overwrite
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (OpenOptimizeMc.getConfig().isRenderPlayers()) {
            if (OpenOptimizeMc.getConfig().isPlayersModelPose() && !OpenOptimizeMc.getConfig().isPlayersOnlyHeads()) this.setModelPose(abstractClientPlayerEntity);
            if (OpenOptimizeMc.getConfig().isPlayersOnlyHeads()) {
                PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = getModel();
                playerEntityModel.setVisible(false);
                playerEntityModel.head.visible = true;
                playerEntityModel.hat.visible = true;
            }
            super.render(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }
    }
}
