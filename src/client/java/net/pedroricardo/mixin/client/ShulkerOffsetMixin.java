package net.pedroricardo.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.pedroricardo.ShulkerRaiseClient;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public class ShulkerOffsetMixin {
    @Inject(method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;", shift = At.Shift.BEFORE))
    private void shulkerraise$renderBlockAbove(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (shulkerBoxBlockEntity.hasWorld() && ShulkerRaiseClient.shouldBeAffected(shulkerBoxBlockEntity.getWorld(), shulkerBoxBlockEntity.getPos().up())) {
            BlockModelRenderer.enableBrightnessCache();
            matrixStack.push();
            matrixStack.scale(1.0f, -1.0f, -1.0f);
            matrixStack.multiply(new Quaternionf().rotateXYZ(0.0f, -270.0F * shulkerBoxBlockEntity.getAnimationProgress(f) * 0.017453292F, 0.0f));
            matrixStack.translate(0.0f, shulkerBoxBlockEntity.getAnimationProgress(f) / 2.0f, 0.0f);
            matrixStack.translate(-0.5f, -0.5f, -0.5f);
            this.renderModel(shulkerBoxBlockEntity.getPos().up(), shulkerBoxBlockEntity.getWorld().getBlockState(shulkerBoxBlockEntity.getPos().up()), matrixStack, vertexConsumerProvider, shulkerBoxBlockEntity.getWorld(), false, j);
            matrixStack.pop();
            BlockModelRenderer.disableBrightnessCache();
        }
    }

    @Unique
    private void renderModel(BlockPos pos, BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, boolean cull, int overlay) {
        RenderLayer renderLayer = RenderLayers.getMovingBlockLayer(state);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(world, MinecraftClient.getInstance().getBlockRenderManager().getModel(state), state, pos, matrices, vertexConsumer, cull, Random.create(), state.getRenderingSeed(pos), overlay);
    }
}
