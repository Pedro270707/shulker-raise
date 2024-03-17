package net.pedroricardo.mixin.client;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.pedroricardo.ShulkerRaiseClient;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityOffsetMixin {
    @Inject(method = "render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", shift = At.Shift.BEFORE))
    private static <T extends BlockEntity> void shulkerraise$offsetBlockEntity(BlockEntityRenderer<T> renderer, T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (blockEntity.hasWorld() && ShulkerRaiseClient.shouldBeAffected(blockEntity.getWorld(), blockEntity.getPos())) {
            ShulkerBoxBlockEntity shulkerBox = (ShulkerBoxBlockEntity) blockEntity.getWorld().getBlockEntity(blockEntity.getPos().down());
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.multiply(new Quaternionf().rotateXYZ(0.0f, -270.0F * shulkerBox.getAnimationProgress(tickDelta) * 0.017453292F, 0.0f));
            matrices.translate(0.0f, shulkerBox.getAnimationProgress(tickDelta) / 2.0f, 0.0f);
            matrices.translate(-0.5f, -0.5f, -0.5f);
        }
    }
}
