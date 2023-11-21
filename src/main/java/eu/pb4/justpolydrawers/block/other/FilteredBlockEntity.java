package eu.pb4.justpolydrawers.block.other;

import net.minecraft.item.ItemStack;

public interface FilteredBlockEntity {
    ItemStack polyfactory$getFilter();
    void polyfactory$setFilter(ItemStack itemStack);
    boolean polyfactory$matchesFilter(ItemStack itemStack);
}
