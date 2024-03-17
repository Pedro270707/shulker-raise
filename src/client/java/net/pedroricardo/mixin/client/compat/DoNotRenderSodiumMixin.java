package net.pedroricardo.mixin.client.compat;

import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.pedroricardo.ShulkerRaiseClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(BlockRenderer.class)
public class DoNotRenderSodiumMixin {
    @Inject(method = "renderQuadList", at = @At("HEAD"), cancellable = true)
    private void shulkerraise$doNotRenderInSodium(BlockRenderContext ctx, Material material, LightPipeline lighter, ColorProvider<BlockState> colorizer, Vec3d offset, ChunkModelBuilder builder, List<BakedQuad> quads, Direction cullFace, CallbackInfo ci) {
        if (ShulkerRaiseClient.shouldBeAffected(ctx.world(), ctx.pos())) {
            ci.cancel();
        }
    }
}
