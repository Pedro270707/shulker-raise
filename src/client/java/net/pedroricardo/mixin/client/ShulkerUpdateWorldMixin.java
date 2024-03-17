package net.pedroricardo.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerUpdateWorldMixin {
    @Inject(method = "updateNeighborStates", at = @At("HEAD"))
    private static void shulkerraise$updateRendering(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        ShulkerBoxBlockEntity shulkerBox = (ShulkerBoxBlockEntity) world.getBlockEntity(pos);
        if (shulkerBox.hasWorld() && shulkerBox.getWorld().isClient()) {
            BlockPos upPos = shulkerBox.getPos().up();
            MinecraftClient.getInstance().worldRenderer.scheduleBlockRenders(upPos.getX(), upPos.getY(), upPos.getZ(), upPos.getX(), upPos.getY(), upPos.getZ());
        }
    }
}
