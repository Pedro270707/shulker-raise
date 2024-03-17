package net.pedroricardo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.List;

public final class ShulkerRaiseExclusions {
    private static final List<Exclusion> EXCLUSIONS = new ArrayList<>();

    public static boolean add(Exclusion exclusion) {
        return EXCLUSIONS.add(exclusion);
    }

    public static boolean exclude(BlockView blockView, BlockPos blockPos) {
        for (Exclusion exclusion : EXCLUSIONS) {
            if (exclusion.exclude(blockView, blockPos)) return true;
        }
        return false;
    }

    @FunctionalInterface
    public interface Exclusion {
        boolean exclude(BlockView blockView, BlockPos blockPos);
    }
}
