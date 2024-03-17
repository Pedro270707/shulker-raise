package net.pedroricardo;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class ShulkerRaiseClient implements ClientModInitializer {
	public static final net.pedroricardo.ShulkerRaiseConfig CONFIG = net.pedroricardo.ShulkerRaiseConfig.createAndLoad();

	@Override
	public void onInitializeClient() {
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).contains(PistonBlock.EXTENDED) && blockView.getBlockState(pos).get(PistonBlock.EXTENDED));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.DOORS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.BEDS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.CLIMBABLE));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.PORTALS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.WALL_CORALS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.WALL_SIGNS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isIn(BlockTags.ALL_HANGING_SIGNS));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).isOf(Blocks.TRIPWIRE_HOOK));
		ShulkerRaiseExclusions.add((blockView, pos) -> blockView.getBlockState(pos).getOutlineShape(blockView, pos).getMin(Direction.Axis.Y) > 0);
		CONFIG.subscribeToAlwaysRenderWithBlockEntity(value -> MinecraftClient.getInstance().worldRenderer.reload());
	}

	public static boolean shouldBeAffected(BlockView blockView, BlockPos pos) {
		if (blockView == null) return false;
		BlockEntity blockEntity = blockView.getBlockEntity(pos);
		BlockState stateDown = blockView.getBlockState(pos.down());
		BlockEntity blockEntityDown = blockView.getBlockEntity(pos.down());
		return stateDown.contains(ShulkerBoxBlock.FACING) && stateDown.get(ShulkerBoxBlock.FACING) == Direction.UP && blockEntityDown instanceof ShulkerBoxBlockEntity shulkerBox && (CONFIG.alwaysRenderWithBlockEntity() || shulkerBox.getAnimationStage() != ShulkerBoxBlockEntity.AnimationStage.CLOSED || (blockEntity != null && MinecraftClient.getInstance().getBlockEntityRenderDispatcher().get(blockEntity) != null)) && !ShulkerRaiseExclusions.exclude(blockView, pos);
    }
}