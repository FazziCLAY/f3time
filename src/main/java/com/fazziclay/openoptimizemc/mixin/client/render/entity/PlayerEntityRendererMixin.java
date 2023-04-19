package com.fazziclay.openoptimizemc.mixin.client.render.entity;

import com.fazziclay.openoptimizemc.OP;
import com.fazziclay.openoptimizemc.OpenOptimizeMc;
import com.fazziclay.openoptimizemc.behavior.BehaviorManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<M> extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    @Shadow protected abstract void setModelPose(AbstractClientPlayerEntity player);

    private static final String OP_OPENOPTIMIZEMC_MIXIN = "OpenOptimizeMC mixin";
    private static final BehaviorManager behaviorManager = OpenOptimizeMc.getBehaviorManager();


    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    /**
     * @author FazziCLAY ( <a href="https://fazziclay.github.io">My site</a> )
     * @reason Add ONLY_HEADS, IS_RENDER_PlAYERS, IS_PLAYER_MODEL_POSE
     */
    @Overwrite
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        if (!behaviorManager.getBehavior().renderPlayers()) {
            return;
        }
        OP.push(OP_OPENOPTIMIZEMC_MIXIN);
        if (behaviorManager.getBehavior().cubePrimitivePlayers(abstractClientPlayerEntity)) {
            renderCubePrimitivePlayer(abstractClientPlayerEntity, matrixStack, vertexConsumerProvider, light);
            OP.pop();
            return;
        }
        setModelPose(abstractClientPlayerEntity);
        if (behaviorManager.getBehavior().onlyHeadPlayers(abstractClientPlayerEntity)) {
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = getModel();
            playerEntityModel.setVisible(false);
            playerEntityModel.head.visible = true;
            playerEntityModel.hat.visible = true;
        }
        OP.pop();
        super.render(abstractClientPlayerEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    private void renderCubePrimitivePlayer(AbstractClientPlayerEntity player, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        float f = 0.75f;
        double x1 = f * -0.5;
        double y1 = f * -0.5 + 1;
        double z1 = f * -0.5;
        double x2 = f * 0.5;
        double y2 = f * 0.5 + 1;
        double z2 = f * 0.5;

        matrices.push();
        MatrixStack matrix = RenderSystem.getModelViewStack();
        matrix.push();
        Matrix4f mmm = matrices.peek().getPositionMatrix();
        matrix.multiplyPositionMatrix(mmm);
        matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-player.getYaw()));
        matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(player.getPitch()));
        RenderSystem.applyModelViewMatrix();


        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR_LIGHT);

        drawBox(bufferBuilder, x1, y1, z1, x2, y2, z2, 0, 1, 0, 1f, light);
        tessellator.draw();
        RenderSystem.enableTexture();

        matrix.pop();
        matrices.pop();
        RenderSystem.applyModelViewMatrix();
    }

    private void drawBox(VertexConsumer buffer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha, int light) {
        buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y1, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x1, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z1).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).light(light).next();
        buffer.vertex(x2, y2, z2).color(red, green, blue, alpha).light(light).next();
    }
}
