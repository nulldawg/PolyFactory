package eu.pb4.justpolydrawers.block.other;

import net.minecraft.block.BlockState;

public interface CustomBlockEntityCalls {
    default void polyfactory$setCachedState(BlockState blockState) {}
    default void polyfactory$markRemoved() {}
}
