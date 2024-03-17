package net.pedroricardo.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.pedroricardo.ShulkerRaiseClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(TerrainRenderContext.class)
public class DoNotRenderMixin {
	@WrapOperation(method = "tessellateBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getModelOffset(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;", ordinal = 0))
	private Vec3d shulkerraise$renderInvisibleBlock(BlockState instance, BlockView blockView, BlockPos pos, Operation<Vec3d> original, @Local(ordinal = 0) MatrixStack matrices) {
		if (ShulkerRaiseClient.shouldBeAffected(blockView, pos)) {
			matrices.scale(0.0f, 0.0f, 0.0f);
		}
		return original.call(instance, blockView, pos);
	}
}